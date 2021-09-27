#!/usr/bin/env bash
set -e
rm -rf build

HERE=application/gpact-examples/conditional
BUILDDIR=$HERE/build
CONTRACTSDIR=$HERE/src/main/solidity
TESTCONTRACTSDIR=$HERE/src/test/solidity
OUTPUTDIR=$HERE/src/main/java
TESTOUTPUTDIR=$HERE/src/test/java
PACKAGE=net.consensys.gpact.examples.conditional.soliditywrappers
#WEB3J=web3j
WEB3J=../web3j-rlp/codegen/build/install/codegen/bin/codegen


# compiling one file also compiles its dependendencies. We use overwrite to avoid the related warnings.
solc $CONTRACTSDIR/OtherBlockchainContract.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/RootBlockchainContract.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
# ls -al $BUILDDIR

$WEB3J solidity generate -r -a=$BUILDDIR/OtherBlockchainContract.abi -b=$BUILDDIR/OtherBlockchainContract.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -r -a=$BUILDDIR/RootBlockchainContract.abi -b=$BUILDDIR/RootBlockchainContract.bin -o=$OUTPUTDIR -p=$PACKAGE


