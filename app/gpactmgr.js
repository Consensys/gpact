import { MsgStore } from "./msgstore.js"
import { Executor } from "./executor.js"
import { Simulator, CrosschainCall } from "./simulator.js"

const bridgeABI = [{"inputs":[{"internalType":"address","name":"_gpactCbcContract","type":"address"}],"stateMutability":"nonpayable","type":"constructor"},{"anonymous":false,"inputs":[{"indexed":false,"internalType":"uint256","name":"_bcId","type":"uint256"},{"indexed":false,"internalType":"bool","name":"_approved","type":"bool"}],"name":"ApprovedRootBcId","type":"event"},{"anonymous":false,"inputs":[{"indexed":false,"internalType":"address","name":"account","type":"address"}],"name":"Paused","type":"event"},{"anonymous":false,"inputs":[{"indexed":false,"internalType":"uint256","name":"_srcBcId","type":"uint256"},{"indexed":false,"internalType":"address","name":"_srcTokenContract","type":"address"},{"indexed":false,"internalType":"address","name":"_destTokenContract","type":"address"},{"indexed":false,"internalType":"address","name":"_recipient","type":"address"},{"indexed":false,"internalType":"uint256","name":"_amount","type":"uint256"}],"name":"ReceivedFrom","type":"event"},{"anonymous":false,"inputs":[{"indexed":true,"internalType":"bytes32","name":"role","type":"bytes32"},{"indexed":true,"internalType":"bytes32","name":"previousAdminRole","type":"bytes32"},{"indexed":true,"internalType":"bytes32","name":"newAdminRole","type":"bytes32"}],"name":"RoleAdminChanged","type":"event"},{"anonymous":false,"inputs":[{"indexed":true,"internalType":"bytes32","name":"role","type":"bytes32"},{"indexed":true,"internalType":"address","name":"account","type":"address"},{"indexed":true,"internalType":"address","name":"sender","type":"address"}],"name":"RoleGranted","type":"event"},{"anonymous":false,"inputs":[{"indexed":true,"internalType":"bytes32","name":"role","type":"bytes32"},{"indexed":true,"internalType":"address","name":"account","type":"address"},{"indexed":true,"internalType":"address","name":"sender","type":"address"}],"name":"RoleRevoked","type":"event"},{"anonymous":false,"inputs":[{"indexed":false,"internalType":"address","name":"_thisBcTokenContract","type":"address"},{"indexed":false,"internalType":"uint256","name":"_otherBcId","type":"uint256"},{"indexed":false,"internalType":"address","name":"_othercTokenContract","type":"address"}],"name":"TokenContractAddressMappingChanged","type":"event"},{"anonymous":false,"inputs":[{"indexed":false,"internalType":"address","name":"_thisBcTokenContract","type":"address"},{"indexed":false,"internalType":"uint256","name":"_config","type":"uint256"}],"name":"TokenContractConfig","type":"event"},{"anonymous":false,"inputs":[{"indexed":false,"internalType":"uint256","name":"_destBcId","type":"uint256"},{"indexed":false,"internalType":"address","name":"_srcTokenContract","type":"address"},{"indexed":false,"internalType":"address","name":"_destTokenContract","type":"address"},{"indexed":false,"internalType":"address","name":"_sender","type":"address"},{"indexed":false,"internalType":"address","name":"_recipient","type":"address"},{"indexed":false,"internalType":"uint256","name":"_amount","type":"uint256"}],"name":"TransferTo","type":"event"},{"anonymous":false,"inputs":[{"indexed":false,"internalType":"address","name":"account","type":"address"}],"name":"Unpaused","type":"event"},{"inputs":[],"name":"DEFAULT_ADMIN_ROLE","outputs":[{"internalType":"bytes32","name":"","type":"bytes32"}],"stateMutability":"view","type":"function"},{"inputs":[],"name":"MAPPING_ROLE","outputs":[{"internalType":"bytes32","name":"","type":"bytes32"}],"stateMutability":"view","type":"function"},{"inputs":[],"name":"PAUSER_ROLE","outputs":[{"internalType":"bytes32","name":"","type":"bytes32"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"uint256","name":"_bcId","type":"uint256"}],"name":"addApprovedRootBcId","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"address","name":"_thisBcTokenContract","type":"address"},{"internalType":"uint256","name":"_otherBcId","type":"uint256"},{"internalType":"address","name":"_otherTokenContract","type":"address"},{"internalType":"bool","name":"_thisBcMassC","type":"bool"}],"name":"addContractFirstMapping","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"uint256","name":"_otherBcId","type":"uint256"},{"internalType":"address","name":"_otherErc20Bridge","type":"address"}],"name":"addRemoteERC20Bridge","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"address","name":"_thisBcTokenContract","type":"address"},{"internalType":"uint256","name":"_otherBcId","type":"uint256"},{"internalType":"address","name":"_otherTokenContract","type":"address"}],"name":"changeContractMapping","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"uint256","name":"_bcId","type":"uint256"},{"internalType":"address","name":"_tokenContract","type":"address"}],"name":"getBcIdTokenMaping","outputs":[{"internalType":"address","name":"","type":"address"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"uint256","name":"_bcId","type":"uint256"}],"name":"getRemoteErc20BridgeContract","outputs":[{"internalType":"address","name":"","type":"address"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"bytes32","name":"role","type":"bytes32"}],"name":"getRoleAdmin","outputs":[{"internalType":"bytes32","name":"","type":"bytes32"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"bytes32","name":"role","type":"bytes32"},{"internalType":"address","name":"account","type":"address"}],"name":"grantRole","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"bytes32","name":"role","type":"bytes32"},{"internalType":"address","name":"account","type":"address"}],"name":"hasRole","outputs":[{"internalType":"bool","name":"","type":"bool"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"uint256","name":"_bcId","type":"uint256"},{"internalType":"address","name":"_tokenContract","type":"address"}],"name":"isBcIdTokenAllowed","outputs":[{"internalType":"bool","name":"","type":"bool"}],"stateMutability":"view","type":"function"},{"inputs":[],"name":"pause","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[],"name":"paused","outputs":[{"internalType":"bool","name":"","type":"bool"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"address","name":"_destTokenContract","type":"address"},{"internalType":"address","name":"_recipient","type":"address"},{"internalType":"uint256","name":"_amount","type":"uint256"}],"name":"receiveFromOtherBlockchain","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"uint256","name":"_bcId","type":"uint256"}],"name":"removeApprovedRootBcId","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"bytes32","name":"role","type":"bytes32"},{"internalType":"address","name":"account","type":"address"}],"name":"renounceRole","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"bytes32","name":"role","type":"bytes32"},{"internalType":"address","name":"account","type":"address"}],"name":"revokeRole","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"address","name":"_thisBcTokenContract","type":"address"},{"internalType":"bool","name":"_thisBcMassC","type":"bool"}],"name":"setTokenConfig","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"bytes4","name":"interfaceId","type":"bytes4"}],"name":"supportsInterface","outputs":[{"internalType":"bool","name":"","type":"bool"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"address","name":"","type":"address"}],"name":"tokenContractConfiguration","outputs":[{"internalType":"uint256","name":"","type":"uint256"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"address","name":"_sender","type":"address"},{"internalType":"uint256","name":"_destBcId","type":"uint256"},{"internalType":"address","name":"_srcTokenContract","type":"address"},{"internalType":"address","name":"_recipient","type":"address"},{"internalType":"uint256","name":"_amount","type":"uint256"}],"name":"transferFromAccountToOtherBlockchain","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"uint256","name":"_destBcId","type":"uint256"},{"internalType":"address","name":"_srcTokenContract","type":"address"},{"internalType":"address","name":"_recipient","type":"address"},{"internalType":"uint256","name":"_amount","type":"uint256"}],"name":"transferToOtherBlockchain","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[],"name":"unpause","outputs":[],"stateMutability":"nonpayable","type":"function"}]
var bridgeMap = new Map([[4, "0x2576CE4BD89Af5D379cA7B5670f25191e3Bb8c6B"], [5, "0xe2AB1D514363338E322AE14c4571A48a4020c822"]]);

// It is a wrapper over JS SDK to provide cross-chain token transfer.
export class GPACTManager {
    constructor(ethereum, account) {
        // Create chain manager.
        var cmgr = new ChainAPManagerMetaMask(ethereum)
        // Create message store manager.
        var ms = new MsgStore("localhost:8080")
        // Create simulator.
        this.simulator = new Simulator(cmgr)
        // Register contract ABI.
        this.simulator.registerABI("bridge", bridgeABI)
        // Register contract crosschain method logic.
        this.simulator.registerCallLink("bridge", "transferToOtherBlockchain", async function (cmgr, chainID, contractAddr, ...params) {
            if (params[0].length != 4) {
                return null
            }
            var destBcID = params[0][0]
            var srcTokenContractAddr = params[0][1]
            var recipient = params[0][2]
            var amount = params[0][3]
            var web3 = await cmgr.chainAP(chainID)
            // Load bridge contract
            var bridge = new web3.eth.Contract(bridgeABI, contractAddr)
            var destAddr = await bridge.methods.getRemoteErc20BridgeContract(destBcID).call()
            var destTokenContractAddr = await bridge.methods.getBcIdTokenMaping(destBcID, srcTokenContractAddr).call()
            var call = new CrosschainCall(destBcID, "bridge", destAddr, "receiveFromOtherBlockchain", destTokenContractAddr, recipient, amount)
            return [call]
        })
        // Create executor
        this.executor = new Executor(cmgr, account, ms)
        this.executor.registerGPACT(4, "0xE9457c399C9DaDdD82B37773d30E3E6b7AD2c67B")
        this.executor.registerGPACT(5, "0x86565e507b3ce02ed206Eb563a829Ec2571eF2Cf")
    }

    async tokenTransfer(srcChainID, destChainID, erc20Addr, recipient, amt) {
        // First simulate to get execution tree root node.
        var root  = await this.simulator.simulate(Number(srcChainID), "bridge", bridgeMap.get(Number(srcChainID)), "transferToOtherBlockchain", Number(destChainID), erc20Addr, recipient, amt)
        // Execution
        await this.executor.crosschainCall(root)
    }
}

class ChainAPManagerMetaMask {
    constructor(ethereum) {
        this.ethereum = ethereum
    }

    async chainAP(chainID) {
        await this.ethereum.request({ method: 'wallet_switchEthereumChain', params:[{chainId: '0x' + (chainID).toString(16)}]})
        return new Web3(this.ethereum)
    }
}