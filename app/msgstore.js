export class MsgStore {
    constructor (msgStoreURL) {
        this.msgStoreURL = msgStoreURL
    }

    async getSignature(msgID) {
        var req = fetch("http://" + this.msgStoreURL + "/messages/" + msgID + "/proofs")
        var resp = await (await req).json()
        if (resp == null) {
            return null
        }
        if (resp[0] == null) {
            return null
        }
        return "0x" + resp[0].proof
    }
}
