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

import "../../../../../../common/openzeppelin/src/main/solidity/token/ERC20/IERC20.sol";
import "../../../../../../functioncall/interface/src/main/solidity/CrosschainFunctionCallInterface.sol";
import "../../../../../../common/openzeppelin/src/main/solidity/security/Pausable.sol";
import "../../../../../../common/openzeppelin/src/main/solidity/access/Ownable.sol";
import "../../../../../../functioncall/interface/src/main/solidity/HiddenParameters.sol";
import "./SfcErc20BridgeInterface.sol";

/**
 * ERC 20 bridge using the Simple Function Call protocol.
 *
 */
abstract contract AbstractSfcErc20Bridge is HiddenParameters, Pausable, Ownable, SfcErc20BridgeInterface {
    // Simple Function Call bridge.
    CrosschainFunctionCallInterface private crosschainBridge;

    // Mapping of ERC 20 contracts on this blockchain to ERC 20 contracts
    // of the same type on different blockchains.
    //
    // Map (token contract address on this blockchain =>
    //  Map (destination blockchain Id => address on remote contract)
    mapping (address => mapping (uint256 => address)) private tokenContractAddressMapping;


    // Addresses of ERC 20 bridges on other blockchains.
    mapping (uint256 => address) private remoteErc20Bridges;


    /**
     * @param _sfcCbcContract  Simple Function Call protocol implementation.
     */
    constructor (address _sfcCbcContract) {
        crosschainBridge = CrosschainFunctionCallInterface(_sfcCbcContract);
    }


    function changeContractMapping(address _thisBcTokenContract, uint256 _otherBcId, address _othercTokenContract) onlyOwner override external {
        tokenContractAddressMapping[_thisBcTokenContract][_otherBcId] = _othercTokenContract;
        emit TokenContractAddressMappingChanged(_thisBcTokenContract, _otherBcId, _othercTokenContract);
    }

    function changeBlockchainMapping(uint256 _otherBcId, address _otherErc20Bridge) onlyOwner override external {
        remoteErc20Bridges[_otherBcId] = _otherErc20Bridge;
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
     * @param _amount           The number of tokens to transfer.
     */
    function transferToOtherBlockchain(uint256 _destBcId, address _srcTokenContract, address _recipient, uint256 _amount) whenNotPaused override public {
        address destErc20BridgeContract = remoteErc20Bridges[_destBcId];
        require(destErc20BridgeContract != address(0), "Blockchain not supported");

        // The token must be able to be transferred to the target blockchain.
        address destTokenContract = tokenContractAddressMapping[_srcTokenContract][_destBcId];
        require(destTokenContract != address(0), "Token not transferable to requested blockchain");

        // Transfer tokens from the user to this contract.
        // The transfer will revert if the account has inadequate balance or if adequate
        // allowance hasn't been set-up.
        transferOrBurn(_srcTokenContract, msg.sender, _amount);

        crosschainBridge.crossBlockchainCall(_destBcId, destErc20BridgeContract,
            abi.encodeWithSelector(this.receiveFromOtherBlockchain.selector, destTokenContract, _recipient, _amount));

        emit TransferTo(_destBcId, _srcTokenContract, destTokenContract, msg.sender, _recipient, _amount);
    }


    /**
     * Transfer tokens that are owned by this contract to a recipient. The tokens have
     * effectively been transferred from another blockchain to this blockchain.
     *
     * @param _destTokenContract  ERC 20 contract of the token being transferred.
     * @param _recipient          Account to transfer ownership of the tokens to.
     * @param _amount             The number of tokens to be transferred.
     */
    function receiveFromOtherBlockchain(address _destTokenContract, address _recipient, uint256 _amount) whenNotPaused override external {
        require(msg.sender == address(crosschainBridge), "Can not process transfers from contracts other than the bridge contract");

        (uint256 sourceBcId, uint256 sourceContract1) = extractTwoHiddenParams();
        address sourceContract = address(uint160(sourceContract1));
        // The source blockchain id is validated at the function call layer. No need to check
        // that it isn't zero.

        require(sourceContract != address(0), "ERC 20 Bridge (caller) contract is 0");
        address remoteErc20Bridge = remoteErc20Bridges[sourceBcId];
        require(remoteErc20Bridge != address(0), "No ERC 20 Bridge supported for source blockchain");
        require(sourceContract == remoteErc20Bridge, "Incorrect source ERC 20 Bridge");

        transferOrMint(_destTokenContract, _recipient, _amount);

        emit ReceivedFrom(sourceBcId, sourceContract, _destTokenContract, _recipient, _amount);
    }



    function adminTransfer(address _erc20Contract, address _recipient, uint256 _amount) onlyOwner override external {
        transferOrMint(_erc20Contract, _recipient, _amount);

        emit AdminTransfer(_erc20Contract, _recipient, _amount);
    }

    function isBcIdTokenAllowed(uint256 _bcId, address _tokenContract) public override view returns(bool) {
        return address(0) != tokenContractAddressMapping[_tokenContract][_bcId];
    }

    function getBcIdTokenMaping(uint256 _bcId, address _tokenContract) public override view returns(address) {
        return tokenContractAddressMapping[_tokenContract][_bcId];
    }

    function getRemoteErc20BridgeContract(uint256 _bcId) external override view returns(address) {
        return remoteErc20Bridges[_bcId];
    }

    // ***************************************************************************
    // ******* Internal below here ***********************************************
    // ***************************************************************************
    /**
     * Mass Conservation: Transfer tokens that are owned by this contract to a recipient.
     * OR
     * Minting Burning: Mint token and assign them to a recipient.
     *
     * @param _tokenContract      ERC 20 contract of the token being transferred or minted.
     * @param _recipient          Account to transfer ownership of the tokens to.
     * @param _amount             The number of tokens to be transferred.
     */
    function transferOrMint(address _tokenContract, address _recipient, uint256 _amount) virtual internal;

    /**
     * Mass Conservation: TransferFrom tokens from a spender to this contract.
     * OR
     * Minting Burning: BurnFrom a spender's tokens.
     *
     * @param _tokenContract      ERC 20 contract of the token being transferred or burned.
     * @param _spender            Account to transfer ownership of the tokens from.
     * @param _amount             The number of tokens to be transferred.
     */
    function transferOrBurn(address _tokenContract, address _spender, uint256 _amount) virtual internal;
}