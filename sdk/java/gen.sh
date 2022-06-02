#!/usr/bin/env bash
set -e
#rm -rf build

HERE=sdk/java
BUILDDIR=$HERE/build
CONTRACTSDIR=contracts/contracts/src
OUTPUTDIR=$BUILDDIR/generated/sources/main/java
BASEPACKAGE=net.consensys.gpact
#WEB3J=web3j
WEB3J=../web3j-abi/codegen/build/install/codegen/bin/codegen

solc $CONTRACTSDIR/common/BlsSignatureTest.sol --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/common/EcdsaSignatureTest.sol --bin --abi --optimize -o $BUILDDIR --overwrite

solc $CONTRACTSDIR/functioncall/gpact/GpactCrosschainControl.sol --allow-paths . --bin --abi --hashes --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/functioncall/gpact/CallExecutionTreeTest.sol --allow-paths . --bin --abi --hashes --optimize -o $BUILDDIR --overwrite

solc $CONTRACTSDIR/functioncall/sfc/SimpleCrosschainControl.sol --allow-paths . --bin --abi --hashes --optimize -o $BUILDDIR --overwrite


solc $CONTRACTSDIR/messaging/common/MessagingRegistrar.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite

solc $CONTRACTSDIR/messaging/eventattest/EventAttestationVerifier.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite

solc $CONTRACTSDIR/messaging/txrootrelay/TxRootRelayVerifier.sol --allow-paths . --bin --abi --hashes --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/messaging/txrootrelay/TxReceiptsRootStorage.sol --allow-paths . --bin --abi --hashes --optimize -o $BUILDDIR --overwrite

solc $CONTRACTSDIR/messaging/eventrelay/EventRelayVerifier.sol --allow-paths . --bin --abi --hashes --optimize -o $BUILDDIR --overwrite


$WEB3J solidity generate -a=$BUILDDIR/BlsSignatureTest.abi -b=$BUILDDIR/BlsSignatureTest.bin -o=$OUTPUTDIR -p=$BASEPACKAGE.common
$WEB3J solidity generate -a=$BUILDDIR/EcdsaSignatureTest.abi -b=$BUILDDIR/EcdsaSignatureTest.bin -o=$OUTPUTDIR -p=$BASEPACKAGE.common

$WEB3J solidity generate -a=$BUILDDIR/GpactCrosschainControl.abi -b=$BUILDDIR/GpactCrosschainControl.bin -o=$OUTPUTDIR -p=$BASEPACKAGE.functioncall.gpact
$WEB3J solidity generate -a=$BUILDDIR/CallExecutionTreeTest.abi -b=$BUILDDIR/CallExecutionTreeTest.bin -o=$OUTPUTDIR -p=$BASEPACKAGE.functioncall.gpact

$WEB3J solidity generate -a=$BUILDDIR/SimpleCrosschainControl.abi -b=$BUILDDIR/SimpleCrosschainControl.bin -o=$OUTPUTDIR -p=$BASEPACKAGE.functioncall.sfc

$WEB3J solidity generate -a=$BUILDDIR/MessagingRegistrar.abi -b=$BUILDDIR/MessagingRegistrar.bin -o=$OUTPUTDIR -p=$BASEPACKAGE.messaging.common

$WEB3J solidity generate -a=$BUILDDIR/EventAttestationVerifier.abi -b=$BUILDDIR/EventAttestationVerifier.bin -o=$OUTPUTDIR -p=$BASEPACKAGE.messaging.eventattest

$WEB3J solidity generate -a=$BUILDDIR/TxRootRelayVerifier.abi -b=$BUILDDIR/TxRootRelayVerifier.bin -o=$OUTPUTDIR -p=$BASEPACKAGE.messaging.txrootrelay
$WEB3J solidity generate -a=$BUILDDIR/TxReceiptsRootStorage.abi -b=$BUILDDIR/TxReceiptsRootStorage.bin -o=$OUTPUTDIR -p=$BASEPACKAGE.messaging.txrootrelay

$WEB3J solidity generate -a=$BUILDDIR/EventRelayVerifier.abi -b=$BUILDDIR/EventRelayVerifier.bin -o=$OUTPUTDIR -p=$BASEPACKAGE.messaging.eventrelay
