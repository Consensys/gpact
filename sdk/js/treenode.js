import { longToByteArray, hexToBytes } from "./helper.js";

// Constants
const numFuncsCalledSize    = 1
const offsetSize            = 4
const blockchainIDSize      = 32
const addrSize              = 20
const dataLenSizeSize       = 2
const MAX_CALL_EX_TREE_SIZE = 1000000
const MAX_CALLED_FUNCTIONS  = 255

// TreeNode is a node in the execution tree.
// It represents a function execution at a specific contract in a specific chain.
export class TreeNode {
    // chain id is number, contract addr and call data are hex string (all starts with 0x)
    constructor(chainID, contractAddr, callData) {
        this.chainID = chainID
        this.contractAddr = contractAddr
        this.callData = callData
        this.children = []
    }

    addChild(child) {
        // TODO: Need to check if child is valid.
        this.children.push(child)
    }

    encode() {
        var data = []
        // Add number of functions
        data = data.concat(longToByteArray(1, this.children.length))
        // Encoded data of this node
        var encoded = []
        encoded = encoded.concat(longToByteArray(blockchainIDSize, this.chainID))        
        encoded = encoded.concat(hexToBytes(addrSize, this.contractAddr))
        encoded = encoded.concat(longToByteArray(dataLenSizeSize, this.callData.length/2-1))
        encoded = encoded.concat(hexToBytes(this.callData.length/2-1, this.callData))
        if (data[0] == 0) {
            // This is a leaf node.
            data = data.concat(encoded)
        } else {
            // This is a non-leaf node.
            var encodedChildren = []
            encodedChildren.push(encoded)
            for (var child of this.children) {
                encodedChildren.push(child.encode())
            }
            // Add offsets
            var offset = (this.children.length+1)*offsetSize + numFuncsCalledSize
            for (let encodedChild of encodedChildren) {
                data = data.concat(longToByteArray(offsetSize, offset))
                offset = offset + encodedChild.length
            }
            for (let encodedChild of encodedChildren) {
                data = data.concat(encodedChild)
            }
        }
        return data
    }
}

// Test
function toHexString(byteArray) {
    return Array.from(byteArray, function(byte) {
      return ('0' + (byte & 0xFF).toString(16)).slice(-2);
    }).join('')
}

var testChild = new TreeNode(32, "0xFdd73d8ee324b19b1FB9950c2CaEf9a7a05f4FF7", "0x8278ef6d000000000000000000000000a6ea31945847dcdac57b1075ec3c1898298d31b9000000000000000000000000120e640a6fca2ea957ca3250a5c4ebc7de3b087b000000000000000000000000000000000000000000000000000000000000000a")
var testRoot = new TreeNode(31, "0xFdd73d8ee324b19b1FB9950c2CaEf9a7a05f4FF7", "0x84aea6330000000000000000000000000000000000000000000000000000000000000020000000000000000000000000a6ea31945847dcdac57b1075ec3c1898298d31b9000000000000000000000000120e640a6fca2ea957ca3250a5c4ebc7de3b087b000000000000000000000000000000000000000000000000000000000000000a")
testRoot.addChild(testChild)
if (toHexString(testRoot.encode()) == "0100000009000000c3000000000000000000000000000000000000000000000000000000000000001ffdd73d8ee324b19b1fb9950c2caef9a7a05f4ff7008484aea6330000000000000000000000000000000000000000000000000000000000000020000000000000000000000000a6ea31945847dcdac57b1075ec3c1898298d31b9000000000000000000000000120e640a6fca2ea957ca3250a5c4ebc7de3b087b000000000000000000000000000000000000000000000000000000000000000a000000000000000000000000000000000000000000000000000000000000000020fdd73d8ee324b19b1fb9950c2caef9a7a05f4ff700648278ef6d000000000000000000000000a6ea31945847dcdac57b1075ec3c1898298d31b9000000000000000000000000120e640a6fca2ea957ca3250a5c4ebc7de3b087b000000000000000000000000000000000000000000000000000000000000000a") {
    console.log("Test passed.")
} else {
    console.log("Test failed.")
}
