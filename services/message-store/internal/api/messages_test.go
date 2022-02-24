package api

import (
	"bytes"
	"encoding/json"
	"fmt"
	"github.com/consensys/gpact/messaging/message-store/internal/logging"
	v1 "github.com/consensys/gpact/services/relayer/pkg/messages/v1"
	"github.com/gin-gonic/gin"
	badger "github.com/ipfs/go-ds-badger2"
	"github.com/stretchr/testify/assert"
	"io/ioutil"
	"net/http"
	"net/http/httptest"
	"os"
	"testing"
)

func TestMessageStoreApi_UpsertMessageHandler(t *testing.T) {
	fixMsg1WithMoreProof := fixMsg1
	fixMsg1WithMoreProof.Proofs = append(fixMsg1.Proofs, fixProofSet2...)

	fixMsg2WithDifferentDetails := fixMsg2
	fixMsg2WithDifferentDetails.Payload = "different payload"

	testCases := map[string]struct {
		endpoint         string
		updatePayloads   []v1.Message
		updateRespCodes  []int
		postUpdateStates []v1.Message
	}{
		"Add-New-Message": {"/messages", []v1.Message{fixMsg1}, []int{201}, []v1.Message{fixMsg1}},
		"Add-New-Message-With-ID-Path-Param": {fmt.Sprintf("/messages/%s", fixMsg2.ID), []v1.Message{fixMsg2},
			[]int{201}, []v1.Message{fixMsg2}},
		"Update-Message-With-Same-Message": {"/messages", []v1.Message{fixMsg2, fixMsg2}, []int{201, 200},
			[]v1.Message{fixMsg2, fixMsg2}},
		"Update-Message-With-Message-Containing-Additional-Proof-Elements": {"/messages", []v1.Message{fixMsg1,
			fixMsg1WithMoreProof}, []int{201,
			200}, []v1.Message{fixMsg1, fixMsg1WithMoreProof}},
		"Update-Message-With-Mismatch-In-Immutable-Details": {"/messages", []v1.Message{fixMsg2,
			fixMsg2WithDifferentDetails}, []int{201, 400}, []v1.Message{fixMsg2, fixMsg2}},
		"Mismatch-Between-PathParam-And-Body-IDs": {"/messages/mismatched-id", []v1.Message{fixMsg1}, []int{400},
			[]v1.Message{}},
	}

	for testName, testCase := range testCases {
		ds, dsClose := newDS(t)
		router := setupTestRouter(ds)
		logging.Info("testing scenario :%s", testName)
		for i, testPayload := range testCase.updatePayloads {
			// add/update message
			msg1Bytes, err := json.Marshal(testPayload)
			assert.Nil(t, err)
			respRec := httptest.NewRecorder()
			req, err := http.NewRequest("PUT", testCase.endpoint, bytes.NewBuffer(msg1Bytes))
			router.ServeHTTP(respRec, req)
			assert.Nil(t, err)
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
	ds, dsClose := newDS(t)
	defer dsClose()
	router := setupTestRouter(ds)

	msg1Bytes, err := json.Marshal(fixMsg1)
	assert.Nil(t, err)

	// add new message with ID in path parameter
	respRec := httptest.NewRecorder()
	endpoint := fmt.Sprintf("/messages/%s", fixMsg1.ID)
	req, err := http.NewRequest("PUT", endpoint, bytes.NewBuffer(msg1Bytes))
	router.ServeHTTP(respRec, req)
	assert.Nil(t, err)
	assert.Equal(t, 201, respRec.Code)

	// record new proofs for message
	proofBytes, err := json.Marshal(fixProofSet2)
	assert.Nil(t, err)
	respRec2 := httptest.NewRecorder()
	endpoint2 := fmt.Sprintf("/messages/%s/proofs", fixMsg1.ID)
	req2, err := http.NewRequest("PUT", endpoint2, bytes.NewBuffer(proofBytes))
	router.ServeHTTP(respRec2, req2)
	assert.Nil(t, err)
	assert.Equal(t, 201, respRec2.Code)

	savedMsgStr := requestGETMessage(t, router, fixMsg1.ID)
	var savedMsg v1.Message
	err = json.Unmarshal([]byte(savedMsgStr), &savedMsg)
	assert.Nil(t, err)
	assert.Len(t, savedMsg.Proofs, len(fixProofSet2)+len(fixProofSet1), "proof set not updated correctly")
	assert.ElementsMatch(t, savedMsg.Proofs, append(fixProofSet1, fixProofSet2...), "proof set not updated correctly")

	// TODO: test more scenarios
}

func TestMessageStoreApi_GetMessageHandler(t *testing.T) {
	ds, dsClose := newDS(t)
	defer dsClose()
	router := setupTestRouter(ds)

	msg1Bytes, err := json.Marshal(fixMsg1)
	endpoint := fmt.Sprintf("/messages/%s", fixMsg1.ID)

	// add message
	respRec := httptest.NewRecorder()
	req, err := http.NewRequest("PUT", endpoint, bytes.NewBuffer(msg1Bytes))
	router.ServeHTTP(respRec, req)
	assert.Nil(t, err)
	assert.Equal(t, 201, respRec.Code)

	// get message with id
	msgSaved := requestGETMessage(t, router, fixMsg1.ID)
	assert.Equal(t, string(msg1Bytes), msgSaved)

	// TODO: test more scenarios
}

func TestMessageStoreApi_GetMessageProofsHandler(t *testing.T) {
	ds, dsClose := newDS(t)
	defer dsClose()
	router := setupTestRouter(ds)

	msg1Bytes, err := json.Marshal(fixMsg1)
	endpoint := fmt.Sprintf("/messages/%s", fixMsg1.ID)

	// add message
	respRec := httptest.NewRecorder()
	req, err := http.NewRequest("PUT", endpoint, bytes.NewBuffer(msg1Bytes))
	router.ServeHTTP(respRec, req)
	assert.Nil(t, err)
	assert.Equal(t, 201, respRec.Code)

	// get message with id
	fixProofs, err := json.Marshal(fixMsg1.Proofs)
	assert.Nil(t, err)
	reqProofs := requestGETMessageProofs(t, router, fixMsg1.ID)
	assert.Equal(t, string(fixProofs), reqProofs)

	// TODO: test more scenarios
}

func requestGETMessage(t *testing.T, router *gin.Engine, id string) string {
	endpoint := fmt.Sprintf("/messages/%s", id)
	return requestGETMessageDetails(t, router, endpoint, id)
}

func requestGETMessageProofs(t *testing.T, router *gin.Engine, id string) string {
	endpoint := fmt.Sprintf("/messages/%s/proofs", id)
	return requestGETMessageDetails(t, router, endpoint, id)
}

func requestGETMessageDetails(t *testing.T, router *gin.Engine, endpoint string, id string) string {
	respRec2 := httptest.NewRecorder()
	reqFail1, err := http.NewRequest("GET", endpoint, nil)
	router.ServeHTTP(respRec2, reqFail1)
	assert.Nil(t, err)
	assert.Equal(t, 200, respRec2.Code)
	return respRec2.Body.String()
}

func newDS(t *testing.T) (*badger.Datastore, func()) {
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

var fixProofSet1 = []v1.Proof{v1.Proof{Proof: "proof-payload-1", ProofType: "proof-type-1", Created: 11}}
var fixProofSet2 = []v1.Proof{v1.Proof{Proof: "proof-payload-2", ProofType: "proof-type-2", Created: 12}}
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
