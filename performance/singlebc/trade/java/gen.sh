#!/usr/bin/env bash
set -e
#rm -rf build

HERE=performance/singlebc/trade/java
BUILDDIR=$HERE/build
CONTRACTSDIR=$HERE/../contracts/src
OUTPUTDIR=$BUILDDIR/generated/sources/main/java
PACKAGE=net.consensys.gpact.performance.singlebc.trade
#WEB3J=web3j
WEB3J=../web3j-abi/codegen/build/install/codegen/bin/codegen


solc $CONTRACTSDIR/TradeWallet.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/Balances.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/BusLogic.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/PriceOracle.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/Stock.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
# ls -al $BUILDDIR

$WEB3J solidity generate -a=$BUILDDIR/TradeWallet.abi -b=$BUILDDIR/TradeWallet.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -a=$BUILDDIR/Balances.abi -b=$BUILDDIR/Balances.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -a=$BUILDDIR/BusLogic.abi -b=$BUILDDIR/BusLogic.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -a=$BUILDDIR/PriceOracle.abi -b=$BUILDDIR/PriceOracle.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -a=$BUILDDIR/Stock.abi -b=$BUILDDIR/Stock.bin -o=$OUTPUTDIR -p=$PACKAGE


