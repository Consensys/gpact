SFC ERC 721 Token Bridge Example
----------------
The sample demonstrates how to use the Simple Function Call (SFC) 
protocol for an ERC 721 token bridge.

The example has some application code to send some tokens from 
a "home" blockchain, where an ERC 721 was first created to a "remote"
blockchain. The contracts used in the example are shown below.

![Architecture of ERC 721 Token Bridge Example](https://raw.githubusercontent.com/ConsenSys/gpact/main/application/sfc-examples/erc721tokenbridge/erc721architecture.png "Architecture of ERC 721 Token Bridge Example")

The [ERC721PresetMinterPauserAutoId](https://github.com/ConsenSys/gpact/tree/main/common/openzeppelin/src/main/solidity/token/ERC721/presets/ERC721PresetMinterPauserAutoId.sol) is a standard ERC 721 contract from
the Open Zeppelin project. It is the contract on the home blockchain
that creates tokens.

The [ERC721RemoteBlockchain](https://github.com/ConsenSys/gpact/tree/main/application/nonatomic-appcontracts/erc721bridge/src/main/solidity/ERC721RemoteBlockchain.sol) is
an ERC 721 contract that allows tokens to be minted with specific token ids. 
This is needed so that tokens from the home blockchain can be created
on the remote blockchain.

The [SfcErc721Bridge](https://github.com/ConsenSys/gpact/tree/main/application/nonatomic-appcontracts/erc721bridge/src/main/solidity/SfcErc721Bridge.sol) is the ERC 721
application layer bridge. An instance of this contract is
deployed to each blockchain. 

The [SimpleCrosschainControl](https://github.com/ConsenSys/gpact/tree/main/functioncall/sfc/src/main/solidity/SimpleCrosschainControl.sol) is the crosschain control
contract for the Simple Function Call (SFC) protocol. An instance of this contract is
deployed to each blockchain. This contract is the function 
call layer bridge.

Any messaging protocol can be used to verify events from source
blockchains. In the diagram, the Attestor - Signer approach is used.

The [CrosschainVerifierSign](https://github.com/ConsenSys/gpact/tree/main/messaging/attestor-sign/src/main/solidity/CrosschainVerifierSign.sol)
contract decodes signed events. It uses the [AttestorSignRegistrar](https://github.com/ConsenSys/gpact/tree/main/messaging/attestor-sign/src/main/solidity/AttestorSignRegistrar.sol)
to verify the event's signature(s).

The entry point example code is in [Erc721TokenBridgeExample.java](https://github.com/ConsenSys/gpact/tree/main/application/sfc-examples/erc721tokenbridge/src/main/java/net/consensys/gpact/sfc/examples/erc721tokenbridge/Erc721TokenBridgeExample.java) .