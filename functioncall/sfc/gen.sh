#!/usr/bin/env bash
set -e
rm -rf build

HERE=functioncall/sfc
BUILDDIR=$HERE/build
CONTRACTSDIR=$HERE/src/main/solidity
TESTCONTRACTSDIR=$HERE/src/test/solidity
OUTPUTDIR=$HERE/src/main/java
TESTOUTPUTDIR=$HERE/src/test/java
PACKAGE=net.consensys.gpact.sfccbc.soliditywrappers

#WEB3J=web3j
WEB3J=../web3j-rlp/codegen/build/install/codegen/bin/codegen


solc $CONTRACTSDIR/SimpleCrosschainControl.sol --allow-paths . --bin --abi --hashes --optimize -o $BUILDDIR --overwrite

$WEB3J solidity generate -a=$BUILDDIR/SimpleCrosschainControl.abi -b=$BUILDDIR/SimpleCrosschainControl.bin -o=$OUTPUTDIR -p=$PACKAGE



