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

solc $CONTRACTSDIR/LockableERC20.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite

$WEB3J solidity generate -r -a=$BUILDDIR/LockableERC20.abi -b=$BUILDDIR/LockableERC20.bin -o=$OUTPUTDIR -p=$PACKAGE


