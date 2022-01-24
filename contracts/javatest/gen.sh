#!/usr/bin/env bash
set -e
# rm -rf build

HERE=contracts/javatest
BUILDDIR=$HERE/build
CONTRACTSDIR=contracts/src/
OUTPUTDIR=$HERE/src/test/java
BASEPACKAGE=net.consensys.gpact.soliditywrappers

#WEB3J=web3j
WEB3J=../web3j-abi/codegen/build/install/codegen/bin/codegen


solc $CONTRACTSDIR/application/lockablestorage/MockCbcForLockableStorageTest.sol --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/application/lockablestorage/TestLockableStorageWrapper.sol --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/application/lockablestorage/TestLockableStorageWrapperAllValues.sol --bin --abi --optimize -o $BUILDDIR --overwrite

solc $CONTRACTSDIR/functioncall/interface/HiddenParamDestTest.sol --allow-paths . --bin --abi --hashes --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/functioncall/interface/HiddenParamSourceTest.sol --allow-paths . --bin --abi --hashes --optimize -o $BUILDDIR --overwrite

solc $CONTRACTSDIR/messaging/common/MessagingRegistrar.sol --allow-paths . --bin --abi --hashes --optimize -o $BUILDDIR --overwrite

solc $CONTRACTSDIR/messaging/eventrelay/EventRelayVerifier.sol --allow-paths . --bin --abi --hashes --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/messaging/eventrelay/EventRelayAppTest.sol --allow-paths . --bin --abi --hashes --optimize -o $BUILDDIR --overwrite

solc $CONTRACTSDIR/messaging/txrootrelay/TestReceipts.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/messaging/txrootrelay/TestEvents.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite



$WEB3J solidity generate -a=$BUILDDIR/MockCbcForLockableStorageTest.abi -b=$BUILDDIR/MockCbcForLockableStorageTest.bin -o=$OUTPUTDIR -p=$BASEPACKAGE.application.lockablestorage
$WEB3J solidity generate -a=$BUILDDIR/TestLockableStorageWrapper.abi -b=$BUILDDIR/TestLockableStorageWrapper.bin -o=$OUTPUTDIR -p=$BASEPACKAGE.application.lockablestorage
$WEB3J solidity generate -a=$BUILDDIR/TestLockableStorageWrapperAllValues.abi -b=$BUILDDIR/TestLockableStorageWrapperAllValues.bin -o=$OUTPUTDIR -p=$BASEPACKAGE.application.lockablestorage

$WEB3J solidity generate -a=$BUILDDIR/HiddenParamDestTest.abi -b=$BUILDDIR/HiddenParamDestTest.bin -o=$OUTPUTDIR -p=$BASEPACKAGE.functioncall
$WEB3J solidity generate -a=$BUILDDIR/HiddenParamSourceTest.abi -b=$BUILDDIR/HiddenParamSourceTest.bin -o=$OUTPUTDIR -p=$BASEPACKAGE.functioncall

$WEB3J solidity generate -a=$BUILDDIR/MessagingRegistrar.abi -b=$BUILDDIR/MessagingRegistrar.bin -o=$OUTPUTDIR -p=$BASEPACKAGE.messaging.common

$WEB3J solidity generate -a=$BUILDDIR/EventRelayVerifier.abi -b=$BUILDDIR/EventRelayVerifier.bin -o=$OUTPUTDIR -p=$BASEPACKAGE.messaging.eventrelay
$WEB3J solidity generate -r -a=$BUILDDIR/EventRelayAppTest.abi -b=$BUILDDIR/EventRelayAppTest.bin -o=$OUTPUTDIR -p=$BASEPACKAGE.messaging.eventrelay

$WEB3J solidity generate -a=$BUILDDIR/TestReceipts.abi -b=$BUILDDIR/TestReceipts.bin -o=$OUTPUTDIR -p=$BASEPACKAGE.messaging.txrootrelay
$WEB3J solidity generate -a=$BUILDDIR/TestEvents.abi -b=$BUILDDIR/TestEvents.bin -o=$OUTPUTDIR -p=$BASEPACKAGE.messaging.txrootrelay

