#!/usr/bin/env bash
set -e
rm -rf build

HERE=examples/trade
BUILDDIR=$HERE/build
CONTRACTSDIR=$HERE/src/main/solidity
TESTCONTRACTSDIR=$HERE/src/test/solidity
OUTPUTDIR=$HERE/src/main/java
TESTOUTPUTDIR=$HERE/src/test/java
PACKAGE=tech.pegasys.ltacfc.examples.trade.soliditywrappers
#WEB3J=web3j
WEB3J=../web3j-rlp/codegen/build/distributions/codegen-4.7.0-SNAPSHOT/bin/codegen


# compiling one file also compiles its dependendencies. We use overwrite to avoid the related warnings.
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


