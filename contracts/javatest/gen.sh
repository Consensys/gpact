#!/usr/bin/env bash
set -e
rm -rf build

HERE=application/atomic-appcontracts/lockablestorage
BUILDDIR=$HERE/build
CONTRACTSDIR=$HERE/src/main/solidity
TESTCONTRACTSDIR=$HERE/src/test/solidity
OUTPUTDIR=$HERE/src/main/java
TESTOUTPUTDIR=$HERE/src/test/java
PACKAGE=net.consensys.gpact.lockablestorage.soliditywrappers

#WEB3J=web3j
WEB3J=../web3j-abi/codegen/build/install/codegen/bin/codegen


solc $TESTCONTRACTSDIR/MockCbcForLockableStorageTest.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
solc $TESTCONTRACTSDIR/TestLockableStorageWrapper.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
solc $TESTCONTRACTSDIR/TestLockableStorageWrapperAllValues.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite

$WEB3J solidity generate -a=$BUILDDIR/MockCbcForLockableStorageTest.abi -b=$BUILDDIR/MockCbcForLockableStorageTest.bin -o=$TESTOUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -a=$BUILDDIR/TestLockableStorageWrapper.abi -b=$BUILDDIR/TestLockableStorageWrapper.bin -o=$TESTOUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -a=$BUILDDIR/TestLockableStorageWrapperAllValues.abi -b=$BUILDDIR/TestLockableStorageWrapperAllValues.bin -o=$TESTOUTPUTDIR -p=$PACKAGE


solc $TESTCONTRACTSDIR/HiddenParamDestTest.sol --allow-paths . --bin --abi --hashes --optimize -o $BUILDDIR --overwrite
solc $TESTCONTRACTSDIR/HiddenParamSourceTest.sol --allow-paths . --bin --abi --hashes --optimize -o $BUILDDIR --overwrite

$WEB3J solidity generate -a=$BUILDDIR/HiddenParamDestTest.abi -b=$BUILDDIR/HiddenParamDestTest.bin -o=$TESTOUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -a=$BUILDDIR/HiddenParamSourceTest.abi -b=$BUILDDIR/HiddenParamSourceTest.bin -o=$TESTOUTPUTDIR -p=$PACKAGE



# compiling one file also compiles its dependencies. We use overwrite to avoid the related warnings.
solc $CONTRACTSDIR/CrosschainVerifierSign.sol --allow-paths . --bin --abi --hashes --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/AttestorSignRegistrar.sol --allow-paths . --bin --abi --hashes --optimize -o $BUILDDIR --overwrite

$WEB3J solidity generate -a=$BUILDDIR/CrosschainVerifierSign.abi -b=$BUILDDIR/CrosschainVerifierSign.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -a=$BUILDDIR/AttestorSignRegistrar.abi -b=$BUILDDIR/AttestorSignRegistrar.bin -o=$OUTPUTDIR -p=$PACKAGE



solc $CONTRACTSDIR/SignedEventStore.sol --allow-paths . --bin --abi --hashes --optimize -o $BUILDDIR --overwrite
solc $TESTCONTRACTSDIR/AppTest.sol --allow-paths . --bin --abi --hashes --optimize -o $BUILDDIR --overwrite

$WEB3J solidity generate -a=$BUILDDIR/SignedEventStore.abi -b=$BUILDDIR/SignedEventStore.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -r -a=$BUILDDIR/AppTest.abi -b=$BUILDDIR/AppTest.bin -o=$TESTOUTPUTDIR -p=$PACKAGE


solc $CONTRACTSDIR/messaging/txrootrelay/TestReceipts.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/messaging/txrootrelay/TestEvents.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite

$WEB3J solidity generate -a=$BUILDDIR/TestReceipts.abi -b=$BUILDDIR/TestReceipts.bin -o=$OUTPUTDIR -p=$BASEPACKAGE.messaging.txrootrelay
$WEB3J solidity generate -a=$BUILDDIR/TestEvents.abi -b=$BUILDDIR/TestEvents.bin -o=$OUTPUTDIR -p=$BASEPACKAGE.messaging.txrootrelay
