#!/usr/bin/env bash
set -e
# rm -rf build

HERE=examples/gpact/hotel-train/java
BUILDDIR=$HERE/build
CONTRACTSDIR=$HERE/../contracts/src
OUTPUTDIR=$HERE/src/main/java
PACKAGE=net.consensys.gpact.soliditywrappers.examples.gpact.hoteltrain
#WEB3J=web3j
WEB3J=../web3j-abi/codegen/build/install/codegen/bin/codegen


solc $CONTRACTSDIR/TravelAgency.sol --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/Hotel.sol --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/Train.sol --bin --abi --optimize -o $BUILDDIR --overwrite
# ls -al $BUILDDIR

$WEB3J solidity generate -r -a=$BUILDDIR/TravelAgency.abi -b=$BUILDDIR/TravelAgency.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -r -a=$BUILDDIR/Hotel.abi -b=$BUILDDIR/Hotel.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -r -a=$BUILDDIR/Train.abi -b=$BUILDDIR/Train.bin -o=$OUTPUTDIR -p=$PACKAGE

solc applications/gpact/erc20bridge/contracts/src/presets/LockableERC20PresetFixedSupply.sol --bin --abi --optimize -o $BUILDDIR --overwrite
$WEB3J solidity generate -r -a=$BUILDDIR/LockableERC20PresetFixedSupply.abi -b=$BUILDDIR/LockableERC20PresetFixedSupply.bin -o=$OUTPUTDIR -p=$PACKAGE

