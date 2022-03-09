import { TreeNode } from "./treenode.js"

export class CrosschainCall {
    constructor(chainID, contractType, contractAddr, method, ...params) {
        this.chainID = chainID
        this.contractType = contractType
        this.contractAddr = contractAddr
        this.method = method
        this.params = params
    }
}

export class Simulator {
    constructor(cmgr) {
        this.cmgr = cmgr
        this.abis = new Map()
        this.links = new Map()
    }

    registerABI(contractType, contractABI) {
        this.abis.set(contractType, contractABI)
    }

    registerCallLink(contractType, method, link) {
        if (!this.links.has(contractType)) {
            this.links.set(contractType, new Map())
        }
        this.links.get(contractType).set(method, link)
    }

    async simulate(chainID, contractType, contractAddr, method, ...params) {
        if (!this.links.has(contractType)) {
            return null
        }
        if (!this.links.get(contractType).has(method)) {
            return null
        }
        var link = this.links.get(contractType).get(method)
        if (!this.abis.has(contractType)) {
            return null
        }
        var contractABI = this.abis.get(contractType)
        var methodABI = searchMethod(contractABI, method)
        var callData = new Web3().eth.abi.encodeFunctionCall(methodABI, params)
        var head = new TreeNode(chainID, contractAddr, callData)
        var crosschainCalls = await link(this.cmgr, chainID, contractAddr, params)
        for (var crosschainCall of crosschainCalls) {
            // TODO: Need to check error.
            var subChainID = crosschainCall.chainID
            var subContractType = crosschainCall.contractType
            var subContractAddr = crosschainCall.contractAddr
            var subMethod = crosschainCall.method
            var subParams = crosschainCall.params
            var child = await this.simulate(subChainID, subContractType, subContractAddr, subMethod, subParams)
            if (child == null) {
                var subABI = this.abis.get(subContractType)
                var subMethodABI = searchMethod(subABI, subMethod)
                var subCallData = new Web3().eth.abi.encodeFunctionCall(subMethodABI, subParams)
                child = new TreeNode(subChainID, subContractAddr, subCallData)
            }
            head.addChild(child)
        }
        return head
    }
}

function searchMethod(abi, name) {
    for (var method of abi) {
        if  (method.name == name) {
            return method
        }
    }
    return null
}
