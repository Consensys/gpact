# Directories
The directories in this repo are shown in the table below.

| Directory               |Description |
|---|---|
|.circleci                |Continuous integration loop config file |
|appcontracts             |Application contracts written for use with crosschain. |
|appcontracts/solidity/erc20 |GPACT compatible ERC20. |
|appcontracts/solidity/openzeppelin| Open Zeppelin contracts copied here for use within this repo.|
|contracts                |Contracts, wrapper code, and test code. |
|contracts/solidity/blockheader  |Block header / transaction receipt root storage.  |
|contracts/solidity/common |Common utility contract code. |
|contracts/solidity/crossblockchaincontrol   |Crosschain Control contract.   |
|contracts/solidity/crypto |ECDSA and BLS signature verification solidity.|
|contracts/solidity/lockablestorage |Lockable storage contracts.|
|contracts/solidity/receipts  |Transaction receipt processing.|
|contracts/solidity/registrar |Registrar of blockchain public keys for transaction receipt root signing.|
|contracts/solidity/rlp       |RLP processing in Solidity.|
|doc                      |Documentation. |
|examples                 |A set of examples demonstrating the technology.|
|gradle                   |Build system related files.|
|library                  |GPACT protocol library code. |
|library/common           |Common utility and test code.  |
|library/crossblockchaincontrol   |Crosschain Control library code.   |
|library/receipts        |Transaction receipt processing.|
|library/registrar       |Java enums used by registrar.|
|library/rlp             |RLP processing code from Hyperledger Besu.|
|trie                     |Merkle Patricia Trie code from Hyperledger Besu.|
|scripts                  |Helper scripts.|
/test-blockchains         |Docker compose config file and Hyperledger Besu config files. |