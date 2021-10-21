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

import "../../../../../../common/openzeppelin/src/main/solidity/token/ERC721/IERC721.sol";
import "../../../../../../functioncall/interface/src/main/solidity/CrosschainFunctionCallInterface.sol";
import "../../../../../../common/openzeppelin/src/main/solidity/access/AccessControl.sol";
import "../../../../../../common/openzeppelin/src/main/solidity/security/Pausable.sol";
import "../../../../../../functioncall/interface/src/main/solidity/HiddenParameters.sol";

/**
 * ERC 20 bridge using the Simple Function Call protocol.
 *
 */
contract SfcErc721Bridge is HiddenParameters, Pausable, AccessControl {
    bytes32 public constant MAPPING_ROLE = keccak256("MAPPING_ROLE");
    bytes32 public constant PAUSER_ROLE = keccak256("PAUSER_ROLE");
    bytes32 public constant ADMINTRANSFER_ROLE = keccak256("ADMINTRANSFER_ROLE");


    // Simple Function Call bridge.
    CrosschainFunctionCallInterface private crosschainBridge;

    // Mapping of ERC 20 contracts on this blockchain to ERC 20 contracts
    // of the same type on different blockchains.
    //
    // Map (token contract address on this blockchain =>
    //  Map (destination blockchain Id => address on remote contract)
    mapping (address => mapping (uint256 => address)) private tokenContractAddressMapping;


    // Addresses of ERC 721 bridges on other blockchains.
    mapping (uint256 => address) private remoteErc721Bridges;


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
    event TransferTo(uint256 _destBcId, address _srcTokenContract, address _destTokenContract, address _sender, address _recipient, uint256 _tokenId);

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
    event ReceivedFrom(uint256 _srcBcId, address _srcTokenContract, address _destTokenContract, address _recipient, uint256 _tokenId);

    /**
     * @dev Update the mapping between an ERC 721 contract on this blockchain and an ERC 721
     * contract on another blockchain.
     *
     * @param _thisBcTokenContract  Address of ERC 721 contract on this blockchain.
     * @param _otherBcId            Blockchain ID where the corresponding ERC 721 contract resides.
     * @param _othercTokenContract  Address of ERC 721 contract on the other blockchain.
     */
    event TokenContractAddressMappingChanged(address _thisBcTokenContract, uint256 _otherBcId, address _othercTokenContract);

    /**
     * @dev Indicate an administrative transfer has occurred.
     *
     * @param _erc721Contract   Token to transfer.
     * @param _recipient        Address to transfer the tokens to.
     * @param _tokenId          Id of token transferred.
     */
    event AdminTransfer(address _erc721Contract, address _recipient, uint256 _tokenId);



    /**
     * @param _sfcCbcContract  Simple Function Call protocol implementation.
     */
    constructor (address _sfcCbcContract) {
        address sender = _msgSender();
        _setupRole(DEFAULT_ADMIN_ROLE, sender);

        _setupRole(MAPPING_ROLE, sender);
        _setupRole(PAUSER_ROLE, sender);
        _setupRole(ADMINTRANSFER_ROLE, sender);

        crosschainBridge = CrosschainFunctionCallInterface(_sfcCbcContract);
    }


    /**
     * @dev Pauses the bridge.
     *
     * Requirements:
     * - the caller must have the `PAUSER_ROLE`.
     */
    function pause() external {
        require(hasRole(PAUSER_ROLE, _msgSender()), "ERC721 Bridge: Must have PAUSER role");
        _pause();
    }


    /**
     * @dev Unpauses the bridge.
     *
     * Requirements:
     * - the caller must have the `PAUSER_ROLE`.
     */
    function unpause() external {
        require(hasRole(PAUSER_ROLE, _msgSender()), "ERC721 Bridge: Must have PAUSER role");
        _unpause();
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
     * @param _othercTokenContract  Address of ERC 721 contract on the other blockchain.
     */
    function changeContractMapping(address _thisBcTokenContract, uint256 _otherBcId, address _othercTokenContract) external {
        require(hasRole(MAPPING_ROLE, _msgSender()), "ERC721 Bridge: Must have MAPPING role");
        tokenContractAddressMapping[_thisBcTokenContract][_otherBcId] = _othercTokenContract;
        emit TokenContractAddressMappingChanged(_thisBcTokenContract, _otherBcId, _othercTokenContract);
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
    function changeBlockchainMapping(uint256 _otherBcId, address _otherErc721Bridge) external {
        require(hasRole(MAPPING_ROLE, _msgSender()), "ERC721 Bridge: Must have MAPPING role");
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
    function transferToOtherBlockchain(uint256 _destBcId, address _srcTokenContract, address _recipient, uint256 _tokenId) whenNotPaused public {
        address destErc721BridgeContract = remoteErc721Bridges[_destBcId];
        require(destErc721BridgeContract != address(0), "ERC 721 Bridge: Blockchain not supported");

        // The token must be able to be transferred to the target blockchain.
        address destTokenContract = tokenContractAddressMapping[_srcTokenContract][_destBcId];
        require(destTokenContract != address(0), "ERC 721 Bridge: Token not transferable to requested blockchain");

        // Transfer tokens from the user to this contract.
        // The transfer will revert if the account has inadequate balance or if adequate
        // allowance hasn't been set-up.
        transferOrBurn(_srcTokenContract, msg.sender, _tokenId);

        crosschainBridge.crossBlockchainCall(_destBcId, destErc721BridgeContract,
            abi.encodeWithSelector(this.receiveFromOtherBlockchain.selector, destTokenContract, _recipient, _tokenId));

        emit TransferTo(_destBcId, _srcTokenContract, destTokenContract, msg.sender, _recipient, _tokenId);
    }


    /**
     * Transfer tokens that are owned by this contract to a recipient. The tokens have
     * effectively been transferred from another blockchain to this blockchain.
     *
     * @param _destTokenContract  ERC 721 contract of the token being transferred.
     * @param _recipient          Account to transfer ownership of the tokens to.
     * @param _tokenId            Id of token transferred.
     */
    function receiveFromOtherBlockchain(address _destTokenContract, address _recipient, uint256 _tokenId) whenNotPaused external {
        require(_msgSender() == address(crosschainBridge), "ERC 721 Bridge: Can not process transfers from contracts other than the bridge contract");

        (uint256 sourceBcId, uint256 sourceContract1) = extractTwoHiddenParams();
        address sourceContract = address(uint160(sourceContract1));
        // The source blockchain id is validated at the function call layer. No need to check
        // that it isn't zero.

        require(sourceContract != address(0), "ERC 721 Bridge: caller contract is 0");
        address remoteErc721Bridge = remoteErc721Bridges[sourceBcId];
        require(remoteErc721Bridge != address(0), "ERC 721 Bridge: No ERC721 Bridge supported for source blockchain");
        require(sourceContract == remoteErc721Bridge, "ERC 721 Bridge: Incorrect source ERC721 Bridge");

        transferOrMint(_destTokenContract, _recipient, _tokenId);

        emit ReceivedFrom(sourceBcId, sourceContract, _destTokenContract, _recipient, _tokenId);
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
    function adminTransfer(address _erc721Contract, address _recipient, uint256 _tokenId) external {
        require(hasRole(ADMINTRANSFER_ROLE, _msgSender()), "ERC721 Bridge: Must have ADMINTRANSFER role");
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
    function isBcIdTokenAllowed(uint256 _bcId, address _tokenContract) public view returns(bool) {
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
    function getBcIdTokenMaping(uint256 _bcId, address _tokenContract) public view returns(address) {
        return tokenContractAddressMapping[_tokenContract][_bcId];
    }

    function getRemoteErc721BridgeContract(uint256 _bcId) external view returns(address) {
        return remoteErc721Bridges[_bcId];
    }

    // ***************************************************************************
    // ******* Internal below here ***********************************************
    // ***************************************************************************
    /**
     * Transfer tokens that are owned by this contract to a recipient.
     *
     * @param _tokenContract      ERC 20 contract of the token being transferred or minted.
     * @param _recipient          Account to transfer ownership of the tokens to.
     * @param _tokenId            Id of token transferred.
     */
    function transferOrMint(address _tokenContract, address _recipient, uint256 _tokenId) internal {
        IERC721(_tokenContract).transferFrom(address(this), _recipient, _tokenId);
    }

    /**
     * Mass Conservation: TransferFrom tokens from a spender to this contract.
     * OR
     * Minting Burning: BurnFrom a spender's tokens.
     *
     * @param _tokenContract      ERC 20 contract of the token being transferred or burned.
     * @param _spender            Account to transfer ownership of the tokens from.
     * @param _tokenId            Id of token transferred.
     */
    function transferOrBurn(address _tokenContract, address _spender, uint256 _tokenId) internal {
        IERC721(_tokenContract).transferFrom(_spender, address(this), _tokenId);
    }
}