import { longToByteArray, hexToBytes } from "./helper.js";

// Constants
const ENCODING_FORMAT_V1 = 0;
const LEAF_FUNCTION = 0;
const NUM_FUNCS_CALLED_SIZE = 1;
const OFFSET_SIZE = 4;
const BLOCKCHAIN_ID_SIZE = 32;
const ADDR_SIZE = 20;
const DATA_LEN_SIZE = 2;
const MAX_CALL_EX_TREE_SIZE = 1000000;
const MAX_CALLED_FUNCTIONS = 255;

// TreeNode is a node in the execution tree.
// It represents a function execution at a specific contract in a specific chain.
export class TreeNode {
  // chain id is number, contract addr and call data are hex string (all starts with 0x)
  constructor(chainID, contractAddr, callData) {
    this.chainID = chainID;
    this.contractAddr = contractAddr;
    this.callData = callData;
    this.children = [];
  }

  addChild(child) {
    // TODO: Need to check if child is valid.
    this.children.push(child);
  }

  encode() {
    let data = [];
    // Add encoding format of call tree.
    data = data.concat(longToByteArray(1, ENCODING_FORMAT_V1));
    // Add the call execution tree
    data = data.concat(this.encodeRecursive());
    return data;
  }

  encodeRecursive() {
    let data = [];

    if (this.children.length === 0) {
      // This is a leaf node.
      // Add number of functions: 0 indicates leaf.
      data = data.concat(longToByteArray(1, LEAF_FUNCTION));
      // Add encoded data of leaf
      data = data.concat(this.encodeFunctionCall());
    } else {
      // This is a non-leaf node.

      let encodedChildren = [];
      encodedChildren.push(this.encodeFunctionCall());
      for (const child of this.children) {
        encodedChildren.push(child.encodeRecursive());
      }

      // Add number of functions
      data = data.concat(longToByteArray(1, this.children.length));

      // Add offsets
      let offset = (this.children.length + 1) * OFFSET_SIZE + NUM_FUNCS_CALLED_SIZE;
      for (const encodedChild of encodedChildren) {
        data = data.concat(longToByteArray(OFFSET_SIZE, offset));
        offset = offset + encodedChild.length;
      }
      for (const encodedChild of encodedChildren) {
        data = data.concat(encodedChild);
      }
    }
    return data;
  }

  encodeFunctionCall() {
    let encoded = [];
    encoded = encoded.concat(longToByteArray(BLOCKCHAIN_ID_SIZE, this.chainID));
    encoded = encoded.concat(hexToBytes(ADDR_SIZE, this.contractAddr));
    encoded = encoded.concat(
        longToByteArray(DATA_LEN_SIZE, this.callData.length / 2 - 1),
    );
    encoded = encoded.concat(
        hexToBytes(this.callData.length / 2 - 1, this.callData)
    );
    return encoded;
  }

}
