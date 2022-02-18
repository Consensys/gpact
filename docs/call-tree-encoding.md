# Call Tree Encoding Format

Call Trees are encoded data structures that represent the entry-point
function calls to each blockchain that are expected to be called in a
crosschain function call. This document describes the format of Call
Trees.

Each function call consists of:

* Blockchain Identifier
* Address of Contract
* Function call data. That is 4 byte Function Selector plus encoded parameters. 
This is the standard Application Binary Interface (ABI) encoding used by Ethereum for calling functions in transactions.

This data is encoded as:
```text
EncodedFunction = abi.encode(
  (256 bit blockchain ID)
  (160 bit contract address)
  (Function call data)
)
```

The encoding of the call tree is currently not documented. See CallExecutionTreeEncodignV1.java.

[//]: # ( Comment: 0 Indicates a leaf function, n Indicates that a function calls n other functions. )


