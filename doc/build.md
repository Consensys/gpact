# Building
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

## Building Solidity, Wrapper code and Test Code
Start blockchain nodes:
```$xslt
cd test-blockchains
docker compose up
```

To build and test:
```$xslt
./gradlew test
```
