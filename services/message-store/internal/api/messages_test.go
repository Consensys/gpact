package api

import (
	"bytes"
	"encoding/json"
	"fmt"
	v1 "github.com/consensys/gpact/messaging/message-store/internal/messages/v1"
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
	ds, dsClose := newDS(t)
	defer dsClose()

	router := setupTestRouter(ds)

	// adding a new message
	msg1Bytes, err := json.Marshal(fixMsg1)
	assert.Nil(t, err)
	respRec := httptest.NewRecorder()
	req, err := http.NewRequest("PUT", "/messages", bytes.NewBuffer(msg1Bytes))
	router.ServeHTTP(respRec, req)
	assert.Nil(t, err)
	assert.Equal(t, 201, respRec.Code)

	// updating a message
	fixMsg12 := fixMsg1
	fixMsg12.Proofs = fixProofSet2
	msg2Bytes, err := json.Marshal(fixMsg12)
	respRec2 := httptest.NewRecorder()
	req2, err := http.NewRequest("PUT", "/messages", bytes.NewBuffer(msg2Bytes))
	router.ServeHTTP(respRec2, req2)
	assert.Nil(t, err)
	assert.Equal(t, 200, respRec2.Code)

	savedMsgStr := requestGETMessage(t, router, fixMsg12.ID)
	var savedMsg v1.Message
	err = json.Unmarshal([]byte(savedMsgStr), &savedMsg)
	assert.Nil(t, err)
	assert.Len(t, savedMsg.Proofs, len(fixProofSet2)+len(fixProofSet1), "proof set not updated correctly")
	assert.ElementsMatch(t, savedMsg.Proofs, append(fixProofSet1, fixProofSet2...), "proof set not updated correctly")

	// TODO: test more scenarios
}

func TestMessageStoreApi_UpsertMessageHandler_WithIDParam(t *testing.T) {
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

	// Bad Request Scenario: request ID in path parameter does not match ID in message body
	respRecFail1 := httptest.NewRecorder()
	endpointDiffID := fmt.Sprintf("/messages/%s", fixMsg2.ID)
	reqFail1, err := http.NewRequest("PUT", endpointDiffID, bytes.NewBuffer(msg1Bytes))
	router.ServeHTTP(respRecFail1, reqFail1)
	assert.Nil(t, err)
	assert.Equal(t, 400, respRecFail1.Code)
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
	ID:          "msg-0001",
	Timestamp:   2384923489234,
	MsgType:     v1.MessageType,
	Version:     v1.Version,
	Destination: v1.ApplicationAddress{NetworkID: "network-001"},
	Source:      v1.ApplicationAddress{NetworkID: "network-002"},
	Proofs:      fixProofSet1,
}

var fixMsg2 = v1.Message{
	ID:          "msg-0002",
	Timestamp:   2384923489234,
	MsgType:     v1.MessageType,
	Version:     v1.Version,
	Destination: v1.ApplicationAddress{NetworkID: "network-002"},
	Source:      v1.ApplicationAddress{NetworkID: "network-003"},
	Proofs:      fixProofSet2,
}
