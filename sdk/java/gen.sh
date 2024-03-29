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
solc $CONTRACTSDIR/functioncall/gpact/CallExecutionTreeV1Test.sol --allow-paths . --bin --abi --hashes --optimize -o $BUILDDIR --overwrite

solc $CONTRACTSDIR/functioncall/gpactv2/GpactV2CrosschainControl.sol --allow-paths . --bin --abi --hashes --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/functioncall/gpactv2/CallExecutionTreeV2Test.sol --allow-paths . --bin --abi --hashes --optimize -o $BUILDDIR --overwrite

solc $CONTRACTSDIR/functioncall/sfc/SimpleCrosschainControl.sol --allow-paths . --bin --abi --hashes --optimize -o $BUILDDIR --overwrite


solc $CONTRACTSDIR/messaging/common/MessagingRegistrar.sol --allow-paths . --bin --abi --hashes --optimize -o $BUILDDIR --overwrite

solc $CONTRACTSDIR/messaging/eventattest/EventAttestationVerifier.sol --allow-paths . --bin --abi --hashes --optimize -o $BUILDDIR --overwrite

solc $CONTRACTSDIR/messaging/txrootrelay/TxRootRelayVerifier.sol --allow-paths . --bin --abi --hashes --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/messaging/txrootrelay/TxReceiptsRootStorage.sol --allow-paths . --bin --abi --hashes --optimize -o $BUILDDIR --overwrite

solc $CONTRACTSDIR/messaging/eventrelay/EventRelayVerifier.sol --allow-paths . --bin --abi --hashes --optimize -o $BUILDDIR --overwrite


$WEB3J solidity generate -a=$BUILDDIR/BlsSignatureTest.abi -b=$BUILDDIR/BlsSignatureTest.bin -o=$OUTPUTDIR -p=$BASEPACKAGE.common
$WEB3J solidity generate -a=$BUILDDIR/EcdsaSignatureTest.abi -b=$BUILDDIR/EcdsaSignatureTest.bin -o=$OUTPUTDIR -p=$BASEPACKAGE.common

$WEB3J solidity generate -a=$BUILDDIR/GpactCrosschainControl.abi -b=$BUILDDIR/GpactCrosschainControl.bin -o=$OUTPUTDIR -p=$BASEPACKAGE.functioncall.gpact
$WEB3J solidity generate -a=$BUILDDIR/CallExecutionTreeV1Test.abi -b=$BUILDDIR/CallExecutionTreeV1Test.bin -o=$OUTPUTDIR -p=$BASEPACKAGE.functioncall.gpact

$WEB3J solidity generate -a=$BUILDDIR/GpactV2CrosschainControl.abi -b=$BUILDDIR/GpactV2CrosschainControl.bin -o=$OUTPUTDIR -p=$BASEPACKAGE.functioncall.gpact
$WEB3J solidity generate -a=$BUILDDIR/CallExecutionTreeV2Test.abi -b=$BUILDDIR/CallExecutionTreeV2Test.bin -o=$OUTPUTDIR -p=$BASEPACKAGE.functioncall.gpact

$WEB3J solidity generate -a=$BUILDDIR/SimpleCrosschainControl.abi -b=$BUILDDIR/SimpleCrosschainControl.bin -o=$OUTPUTDIR -p=$BASEPACKAGE.functioncall.sfc

$WEB3J solidity generate -a=$BUILDDIR/MessagingRegistrar.abi -b=$BUILDDIR/MessagingRegistrar.bin -o=$OUTPUTDIR -p=$BASEPACKAGE.messaging.common

# -r to add functions to get ABI encoding of a function call
# -B to add functions to have transaction functions for view call functions
$WEB3J solidity generate -B -r -a=$BUILDDIR/EventAttestationVerifier.abi -b=$BUILDDIR/EventAttestationVerifier.bin -o=$OUTPUTDIR -p=$BASEPACKAGE.messaging.eventattest

$WEB3J solidity generate -a=$BUILDDIR/TxRootRelayVerifier.abi -b=$BUILDDIR/TxRootRelayVerifier.bin -o=$OUTPUTDIR -p=$BASEPACKAGE.messaging.txrootrelay
$WEB3J solidity generate -a=$BUILDDIR/TxReceiptsRootStorage.abi -b=$BUILDDIR/TxReceiptsRootStorage.bin -o=$OUTPUTDIR -p=$BASEPACKAGE.messaging.txrootrelay

$WEB3J solidity generate -a=$BUILDDIR/EventRelayVerifier.abi -b=$BUILDDIR/EventRelayVerifier.bin -o=$OUTPUTDIR -p=$BASEPACKAGE.messaging.eventrelay
