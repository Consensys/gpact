/*
 * Copyright 2021 ConsenSys Software Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
pragma solidity >=0.8.0;

import "./ERC721RemoteBlockchain.sol";
import "../../../../../../functioncall/interface/src/main/solidity/CrosschainFunctionCallInterface.sol";
import "../../../../../../functioncall/interface/src/main/solidity/NonAtomicHiddenAuthParameters.sol";
import "@openzeppelin/contracts/token/ERC721/IERC721.sol";
import "@openzeppelin/contracts/access/AccessControl.sol";
import "@openzeppelin/contracts/security/Pausable.sol";

/**
 * ERC 20 bridge using the Simple Function Call protocol.
 *
 */
contract SfcErc721Bridge is
    NonAtomicHiddenAuthParameters,
    Pausable,
    AccessControl
{
    bytes32 public constant MAPPING_ROLE = keccak256("MAPPING_ROLE");
    bytes32 public constant PAUSER_ROLE = keccak256("PAUSER_ROLE");
    bytes32 public constant ADMINTRANSFER_ROLE =
        keccak256("ADMINTRANSFER_ROLE");
    bytes32 public constant DENYLIST_ROLE = keccak256("DENYLIST_ROLE");

    // Token contract configuration.
    // Token has not been added to the bridge yet.
    uint256 private constant TOKEN_CONTRACT_CONF_NONE = 0;
    // Tokens of this type are created on this blockchain. This is the
    // home blockchain for the token.
    uint256 private constant TOKEN_CONTRACT_CONF_HOME = 1;
    // Tokens of this type are created on another blockchain. This is a
    // remote blockchain for the token.
    uint256 private constant TOKEN_CONTRACT_CONF_REMOTE = 2;
    // The maximum token contract configuration value.
    uint256 private constant TOKEN_CONTRACT_CONF_MAX = 2;

    // Simple Function Call bridge.
    CrosschainFunctionCallInterface private crosschainBridge;

    // Token configuration. This mapping is used to determine
    // if a token contract is for a token that is minted originally
    // on this blockchain, or if the token is minted on another
    // blockchain. That is, is this home blockchain the token's "home".
    //
    // NOTE: An enum is not being used as when variables are invalid
    // enums, unhelpful errors are thrown.
    //
    // Map (token contract address on this blockchain =>
    //  token contract configuration)
    mapping(address => uint256) public tokenContractConfiguration;

    // Mapping of ERC 721 contracts on this blockchain to ERC 721 contracts
    // of the same type on different blockchains.
    //
    // Map (token contract address on this blockchain =>
    //  Map (destination blockchain Id => address on remote contract)
    mapping(address => mapping(uint256 => address))
        private tokenContractAddressMapping;

    // Addresses of ERC 721 bridges on other blockchains.
    mapping(uint256 => address) private remoteErc721Bridges;

    // List of addresses that have been blocked from using this bridge.
    mapping(address => bool) private denylist;

    /**
     * @dev Indicates a request to transfer some tokens has occurred on this blockchain.
     *
     * @param _destBcId           Blockchain the tokens are being transferred to.
     * @param _srcTokenContract   Address of the ERC 721 token contract on this blockchain.
     * @param _destTokenContract  Address of the ERC 721 token contract on the blockchain
     *                            the tokens are being transferred to.
     * @param _sender             Address sending the tokens.
     * @param _recipient          Address to transfer the tokens to on the target blockchain.
     * @param _tokenId            Id of token transferred.
     */
    event TransferTo(
        uint256 _destBcId,
        address _srcTokenContract,
        address _destTokenContract,
        address _sender,
        address _recipient,
        uint256 _tokenId
    );

    /**
     * @dev Indicates a transfer request has been received on this blockchain.
     *
     * @param _srcBcId            Blockchain the tokens are being transferred from.
     * @param _srcTokenContract   Address of the ERC 721 token contract on the blockchain
     *                            the tokens are being transferred from.
     * @param _destTokenContract  Address of the ERC 721 token contract on this blockchain.
     * @param _recipient          Address to transfer the tokens to on the this blockchain.
     * @param _tokenId            Id of token transferred.
     */
    event ReceivedFrom(
        uint256 _srcBcId,
        address _srcTokenContract,
        address _destTokenContract,
        address _recipient,
        uint256 _tokenId
    );

    /**
     * @dev Update the mapping between an ERC 721 contract on this blockchain and an ERC 721
     * contract on another blockchain.
     *
     * @param _thisBcTokenContract  Address of ERC 721 contract on this blockchain.
     * @param _otherBcId            Blockchain ID where the corresponding ERC 721 contract resides.
     * @param _othercTokenContract  Address of ERC 721 contract on the other blockchain.
     */
    event TokenContractAddressMappingChanged(
        address _thisBcTokenContract,
        uint256 _otherBcId,
        address _othercTokenContract
    );

    /**
     * @dev Indicate an administrative transfer has occurred.
     *
     * @param _erc721Contract   Token to transfer.
     * @param _recipient        Address to transfer the tokens to.
     * @param _tokenId          Id of token transferred.
     */
    event AdminTransfer(
        address _erc721Contract,
        address _recipient,
        uint256 _tokenId
    );

    /**
     * @dev  Indicates that the specified address has been denylisted, and cannot transfer or receive ERC721 tokens through the bridge.
     *
     * @param _address   The address added to the denylist
     */
    event AddressDenylisted(address _address);

    /**
     * @dev  Indicates that the specified address has been removed from the denylist,
     *       and can resume transferring and receiving ERC721 tokens through the bridge.
     *
     * @param _address The address removed from the denylist
     */
    event AddressRemovedFromDenylist(address _address);

    /**
     * @param _sfcCbcContract  Simple Function Call protocol implementation.
     */
    constructor(address _sfcCbcContract) {
        address sender = _msgSender();
        _setupRole(DEFAULT_ADMIN_ROLE, sender);

        _setupRole(MAPPING_ROLE, sender);
        _setupRole(PAUSER_ROLE, sender);
        _setupRole(ADMINTRANSFER_ROLE, sender);
        _setupRole(DENYLIST_ROLE, sender);

        crosschainBridge = CrosschainFunctionCallInterface(_sfcCbcContract);
    }

    /**
     * @dev Adds the specified address to a denylist, preventing it from transferring or receiving tokens through the bridge.
     *
     * Requirements:
     *   - the caller must have the `DENYLIST_ROLE`.
     *
     * @param _address The address to denylist
     */
    function addToDenylist(address _address) external {
        require(
            hasRole(DENYLIST_ROLE, _msgSender()),
            "ERC721 Bridge: Must have DENYLIST role"
        );
        denylist[_address] = true;
        emit AddressDenylisted(_address);
    }

    /**
     * @dev Removes the specified address from the denylist, allowing it to resume transferring or receiving tokens through the bridge.
     *
     * Requirements:
     *   - the caller must have the `DENYLIST_ROLE`.
     *
     * @param _address The address to remove from the denylist
     */
    function removeFromDenylist(address _address) external {
        require(
            hasRole(DENYLIST_ROLE, _msgSender()),
            "ERC721 Bridge: Must have DENYLIST role"
        );
        denylist[_address] = false;
        emit AddressRemovedFromDenylist(_address);
    }

    /**
     * @dev Checks whether a given address is in the denylist.
     *
     * @param _address   The address to check against the denylist
     * @return true if the address is in the denylist, false otherwise.
     */
    function isDenylisted(address _address) public view returns (bool) {
        return denylist[_address];
    }

    /**
     * @dev Pauses the bridge.
     *
     * Requirements:
     * - the caller must have the `PAUSER_ROLE`.
     */
    function pause() external {
        require(
            hasRole(PAUSER_ROLE, _msgSender()),
            "ERC721 Bridge: Must have PAUSER role"
        );
        _pause();
    }

    /**
     * @dev Unpauses the bridge.
     *
     * Requirements:
     * - the caller must have the `PAUSER_ROLE`.
     */
    function unpause() external {
        require(
            hasRole(PAUSER_ROLE, _msgSender()),
            "ERC721 Bridge: Must have PAUSER role"
        );
        _unpause();
    }

    /**
     * @dev Add a token mapping and set the token contract configuration. This can
     * only be called if the token has not been set-up yet.
     *
     * @param _thisBcTokenContract  Address of ERC 721 contract on this blockchain.
     * @param _otherBcId            Blockchain ID where the corresponding ERC 721 contract resides.
     * @param _otherTokenContract  Address of ERC 721 contract on the other blockchain.
     * @param _thisIsTheHomeBlockchain  True if this blockchain is the home blockchain for the token.
     */
    function addContractFirstMapping(
        address _thisBcTokenContract,
        uint256 _otherBcId,
        address _otherTokenContract,
        bool _thisIsTheHomeBlockchain
    ) external {
        require(
            hasRole(MAPPING_ROLE, _msgSender()),
            "ERC721 Bridge: Must have MAPPING role"
        );
        require(
            !tokenExists(_thisBcTokenContract),
            "ERC721 Bridge: token already configured"
        );

        setTokenConfigInternal(_thisBcTokenContract, _thisIsTheHomeBlockchain);
        changeContractMappingInternal(
            _thisBcTokenContract,
            _otherBcId,
            _otherTokenContract
        );
    }

    /**
     * @dev Set the token configuration after the token has first been added. The ONLY reason
     * to call this function is that when the contract was first added, it was added with
     * the wrong value.
     *
     * @param _thisBcTokenContract      Address of ERC 721 contract on this blockchain.
     * @param _thisIsTheHomeBlockchain  True if this blockchain is the home blockchain for the token.
     */
    function setTokenConfig(
        address _thisBcTokenContract,
        bool _thisIsTheHomeBlockchain
    ) external {
        require(
            hasRole(MAPPING_ROLE, _msgSender()),
            "ERC721 Bridge: Must have MAPPING role"
        );
        require(
            tokenExists(_thisBcTokenContract),
            "ERC721 Bridge: token not configured"
        );
        setTokenConfigInternal(_thisBcTokenContract, _thisIsTheHomeBlockchain);
    }

    /**
     * @dev Update the mapping between an ERC 721 contract on this blockchain and an ERC 721
     * contract on another blockchain.
     *
     * Requirements:
     * - the caller must have the `MAPPING_ROLE`.
     *
     * @param _thisBcTokenContract  Address of ERC 721 contract on this blockchain.
     * @param _otherBcId            Blockchain ID where the corresponding ERC 721 contract resides.
     * @param _otherTokenContract  Address of ERC 721 contract on the other blockchain.
     */
    function changeContractMapping(
        address _thisBcTokenContract,
        uint256 _otherBcId,
        address _otherTokenContract
    ) external {
        require(
            hasRole(MAPPING_ROLE, _msgSender()),
            "ERC721 Bridge: Must have MAPPING role"
        );
        require(
            tokenExists(_thisBcTokenContract),
            "ERC721 Bridge: token not configured"
        );
        changeContractMappingInternal(
            _thisBcTokenContract,
            _otherBcId,
            _otherTokenContract
        );
    }

    /**
     * Connect this ERC721 Bridge contract to an ERC721 Bridge contract on another blockchain.
     *
     * Requirements:
     * - the caller must have the `MAPPING_ROLE`.
     *
     * @param _otherBcId            Blockchain ID where the corresponding ERC721 bridge contract resides.
     * @param _otherErc721Bridge    Address of ERC721 Bridge contract on other blockchain.
     */
    function changeBlockchainMapping(
        uint256 _otherBcId,
        address _otherErc721Bridge
    ) external {
        require(
            hasRole(MAPPING_ROLE, _msgSender()),
            "ERC721 Bridge: Must have MAPPING role"
        );
        remoteErc721Bridges[_otherBcId] = _otherErc721Bridge;
    }

    /**
     * Transfer tokens from msg.sender to this contract on this blockchain,
     * and request tokens on the remote blockchain be given to the requested
     * account on the destination blockchain.
     *
     * NOTE: msg.sender must have called approve() on the token contract.
     *
     * @param _srcTokenContract Address of ERC 20 contract on this blockchain.
     * @param _recipient        Address of account to transfer tokens to on the destination blockchain.
     * @param _tokenId            Id of token transferred.
     */
    function transferToOtherBlockchain(
        uint256 _destBcId,
        address _srcTokenContract,
        address _recipient,
        uint256 _tokenId
    ) public whenNotPaused {
        transferToOtherBlockchain(
            _destBcId,
            _srcTokenContract,
            _recipient,
            _tokenId,
            ""
        );
    }

    /**
     * Transfer tokens from msg.sender to this contract on this blockchain,
     * and request tokens on the remote blockchain be given to the requested
     * account on the destination blockchain.
     *
     * NOTE: msg.sender must have called approve() on the token contract.
     *
     * @param _srcTokenContract Address of ERC 20 contract on this blockchain.
     * @param _recipient        Address of account to transfer tokens to on the destination blockchain.
     * @param _tokenId          Id of token transferred.
     * @param _data             Data to send to recipient, as part of the transfer.
     */
    function transferToOtherBlockchain(
        uint256 _destBcId,
        address _srcTokenContract,
        address _recipient,
        uint256 _tokenId,
        bytes memory _data
    ) public whenNotPaused {
        require(
            !isDenylisted(msg.sender),
            "ERC 721 Bridge: Sender is denylisted"
        );
        require(
            !isDenylisted(_recipient),
            "ERC 721 Bridge: Recipient is denylisted"
        );
        require(
            !isDenylisted(tx.origin),
            "ERC 721 Bridge: Transaction originator is denylisted"
        );

        address destErc721BridgeContract = remoteErc721Bridges[_destBcId];
        require(
            destErc721BridgeContract != address(0),
            "ERC 721 Bridge: Blockchain not supported"
        );

        // The token must be able to be transferred to the target blockchain.
        address destTokenContract = tokenContractAddressMapping[
            _srcTokenContract
        ][_destBcId];
        require(
            destTokenContract != address(0),
            "ERC 721 Bridge: Token not transferable to requested blockchain"
        );

        // Transfer tokens from the user to this contract.
        // The transfer will revert if the account has inadequate balance or if adequate
        // allowance hasn't been set-up.
        transferOrBurn(_srcTokenContract, msg.sender, _tokenId);

        crosschainBridge.crossBlockchainCall(
            _destBcId,
            destErc721BridgeContract,
            abi.encodeWithSelector(
                this.receiveFromOtherBlockchain.selector,
                destTokenContract,
                _recipient,
                _tokenId,
                _data
            )
        );

        emit TransferTo(
            _destBcId,
            _srcTokenContract,
            destTokenContract,
            msg.sender,
            _recipient,
            _tokenId
        );
    }

    /**
     * Transfer tokens that are owned by this contract to a recipient. The tokens have
     * effectively been transferred from another blockchain to this blockchain.
     *
     * @param _destTokenContract  ERC 721 contract of the token being transferred.
     * @param _recipient          Account to transfer ownership of the tokens to.
     * @param _tokenId            Id of token transferred.
     * @param _data               Data sent to recipient, as part of the transfer.
     */
    function receiveFromOtherBlockchain(
        address _destTokenContract,
        address _recipient,
        uint256 _tokenId,
        bytes memory _data
    ) external whenNotPaused {
        require(
            !isDenylisted(tx.origin),
            "ERC 721 Bridge: Transaction originator is denylisted"
        );
        require(
            !isDenylisted(_recipient),
            "ERC 721 Bridge: Recipient is denylisted"
        );

        require(
            _msgSender() == address(crosschainBridge),
            "ERC 721 Bridge: Can not process transfers from contracts other than the bridge contract"
        );
        (
            uint256 sourceBcId,
            address sourceContract
        ) = decodeNonAtomicAuthParams();
        // The source blockchain id is validated at the function call layer. No need to check
        // that it isn't zero.

        require(
            sourceContract != address(0),
            "ERC 721 Bridge: caller contract is 0"
        );
        address remoteErc721Bridge = remoteErc721Bridges[sourceBcId];
        require(
            remoteErc721Bridge != address(0),
            "ERC 721 Bridge: No ERC721 Bridge supported for source blockchain"
        );
        require(
            sourceContract == remoteErc721Bridge,
            "ERC 721 Bridge: Incorrect source ERC721 Bridge"
        );

        transferOrMint(_destTokenContract, _recipient, _tokenId, _data);

        emit ReceivedFrom(
            sourceBcId,
            sourceContract,
            _destTokenContract,
            _recipient,
            _tokenId
        );
    }

    /**
     * Transfer any ERC 721 token to anyone. This is needed to provide refunds to
     * customers who have had failed transactions where the token transfer occurred on this
     * blockchain, but did not happen on the destination blockchain.
     *
     * This function needs to be used with extreme caution. A system with
     * users' funds escrowed into this contact while they are used on a rollup
     * or sidechain needs to be kept in perfect balance. That is, the number of
     * escrowed tokens must match the number of tokens on other blockchains.
     *
     * Requirements:
     * - the caller must have the `ADMINTRANSFER_ROLE`.
     *
     * @param _erc721Contract    Token to transfer.
     * @param _recipient        Address to transfer the tokens to.
     * @param _tokenId            Id of token transferred.
     */
    function adminTransfer(
        address _erc721Contract,
        address _recipient,
        uint256 _tokenId
    ) external {
        require(
            hasRole(ADMINTRANSFER_ROLE, _msgSender()),
            "ERC721 Bridge: Must have ADMINTRANSFER role"
        );
        transferOrMint(_erc721Contract, _recipient, _tokenId);
        emit AdminTransfer(_erc721Contract, _recipient, _tokenId);
    }

    /**
     * Indicates whether a token can be transferred to (or from) a blockchain.
     *
     * @param _bcId          Blockchain id of other blockchain.
     * @param _tokenContract Address of ERC 721 token contract on this blockchain.
     * @return bool          True if the token can be transferred to (or from) a blockchain.
     */
    function isBcIdTokenAllowed(uint256 _bcId, address _tokenContract)
        public
        view
        returns (bool)
    {
        return address(0) != tokenContractAddressMapping[_tokenContract][_bcId];
    }

    /**
     * Gets the mapping between an ERC 20 contract on this blockchain and the ERC 20 contract on
     * another blockchain.
     *
     * @param _bcId          Blockchain id of other blockchain.
     * @param _tokenContract Address of ERC 721 token contract on this blockchain.
     * @return address       Contract address of ERC 721 token contract on other blockchain.
     */
    function getBcIdTokenMaping(uint256 _bcId, address _tokenContract)
        public
        view
        returns (address)
    {
        return tokenContractAddressMapping[_tokenContract][_bcId];
    }

    function getRemoteErc721BridgeContract(uint256 _bcId)
        external
        view
        returns (address)
    {
        return remoteErc721Bridges[_bcId];
    }

    // ***************************************************************************
    // ******* Internal below here ***********************************************
    // ***************************************************************************
    /**
     * Home Blockchains: Transfer tokens that are owned by this contract to a recipient.
     * Remote Blockchains: Mints tokens.
     *
     * @param _tokenContract      ERC 721 contract of the token being transferred or minted.
     * @param _recipient          Account to transfer ownership of the tokens to.
     * @param _tokenId            Id of token transferred.
     */
    function transferOrMint(
        address _tokenContract,
        address _recipient,
        uint256 _tokenId
    ) private {
        transferOrMint(_tokenContract, _recipient, _tokenId, "");
    }

    /**
     * Home Blockchains: Transfer tokens that are owned by this contract to a recipient.
     * Remote Blockchains: Mints tokens.
     *
     * @param _tokenContract      ERC 721 contract of the token being transferred or minted.
     * @param _recipient          Account to transfer ownership of the tokens to.
     * @param _tokenId            Id of token transferred.
     * @param _data               Data to send to recipient, as part of the transfer.
     */
    function transferOrMint(
        address _tokenContract,
        address _recipient,
        uint256 _tokenId,
        bytes memory _data
    ) private {
        if (
            tokenContractConfiguration[_tokenContract] ==
            TOKEN_CONTRACT_CONF_HOME
        ) {
            IERC721(_tokenContract).safeTransferFrom(
                address(this),
                _recipient,
                _tokenId,
                _data
            );
        } else {
            ERC721RemoteBlockchain(_tokenContract).mint(
                _recipient,
                _tokenId,
                _data
            );
        }
    }

    /**
     * Home Blockchains: Transfer tokens to this contract.
     * Remote Blockchains: Burns tokens.
     *
     * @param _tokenContract      ERC 721 contract of the token being transferred or burned.
     * @param _spender            Account to transfer ownership of the tokens from.
     * @param _tokenId            Id of token transferred.
     */
    function transferOrBurn(
        address _tokenContract,
        address _spender,
        uint256 _tokenId
    ) private {
        if (
            tokenContractConfiguration[_tokenContract] ==
            TOKEN_CONTRACT_CONF_HOME
        ) {
            IERC721(_tokenContract).transferFrom(
                _spender,
                address(this),
                _tokenId
            );
        } else {
            ERC721RemoteBlockchain(_tokenContract).burn(_tokenId);
        }
    }

    function tokenExists(address _tokenContract) private view returns (bool) {
        return
            tokenContractConfiguration[_tokenContract] !=
            TOKEN_CONTRACT_CONF_NONE;
    }

    function setTokenConfigInternal(
        address _thisBcTokenContract,
        bool _thisIsTheHomeBlockchain
    ) private {
        tokenContractConfiguration[
            _thisBcTokenContract
        ] = _thisIsTheHomeBlockchain
            ? TOKEN_CONTRACT_CONF_HOME
            : TOKEN_CONTRACT_CONF_REMOTE;
    }

    function changeContractMappingInternal(
        address _thisBcTokenContract,
        uint256 _otherBcId,
        address _othercTokenContract
    ) private {
        tokenContractAddressMapping[_thisBcTokenContract][
            _otherBcId
        ] = _othercTokenContract;
        emit TokenContractAddressMappingChanged(
            _thisBcTokenContract,
            _otherBcId,
            _othercTokenContract
        );
    }
}
