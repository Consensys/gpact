#!/usr/bin/env bash
set -e
rm -rf build

HERE=application/atomic-appcontracts/erc20
BUILDDIR=$HERE/build
CONTRACTSDIR=$HERE/src/main/solidity
TESTCONTRACTSDIR=$HERE/src/test/solidity
OUTPUTDIR=$HERE/src/main/java
TESTOUTPUTDIR=$HERE/src/test/java
PACKAGE=net.consensys.gpact.appcontracts.atomic.erc20.soliditywrappers
#WEB3J=web3j
WEB3J=../web3j-abi/codegen/build/install/codegen/bin/codegen

solc $CONTRACTSDIR/presets/LockableERC20PresetFixedSupply.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/presets/LockableERC20PresetMinterPauser.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/presets/LockableERC20PresetTraditionalPresetFixedSupply.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/GpactERC20Bridge.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
solc $TESTCONTRACTSDIR/MockCbcForERC20Test.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite

$WEB3J solidity generate -r -a=$BUILDDIR/LockableERC20PresetFixedSupply.abi -b=$BUILDDIR/LockableERC20PresetFixedSupply.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -r -a=$BUILDDIR/LockableERC20PresetMinterPauser.abi -b=$BUILDDIR/LockableERC20PresetMinterPauser.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -r -a=$BUILDDIR/LockableERC20PresetTraditionalPresetFixedSupply.abi -b=$BUILDDIR/LockableERC20PresetTraditionalPresetFixedSupply.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -r -a=$BUILDDIR/GpactERC20Bridge.abi -b=$BUILDDIR/GpactERC20Bridge.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -r -a=$BUILDDIR/MockCbcForERC20Test.abi -b=$BUILDDIR/MockCbcForERC20Test.bin -o=$TESTOUTPUTDIR -p=$PACKAGE



solc $CONTRACTSDIR/token/ERC20/presets/ERC20PresetFixedSupply.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/token/ERC20/presets/ERC20PresetMinterPauser.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/token/ERC721/presets/ERC721PresetMinterPauserAutoId.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite

#solc $CONTRACTSDIR/proxy/transparent/ProxyAdmin.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
# solc $CONTRACTSDIR/proxy/transparent/TransparentUpgradeableProxy.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite

$WEB3J solidity generate -a=$BUILDDIR/ERC20PresetFixedSupply.abi -b=$BUILDDIR/ERC20PresetFixedSupply.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -a=$BUILDDIR/ERC20PresetMinterPauser.abi -b=$BUILDDIR/ERC20PresetMinterPauser.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -a=$BUILDDIR/ERC721PresetMinterPauserAutoId.abi -b=$BUILDDIR/ERC721PresetMinterPauserAutoId.bin -o=$OUTPUTDIR -p=$PACKAGE

# $WEB3J solidity generate -a=$BUILDDIR/ProxyAdmin.abi -b=$BUILDDIR/ProxyAdmin.bin -o=$OUTPUTDIR -p=$PACKAGE
# $WEB3J solidity generate -a=$BUILDDIR/TransparentUpgradeableProxy.abi -b=$BUILDDIR/TransparentUpgradeableProxy.bin -o=$OUTPUTDIR -p=$PACKAGE



