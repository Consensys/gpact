#!/usr/bin/env bash
set -e
#rm -rf build

HERE=examples/sfc/erc721bridge/java
BUILDDIR=$HERE/build
CONTRACTSDIR=applications/sfc/erc721bridge/contracts/src
OUTPUTDIR=$HERE/src/main/java
PACKAGE=net.consensys.gpact.soliditywrappers.examples.sfc.erc721bridge
#WEB3J=web3j
WEB3J=../web3j-abi/codegen/build/install/codegen/bin/codegen

solc contracts/contracts/src/openzeppelin/token/ERC721/presets/ERC721PresetMinterPauserAutoId.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/ERC721AutoURIRemoteBlockchain.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/SfcErc721Bridge.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite

$WEB3J solidity generate -r -a=$BUILDDIR/ERC721PresetMinterPauserAutoId.abi -b=$BUILDDIR/ERC721PresetMinterPauserAutoId.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -r -a=$BUILDDIR/ERC721AutoURIRemoteBlockchain.abi -b=$BUILDDIR/ERC721AutoURIRemoteBlockchain.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -r -a=$BUILDDIR/SfcErc721Bridge.abi -b=$BUILDDIR/SfcErc721Bridge.bin -o=$OUTPUTDIR -p=$PACKAGE

