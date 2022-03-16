export class ChainAPManager {
    constructor() {
        this.chainAPs = new Map()
    }

    registerChainAP(chainID, web3) {
        this.chainAPs.set(chainID, web3)
    }

    async chainAP(chainID) {
        return this.chainAPs.get(chainID)
    }
}
