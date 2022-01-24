#!/usr/bin/env bash
set -e
rm -rf build

HERE=applications/gpact/erc20bridge/javatest
BUILDDIR=$HERE/build
CONTRACTSDIR=applications/gpact/erc20bridge/src
OUTPUTDIR=$HERE/src/test/java
BASEPACKAGE=net.consensys.gpact.soliditywrappers.applications.gpact.erc20bridge
#WEB3J=web3j
WEB3J=../web3j-abi/codegen/build/install/codegen/bin/codegen

solc $CONTRACTSDIR/presets/LockableERC20PresetFixedSupply.sol --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/presets/LockableERC20PresetMinterPauser.sol --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/presets/LockableERC20PresetTraditionalPresetFixedSupply.sol --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/GpactERC20Bridge.sol --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/MockCbcForERC20Test.sol --bin --abi --optimize -o $BUILDDIR --overwrite

$WEB3J solidity generate -r -a=$BUILDDIR/LockableERC20PresetFixedSupply.abi -b=$BUILDDIR/LockableERC20PresetFixedSupply.bin -o=$OUTPUTDIR -p=$BASEPACKAGE.presets
$WEB3J solidity generate -r -a=$BUILDDIR/LockableERC20PresetMinterPauser.abi -b=$BUILDDIR/LockableERC20PresetMinterPauser.bin -o=$OUTPUTDIR -p=$BASEPACKAGE.presets
$WEB3J solidity generate -r -a=$BUILDDIR/LockableERC20PresetTraditionalPresetFixedSupply.abi -b=$BUILDDIR/LockableERC20PresetTraditionalPresetFixedSupply.bin -o=$OUTPUTDIR -p=$BASEPACKAGE.presets
$WEB3J solidity generate -r -a=$BUILDDIR/GpactERC20Bridge.abi -b=$BUILDDIR/GpactERC20Bridge.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -r -a=$BUILDDIR/MockCbcForERC20Test.abi -b=$BUILDDIR/MockCbcForERC20Test.bin -o=$OUTPUTDIR -p=$PACKAGE
