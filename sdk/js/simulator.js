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
        var link = this.links.get(contractType).get(method)
        if (!this.abis.has(contractType)) {
            return null
        }
        var contractABI = this.abis.get(contractType)
        var methodABI = searchMethod(contractABI, method)
        var callData = Web3ABI.encodeFunctionCall(methodABI, params)
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
                var subCallData = Web3ABI.encodeFunctionCall(subMethodABI, subParams)
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

// Test
function toHexString(byteArray) {
    return Array.from(byteArray, function(byte) {
      return ('0' + (byte & 0xFF).toString(16)).slice(-2);
    }).join('')
}

// var testSim = new Simulator(null)
// var testABI = [{"inputs":[{"internalType":"address","name":"_gpactCbcContract","type":"address"}],"stateMutability":"nonpayable","type":"constructor"},{"anonymous":false,"inputs":[{"indexed":false,"internalType":"uint256","name":"_bcId","type":"uint256"},{"indexed":false,"internalType":"bool","name":"_approved","type":"bool"}],"name":"ApprovedRootBcId","type":"event"},{"anonymous":false,"inputs":[{"indexed":false,"internalType":"address","name":"account","type":"address"}],"name":"Paused","type":"event"},{"anonymous":false,"inputs":[{"indexed":false,"internalType":"uint256","name":"_srcBcId","type":"uint256"},{"indexed":false,"internalType":"address","name":"_srcTokenContract","type":"address"},{"indexed":false,"internalType":"address","name":"_destTokenContract","type":"address"},{"indexed":false,"internalType":"address","name":"_recipient","type":"address"},{"indexed":false,"internalType":"uint256","name":"_amount","type":"uint256"}],"name":"ReceivedFrom","type":"event"},{"anonymous":false,"inputs":[{"indexed":true,"internalType":"bytes32","name":"role","type":"bytes32"},{"indexed":true,"internalType":"bytes32","name":"previousAdminRole","type":"bytes32"},{"indexed":true,"internalType":"bytes32","name":"newAdminRole","type":"bytes32"}],"name":"RoleAdminChanged","type":"event"},{"anonymous":false,"inputs":[{"indexed":true,"internalType":"bytes32","name":"role","type":"bytes32"},{"indexed":true,"internalType":"address","name":"account","type":"address"},{"indexed":true,"internalType":"address","name":"sender","type":"address"}],"name":"RoleGranted","type":"event"},{"anonymous":false,"inputs":[{"indexed":true,"internalType":"bytes32","name":"role","type":"bytes32"},{"indexed":true,"internalType":"address","name":"account","type":"address"},{"indexed":true,"internalType":"address","name":"sender","type":"address"}],"name":"RoleRevoked","type":"event"},{"anonymous":false,"inputs":[{"indexed":false,"internalType":"address","name":"_thisBcTokenContract","type":"address"},{"indexed":false,"internalType":"uint256","name":"_otherBcId","type":"uint256"},{"indexed":false,"internalType":"address","name":"_othercTokenContract","type":"address"}],"name":"TokenContractAddressMappingChanged","type":"event"},{"anonymous":false,"inputs":[{"indexed":false,"internalType":"address","name":"_thisBcTokenContract","type":"address"},{"indexed":false,"internalType":"uint256","name":"_config","type":"uint256"}],"name":"TokenContractConfig","type":"event"},{"anonymous":false,"inputs":[{"indexed":false,"internalType":"uint256","name":"_destBcId","type":"uint256"},{"indexed":false,"internalType":"address","name":"_srcTokenContract","type":"address"},{"indexed":false,"internalType":"address","name":"_destTokenContract","type":"address"},{"indexed":false,"internalType":"address","name":"_sender","type":"address"},{"indexed":false,"internalType":"address","name":"_recipient","type":"address"},{"indexed":false,"internalType":"uint256","name":"_amount","type":"uint256"}],"name":"TransferTo","type":"event"},{"anonymous":false,"inputs":[{"indexed":false,"internalType":"address","name":"account","type":"address"}],"name":"Unpaused","type":"event"},{"inputs":[],"name":"DEFAULT_ADMIN_ROLE","outputs":[{"internalType":"bytes32","name":"","type":"bytes32"}],"stateMutability":"view","type":"function"},{"inputs":[],"name":"MAPPING_ROLE","outputs":[{"internalType":"bytes32","name":"","type":"bytes32"}],"stateMutability":"view","type":"function"},{"inputs":[],"name":"PAUSER_ROLE","outputs":[{"internalType":"bytes32","name":"","type":"bytes32"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"uint256","name":"_bcId","type":"uint256"}],"name":"addApprovedRootBcId","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"address","name":"_thisBcTokenContract","type":"address"},{"internalType":"uint256","name":"_otherBcId","type":"uint256"},{"internalType":"address","name":"_otherTokenContract","type":"address"},{"internalType":"bool","name":"_thisBcMassC","type":"bool"}],"name":"addContractFirstMapping","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"uint256","name":"_otherBcId","type":"uint256"},{"internalType":"address","name":"_otherErc20Bridge","type":"address"}],"name":"addRemoteERC20Bridge","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"address","name":"_thisBcTokenContract","type":"address"},{"internalType":"uint256","name":"_otherBcId","type":"uint256"},{"internalType":"address","name":"_otherTokenContract","type":"address"}],"name":"changeContractMapping","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"uint256","name":"_bcId","type":"uint256"},{"internalType":"address","name":"_tokenContract","type":"address"}],"name":"getBcIdTokenMaping","outputs":[{"internalType":"address","name":"","type":"address"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"uint256","name":"_bcId","type":"uint256"}],"name":"getRemoteErc20BridgeContract","outputs":[{"internalType":"address","name":"","type":"address"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"bytes32","name":"role","type":"bytes32"}],"name":"getRoleAdmin","outputs":[{"internalType":"bytes32","name":"","type":"bytes32"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"bytes32","name":"role","type":"bytes32"},{"internalType":"address","name":"account","type":"address"}],"name":"grantRole","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"bytes32","name":"role","type":"bytes32"},{"internalType":"address","name":"account","type":"address"}],"name":"hasRole","outputs":[{"internalType":"bool","name":"","type":"bool"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"uint256","name":"_bcId","type":"uint256"},{"internalType":"address","name":"_tokenContract","type":"address"}],"name":"isBcIdTokenAllowed","outputs":[{"internalType":"bool","name":"","type":"bool"}],"stateMutability":"view","type":"function"},{"inputs":[],"name":"pause","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[],"name":"paused","outputs":[{"internalType":"bool","name":"","type":"bool"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"address","name":"_destTokenContract","type":"address"},{"internalType":"address","name":"_recipient","type":"address"},{"internalType":"uint256","name":"_amount","type":"uint256"}],"name":"receiveFromOtherBlockchain","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"uint256","name":"_bcId","type":"uint256"}],"name":"removeApprovedRootBcId","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"bytes32","name":"role","type":"bytes32"},{"internalType":"address","name":"account","type":"address"}],"name":"renounceRole","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"bytes32","name":"role","type":"bytes32"},{"internalType":"address","name":"account","type":"address"}],"name":"revokeRole","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"address","name":"_thisBcTokenContract","type":"address"},{"internalType":"bool","name":"_thisBcMassC","type":"bool"}],"name":"setTokenConfig","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"bytes4","name":"interfaceId","type":"bytes4"}],"name":"supportsInterface","outputs":[{"internalType":"bool","name":"","type":"bool"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"address","name":"","type":"address"}],"name":"tokenContractConfiguration","outputs":[{"internalType":"uint256","name":"","type":"uint256"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"address","name":"_sender","type":"address"},{"internalType":"uint256","name":"_destBcId","type":"uint256"},{"internalType":"address","name":"_srcTokenContract","type":"address"},{"internalType":"address","name":"_recipient","type":"address"},{"internalType":"uint256","name":"_amount","type":"uint256"}],"name":"transferFromAccountToOtherBlockchain","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"uint256","name":"_destBcId","type":"uint256"},{"internalType":"address","name":"_srcTokenContract","type":"address"},{"internalType":"address","name":"_recipient","type":"address"},{"internalType":"uint256","name":"_amount","type":"uint256"}],"name":"transferToOtherBlockchain","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[],"name":"unpause","outputs":[],"stateMutability":"nonpayable","type":"function"}]
// testSim.registerABI("bridge", testABI)
// testSim.registerCallLink("bridge", "transferToOtherBlockchain", async function (cmgr, chainID, contractAddr, ...params) {
//     if (params[0].length != 4) {
//         return null
//     }
//     var destBcID = params[0][0]
//     var srcTokenContractAddr = params[0][1]
//     var recipient = params[0][2]
//     var amount = params[0][3]
//     var call = new CrosschainCall(destBcID, "bridge", contractAddr, "receiveFromOtherBlockchain", srcTokenContractAddr, recipient, amount)
//     return [call]
// })
// var testRoot = await testSim.simulate(31, "bridge", "0xF96EED222dBa6aBa379BDe49E64A3139642EC09f", "transferToOtherBlockchain", 32, "0xfA1bdE5Bd01FC715c3b97F1eAe0e114db0dcc5a2", "0x557E662D052807d1FAe1AB8727C2f7D47BDaA56E", 10)
// if (toHexString(testRoot.encode()) == "0100000009000000c3000000000000000000000000000000000000000000000000000000000000001ff96eed222dba6aba379bde49e64a3139642ec09f008484aea6330000000000000000000000000000000000000000000000000000000000000020000000000000000000000000fa1bde5bd01fc715c3b97f1eae0e114db0dcc5a2000000000000000000000000557e662d052807d1fae1ab8727c2f7d47bdaa56e000000000000000000000000000000000000000000000000000000000000000a000000000000000000000000000000000000000000000000000000000000000020f96eed222dba6aba379bde49e64a3139642ec09f00648278ef6d000000000000000000000000fa1bde5bd01fc715c3b97f1eae0e114db0dcc5a2000000000000000000000000557e662d052807d1fae1ab8727c2f7d47bdaa56e000000000000000000000000000000000000000000000000000000000000000a") {
//     console.log("Test passed.")
// } else {
//     console.log("Test failed.")
// }

