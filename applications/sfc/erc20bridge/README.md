ERC 20 Bridge Contracts for Non-Atomic Function Calls
----------------
The directory contains ERC 20 bridge contracts that can 
be used with non-atomic function call protocols.

The contracts are:
* SfcErc20BridgeInterface: Interface for all non-atomic ERC 20 bridges.
* AbstractSfcErc20Bridge: Base contract for all non-atomic ERC 20 bridges. 
* SfcErc20MassConservationBridge: Bridge that holds tokens that are transferred to another blockchain.
* SfcErc20MintingBurningBridge: Bridge that burns tokens that are transferred to other blockchains
 and mints new tokens that are transferred to the blockchain the contract is operating on.

