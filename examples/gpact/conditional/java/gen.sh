#!/usr/bin/env bash
set -e
#rm -rf build

HERE=examples/gpact/conditional/java
BUILDDIR=$HERE/build
CONTRACTSDIR=examples/gpact/conditional/src
OUTPUTDIR=$HERE/src/main/java
PACKAGE=net.consensys.gpact.soliditywrappers.examples.conditional
#WEB3J=web3j
WEB3J=../web3j-abi/codegen/build/install/codegen/bin/codegen


# compiling one file also compiles its dependendencies. We use overwrite to avoid the related warnings.
solc $CONTRACTSDIR/OtherBlockchainContract.sol --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/RootBlockchainContract.sol --bin --abi --optimize -o $BUILDDIR --overwrite
# ls -al $BUILDDIR

$WEB3J solidity generate -r -a=$BUILDDIR/OtherBlockchainContract.abi -b=$BUILDDIR/OtherBlockchainContract.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -r -a=$BUILDDIR/RootBlockchainContract.abi -b=$BUILDDIR/RootBlockchainContract.bin -o=$OUTPUTDIR -p=$PACKAGE


