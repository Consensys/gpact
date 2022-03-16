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
        let data = []
        // Add number of functions
        data = data.concat(longToByteArray(1, this.children.length))
        // Encoded data of this node
        let encoded = []
        encoded = encoded.concat(longToByteArray(blockchainIDSize, this.chainID))        
        encoded = encoded.concat(hexToBytes(addrSize, this.contractAddr))
        encoded = encoded.concat(longToByteArray(dataLenSizeSize, this.callData.length/2-1))
        encoded = encoded.concat(hexToBytes(this.callData.length/2-1, this.callData))
        if (data[0] == 0) {
            // This is a leaf node.
            data = data.concat(encoded)
        } else {
            // This is a non-leaf node.
            let encodedChildren = []
            encodedChildren.push(encoded)
            for (let child of this.children) {
                encodedChildren.push(child.encode())
            }
            // Add offsets
            let offset = (this.children.length+1)*offsetSize + numFuncsCalledSize
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
