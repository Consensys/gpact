#!/usr/bin/env bash
set -e
rm -rf build

HERE=functioncall/interface
BUILDDIR=$HERE/build
CONTRACTSDIR=$HERE/src/main/solidity
TESTCONTRACTSDIR=$HERE/src/test/solidity
OUTPUTDIR=$HERE/src/main/java
TESTOUTPUTDIR=$HERE/src/test/java
PACKAGE=net.consensys.gpact.funcioninterfaces.soliditywrappers

#WEB3J=web3j
WEB3J=../web3j-rlp/codegen/build/install/codegen/bin/codegen


solc $TESTCONTRACTSDIR/HiddenParamDestTest.sol --allow-paths . --bin --abi --hashes --optimize -o $BUILDDIR --overwrite
solc $TESTCONTRACTSDIR/HiddenParamSourceTest.sol --allow-paths . --bin --abi --hashes --optimize -o $BUILDDIR --overwrite

$WEB3J solidity generate -a=$BUILDDIR/HiddenParamDestTest.abi -b=$BUILDDIR/HiddenParamDestTest.bin -o=$TESTOUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -a=$BUILDDIR/HiddenParamSourceTest.abi -b=$BUILDDIR/HiddenParamSourceTest.bin -o=$TESTOUTPUTDIR -p=$PACKAGE



