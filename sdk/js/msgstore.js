import fetch from "node-fetch";

export class MsgStore {
  constructor(msgStoreURL) {
    this.msgStoreURL = msgStoreURL;
  }

  async getSignature(msgID) {
    let msgIDLow = msgID.toLowerCase();
    const req = fetch(`http://${this.msgStoreURL}/messages/${msgIDLow}/proofs`);
    const resp = await (await req).json();
    if (resp == null) {
      return null;
    }
    if (resp[0] == null) {
      return null;
    }
    return `0x${resp[0].proof}`;
  }
}
