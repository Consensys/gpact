#!/usr/bin/env bash
set -e
rm -rf build

HERE=application/nonatomic-appcontracts/erc721bridge
BUILDDIR=$HERE/build
CONTRACTSDIR=$HERE/src/main/solidity
TESTCONTRACTSDIR=$HERE/src/test/solidity
OUTPUTDIR=$HERE/src/main/java
TESTOUTPUTDIR=$HERE/src/test/java
PACKAGE=net.consensys.gpact.appcontracts.nonatomic.erc721bridge.soliditywrappers
#WEB3J=web3j
WEB3J=../web3j-rlp/codegen/build/install/codegen/bin/codegen


solc $CONTRACTSDIR/SfcErc721Bridge.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/ERC721CrosschainEndPoint.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite

$WEB3J solidity generate -a=$BUILDDIR/SfcErc721Bridge.abi -b=$BUILDDIR/SfcErc721Bridge.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -a=$BUILDDIR/ERC721CrosschainEndPoint.abi -b=$BUILDDIR/ERC721CrosschainEndPoint.bin -o=$OUTPUTDIR -p=$PACKAGE


