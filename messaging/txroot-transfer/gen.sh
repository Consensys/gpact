#!/usr/bin/env bash
set -e
rm -rf build

HERE=messaging/txroot-transfer
BUILDDIR=$HERE/build
CONTRACTSDIR=$HERE/src/main/solidity
OUTPUTDIR=$HERE/src/main/java
TESTCONTRACTSDIR=$HERE/src/test/solidity
TESTOUTPUTDIR=$HERE/src/test/java
PACKAGE=net.consensys.gpact.txroot.soliditywrappers
#WEB3J=web3j
WEB3J=../web3j-rlp/codegen/build/install/codegen/bin/codegen


solc $TESTCONTRACTSDIR/TestReceipts.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
solc $TESTCONTRACTSDIR/TestEvents.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/CrosschainVerifierTxRoot.sol --allow-paths . --bin --abi --hashes --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/TxReceiptsRootStorage.sol --allow-paths . --bin --abi --hashes --optimize -o $BUILDDIR --overwrite


$WEB3J solidity generate -a=$BUILDDIR/TestReceipts.abi -b=$BUILDDIR/TestReceipts.bin -o=$TESTOUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -a=$BUILDDIR/TestEvents.abi -b=$BUILDDIR/TestEvents.bin -o=$TESTOUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -a=$BUILDDIR/CrosschainVerifierTxRoot.abi -b=$BUILDDIR/CrosschainVerifierTxRoot.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -a=$BUILDDIR/TxReceiptsRootStorage.abi -b=$BUILDDIR/TxReceiptsRootStorage.bin -o=$OUTPUTDIR -p=$PACKAGE

