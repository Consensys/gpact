#!/usr/bin/env bash
set -e
rm -rf build

HERE=contracts/solidity/crossblockchaincontrol
BUILDDIR=$HERE/build
CONTRACTSDIR=$HERE/src/main/solidity
OUTPUTDIR=$HERE/src/main/java
PACKAGE=net.consensys.gpact.cbc.soliditywrappers

#WEB3J=web3j
WEB3J=../web3j-rlp/codegen/build/install/codegen/bin/codegen


# compiling one file also compiles its dependencies. We use overwrite to avoid the related warnings.
solc $CONTRACTSDIR/CrosschainControl.sol --allow-paths . --bin --abi --hashes --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/CrosschainVerifierSign.sol --allow-paths . --bin --abi --hashes --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/CrosschainVerifierTxRoot.sol --allow-paths . --bin --abi --hashes --optimize -o $BUILDDIR --overwrite

$WEB3J solidity generate -a=$BUILDDIR/CrosschainControl.abi -b=$BUILDDIR/CrosschainControl.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -a=$BUILDDIR/CrosschainVerifierSign.abi -b=$BUILDDIR/CrosschainVerifierSign.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -a=$BUILDDIR/CrosschainVerifierTxRoot.abi -b=$BUILDDIR/CrosschainVerifierTxRoot.bin -o=$OUTPUTDIR -p=$PACKAGE



