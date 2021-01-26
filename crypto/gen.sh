#!/usr/bin/env bash
set -e
rm -rf build

HERE=crypto
BUILDDIR=$HERE/build
CONTRACTSDIR=$HERE/src/main/solidity
OUTPUTDIR=$HERE/src/main/java
PACKAGE=tech.pegasys.ltacfc.soliditywrappers
#WEB3J=web3j
WEB3J=../web3j-rlp/codegen/build/distributions/codegen-4.7.0-SNAPSHOT/bin/codegen


# compiling one file also compiles its dependendencies. We use overwrite to avoid the related warnings.
solc $CONTRACTSDIR/BlsSignatureTest.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/EcdsaSignatureTest.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
# ls -al $BUILDDIR

$WEB3J solidity generate -a=$BUILDDIR/BlsSignatureTest.abi -b=$BUILDDIR/BlsSignatureTest.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -a=$BUILDDIR/EcdsaSignatureTest.abi -b=$BUILDDIR/EcdsaSignatureTest.bin -o=$OUTPUTDIR -p=$PACKAGE


