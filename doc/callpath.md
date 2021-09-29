# Call Tree and Call Path Format

TODO: The wording below is out of date. The encoding has changed 
since this was written.

Call Trees are encoded data structures that represent the entry-point
function calls to each blockchain that are expected to be called in a
crosschain function call. Call Paths are used to identify function 
calls within a call tree. This document describes the format of Call
Trees and Call Paths.


## Call Trees
Each function call consists of:

* Blockchain Identifier
* Address of Contract
* Function call data. That is 4 byte Function Selector plus encoded parameters. 
This is the standard encoding used by Ethereum for calling functions in transactions.

This data is encoded as:
```text
EncodedFunction = abi.encode(
  (256 bit blockchain ID)
  (160 bit contract address)
  (Function call data)
)
```

A call tree consists of a root node, intermediate nodes, and 
leaf nodes. That is, if a() calls b() and then c(), and b() calls d(),
then a() is the root, c() and d() are leaf nodes, and b() is an
intermediate node. The nodes are encoded in the following way:

* LeafNode encoded as an EncodedFunction
* IntermediateNode encoded as abi.encode of:
    * EncodedFunction representing the IntermediateNode itself.
    * Multiple IntermediateNodes or LeafNodes. The encoding is in 
     order in which the functions are called.  
* RootNode is encoded in the same way as IntermediateNodes.

Hence, the call tree described above would be encoded:
```text
{
    EncodedFunction for a()
    {
        EncodedFunction for b()
        EncodedFunction for d()
    }
    Encoded Function for c()
}
```

## Call Path
Call Paths are used to identify function calls within a call tree. They
are used to indicate to the Cross Blockchain Control contract which 
function should be being executed. 

Call Paths are arrays of unsigned integers. The size of the array indicates
how deep the call is in the call tree. The zeroth element is the top of the 
call tree.

Using the example above:

| Function | Call Path |
| -------- | --------- |
| a()      | [0]       |
| b()      | [1, 0]    |
| c()      | [2]       |
| d()      | [1, 1]    |

This type of call path encoding means that the function that 
called the current function can be determined by the following 
algorithm:
```java
int[] callPath = Some Call Path
int[] callPathOfParent;
int callPathLen = callPath.length;

if (callPathLen == 1 && callPath[0] == 0) {
  throw new Exception("No parent as this is the root function.");
}

if (callPath[callPathLen - 1] != 0) { 
  callPathOfParent = new int[callPathLen];
  for (int i=0; i<callPathLen - 1; i++) {
    callPathOfParent[i] = callPath[i]
  }
  callPathOfParent[callPathLen - 1] = 0;
}
else { 
  callPathOfParent = new int[callPathLen - 1];
  for (int i=0; i<callPathLen - 2; i++) {
    callPathOfParent[i] = callPath[i]
  }
  callPathOfParent[callPathLen - 2] = 0;
  
}



``` 
