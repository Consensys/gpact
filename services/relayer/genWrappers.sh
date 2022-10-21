#!/usr/bin/env bash
#
# This script re-generates the Solidity wrappers in the directory:
# ./services/relayer/internal/contracts
#
# Follow the steps here to install solc and abigen:
#  https://goethereumbook.org/smart-contract-compile/
# or use the script genWrappersInstallAbiGen.sh
#
set -e
#rm -rf build


HERE=./services/relayer
BUILDDIR=$HERE/build
CONTRACTSDIR=$HERE/../../contracts/contracts/src

GOFILE_OUTPUTDIR=$HERE/../../services/relayer/internal/contracts
GOFILE_APPLICATION=$GOFILE_OUTPUTDIR/application
GOFILE_FUNCTIONCALL=$GOFILE_OUTPUTDIR/functioncall
GOFILE_MESSAGING=$GOFILE_OUTPUTDIR/messaging

ABIGEN=~/go/bin/abigen


solc $CONTRACTSDIR/openzeppelin/token/ERC20/presets/ERC20PresetFixedSupply.sol --bin --abi --optimize -o $BUILDDIR --overwrite
$ABIGEN --abi $BUILDDIR/ERC20PresetFixedSupply.abi --bin $BUILDDIR/ERC20PresetFixedSupply.bin --pkg application --type ERC20FixedSupply --out $GOFILE_APPLICATION/erc20_fixed_supply.go

solc $CONTRACTSDIR/openzeppelin/token/ERC20/presets/ERC20PresetMinterPauser.sol --bin --abi --optimize -o $BUILDDIR --overwrite
$ABIGEN --abi $BUILDDIR/ERC20PresetMinterPauser.abi --bin $BUILDDIR/ERC20PresetMinterPauser.bin --pkg application --type ERC20MinterPauser --out $GOFILE_APPLICATION/erc20_minter_pauser.go

solc ./applications/gpact/erc20bridge/contracts/src/GpactERC20Bridge.sol --bin --abi --optimize -o $BUILDDIR --overwrite
$ABIGEN --abi $BUILDDIR/GpactERC20Bridge.abi --bin $BUILDDIR/GpactERC20Bridge.bin --pkg application --type GpactERC20Bridge --out $GOFILE_APPLICATION/gpact_erc20_bridge.go

solc ./applications/gpact/erc20bridge/contracts/src/presets/LockableERC20PresetFixedSupply.sol --bin --abi --optimize -o $BUILDDIR --overwrite
$ABIGEN --abi $BUILDDIR/LockableERC20PresetFixedSupply.abi --bin $BUILDDIR/LockableERC20PresetFixedSupply.bin --pkg application --type LockableERC20FixedSupply --out $GOFILE_APPLICATION/lockable_erc20_fixed_supply.go

solc ./examples/gpact/hotel-train/contracts/src/Hotel.sol --bin --abi --optimize -o $BUILDDIR --overwrite
$ABIGEN --abi $BUILDDIR/Hotel.abi --bin $BUILDDIR/Hotel.bin --pkg application --type Hotel --out $GOFILE_APPLICATION/hotel.go

solc ./examples/gpact/hotel-train/contracts/src/TravelAgency.sol --bin --abi --optimize -o $BUILDDIR --overwrite
$ABIGEN --abi $BUILDDIR/TravelAgency.abi --bin $BUILDDIR/TravelAgency.bin --pkg application --type TravelAgency --out $GOFILE_APPLICATION/travel_agency.go

solc ./applications/sfc/erc20bridge/contracts/src/SfcErc20Bridge.sol --bin --abi --optimize -o $BUILDDIR --overwrite
$ABIGEN --abi $BUILDDIR/SfcErc20Bridge.abi --bin $BUILDDIR/SfcErc20Bridge.bin --pkg application --type SfcERC20Bridge --out $GOFILE_APPLICATION/sfc_erc20_bridge.go

solc $CONTRACTSDIR/functioncall/gpact/GpactCrosschainControl.sol --bin --abi --optimize -o $BUILDDIR --overwrite
$ABIGEN --abi $BUILDDIR/GpactCrosschainControl.abi --bin $BUILDDIR/GpactCrosschainControl.bin --pkg functioncall --type Gpact --out $GOFILE_FUNCTIONCALL/gpact.go

solc $CONTRACTSDIR/functioncall/sfc/SimpleCrosschainControl.sol --bin --abi --optimize -o $BUILDDIR --overwrite
$ABIGEN --abi $BUILDDIR/SimpleCrosschainControl.abi --bin  $BUILDDIR/SimpleCrosschainControl.bin --pkg functioncall --type Sfc --out $GOFILE_FUNCTIONCALL/sfc.go

solc $CONTRACTSDIR/messaging/eventattest/EventAttestationVerifier.sol --bin --abi --optimize -o $BUILDDIR --overwrite
$ABIGEN --abi $BUILDDIR/EventAttestationVerifier.abi --bin $BUILDDIR/EventAttestationVerifier.bin --pkg messaging --type EventAttestationVerifier --out $GOFILE_MESSAGING/event_attestation_verifier.go

solc $CONTRACTSDIR/messaging/common/MessagingRegistrar.sol --bin --abi --optimize -o $BUILDDIR --overwrite
$ABIGEN --abi $BUILDDIR/MessagingRegistrar.abi --bin $BUILDDIR/MessagingRegistrar.bin --pkg messaging --type Registrar --out $GOFILE_MESSAGING/registrar.go

solc $CONTRACTSDIR/messaging/eventrelay/EventRelayVerifier.sol --bin --abi --optimize -o $BUILDDIR --overwrite
$ABIGEN --abi $BUILDDIR/EventRelayVerifier.abi --bin $BUILDDIR/EventRelayVerifier.bin --pkg messaging --type SignedEventStore --out $GOFILE_MESSAGING/signed_event_store.go
