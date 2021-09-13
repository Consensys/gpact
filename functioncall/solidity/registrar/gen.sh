#!/usr/bin/env bash
set -e
rm -rf build

HERE=functioncall/solidity/registrar
BUILDDIR=$HERE/build
CONTRACTSDIR=$HERE/src/main/solidity
OUTPUTDIR=$HERE/src/main/java
PACKAGE=net.consensys.gpact.registrar.soliditywrappers
#WEB3J=web3j
WEB3J=../web3j-rlp/codegen/build/install/codegen/bin/codegen


# compiling one file also compiles its dependendencies. We use overwrite to avoid the related warnings.
solc $CONTRACTSDIR/Registrar.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/VotingAlgMajority.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/VotingAlgMajorityWhoVoted.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
# ls -al $BUILDDIR

$WEB3J solidity generate -a=$BUILDDIR/Registrar.abi -b=$BUILDDIR/Registrar.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -a=$BUILDDIR/VotingAlgMajority.abi -b=$BUILDDIR/VotingAlgMajority.bin -o=$OUTPUTDIR -p=$PACKAGE
$WEB3J solidity generate -a=$BUILDDIR/VotingAlgMajorityWhoVoted.abi -b=$BUILDDIR/VotingAlgMajorityWhoVoted.bin -o=$OUTPUTDIR -p=$PACKAGE


