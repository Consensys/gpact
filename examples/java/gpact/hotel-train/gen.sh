#!/usr/bin/env bash
set -e
rm -rf build

HERE=application/gpact-examples/hotel-train
BUILDDIR=$HERE/build
CONTRACTSDIR=$HERE/src/main/solidity
TESTCONTRACTSDIR=$HERE/src/test/solidity
OUTPUTDIR=$HERE/src/main/java
TESTOUTPUTDIR=$HERE/src/test/java
PACKAGE=net.consensys.gpact.examples.hoteltrain.soliditywrappers
#WEB3J=web3j
WEB3J=../web3j-abi/codegen/build/install/codegen/bin/codegen


# compiling one file also compiles its dependencies. We use overwrite to avoid the related warnings.
solc $CONTRACTSDIR/TravelAgency.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/Hotel.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
# ls -al $BUILDDIR

$WEB3J solidity generate -r -a=$BUILDDIR/TravelAgency.abi -b=$BUILDDIR/TravelAgency.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -r -a=$BUILDDIR/Hotel.abi -b=$BUILDDIR/Hotel.bin -o=$OUTPUTDIR -p=$PACKAGE


