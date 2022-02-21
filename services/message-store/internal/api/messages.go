package api

import (
	"context"
	"encoding/json"
	"fmt"
	v1 "github.com/consensys/gpact/messaging/message-store/internal/messages/v1"
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
		statusBadRequest(c, fmt.Sprintf("message id '%s' is not valid", message.ID))
		return
	}

	paramId := c.Param("id")
	if len(paramId) > 0 && paramId != message.ID {
		statusBadRequest(c, fmt.Sprintf("message id provided in the path parameter, '%s', "+
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
		statusCreated(c, "message successfully created")
	} else {
		statusOk(c, "message successfully updated")
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
		statusBadRequest(c, fmt.Sprintf("message id '%s' is not valid", id))
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
		return false, mApi.updateMessageProofSet(c, tx, id, newMessage)
	} else {
		return true, mApi.addMessage(c, tx, id, newMessage)
	}
}

// updateMessageProofSet adds new proof elements of the message provided as argument,
// to the proof set of the message in the datastore.
func (mApi *MessageStoreApi) updateMessageProofSet(c *gin.Context, tx datastore.Txn, msgId datastore.Key,
	newMessage *v1.Message) error {
	existing, err := mApi.queryMessageById(c, msgId, tx.Get)
	if err != nil {
		return err
	}

	existing.Proofs = aggregateProofSets(existing.Proofs, newMessage.Proofs)
	updatedMsg, err := json.Marshal(existing)
	if err != nil {
		return err
	}

	err = tx.Put(c, msgId, updatedMsg)
	if err != nil {
		return err
	}
	return nil
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
