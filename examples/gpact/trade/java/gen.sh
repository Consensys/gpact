#!/usr/bin/env bash
set -e
#rm -rf build

HERE=examples/gpact/trade/java
BUILDDIR=$HERE/build
CONTRACTSDIR=$HERE/../contracts/src
OUTPUTDIR=$HERE/src/main/java
PACKAGE=net.consensys.gpact.soliditywrappers.examples.gpact.trade
#WEB3J=web3j
WEB3J=../web3j-abi/codegen/build/install/codegen/bin/codegen


# compiling one file also compiles its dependendencies. We use overwrite to avoid the related warnings.
solc $CONTRACTSDIR/TradeWallet.sol  --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/Balances.sol  --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/BusLogic.sol  --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/PriceOracle.sol  --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/Stock.sol --bin --abi --optimize -o $BUILDDIR --overwrite
# ls -al $BUILDDIR

$WEB3J solidity generate -r -a=$BUILDDIR/TradeWallet.abi -b=$BUILDDIR/TradeWallet.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -r -a=$BUILDDIR/Balances.abi -b=$BUILDDIR/Balances.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -r -a=$BUILDDIR/BusLogic.abi -b=$BUILDDIR/BusLogic.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -r -a=$BUILDDIR/PriceOracle.abi -b=$BUILDDIR/PriceOracle.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -r -a=$BUILDDIR/Stock.abi -b=$BUILDDIR/Stock.bin -o=$OUTPUTDIR -p=$PACKAGE


