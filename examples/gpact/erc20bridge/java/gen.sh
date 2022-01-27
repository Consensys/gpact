#!/usr/bin/env bash
set -e
#rm -rf build

HERE=examples/gpact/erc20bridge/java
BUILDDIR=$HERE/build
CONTRACTSDIR=applications/gpact/erc20bridge/contracts/src
OUTPUTDIR=$HERE/src/main/java
PACKAGE=net.consensys.gpact.soliditywrappers.examples.gpact.erc20bridge
#WEB3J=web3j
WEB3J=../web3j-abi/codegen/build/install/codegen/bin/codegen

solc $CONTRACTSDIR/presets/LockableERC20PresetFixedSupply.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/GpactERC20Bridge.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite

$WEB3J solidity generate -r -a=$BUILDDIR/LockableERC20PresetFixedSupply.abi -b=$BUILDDIR/LockableERC20PresetFixedSupply.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -r -a=$BUILDDIR/GpactERC20Bridge.abi -b=$BUILDDIR/GpactERC20Bridge.bin -o=$OUTPUTDIR -p=$PACKAGE

