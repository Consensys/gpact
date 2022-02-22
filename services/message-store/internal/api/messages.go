package api

import (
	"context"
	"encoding/json"
	"fmt"
	v1 "github.com/consensys/gpact/services/relayer/pkg/messages/v1"
	"github.com/gin-gonic/gin"
	"github.com/ipfs/go-datastore"
	"github.com/ipfs/go-ds-badger2"
	"net/http"
)

type MessageStoreApi struct {
	DataStore *badger.Datastore
}

// UpsertMessageHandler is a handler for PUT /messages and PUT /messages:id endpoints.
// The method adds a new message to a datastore, if it does not already exist.
// If the message already exists, it updates the proof set of the message in the datastore
// to include new proof elements from the message submitted.
// Parameters:
// - Path (optional): Message ID
// Responses:
// - HTTP 201: New message added to datastore
// - HTTP 200: Existing message updated
// - HTTP 400: Invalid parameters or payload submitted by client
func (mApi *MessageStoreApi) UpsertMessageHandler(c *gin.Context) {
	var message *v1.Message
	err := c.BindJSON(&message)
	if err != nil {
		return
	}

	if !isValidId(message.ID) {
		statusBadRequest(c, fmt.Sprintf("Message id '%s' is not valid", message.ID))
		return
	}

	paramId := c.Param("id")
	if len(paramId) > 0 && paramId != message.ID {
		statusBadRequest(c, fmt.Sprintf("Message id provided in the path parameter, '%s', "+
			"does not match id in the message body, '%s'", paramId, message.ID))
		return
	}

	tx, err := mApi.DataStore.NewTransaction(c, false)
	if err != nil {
		statusServerError(c, err)
		tx.Discard(c)
		return
	}

	created, err := mApi.upsertMessage(c, tx, message)
	if err != nil {
		statusServerError(c, err)
		tx.Discard(c)
		return
	}

	err = tx.Commit(c)
	if err != nil {
		statusServerError(c, err)
		return
	}

	if created {
		statusCreated(c, "Message successfully added to the data store")
	} else {
		statusOk(c, "Message successfully updated")
	}
}

// RecordProofsHandler The endpoint adds the given proof elements to the set of proofs that has already been recorded
// for a message. If specific elements within the array of proof submitted have already been stored,
// they are ignored,  if not, they are added to the datastore.
// Parameters:
// - Path: Message ID
// - Body: Array of Proof elements
// Responses:
// - HTTP 201: One or more new proof elements in the request body, were added to the existing proof set for a message.
// - HTTP 200: All proof elements in the request body were already a part of the recorded proof set for a message,
//   		   so no updates needed to be performed.
// - HTTP 400: Invalid parameters or payload submitted by client
func (mApi *MessageStoreApi) RecordProofsHandler(c *gin.Context) {
	var newProofs []v1.Proof
	err := c.BindJSON(&newProofs)
	if err != nil {
		return
	}

	paramId := c.Param("id")
	if !isValidId(paramId) {
		statusBadRequest(c, fmt.Sprintf("Message id '%s' is not valid", paramId))
		return
	}

	if !mApi.messageExists(c, paramId) {
		statusMessageNotFound(c, paramId)
		return
	}

	tx, err := mApi.DataStore.NewTransaction(c, false)
	if err != nil {
		statusServerError(c, err)
		tx.Discard(c)
		return
	}

	updated, err := mApi.updateMessageProofSet(c, tx, datastore.NewKey(paramId), newProofs)
	if err != nil {
		statusServerError(c, err)
		tx.Discard(c)
		return
	}

	err = tx.Commit(c)
	if err != nil {
		statusServerError(c, err)
		return
	}

	if updated {
		statusCreated(c, "One or more proof elements submitted were successfully added to message's proof set")
	} else {
		statusOk(c, "All proof elements submitted are already a part of the message's proof set")
	}
}

// GetMessageHandler retrieves a message with the given ID if it exists in the datastore.
// Parameters:
//  - Path: Message ID
// Response Status:
// - HTTP 200: Message successfully retrieved
// - HTTP 400: Invalid message ID provided
// - HTTP 404: Message could not be found
func (mApi *MessageStoreApi) GetMessageHandler(c *gin.Context) {
	id := c.Param("id")
	if !isValidId(id) {
		statusBadRequest(c, fmt.Sprintf("message id '%s' is not valid", id))
		return
	}
	mApi.getMessageDetails(c, id, nil)
}

// GetMessageProofsHandler retrieves the proof set for a message  with the given ID if it exists in the datastore.
// Parameters:
//  - Path: Message ID
// Response Status:
// - HTTP 200: Proof set for message successfully retrieved
// - HTTP 400: Invalid message ID provided
// - HTTP 404: Message could not be found
func (mApi *MessageStoreApi) GetMessageProofsHandler(c *gin.Context) {
	id := c.Param("id")
	if !isValidId(id) {
		statusBadRequest(c, fmt.Sprintf("Message id '%s' is not valid", id))
		return
	}
	mApi.getMessageDetails(c, id, func(message *v1.Message) interface{} { return message.Proofs })
}

func (mApi *MessageStoreApi) getMessageDetails(c *gin.Context, id string,
	attribExtractor func(message *v1.Message) interface{}) {
	message, err := mApi.queryMessageById(c, datastore.NewKey(id), mApi.DataStore.Get)
	if err == datastore.ErrNotFound {
		statusMessageNotFound(c, id)
		return
	} else if err != nil {
		statusServerError(c, err)
		return
	}

	var msgDetails interface{} = message
	if attribExtractor != nil {
		msgDetails = attribExtractor(message)
	}

	c.JSON(http.StatusOK, msgDetails)
}

func isValidId(id string) bool {
	// TODO: more validation checks based on message id format
	return len(id) > 0
}

// queryMessageById queries the datastore for a message with the given ID
func (mApi *MessageStoreApi) queryMessageById(c *gin.Context, id datastore.Key, dsGetter func(c context.Context,
	key datastore.Key) ([]byte, error)) (*v1.Message, error) {
	msgBytes, err := dsGetter(c, id)
	if err != nil {
		return nil, err
	}

	var msg v1.Message
	err = json.Unmarshal(msgBytes, &msg)
	if err != nil {
		return nil, err
	}

	return &msg, err
}

func (mApi *MessageStoreApi) messageExists(c *gin.Context, id string) bool {
	has, err := mApi.DataStore.Has(c, datastore.NewKey(id))
	if err != nil {
		return false
	}
	return has
}

// upsertMessage create or updates a message.
// Adds a message to the datastore if it does not already exist.
// If it exists, the proof set of the message in the datastore is updated
// with unique proof elements from the new message submitted.
func (mApi *MessageStoreApi) upsertMessage(c *gin.Context, tx datastore.Txn, newMessage *v1.Message) (bool, error) {
	id := datastore.NewKey(newMessage.ID)
	exists, err := tx.Has(c, id)
	if err != nil {
		return false, err
	}
	if exists {
		// TODO: validate that all other fields of the two messages match as a sanity check
		_, err = mApi.updateMessageProofSet(c, tx, id, newMessage.Proofs)
		return false, err
	} else {
		return true, mApi.addMessage(c, tx, id, newMessage)
	}
}

// updateMessageProofSet adds new proof elements of the message provided as argument,
// to the proof set of the message in the datastore.
func (mApi *MessageStoreApi) updateMessageProofSet(c *gin.Context, tx datastore.Txn, msgId datastore.Key,
	newProofSet []v1.Proof) (bool, error) {
	existing, err := mApi.queryMessageById(c, msgId, tx.Get)
	if err != nil {
		return false, err
	}
	oldProofCount := len(existing.Proofs)
	existing.Proofs = aggregateProofSets(existing.Proofs, newProofSet)
	updatedMsg, err := json.Marshal(existing)
	if err != nil {
		return false, err
	}

	err = tx.Put(c, msgId, updatedMsg)
	if err != nil {
		return false, err
	}
	return oldProofCount < len(existing.Proofs), nil
}

func (mApi *MessageStoreApi) addMessage(c *gin.Context, tx datastore.Txn, msgId datastore.Key,
	newMessage *v1.Message) error {
	m, err := json.Marshal(newMessage)
	if err != nil {
		return err
	}
	return tx.Put(c, msgId, m)
}

func aggregateProofSets(existing []v1.Proof, newProofs []v1.Proof) []v1.Proof {
	var updated = existing
	// TODO: consider optimising
	for _, v := range newProofs {
		if !containsProof(updated, v) {
			updated = append(updated, v)
		}
	}
	return updated
}

func containsProof(proofSet []v1.Proof, proof v1.Proof) bool {
	for _, p := range proofSet {
		if p == proof {
			return true
		}
	}
	return false
}

func NewMessageStoreService(dsPath string, options *badger.Options) (*MessageStoreApi, error) {
	ds, err := badger.NewDatastore(dsPath, options)
	if err != nil {
		return nil, err
	}
	return &MessageStoreApi{DataStore: ds}, nil
}
