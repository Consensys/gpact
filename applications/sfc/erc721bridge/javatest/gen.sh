#!/usr/bin/env bash
set -e
# rm -rf build

HERE=applications/sfc/erc721bridge/javatest
BUILDDIR=$HERE/build
CONTRACTSDIR=applications/sfc/erc721bridge/src
OUTPUTDIR=$HERE/src/test/java
PACKAGE=net.consensys.gpact.soliditywrappers.applications.sfc.erc721bridge
#WEB3J=web3j
WEB3J=../web3j-abi/codegen/build/install/codegen/bin/codegen


solc $CONTRACTSDIR/SfcErc721Bridge.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/ERC721AutoURIRemoteBlockchain.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/ERC721CustomURIRemoteBlockchain.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite

$WEB3J solidity generate -a=$BUILDDIR/SfcErc721Bridge.abi -b=$BUILDDIR/SfcErc721Bridge.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -a=$BUILDDIR/ERC721AutoURIRemoteBlockchain.abi -b=$BUILDDIR/ERC721AutoURIRemoteBlockchain.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -a=$BUILDDIR/ERC721CustomURIRemoteBlockchain.abi -b=$BUILDDIR/ERC721CustomURIRemoteBlockchain.bin -o=$OUTPUTDIR -p=$PACKAGE


