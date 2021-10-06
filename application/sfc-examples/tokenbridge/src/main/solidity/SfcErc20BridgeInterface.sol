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

/**
 * ERC 20 bridge using the Simple Function Call protocol.
 *
 */
interface SfcErc20BridgeInterface {
    /**
     * Indicates a request to transfer some tokens has occurred on this blockchain.
     *
     * @param _destBcId           Blockchain the tokens are being transferred to.
     * @param _srcTokenContract   Address of the ERC 20 token contract on this blockchain.
     * @param _destTokenContract  Address of the ERC 20 token contract on the blockchain
     *                            the tokens are being transferred to.
     * @param _sender             Address sending the tokens.
     * @param _recipient          Address to transfer the tokens to on the target blockchain.
     * @param _amount             Number of tokens to transfer.
     */
    event TransferTo(uint256 _destBcId, address _srcTokenContract, address _destTokenContract, address _sender, address _recipient, uint256 _amount);

    /**
     * Indicates a transfer request has been received on this blockchain.
     *
     * @param _srcBcId            Blockchain the tokens are being transferred from.
     * @param _srcTokenContract   Address of the ERC 20 token contract on the blockchain
     *                            the tokens are being transferred from.
     * @param _destTokenContract  Address of the ERC 20 token contract on this blockchain.
     * @param _recipient          Address to transfer the tokens to on the this blockchain.
     * @param _amount             Number of tokens to transfer.
     */
    event ReceivedFrom(uint256 _srcBcId, address _srcTokenContract, address _destTokenContract, address _recipient, uint256 _amount);

    /**
     * Update the mapping between an ERC 20 contract on this blockchain and an ERC 20
     * contract on another blockchain.
     *
     * @param _thisBcTokenContract  Address of ERC 20 contract on this blockchain.
     * @param _otherBcId            Blockchain ID where the corresponding ERC 20 contract resides.
     * @param _othercTokenContract  Address of ERC 20 contract on the other blockchain.
     */
    event TokenContractAddressMappingChanged(address _thisBcTokenContract, uint256 _otherBcId, address _othercTokenContract);

    /**
     * Indicate an administrative transfer has occurred.
     *
     * @param _erc20Contract    Token to transfer.
     * @param _recipient        Address to transfer the tokens to.
     * @param _amount           Number of tokens to transfer.
     */
    event AdminTransfer(address _erc20Contract, address _recipient, uint256 _amount);



    /**
     * Update the mapping between an ERC 20 contract on this blockchain and an ERC 20
     * contract on another blockchain.
     *
     * @param _thisBcTokenContract  Address of ERC 20 contract on this blockchain.
     * @param _otherBcId            Blockchain ID where the corresponding ERC 20 contract resides.
     * @param _othercTokenContract  Address of ERC 20 contract on the other blockchain.
     */
    function changeContractMapping(address _thisBcTokenContract, uint256 _otherBcId, address _othercTokenContract) external;

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
    function transferToOtherBlockchain(uint256 _destBcId, address _srcTokenContract, address _recipient, uint256 _amount) external;


    /**
     * Transfer tokens that are owned by this contract to a recipient. The tokens have
     * effectively been transferred from another blockchain to this blockchain.
     *
     * @param _destTokenContract  ERC 20 contract of the token being transferred.
     * @param _recipient          Account to transfer ownership of the tokens to.
     * @param _amount             The number of tokens to be transferred.
     */
    function receiveFromOtherBlockchain(address _destTokenContract, address _recipient, uint256 _amount) external;


    /**
     * Transfer any amount of any ERC 20 to anyone. This is needed to provide refunds to
     * customers who have had failed transactions where the token transfer occurred on this
     * blockchain, but did not happen on the destination blockchain.
     *
     * This function needs to be used with extreme caution. A system with
     * users' funds escrowed into this contact while they are used on a rollup
     * or sidechain needs to be kept in perfect balance. That is, the number of
     * escrowed tokens must match the number of tokens on other blockchains.
     *
     * @param _erc20Contract    Token to transfer.
     * @param _recipient        Address to transfer the tokens to.
     * @param _amount           Number of tokens to transfer.
     */
    function adminTransfer(address _erc20Contract, address _recipient, uint256 _amount) external;

    /**
     * Indicates whether a token can be transferred to (or from) a blockchain.
     *
     * @param _bcId          Blockchain id of other blockchain.
     * @param _tokenContract Address of ERC 20 token contract on this blockchain.
     * @return bool          True if the token can be transferred to (or from) a blockchain.
     */
    function isBcIdTokenAllowed(uint256 _bcId, address _tokenContract) external view returns(bool);

    /**
     * Gets the mapping between an ERC 20 contract on this blockchain and the ERC 20 contract on
     * another blockchain.
     *
     * @param _bcId          Blockchain id of other blockchain.
     * @param _tokenContract Address of ERC 20 token contract on this blockchain.
     * @return address       Contract address of ERC 20 token contract on other blockchain.
     */
    function getBcIdTokenMaping(uint256 _bcId, address _tokenContract) external view returns(address);

}