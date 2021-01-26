# Building
## Tools

* Java: The code was built with Java 11. The code is likely to build with later versions of Java.
* Solidity:  The code was build with solc 0.7.4+commit.3f05b770.Darwin.appleclang, though should work with any 
version of Solidity after 0.7.1.
* Gradle: The build system uses Gradle 6.6.1. This will download when the code is built.
* Node: Node is needed to run the scripts. v14.12.0 was used to execute the scripts.
 The following modules need to be installed to run the scripts: at-least-node,
 fs-extra, graceful-fs, jsonfile, toml-j0.4, tomlify-j0.4, universalify.

## Web3J
Solidity code that is to be compiled for use in crosschain calls needs to use
a special version of Web3J to generate the Java wrapper code for the contracts. 
An additional function is added for each public or external function in the Solidity
code to allow the RLP encoded function to be fetched.  

The special version of Web3J needs to end up in the directory `./LTACFC/..`. To create the special version of Web3J:
* cd ..
* git clone https://github.com/drinkcoffee/web3j-rlp
* cd web3j-rlp
* ./gradlew build
* cd codegen/build/distributions
* tar xvf codegen-4.7.0-SNAPSHOT.tar
* cd ../../../../LTACFC

## Creating a blockchain for test purposes
To build:
```$xslt
scripts/create_chain.js 32 1
```


## Building Solidity, Wrapper code and Test Code
To build:
```$xslt
./gradlew 
```

To build and test:
```$xslt
./scripts/run_node.js 32
```
and then in another command window:
```
./gradlew test
```

