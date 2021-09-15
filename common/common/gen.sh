#!/usr/bin/env bash
set -e
rm -rf build

HERE=common/common
BUILDDIR=$HERE/build
TESTCONTRACTSDIR=$HERE/src/test/solidity
TESTOUTPUTDIR=$HERE/src/test/java
PACKAGE=net.consensys.gpact.test.soliditywrappers
#WEB3J=web3j
WEB3J=../web3j-rlp/codegen/build/install/codegen/bin/codegen


# compiling one file also compiles its dependendencies. We use overwrite to avoid the related warnings.
solc $TESTCONTRACTSDIR/BlsSignatureTest.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
solc $TESTCONTRACTSDIR/EcdsaSignatureTest.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
# ls -al $BUILDDIR

$WEB3J solidity generate -a=$BUILDDIR/BlsSignatureTest.abi -b=$BUILDDIR/BlsSignatureTest.bin -o=$TESTOUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -a=$BUILDDIR/EcdsaSignatureTest.abi -b=$BUILDDIR/EcdsaSignatureTest.bin -o=$TESTOUTPUTDIR -p=$PACKAGE


