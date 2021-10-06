#!/usr/bin/env bash
set -e
rm -rf build

HERE=application/sfc-examples/tokenbridge
BUILDDIR=$HERE/build
CONTRACTSDIR=$HERE/src/main/solidity
TESTCONTRACTSDIR=$HERE/src/test/solidity
OUTPUTDIR=$HERE/src/main/java
TESTOUTPUTDIR=$HERE/src/test/java
PACKAGE=net.consensys.gpact.sfc.examples.tokenbridge.soliditywrappers
#WEB3J=web3j
WEB3J=../web3j-rlp/codegen/build/install/codegen/bin/codegen


solc $CONTRACTSDIR/SfcErc20MassConservationBridge.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite

$WEB3J solidity generate -a=$BUILDDIR/SfcErc20MassConservationBridge.abi -b=$BUILDDIR/SfcErc20MassConservationBridge.bin -o=$OUTPUTDIR -p=$PACKAGE


