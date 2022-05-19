#!/usr/bin/env bash
set -e
#rm -rf build

HERE=performance/singlebc/write/java
BUILDDIR=$HERE/build
CONTRACTSDIR=$HERE/../contracts/src
OUTPUTDIR=$BUILDDIR/generated/sources/main/java
PACKAGE=net.consensys.gpact.performance.singlebc.write
#WEB3J=web3j
WEB3J=../web3j-abi/codegen/build/install/codegen/bin/codegen


solc $CONTRACTSDIR/ContractA.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/ContractB.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
# ls -al $BUILDDIR

$WEB3J solidity generate -a=$BUILDDIR/ContractA.abi -b=$BUILDDIR/ContractA.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -a=$BUILDDIR/ContractB.abi -b=$BUILDDIR/ContractB.bin -o=$OUTPUTDIR -p=$PACKAGE


