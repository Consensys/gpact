package eth

import (
	"encoding/base64"

	"github.com/consensys/gpact/messaging/relayer/internal/crypto"
)

func randomBytes(n int) []byte {
	res := make([]byte, n)
	crypto.GetPrivatePRNG().ReadBytes(res)
	return res
}

func toBase64String(data []byte) string {
	return base64.StdEncoding.EncodeToString(data)
}
