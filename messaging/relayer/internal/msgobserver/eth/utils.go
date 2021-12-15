package eth

import (
	"crypto/rand"
	"encoding/base64"
)

func randomBytes(n int) []byte {
	res := make([]byte, n)
	rand.Read(res)
	return res
}

func toBase64String(data []byte) string {
	return base64.StdEncoding.EncodeToString(data)
}
