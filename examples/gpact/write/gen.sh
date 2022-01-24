#!/usr/bin/env bash
set -e
rm -rf build

HERE=application/gpact-examples/write
BUILDDIR=$HERE/build
CONTRACTSDIR=$HERE/src/main/solidity
TESTCONTRACTSDIR=$HERE/src/test/solidity
OUTPUTDIR=$HERE/src/main/java
TESTOUTPUTDIR=$HERE/src/test/java
PACKAGE=net.consensys.gpact.examples.write.soliditywrappers
#WEB3J=web3j
WEB3J=../web3j-abi/codegen/build/install/codegen/bin/codegen


# compiling one file also compiles its dependendencies. We use overwrite to avoid the related warnings.
solc $CONTRACTSDIR/ContractA.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/ContractB.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
# ls -al $BUILDDIR

$WEB3J solidity generate -r -a=$BUILDDIR/ContractA.abi -b=$BUILDDIR/ContractA.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -r -a=$BUILDDIR/ContractB.abi -b=$BUILDDIR/ContractB.bin -o=$OUTPUTDIR -p=$PACKAGE


