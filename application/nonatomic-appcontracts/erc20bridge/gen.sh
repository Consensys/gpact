#!/usr/bin/env bash
set -e
rm -rf build

HERE=application/nonatomic-appcontracts/erc20bridge
BUILDDIR=$HERE/build
CONTRACTSDIR=$HERE/src/main/solidity
TESTCONTRACTSDIR=$HERE/src/test/solidity
OUTPUTDIR=$HERE/src/main/java
TESTOUTPUTDIR=$HERE/src/test/java
PACKAGE=net.consensys.gpact.nonatomic.appcontracts.erc20bridge.soliditywrappers
#WEB3J=web3j
WEB3J=../web3j-abi/codegen/build/install/codegen/bin/codegen


solc $CONTRACTSDIR/SfcErc20Bridge.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite

$WEB3J solidity generate -a=$BUILDDIR/SfcErc20Bridge.abi -b=$BUILDDIR/SfcErc20Bridge.bin -o=$OUTPUTDIR -p=$PACKAGE


