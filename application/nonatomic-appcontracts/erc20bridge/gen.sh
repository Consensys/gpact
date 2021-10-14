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
WEB3J=../web3j-rlp/codegen/build/install/codegen/bin/codegen


solc $CONTRACTSDIR/SfcErc20MassConservationBridge.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/SfcErc20MintingBurningBridge.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite

$WEB3J solidity generate -a=$BUILDDIR/SfcErc20MassConservationBridge.abi -b=$BUILDDIR/SfcErc20MassConservationBridge.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -a=$BUILDDIR/SfcErc20MintingBurningBridge.abi -b=$BUILDDIR/SfcErc20MintingBurningBridge.bin -o=$OUTPUTDIR -p=$PACKAGE


