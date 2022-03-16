package api

import (
	"bytes"
	"encoding/json"
	"fmt"
	"io/ioutil"
	"net/http"
	"net/http/httptest"
	"os"
	"testing"

	"github.com/consensys/gpact/messaging/message-store/internal/logging"
	v1 "github.com/consensys/gpact/services/relayer/pkg/messages/v1"
	"github.com/gin-gonic/gin"
	badger "github.com/ipfs/go-ds-badger"
	"github.com/stretchr/testify/assert"
)

func TestMessageStoreApi_UpsertMessageHandler(t *testing.T) {
	fixMsg1WithMoreProof := fixMsg1
	fixMsg1WithMoreProof.Proofs = append(fixMsg1.Proofs, fixProofSet2...)

	fixMsg2WithDifferentDetails := fixMsg2
	fixMsg2WithDifferentDetails.Payload = "different payload"

	testCases := map[string]struct {
		updateEndpoint   string
		updatePayloads   []v1.Message
		updateRespCodes  []int
		postUpdateStates []v1.Message
	}{
		"Add-New-Message": {"/messages", []v1.Message{fixMsg1}, []int{201}, []v1.Message{fixMsg1}},
		"Add-New-Message-with-ID-Path-Param": {fmt.Sprintf("/messages/%s", fixMsg2.ID), []v1.Message{fixMsg2},
			[]int{201}, []v1.Message{fixMsg2}},
		"Add-New-Message-with-Invalid-ID": {"/messages", []v1.Message{fixMsgInvalidID}, []int{400},
			[]v1.Message{}},
		"Update-Message-with-Same-Message": {"/messages", []v1.Message{fixMsg2, fixMsg2}, []int{201, 200},
			[]v1.Message{fixMsg2, fixMsg2}},
		"Update-Message-with-Message-Containing-Additional-Proof-Elements": {"/messages", []v1.Message{fixMsg1,
			fixMsg1WithMoreProof}, []int{201,
			200}, []v1.Message{fixMsg1, fixMsg1WithMoreProof}},
		"Update-Message-with-Mismatch-In-Immutable-Details": {"/messages", []v1.Message{fixMsg2,
			fixMsg2WithDifferentDetails}, []int{201, 400}, []v1.Message{fixMsg2, fixMsg2}},
		"Mismatch-Between-PathParam-And-Body-IDs": {"/messages/mismatched-id", []v1.Message{fixMsg1}, []int{400},
			[]v1.Message{}},
	}

	for testName, testCase := range testCases {
		ds, dsClose := createNewDS(t)
		router := setupTestRouter(ds)
		logging.Info("testing scenario: %s", testName)
		for i, testPayload := range testCase.updatePayloads {
			// add or update message
			msgBytes, err := json.Marshal(testPayload)
			assert.Nil(t, err)
			respRec := httptest.NewRecorder()
			req, err := http.NewRequest("PUT", testCase.updateEndpoint, bytes.NewBuffer(msgBytes))
			assert.Nil(t, err)
			router.ServeHTTP(respRec, req)
			assert.Equal(t, testCase.updateRespCodes[i], respRec.Code)

			if len(testCase.postUpdateStates) > 0 {
				// fetch message and check payload matches expected
				savedMsgStr := requestGETMessage(t, router, testPayload.ID)
				var savedMsg v1.Message
				err = json.Unmarshal([]byte(savedMsgStr), &savedMsg)
				assert.Nil(t, err)
				assert.Equal(t, testCase.postUpdateStates[i], savedMsg)
			}
		}
		dsClose()
	}
}

func TestMessageStoreApi_RecordProofsHandler(t *testing.T) {
	testCases := map[string]struct {
		preUpdateMsgCreate  *v1.Message
		proofsEndpoint      string
		proofsToRecord      []v1.Proof
		proofRecordResponse int
		postUpdateProofSet  []v1.Proof
	}{
		"Update-Message-with-Existing-Proof-Set": {&fixMsg1, fmt.Sprintf("/messages/%s/proofs", fixMsg1.ID),
			fixMsg1.Proofs, 200, fixMsg1.Proofs},
		"Update-Message-with-Distinct-Proof-Elements": {&fixMsg1, fmt.Sprintf("/messages/%s/proofs", fixMsg1.ID),
			fixMsg2.Proofs, 201, append(fixMsg1.Proofs, fixMsg2.Proofs...)},
		"Update-Message-with-Some-Overlapping-Proof-Elements": {&fixMsg1, fmt.Sprintf("/messages/%s/proofs",
			fixMsg1.ID), append(fixMsg1.Proofs, fixMsg2.Proofs...), 201, append(fixMsg1.Proofs, fixMsg2.Proofs...)},
		"Update-for-Non-Existent-Message": {nil, fmt.Sprintf("/messages/%s/proofs", fixMsg1.ID), fixMsg1.Proofs, 404,
			[]v1.Proof{}},
		"Update-using-Invalid-Message-Id": {nil, "/messages/invalid-message-id/proofs", fixMsg1.Proofs,
			400, []v1.Proof{}},
	}
	for testName, testCase := range testCases {
		ds, dsClose := createNewDS(t)
		router := setupTestRouter(ds)
		logging.Info("testing scenario: %s", testName)

		if testCase.preUpdateMsgCreate != nil {
			// add new message with ID in path parameter
			msgBytes, err := json.Marshal(testCase.preUpdateMsgCreate)
			assert.Nil(t, err)
			respRec1 := httptest.NewRecorder()
			req1, err := http.NewRequest("PUT", "/messages", bytes.NewBuffer(msgBytes))
			assert.Nil(t, err)
			router.ServeHTTP(respRec1, req1)
			assert.Equal(t, 201, respRec1.Code)
		}

		// record new proofs for message
		proofBytes, err := json.Marshal(testCase.proofsToRecord)
		assert.Nil(t, err)
		respRec2 := httptest.NewRecorder()
		req2, err := http.NewRequest("PUT", testCase.proofsEndpoint, bytes.NewBuffer(proofBytes))
		assert.Nil(t, err)
		router.ServeHTTP(respRec2, req2)
		assert.Equal(t, testCase.proofRecordResponse, respRec2.Code)

		if len(testCase.postUpdateProofSet) > 0 {
			savedMsgStr := requestGETMessage(t, router, testCase.preUpdateMsgCreate.ID)
			var savedMsg v1.Message
			err = json.Unmarshal([]byte(savedMsgStr), &savedMsg)
			assert.Nil(t, err)
			assert.ElementsMatch(t, savedMsg.Proofs, testCase.postUpdateProofSet, "proof set not updated correctly")
		}
		dsClose()
	}
}

func TestMessageStoreApi_GetMessageHandler(t *testing.T) {
	testCases := map[string]struct {
		preQueryMsgCreate *v1.Message
		queryId           string
		queryResponseCode int
	}{
		"Fetch-Existing-Message":        {&fixMsg1, fixMsg1.ID, 200},
		"Fetch-Non-Existing-Message":    {nil, fixMsg1.ID, 404},
		"Fetch-Request-with-Invalid-ID": {nil, fixMsgInvalidID.ID, 400},
	}

	for testName, testCase := range testCases {
		ds, dsClose := createNewDS(t)
		router := setupTestRouter(ds)
		logging.Info("testing scenario: %s", testName)

		endpoint := fmt.Sprintf("/messages/%s", testCase.queryId)

		if testCase.preQueryMsgCreate != nil {
			// add message
			msgBytes, err := json.Marshal(testCase.preQueryMsgCreate)
			assert.Nil(t, err)
			respRec1 := httptest.NewRecorder()
			req1, err := http.NewRequest("PUT", endpoint, bytes.NewBuffer(msgBytes))
			assert.Nil(t, err)
			router.ServeHTTP(respRec1, req1)
			assert.Equal(t, 201, respRec1.Code)
		}

		// get message with id
		respRec2 := httptest.NewRecorder()
		req2, err := http.NewRequest("GET", endpoint, nil)
		assert.Nil(t, err)
		router.ServeHTTP(respRec2, req2)
		assert.Equal(t, testCase.queryResponseCode, respRec2.Code)

		var savedMsg v1.Message
		err = json.Unmarshal([]byte(respRec2.Body.String()), &savedMsg)
		assert.Nil(t, err)
		dsClose()
	}
}

func TestMessageStoreApi_GetMessageProofsHandler(t *testing.T) {
	testCases := map[string]struct {
		preQueryMsgCreate *v1.Message
		queryId           string
		queryResponse     []v1.Proof
		queryResponseCode int
	}{
		"Fetch-Proof-for-Existing-Message":     {&fixMsg1, fixMsg1.ID, fixMsg1.Proofs, 200},
		"Fetch-Proof-for-Non-Existing-Message": {nil, fixMsg1.ID, nil, 404},
		"Fetch-Proof-using-Invalid-ID":         {nil, fixMsgInvalidID.ID, nil, 400},
	}

	for testName, testCase := range testCases {
		ds, dsClose := createNewDS(t)
		router := setupTestRouter(ds)
		logging.Info("testing scenario: %s", testName)

		if testCase.preQueryMsgCreate != nil {
			// add message
			msgBytes, err := json.Marshal(testCase.preQueryMsgCreate)
			assert.Nil(t, err)
			respRec := httptest.NewRecorder()
			req, err := http.NewRequest("PUT", "/messages", bytes.NewBuffer(msgBytes))
			assert.Nil(t, err)
			router.ServeHTTP(respRec, req)
			assert.Equal(t, 201, respRec.Code)
		}
		// get message proof with id
		respRec2 := httptest.NewRecorder()
		req2, err := http.NewRequest("GET", fmt.Sprintf("/messages/%s/proofs", testCase.queryId), nil)
		assert.Nil(t, err)
		router.ServeHTTP(respRec2, req2)
		assert.Equal(t, testCase.queryResponseCode, respRec2.Code)
		if len(testCase.queryResponse) > 0 {
			var savedProofs []v1.Proof
			err = json.Unmarshal([]byte(respRec2.Body.String()), &savedProofs)
			assert.Nil(t, err)
		}
		dsClose()
	}
}

func requestGETMessage(t *testing.T, router *gin.Engine, id string) string {
	endpoint := fmt.Sprintf("/messages/%s", id)
	return requestGETMessageDetails(t, router, endpoint)
}

func requestGETMessageProofs(t *testing.T, router *gin.Engine, id string) string {
	endpoint := fmt.Sprintf("/messages/%s/proofs", id)
	return requestGETMessageDetails(t, router, endpoint)
}

func requestGETMessageDetails(t *testing.T, router *gin.Engine, endpoint string) string {
	resp := httptest.NewRecorder()
	req, err := http.NewRequest("GET", endpoint, nil)
	router.ServeHTTP(resp, req)
	assert.Nil(t, err)
	assert.Equal(t, 200, resp.Code)
	return resp.Body.String()
}

func createNewDS(t *testing.T) (*badger.Datastore, func()) {
	path, err := ioutil.TempDir(os.TempDir(), "testing_badger_")
	if err != nil {
		t.Fatal(err)
	}

	d, err := badger.NewDatastore(path, nil)
	if err != nil {
		t.Fatal(err)
	}
	return d, func() {
		d.Close()
		os.RemoveAll(path)
	}
}

func setupTestRouter(ds *badger.Datastore) *gin.Engine {
	mApi := MessageStoreApi{DataStore: ds}
	router := gin.Default()
	SetupRouter(router, &mApi)
	return router
}

var fixProofSet1 = []v1.Proof{{Proof: "proof-payload-1", ProofType: "proof-type-1", Created: 11}}
var fixProofSet2 = []v1.Proof{{Proof: "proof-payload-2", ProofType: "proof-type-2", Created: 12}}
var fixMsg1 = v1.Message{
	ID:          "chain001-0xed617B1555080073cE6B4DEe31fcd68B7a0c3703-10101984-4-1",
	Timestamp:   2384923489234,
	MsgType:     v1.MessageType,
	Version:     v1.Version,
	Destination: v1.ApplicationAddress{NetworkID: "network-001"},
	Source:      v1.ApplicationAddress{NetworkID: "network-002"},
	Proofs:      fixProofSet1,
}

var fixMsg2 = v1.Message{
	ID:          "chain001-0xed617B1555080073cE6B4DEe31fcd68B7a0c3703-10101984-4-2",
	Timestamp:   2384923489234,
	MsgType:     v1.MessageType,
	Version:     v1.Version,
	Destination: v1.ApplicationAddress{NetworkID: "network-002"},
	Source:      v1.ApplicationAddress{NetworkID: "network-003"},
	Proofs:      fixProofSet2,
}

var fixMsgInvalidID = v1.Message{
	ID:          "invalid-message-id",
	Timestamp:   2384923489234,
	MsgType:     v1.MessageType,
	Version:     v1.Version,
	Destination: v1.ApplicationAddress{NetworkID: "network-002"},
	Source:      v1.ApplicationAddress{NetworkID: "network-003"},
	Proofs:      fixProofSet2,
}
