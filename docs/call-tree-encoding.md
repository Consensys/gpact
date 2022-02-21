# Call Tree Encoding Format

Call Trees are encoded data structures that represent the entry-point
function calls to each blockchain that are expected to be called in a
crosschain function call. This document describes the format of Call
Trees.

The encoding uses as a base type an ```EncodedFunction```. The definition of this type is:
```text
abi.encode(struct {
  uint256 blockchainId;
  uint160 contractAddress;
  bytes   callData;
}) EncodedFunction
```
Where:
* ```abi.encode``` indicates the structure should be converted to an array of bytes using standard Application Binary Interface (ABI) encoding rules. 
* ```blockchainId``` is the blockchain identifier for the blockchain that the function should be executed on.
* ```contractAddress``` is the address of contract that the function should be called on.
* ```callData``` is the standard ABI encoding for the function call. That is, a four byte Function Selector plus 
  encoded parameters. 

The encoding of an arbitrary call tree is shown below:
```text
struct {
  uint8 numCalledFunctions: (0 == Leaf Function, 1..255: Non-Leaf Function)
  IF (numCalledFunctions == Leaf Function) {
    EncodedFunction leafFunction
  } 
  else {
    uint32[numCalledFunctions+1] startOffets
    EncodedFunction callingFunction
    CallTree[numCalledFunctions] calledFunctions 
  }
}) CallTree
```
Where:
* ```numCalledFunctions``` is the number of functions the function represented at this 
  level of the call tree calls. If the function is a leaf function, it calls not other functions. 
  In this case, ```numCalledFunctions``` will be zero. 
* ```leafFunction``` is the encoding of the function to be called. 
* ```startOffsets``` provides start offsets of the ```intermediateFunction``` and ```calledFunctions``` 
  relative to the start of the ```CallTree``` object. 
* ```callingFunction``` is the function that is calling other functions.
* ```calledFunctions``` is an array of ```CallTree``` objects.

This structure has been used to allow EncodedFunction objects to be 
efficiently extracted from multi-level call trees.

NOTE: The maximum number of functions called by a calling function at any level of the call tree
is 255. 





