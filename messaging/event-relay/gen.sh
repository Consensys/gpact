#!/usr/bin/env bash
set -e
rm -rf build

HERE=messaging/event-relay
BUILDDIR=$HERE/build
CONTRACTSDIR=$HERE/src/main/solidity
TESTCONTRACTSDIR=$HERE/src/test/solidity
OUTPUTDIR=$HERE/src/main/java
TESTOUTPUTDIR=$HERE/src/test/java
PACKAGE=net.consensys.gpact.eventrelay.soliditywrappers

#WEB3J=web3j
WEB3J=../web3j-abi/codegen/build/install/codegen/bin/codegen


solc $CONTRACTSDIR/SignedEventStore.sol --allow-paths . --bin --abi --hashes --optimize -o $BUILDDIR --overwrite
solc $TESTCONTRACTSDIR/AppTest.sol --allow-paths . --bin --abi --hashes --optimize -o $BUILDDIR --overwrite

$WEB3J solidity generate -a=$BUILDDIR/SignedEventStore.abi -b=$BUILDDIR/SignedEventStore.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -r -a=$BUILDDIR/AppTest.abi -b=$BUILDDIR/AppTest.bin -o=$TESTOUTPUTDIR -p=$PACKAGE



