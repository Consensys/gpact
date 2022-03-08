
import { default as Web3 } from "web3";
import { ChainAPManager } from "./chainmgr.js"
import { TreeNode } from "./treenode.js"
import { MsgStore } from "./msgstore.js"
import { CrosschainCall, Simulator } from "./simulator.js"

const startFuncSig = "0x77dab611ad9a24b763e2742f57749a0227393e0da76212d74fceb326b0661424"
const segmentFuncSig = "0xb01557f1f634b7c5072ab5e36d07a2355ef819faca5a3d321430d71987155b8f"
const rootFuncSig = "0xe6763dd99bf894d72f3499dd572aa42876eae7ae028c32fff21654e1bbc4c807"
const gpactABI = [{"inputs":[{"internalType":"uint256","name":"_myBlockchainId","type":"uint256"}],"stateMutability":"nonpayable","type":"constructor"},{"anonymous":false,"inputs":[{"indexed":false,"internalType":"uint256","name":"_expectedBlockchainId","type":"uint256"},{"indexed":false,"internalType":"uint256","name":"_actualBlockchainId","type":"uint256"},{"indexed":false,"internalType":"address","name":"_expectedContract","type":"address"},{"indexed":false,"internalType":"address","name":"_actualContract","type":"address"},{"indexed":false,"internalType":"bytes","name":"_expectedFunctionCall","type":"bytes"},{"indexed":false,"internalType":"bytes","name":"_actualFunctionCall","type":"bytes"}],"name":"BadCall","type":"event"},{"anonymous":false,"inputs":[{"indexed":false,"internalType":"string","name":"_revertReason","type":"string"}],"name":"CallFailure","type":"event"},{"anonymous":false,"inputs":[{"indexed":false,"internalType":"uint256","name":"_blockchainId","type":"uint256"},{"indexed":false,"internalType":"address","name":"_contract","type":"address"},{"indexed":false,"internalType":"bytes","name":"_functionCall","type":"bytes"},{"indexed":false,"internalType":"bytes","name":"_result","type":"bytes"}],"name":"CallResult","type":"event"},{"anonymous":false,"inputs":[{"indexed":false,"internalType":"uint256","name":"_val1","type":"uint256"},{"indexed":false,"internalType":"bytes32","name":"_val2","type":"bytes32"},{"indexed":false,"internalType":"address","name":"_val3","type":"address"},{"indexed":false,"internalType":"bytes","name":"_val4","type":"bytes"}],"name":"Dump","type":"event"},{"anonymous":false,"inputs":[{"indexed":false,"internalType":"uint256","name":"_expectedNumberOfCalls","type":"uint256"},{"indexed":false,"internalType":"uint256","name":"_actualNumberOfCalls","type":"uint256"}],"name":"NotEnoughCalls","type":"event"},{"anonymous":false,"inputs":[{"indexed":true,"internalType":"address","name":"previousOwner","type":"address"},{"indexed":true,"internalType":"address","name":"newOwner","type":"address"}],"name":"OwnershipTransferred","type":"event"},{"anonymous":false,"inputs":[{"indexed":false,"internalType":"uint256","name":"_crossBlockchainTransactionId","type":"uint256"},{"indexed":false,"internalType":"bool","name":"_success","type":"bool"}],"name":"Root","type":"event"},{"anonymous":false,"inputs":[{"indexed":false,"internalType":"uint256","name":"_crossBlockchainTransactionId","type":"uint256"},{"indexed":false,"internalType":"bytes32","name":"_hashOfCallGraph","type":"bytes32"},{"indexed":false,"internalType":"uint256[]","name":"_callPath","type":"uint256[]"},{"indexed":false,"internalType":"address[]","name":"_lockedContracts","type":"address[]"},{"indexed":false,"internalType":"bool","name":"_success","type":"bool"},{"indexed":false,"internalType":"bytes","name":"_returnValue","type":"bytes"}],"name":"Segment","type":"event"},{"anonymous":false,"inputs":[{"indexed":false,"internalType":"uint256","name":"_rootBcId","type":"uint256"},{"indexed":false,"internalType":"uint256","name":"_crossBlockchainTransactionId","type":"uint256"}],"name":"Signalling","type":"event"},{"anonymous":false,"inputs":[{"indexed":false,"internalType":"uint256","name":"_crossBlockchainTransactionId","type":"uint256"},{"indexed":false,"internalType":"address","name":"_caller","type":"address"},{"indexed":false,"internalType":"uint256","name":"_timeout","type":"uint256"},{"indexed":false,"internalType":"bytes","name":"_callGraph","type":"bytes"}],"name":"Start","type":"event"},{"inputs":[],"name":"activeCallCrosschainRootTxId","outputs":[{"internalType":"bytes32","name":"","type":"bytes32"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"uint256","name":"_blockchainId","type":"uint256"},{"internalType":"address","name":"_cbc","type":"address"}],"name":"addRemoteCrosschainControl","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"address","name":"_contractToLock","type":"address"}],"name":"addToListOfLockedContracts","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"uint256","name":"_blockchainId","type":"uint256"},{"internalType":"address","name":"_verifier","type":"address"}],"name":"addVerifier","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"uint256","name":"_blockchainId","type":"uint256"},{"internalType":"address","name":"_contract","type":"address"},{"internalType":"bytes","name":"_functionCallData","type":"bytes"}],"name":"crossBlockchainCall","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"uint256","name":"_blockchainId","type":"uint256"},{"internalType":"address","name":"_contract","type":"address"},{"internalType":"bytes","name":"_functionCallData","type":"bytes"}],"name":"crossBlockchainCallReturnsUint256","outputs":[{"internalType":"uint256","name":"","type":"uint256"}],"stateMutability":"nonpayable","type":"function"},{"inputs":[],"name":"getActiveCallCrosschainRootTxId","outputs":[{"internalType":"bytes32","name":"","type":"bytes32"}],"stateMutability":"view","type":"function"},{"inputs":[],"name":"isSingleBlockchainCall","outputs":[{"internalType":"bool","name":"","type":"bool"}],"stateMutability":"view","type":"function"},{"inputs":[],"name":"myBlockchainId","outputs":[{"internalType":"uint256","name":"","type":"uint256"}],"stateMutability":"view","type":"function"},{"inputs":[],"name":"owner","outputs":[{"internalType":"address","name":"","type":"address"}],"stateMutability":"view","type":"function"},{"inputs":[],"name":"renounceOwnership","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"uint256[]","name":"_blockchainIds","type":"uint256[]"},{"internalType":"address[]","name":"_cbcAddresses","type":"address[]"},{"internalType":"bytes32[]","name":"_eventFunctionSignatures","type":"bytes32[]"},{"internalType":"bytes[]","name":"_eventData","type":"bytes[]"},{"internalType":"bytes[]","name":"_signatures","type":"bytes[]"}],"name":"root","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"uint256","name":"","type":"uint256"}],"name":"rootTransactionInformation","outputs":[{"internalType":"uint256","name":"","type":"uint256"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"uint256[]","name":"_blockchainIds","type":"uint256[]"},{"internalType":"address[]","name":"_cbcAddresses","type":"address[]"},{"internalType":"bytes32[]","name":"_eventFunctionSignatures","type":"bytes32[]"},{"internalType":"bytes[]","name":"_eventData","type":"bytes[]"},{"internalType":"bytes[]","name":"_signatures","type":"bytes[]"},{"internalType":"uint256[]","name":"_callPath","type":"uint256[]"}],"name":"segment","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"bytes32","name":"","type":"bytes32"}],"name":"segmentTransactionExecuted","outputs":[{"internalType":"bool","name":"","type":"bool"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"uint256[]","name":"_blockchainIds","type":"uint256[]"},{"internalType":"address[]","name":"_cbcAddresses","type":"address[]"},{"internalType":"bytes32[]","name":"_eventFunctionSignatures","type":"bytes32[]"},{"internalType":"bytes[]","name":"_eventData","type":"bytes[]"},{"internalType":"bytes[]","name":"_signatures","type":"bytes[]"}],"name":"signalling","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"uint256","name":"_crossBlockchainTransactionId","type":"uint256"},{"internalType":"uint256","name":"_timeout","type":"uint256"},{"internalType":"bytes","name":"_callGraph","type":"bytes"}],"name":"start","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"address","name":"newOwner","type":"address"}],"name":"transferOwnership","outputs":[],"stateMutability":"nonpayable","type":"function"}]

export class Executor {
    constructor (cmgr, account, ms) {
        this.cmgr = cmgr
        this.account = account
        this.ms = ms
        this.gpacts = new Map()
    }

    registerGPACT(chainID, gpactAddr) {
        this.gpacts.set(chainID, gpactAddr)
    }

    async crosschainCall(root) {
        // Generate a random transaction id
        var temp = '0b'
        for (let i = 0; i < 256; i++) {
            temp += Math.round(Math.random())
        }
        var transID = BigInt(temp)

        // Start event
        console.log("start...")
        var [startEvent, startEventSig, err] = await this.start(transID, root)
        if (err != null) {
            return err
        }
        // Get Segment events for every child.
        console.log("segment...")
        var segmentChainIDs = []
        var segmentEvents = []
        var segmentEventSigs = []
        var lockedSegments = new Map()
        var lockedSegmentsSigs = new Map()
        for (let i = 0; i < root.children.length; i++) {
            console.log("segment ", i, "...")
            var [segEvent, segEventSig, err2] = await this.segment(root.chainID, startEvent, startEventSig, root.children[i], [i + 1], lockedSegments, lockedSegmentsSigs)
            if (err2 != null) {
                return err2
            }
            segmentChainIDs.push(root.children[i].chainID)
            segmentEvents.push(segEvent)
            segmentEventSigs.push(segEventSig)
        }
        console.log("root...")
        // Root.
        var [rootEvent, rootEventSig, err3] = await this.root(root.chainID, startEvent, startEventSig, segmentChainIDs, segmentEvents, segmentEventSigs)
        if (err3 != null) {
            return err3
        }
        // Signal.
        console.log("signal...")
        return this.signal(root.chainID, rootEvent, rootEventSig, lockedSegments, lockedSegmentsSigs)
    }

    async start(transID, root) {
        // Get chain ap.
        var web3 = this.cmgr.chainAP(root.chainID)
        // Get GPACT contract
        var gpactAddr = this.gpacts.get(root.chainID)
        var gpact = new web3.eth.Contract(gpactABI, gpactAddr)
        // gpact.events.
        var startEvent
        var startEventSig
        var err
        // Try 5 times, 5 seconds apart.
        for (let i = 0; i < 5; i++) {
            try {
                var gasPrice = await web3.eth.getGasPrice()
                var res = await gpact.methods.start(transID, BigInt(10000), root.encode()).send({
                    from: this.account,
                    gas: 600000,
                    gasPrice: gasPrice,
                })
                console.log("start succeed")
                console.log(res)
                startEvent = res.events.Start
                // Try to get event sig
                // Try 60 times, 2 seconds apart.
                var eventID = getEventID(root.chainID, startEvent)
                for (let j = 0; j < 60; j++) {
                    var sig = await this.ms.getSignature(eventID)
                    if (sig != null) {
                        startEventSig = sig
                        break
                    }
                    console.log("fail to get event sig, try again...")
                    await new Promise(r => setTimeout(r, 2000))
                }
                err = null
                break
            } catch (error) {
                console.log("got error", error, "try again...")
                err = "fail to do start transaction"
            }
            await new Promise(r => setTimeout(r, 5000))
        }
        return [startEvent, startEventSig, err]
    }

    async segment(startChainID, startEvent, startEventSig, node, callPath, lockedSegments, lockedSegmentsSigs) {
        // Segment events.
        var segmentEvent
        var segmentEventSig
        var err
        var chainIDs = [startChainID]
        var cbcAddrs = [startEvent.address]
        var eventFuncSigs = [startFuncSig]
        var eventDatas = [startEvent.raw.data]
        var eventSigs = [startEventSig]
        if (node.children.length > 0) {
            // This is an intermediate node.
            // Append a 0 in the end of the call path.
            callPath.push(0)
            // Copy the call path
            var childCallPath = callPath.slice()
            // Need to collect segments event for all the child nodes.
            for (let i = 0; i < node.children.length; i++) {
                // Child call path last element starts with 1.
                childCallPath[childCallPath.length - 1] = i + 1
                var [childSegEvent, childSegSig, err2] = await this.segment(startChainID, startEvent, startEventSig, child, childCallPath, lockedSegments, lockedSegmentsSigs)
                if (err2 != null) {
                    return [null, null, err2]
                }
                chainIDs.push(child.chainID)
                cbcAddrs.push(childSegEvent.address)
                eventFuncSigs.push(segmentFuncSig)
                eventDatas.push(childSegEvent.raw.data)
                eventSigs.push(childSegSig)
            }
        }
        // Either this node is a leaf node or this node is an intermediate node and we have already collected all segment events for its child nodes.
        // Get chain ap.
        var web3 = this.cmgr.chainAP(node.chainID)
        // Get GPACT contract
        var gpactAddr = this.gpacts.get(node.chainID)
        var gpact = new web3.eth.Contract(gpactABI, gpactAddr)
        // //
        // Try 5 times, 5 seconds apart.
        for (let i = 0; i < 5; i++) {
            try {
                var gasPrice = await web3.eth.getGasPrice()
                var res = await gpact.methods.segment(chainIDs, cbcAddrs, eventFuncSigs, eventDatas, eventSigs, callPath).send({
                    from: this.account,
                    gas: 600000,
                    gasPrice: gasPrice,
                })
                console.log("segment succeed")
                console.log(res)
                segmentEvent = res.events.Segment   
                // Try to get event sig
                // Try 60 times, 2 seconds apart.
                var eventID = getEventID(node.chainID, segmentEvent)
                for (let j = 0; j < 60; j++) {
                    var sig = await this.ms.getSignature(eventID)
                    if (sig != null) {
                        segmentEventSig = sig
                        break
                    }
                    console.log("fail to get event sig, try again...")
                    await new Promise(r => setTimeout(r, 2000))
                }
                // Got the segment event sig.
                // Locked contracts
                if (segmentEvent.returnValues._lockedContracts.length > 0) {
                    if (!lockedSegments.has(node.chainID)) {
                        lockedSegments.set(node.chainID, [])
                    }
                    lockedSegments.get(node.chainID).push(segmentEvent)
                    if (!lockedSegmentsSigs.has(node.chainID)) {
                        lockedSegmentsSigs.set(node.chainID, [])
                    }
                    lockedSegmentsSigs.get(node.chainID).push(segmentEventSig)
                }
                err = null
                break
            } catch (error) {
                console.log("got error", error, "try again...")
                err = "fail to do segment transaction"
            }
            await new Promise(r => setTimeout(r, 5000))
        }
        return [segmentEvent, segmentEventSig, err]
    }

    async root(startChainID, startEvent, startEventSig, segmentChainIDs, segmentEvents, segmentEventSigs) {
        // Root events.
        var rootEvent
        var rootEventSig
        var err
        var chainIDs = [startChainID]
        var cbcAddrs = [startEvent.address]
        var eventFuncSigs = [startFuncSig]
        var eventDatas = [startEvent.raw.data]
        var eventSigs = [startEventSig]
        for (let i = 0; i < segmentChainIDs.length; i++) {
            chainIDs.push(segmentChainIDs[i])
            cbcAddrs.push(segmentEvents[i].address)
            eventFuncSigs.push(segmentFuncSig)
            eventDatas.push(segmentEvents[i].raw.data)
            eventSigs.push(segmentEventSigs[i])
        }
        // Get chain ap.
        var web3 = this.cmgr.chainAP(startChainID)
        // Get GPACT contract
        var gpactAddr = this.gpacts.get(startChainID)
        var gpact = new web3.eth.Contract(gpactABI, gpactAddr)
        // Try 5 times, 5 seconds apart.
        for (let i = 0; i < 5; i++) {
            try {
                var gasPrice = await web3.eth.getGasPrice()
                var res = await gpact.methods.root(chainIDs, cbcAddrs, eventFuncSigs, eventDatas, eventSigs).send({
                    from: this.account,
                    gas: 1000000,
                    gasPrice: gasPrice,
                })
                console.log("root succeed")
                console.log(res)
                rootEvent = res.events.Root
                // Try to get event sig
                // Try 60 times, 2 seconds apart.
                var eventID = getEventID(startChainID, rootEvent)
                for (let j = 0; j < 60; j++) {
                    var sig = await this.ms.getSignature(eventID)
                    if (sig != null) {
                        rootEventSig = sig
                        break
                    }
                    console.log("fail to get event sig, try again...")
                    await new Promise(r => setTimeout(r, 2000))
                }
                err = null
                break
            } catch (error) {
                console.log("got error", error, "try again...")
                err = "fail to do root transaction"
            }
            await new Promise(r => setTimeout(r, 5000))
        }
        return [rootEvent, rootEventSig, err]
    }

    async signal(rootChainID, rootEvent, rootEventSig, lockedSegments, lockedSegmentsSigs) {
        var err
        for (var [segChainID, segments] of lockedSegments.entries()) {
            var chainIDs = [rootChainID]
            var cbcAddrs = [rootEvent.address]
            var eventFuncSigs = [rootFuncSig]
            var eventDatas = [rootEvent.raw.data]
            var eventSigs = [rootEventSig]
            for (var segment of segments) {
                chainIDs.push(segChainID)
                cbcAddrs.push(segment.address)
                eventFuncSigs.push(segmentFuncSig)
                eventDatas.push(segment.raw.data)
            }
            eventSigs = eventSigs.concat(lockedSegmentsSigs.get(segChainID))
            // Get chain ap.
            var web3 = this.cmgr.chainAP(segChainID)
            // Get GPACT contract
            var gpactAddr = this.gpacts.get(segChainID)
            var gpact = new web3.eth.Contract(gpactABI, gpactAddr)
            // Try 5 times, 5 seconds apart.
            for (let i = 0; i < 5; i++) {
                try {
                    var gasPrice = await web3.eth.getGasPrice()
                    var res = await gpact.methods.signalling(chainIDs, cbcAddrs, eventFuncSigs, eventDatas, eventSigs).send({
                        from: this.account,
                        gas: 600000,
                        gasPrice: gasPrice,
                    })
                    console.log("signal succeed")
                    console.log(res)
                    err = null
                    break
                } catch (error) {
                    console.log("got error", error, "try again...")
                    err = "fail to do signal transaction"
                }
                await new Promise(r => setTimeout(r, 5000))
            }
        }
        return err
    }
}

function getEventID(chainID, event) {
    return "" + chainID + "-" + event.address + "-" + event.blockNumber + "-" + event.transactionIndex + "-" + event.logIndex
}
