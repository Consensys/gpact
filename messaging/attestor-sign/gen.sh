#!/usr/bin/env bash
set -e
rm -rf build

HERE=messaging/attestor-sign
BUILDDIR=$HERE/build
CONTRACTSDIR=$HERE/src/main/solidity
OUTPUTDIR=$HERE/src/main/java
PACKAGE=net.consensys.gpact.cbc.soliditywrappers

#WEB3J=web3j
WEB3J=../web3j-rlp/codegen/build/install/codegen/bin/codegen


# compiling one file also compiles its dependencies. We use overwrite to avoid the related warnings.
solc $CONTRACTSDIR/CrosschainVerifierSign.sol --allow-paths . --bin --abi --hashes --optimize -o $BUILDDIR --overwrite

$WEB3J solidity generate -a=$BUILDDIR/CrosschainVerifierSign.abi -b=$BUILDDIR/CrosschainVerifierSign.bin -o=$OUTPUTDIR -p=$PACKAGE



