#!/usr/bin/env bash
set -e
rm -rf build

HERE=applications/20acts/javatest
BUILDDIR=$HERE/build
CONTRACTSDIR=$HERE/../contracts/src
OUTPUTDIR=$HERE/src/test/java
BASEPACKAGE=net.consensys.gpact.soliditywrappers.applications.twentyacts
#WEB3J=web3j
WEB3J=../web3j-abi/codegen/build/install/codegen/bin/codegen

solc $CONTRACTSDIR/TwentyActs.sol --bin --abi --optimize -o $BUILDDIR --overwrite
solc contracts/contracts/src/openzeppelin/token/ERC20/presets/ERC20PresetFixedSupply.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite

$WEB3J solidity generate -r -a=$BUILDDIR/TwentyActs.abi -b=$BUILDDIR/TwentyActs.bin -o=$OUTPUTDIR -p=$BASEPACKAGE
$WEB3J solidity generate -r -a=$BUILDDIR/ERC20PresetFixedSupply.abi -b=$BUILDDIR/ERC20PresetFixedSupply.bin -o=$OUTPUTDIR -p=$BASEPACKAGE
