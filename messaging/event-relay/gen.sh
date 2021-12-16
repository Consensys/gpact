#!/usr/bin/env bash
set -e
rm -rf build

HERE=messaging/event-relay
BUILDDIR=$HERE/build
CONTRACTSDIR=$HERE/src/main/solidity
OUTPUTDIR=$HERE/src/main/java
PACKAGE=net.consensys.gpact.eventrelay.soliditywrappers

#WEB3J=web3j
WEB3J=../web3j-abi/codegen/build/install/codegen/bin/codegen


# compiling one file also compiles its dependencies. We use overwrite to avoid the related warnings.
solc $CONTRACTSDIR/SignedEventStore.sol --allow-paths . --bin --abi --hashes --optimize -o $BUILDDIR --overwrite

$WEB3J solidity generate -a=$BUILDDIR/SignedEventStore.abi -b=$BUILDDIR/SignedEventStore.bin -o=$OUTPUTDIR -p=$PACKAGE



