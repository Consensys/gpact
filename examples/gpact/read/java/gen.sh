#!/usr/bin/env bash
set -e
#rm -rf build

HERE=examples/gpact/read/java
BUILDDIR=$HERE/build
CONTRACTSDIR=$HERE/../contracts/src
OUTPUTDIR=$BUILDDIR/generated/sources/main/java
PACKAGE=net.consensys.gpact.examples.gpact.read
#WEB3J=web3j
WEB3J=../web3j-abi/codegen/build/install/codegen/bin/codegen


solc $CONTRACTSDIR/ContractA.sol --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/ContractB.sol --bin --abi --optimize -o $BUILDDIR --overwrite
# ls -al $BUILDDIR

$WEB3J solidity generate -r -a=$BUILDDIR/ContractA.abi -b=$BUILDDIR/ContractA.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -r -a=$BUILDDIR/ContractB.abi -b=$BUILDDIR/ContractB.bin -o=$OUTPUTDIR -p=$PACKAGE


