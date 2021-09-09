#!/usr/bin/env bash
set -e
rm -rf build

HERE=library/crossblockchaincontrol
BUILDDIR=$HERE/build
TESTCONTRACTSDIR=$HERE/src/test/solidity
TESTOUTPUTDIR=$HERE/src/test/java
PACKAGE=net.consensys.gpact.cbc.calltree.soliditywrappers

#WEB3J=web3j
WEB3J=../web3j-rlp/codegen/build/install/codegen/bin/codegen

solc $TESTCONTRACTSDIR/CallExecutionTreeTest.sol --allow-paths . --bin --abi --hashes --optimize -o $BUILDDIR --overwrite
$WEB3J solidity generate -a=$BUILDDIR/CallExecutionTreeTest.abi -b=$BUILDDIR/CallExecutionTreeTest.bin -o=$TESTOUTPUTDIR -p=$PACKAGE


