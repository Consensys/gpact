#!/usr/bin/env bash
set -e
#rm -rf build

HERE=performance/singlebc/hotel-train/java
BUILDDIR=$HERE/build
CONTRACTSDIR=$HERE/../contracts/src
OUTPUTDIR=$HERE/src/main/java
PACKAGE=net.consensys.gpact.soliditywrappers.performance.singlebc.hoteltrain
#WEB3J=web3j
WEB3J=../web3j-abi/codegen/build/install/codegen/bin/codegen


solc contracts/contracts/src/openzeppelin/token/ERC20/presets/ERC20PresetFixedSupply.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/TravelAgency.sol --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/Hotel.sol --bin --abi --optimize -o $BUILDDIR --overwrite
# ls -al $BUILDDIR

$WEB3J solidity generate -r -a=$BUILDDIR/ERC20PresetFixedSupply.abi -b=$BUILDDIR/ERC20PresetFixedSupply.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -a=$BUILDDIR/TravelAgency.abi -b=$BUILDDIR/TravelAgency.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -a=$BUILDDIR/Hotel.abi -b=$BUILDDIR/Hotel.bin -o=$OUTPUTDIR -p=$PACKAGE


#solc contracts/src/openzeppelin/token/ERC20/presets/ERC20PresetMinterPauser.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
#$WEB3J solidity generate -r -a=$BUILDDIR/ERC20PresetMinterPauser.abi -b=$BUILDDIR/ERC20PresetMinterPauser.bin -o=$OUTPUTDIR -p=$PACKAGE
