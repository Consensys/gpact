import { default as Web3ABI } from "web3-eth-abi";
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
        let link = this.links.get(contractType).get(method)
        if (!this.abis.has(contractType)) {
            return null
        }
        let contractABI = this.abis.get(contractType)
        let methodABI = searchMethod(contractABI, method)
        let callData = Web3ABI.encodeFunctionCall(methodABI, params)
        let head = new TreeNode(chainID, contractAddr, callData)
        let crosschainCalls = await link(this.cmgr, chainID, contractAddr, params)
        for (let crosschainCall of crosschainCalls) {
            // TODO: Need to check error.
            let subChainID = crosschainCall.chainID
            let subContractType = crosschainCall.contractType
            let subContractAddr = crosschainCall.contractAddr
            let subMethod = crosschainCall.method
            let subParams = crosschainCall.params
            let child = await this.simulate(subChainID, subContractType, subContractAddr, subMethod, subParams)
            if (child == null) {
                let subABI = this.abis.get(subContractType)
                let subMethodABI = searchMethod(subABI, subMethod)
                let subCallData = Web3ABI.encodeFunctionCall(subMethodABI, subParams)
                child = new TreeNode(subChainID, subContractAddr, subCallData)
            }
            head.addChild(child)
        }
        return head
    }
}

function searchMethod(abi, name) {
    for (let method of abi) {
        if  (method.name == name) {
            return method
        }
    }
    return null
}
