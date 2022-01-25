#!/usr/bin/env bash
set -e
#rm -rf build

HERE=examples/sfc/write/java
BUILDDIR=$HERE/build
CONTRACTSDIR=examples/sfc/write/src
OUTPUTDIR=$HERE/src/main/java
PACKAGE=net.consensys.gpact.soliditywrappers.examples.sfc.write
#WEB3J=web3j
WEB3J=../web3j-abi/codegen/build/install/codegen/bin/codegen


solc $CONTRACTSDIR/ContractA.sol --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/ContractB.sol --bin --abi --optimize -o $BUILDDIR --overwrite
# ls -al $BUILDDIR

$WEB3J solidity generate -a=$BUILDDIR/ContractA.abi -b=$BUILDDIR/ContractA.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -a=$BUILDDIR/ContractB.abi -b=$BUILDDIR/ContractB.bin -o=$OUTPUTDIR -p=$PACKAGE
