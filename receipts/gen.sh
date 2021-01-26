#!/usr/bin/env bash
set -e
rm -rf build

HERE=receipts
BUILDDIR=$HERE/build
CONTRACTSDIR=$HERE/src/test/solidity
OUTPUTDIR=$HERE/src/test/java
PACKAGE=tech.pegasys.ltacfc.receipts.soliditywrappers
#WEB3J=web3j
WEB3J=../web3j-rlp/codegen/build/distributions/codegen-4.7.0-SNAPSHOT/bin/codegen


# compiling one file also compiles its dependendencies. We use overwrite to avoid the related warnings.
solc $CONTRACTSDIR/TestReceipts.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
# ls -al $BUILDDIR

$WEB3J solidity generate -a=$BUILDDIR/TestReceipts.abi -b=$BUILDDIR/TestReceipts.bin -o=$OUTPUTDIR -p=$PACKAGE


