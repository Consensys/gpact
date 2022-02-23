package api

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"net/http"
)

func statusMessageNotFound(c *gin.Context, id string) {
	setErrorResponse(c, http.StatusNotFound, fmt.Sprintf("message with id '%s' not found", id))
}

func statusServerError(c *gin.Context, err interface{}) {
	setErrorResponse(c, http.StatusInternalServerError, err)
}

func statusOk(c *gin.Context, msg string) {
	setResponse(c, http.StatusOK, msg)
}

func statusCreated(c *gin.Context, msg string) {
	setResponse(c, http.StatusCreated, msg)
}

func statusBadRequest(c *gin.Context, msg string) {
	c.JSON(http.StatusBadRequest, gin.H{"error": msg})
}

func setResponse(c *gin.Context, status int, msg string) {
	c.JSON(status, gin.H{"message": msg})
}

func setErrorResponse(c *gin.Context, status int, err interface{}) {
	c.JSON(status, gin.H{"error": err})
}
