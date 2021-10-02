#!/usr/bin/env bash
set -e
rm -rf build

HERE=functioncall/gpact
BUILDDIR=$HERE/build
CONTRACTSDIR=$HERE/src/main/solidity
TESTCONTRACTSDIR=$HERE/src/test/solidity
OUTPUTDIR=$HERE/src/main/java
TESTOUTPUTDIR=$HERE/src/test/java
PACKAGE=net.consensys.gpact.cbc.soliditywrappers

#WEB3J=web3j
WEB3J=../web3j-rlp/codegen/build/install/codegen/bin/codegen


solc $CONTRACTSDIR/CrosschainControl.sol --allow-paths . --bin --abi --hashes --optimize -o $BUILDDIR --overwrite
solc $TESTCONTRACTSDIR/CallExecutionTreeTest.sol --allow-paths . --bin --abi --hashes --optimize -o $BUILDDIR --overwrite

$WEB3J solidity generate -a=$BUILDDIR/CrosschainControl.abi -b=$BUILDDIR/CrosschainControl.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -a=$BUILDDIR/CallExecutionTreeTest.abi -b=$BUILDDIR/CallExecutionTreeTest.bin -o=$TESTOUTPUTDIR -p=net.consensys.gpact.cbc.calltree.soliditywrappers



