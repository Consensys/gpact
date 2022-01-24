#!/usr/bin/env bash
set -e
# rm -rf build

HERE=applications/sfc/erc20bridge/javatest
BUILDDIR=$HERE/build
CONTRACTSDIR=applications/sfc/erc20bridge/src
OUTPUTDIR=$HERE/src/test/java
PACKAGE=net.consensys.gpact.soliditywrappers.applications.sfc.erc20bridge
#WEB3J=web3j
WEB3J=../web3j-abi/codegen/build/install/codegen/bin/codegen


solc $CONTRACTSDIR/SfcErc20Bridge.sol --bin --abi --optimize -o $BUILDDIR --overwrite

$WEB3J solidity generate -a=$BUILDDIR/SfcErc20Bridge.abi -b=$BUILDDIR/SfcErc20Bridge.bin -o=$OUTPUTDIR -p=$PACKAGE


