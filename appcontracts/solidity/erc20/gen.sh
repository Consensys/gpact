#!/usr/bin/env bash
set -e
rm -rf build

HERE=appcontracts/solidity/erc20
BUILDDIR=$HERE/build
CONTRACTSDIR=$HERE/src/main/solidity
TESTCONTRACTSDIR=$HERE/src/test/solidity
OUTPUTDIR=$HERE/src/main/java
TESTOUTPUTDIR=$HERE/src/test/java
PACKAGE=net.consensys.gpact.appcontracts.erc20.soliditywrappers
#WEB3J=web3j
WEB3J=../web3j-rlp/codegen/build/install/codegen/bin/codegen

solc $CONTRACTSDIR/CrosschainERC20PresetFixedSupply.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/LockableERC20PresetFixedSupply.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/CrosschainERC20.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/TraditionalERC20Adaptor.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
solc $TESTCONTRACTSDIR/MockCbcForERC20Test.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite

$WEB3J solidity generate -r -a=$BUILDDIR/CrosschainERC20PresetFixedSupply.abi -b=$BUILDDIR/CrosschainERC20PresetFixedSupply.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -r -a=$BUILDDIR/LockableERC20PresetFixedSupply.abi -b=$BUILDDIR/LockableERC20PresetFixedSupply.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -r -a=$BUILDDIR/CrosschainERC20.abi -b=$BUILDDIR/CrosschainERC20.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -r -a=$BUILDDIR/TraditionalERC20Adaptor.abi -b=$BUILDDIR/TraditionalERC20Adaptor.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -r -a=$BUILDDIR/MockCbcForERC20Test.abi -b=$BUILDDIR/MockCbcForERC20Test.bin -o=$TESTOUTPUTDIR -p=$PACKAGE
