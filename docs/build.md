# Building
## Introduction
The instructions below describe the process for building and testing
the code in this repo. These instructions reflect the steps used in 
the CircleCI continuous integration system described in the
[config.yml](../.circleci/config.yml) file. 

## Tools

* Java: The code was built with Java 11. The code is likely to build with later versions of Java.
* Solidity:  The code was build with solc 0.8.5+commit.a4f2e591.Darwin.appleclang, though should work with any 
version of Solidity after 0.8.
* Gradle: The build system uses Gradle 7.1. This will download when the code is built.

## Web3J
Solidity code that is to be compiled for use in crosschain calls needs to use
a special version of Web3J to generate the Java wrapper code for the contracts. 
An additional function is added for each public or external function in the Solidity
code to allow the RLP encoded function to be fetched.  

The special version of Web3J needs to end up in the directory `./gpact/..`. To create the special version of Web3J:
* cd ..
* git clone https://github.com/drinkcoffee/web3j-abi
* cd web3j-abi
* ./gradlew installDist
* cd ../gpact

## Build and Run Blockchain and Relayer Nodes
Build message store container:
```$xslt
cd services/message-store
make docker
```
Build relayer container:
```$xslt
cd services/relayer
make docker
```

Start blockchain nodes, relayer node, and message store node:
```$xslt
cd test-blockchains
docker compose up
```

## Test Java SDK and Solidity Contracts
Once the blockchain nodes and relayer node are running, 
to build and test Java SDK and tests:
```$xslt
./gradlew test
```

## Test JavaScript SDK
Once the blockchain nodes and relayer node are running,
the following steps need to be run to build and test the JavaScript SDK.

Build Solidity code for use with JavaScript compile
```$xslt
./gradlew build -x test -x intTest -x perfTest
```

Create environment for testing JS-SDK.
```$xslt
cd sdk/js
npm install
```

Test JS-SDK.
```$xslt
cd sdk/js
node ./test.js
```

## Relayer Build and Test 
If you wish to alter the relayer node code, you need to use these build and test steps.  

If any contracts have been altered, the wrappers need to be re-generated. 
First install abigen using the instruction here:
```$xslt
sh services/relayer/genWrappersInstallAbiGen.sh
```
and then generate the golang files:
```$xslt
sh services/relayer/genWrappers.sh
```



To build the relayer:
```$xslt
cd services/relayer
make build
```

To test the relayer:
```$xslt
cd services/relayer
make utest
```

##Code Style Checks
To check Solidity for compliance with code style:
```$xslt
./gradlew solFormatCheck
```

To apply Solidity format:
```$xslt
./gradlew solFormat
```

To check Java style:
```$xslt
./gradlew spotlessCheck
```

To apply Java format:
```$xslt
./gradlew spotlessApply
```

