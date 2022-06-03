#!/usr/bin/env bash
set -e
# rm -rf build

HERE=examples/gpact/nft
BUILDDIR=$HERE/build
CONTRACTSDIR=$HERE/contracts/src

solc $CONTRACTSDIR/CoinToken.sol --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/GameItem.sol --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/GpactNftBridge.sol --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/ListingStorage.sol --bin --abi --optimize -o $BUILDDIR --overwrite