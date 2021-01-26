# Performance Results
Performance results were gathered for the GPACT paper. This page describes
how to reproduce the performance results shown in that paper.

## Tools use
The version of Hyperledger Besu version used was the git tag: 52814d537eb7ae9f209516d74d6be8ead13fd6bf. 
Any version of Hyperledger Besu after January 2020 should work equally well.


## Creating Blockchains
The following will create five blockchains with one node per blockchain:
```$xslt
scripts/create_chain.js 31 1
scripts/create_chain.js 32 1
scripts/create_chain.js 33 1
scripts/create_chain.js 34 1
scripts/create_chain.js 35 1
```

## Run Blockchains
To start the blockchain nodes, in separate windows run:
```$xslt
./scripts/run_node.js 31
./scripts/run_node.js 32
./scripts/run_node.js 33
./scripts/run_node.js 34
./scripts/run_node.js 35
```

## Config file
Use the following configuration file and name is config.props.

```$xslt

PRIVATE_KEY=12345678919ad0123456765556ead3961456349a2421111111111d85ea4d89b1
CONSENSUS_METHODOLOGY=TRANSACTION_RECEIPT_SIGNING
# CONSENSUS_METHODOLOGY=EVENT_SIGNING

OTHER_BC_ID=1F
OTHER_URI=http://127.0.0.1:8310/
OTHER_GAS=FREE
OTHER_PERIOD=2000

ROOT_BC_ID=1F
ROOT_URI=http://127.0.0.1:8310/
ROOT_GAS=FREE
ROOT_PERIOD=2000

BC2_BC_ID=20
BC2_URI=http://127.0.0.1:8320/
BC2_GAS=FREE
BC2_PERIOD=2000

BC3_BC_ID=21
BC3_URI=http://127.0.0.1:8330/
BC3_GAS=FREE
BC3_PERIOD=2000

BC4_BC_ID=22
BC4_URI=http://127.0.0.1:8340/
BC4_GAS=FREE
BC4_PERIOD=2000

BC5_BC_ID=23
BC5_URI=http://127.0.0.1:8350/
BC5_GAS=FREE
BC5_PERIOD=2000
```

## Run Examples
Import the Gradle project into Intelli-J Idea. To execute an example program,
execute the Main.java file in the example, with the parameter config.props.

