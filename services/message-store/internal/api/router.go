package api

import (
	"github.com/gin-gonic/gin"
	"net/http"
)

func SetupRouter(r *gin.Engine, m *MessageStoreApi) {
	r.Use(gin.Logger())

	r.Use(gin.CustomRecovery(func(c *gin.Context, recovered interface{}) {
		if err, ok := recovered.(string); ok {
			statusServerError(c, err)
		}
		c.AbortWithStatus(http.StatusInternalServerError)
	}))

	r.GET("/messages/:id", m.GetMessageHandler)
	r.GET("/messages/:id/proofs", m.GetMessageProofsHandler)
	r.PUT("/messages", m.UpsertMessageHandler)
	r.PUT("/messages/:id", m.UpsertMessageHandler)
	r.PUT("/messages/:id/proofs", m.RecordProofsHandler)
}
