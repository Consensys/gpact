#!/usr/bin/env bash
set -e
#rm -rf build

HERE=examples/sfc/erc20bridge/java
BUILDDIR=$HERE/build
CONTRACTSDIR=applications/sfc/erc20bridge/contracts/src
OUTPUTDIR=$BUILDDIR/generated/sources/main/java
PACKAGE=net.consensys.gpact.examples.sfc.erc20bridge
#WEB3J=web3j
WEB3J=../web3j-abi/codegen/build/install/codegen/bin/codegen

solc contracts/contracts/src/openzeppelin/token/ERC20/presets/ERC20PresetFixedSupply.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
solc contracts/contracts/src/openzeppelin/token/ERC20/presets/ERC20PresetMinterPauser.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/SfcErc20Bridge.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite

$WEB3J solidity generate -r -a=$BUILDDIR/ERC20PresetFixedSupply.abi -b=$BUILDDIR/ERC20PresetFixedSupply.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -r -a=$BUILDDIR/ERC20PresetMinterPauser.abi -b=$BUILDDIR/ERC20PresetMinterPauser.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -r -a=$BUILDDIR/SfcErc20Bridge.abi -b=$BUILDDIR/SfcErc20Bridge.bin -o=$OUTPUTDIR -p=$PACKAGE

