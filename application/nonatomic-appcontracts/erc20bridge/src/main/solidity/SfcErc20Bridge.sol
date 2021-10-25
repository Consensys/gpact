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
import "../../../../../../common/openzeppelin/src/main/solidity/access/AccessControl.sol";
import "../../../../../../common/openzeppelin/src/main/solidity/security/Pausable.sol";
import "../../../../../../functioncall/interface/src/main/solidity/HiddenParameters.sol";
import "../../../../../../common/openzeppelin/src/main/solidity/token/ERC20/presets/ERC20PresetMinterPauser.sol";

/**
 * ERC 20 bridge using the Simple Function Call protocol.
 *
 */
contract SfcErc20Bridge is HiddenParameters, Pausable, AccessControl {
    bytes32 public constant MAPPING_ROLE = keccak256("MAPPING_ROLE");
    bytes32 public constant PAUSER_ROLE = keccak256("PAUSER_ROLE");
    bytes32 public constant ADMINTRANSFER_ROLE = keccak256("ADMINTRANSFER_ROLE");

    // Token contract configuration.
    // Token has not been added to the bridge yet.
    uint256 private constant TOKEN_CONTRACT_CONF_NONE = 0;
    // Tokens of this type are minted when transferred to this blockchain and burn
    // when transferred to another blockchain.
    uint256 private constant TOKEN_CONTRACT_CONF_MINTER = 1;
    // Tokens of this type are kept in escrow when transferred from this chain.
    // That is, transferFrom is used, and not mint or burn.
    uint256 private constant TOKEN_CONTRACT_CONF_MASSC = 2;
    // The maximum token contract configuration value.
    uint256 private constant TOKEN_CONTRACT_CONF_MAX = 2;


    // Simple Function Call bridge.
    CrosschainFunctionCallInterface private crosschainBridge;

    // Token configuration. This mapping is used to determine
    // how tokens should be processed when cross-blockchain transfers
    // occur.
    //
    // NOTE: An enum is not being used as when variables are invalid
    // enums, unhelpful errors are thrown.
    //
    // Map (token contract address on this blockchain =>
    //  token contract configuration)
    mapping(address => uint256) public tokenContractConfiguration;

    // Mapping of ERC 20 contracts on this blockchain to ERC 20 contracts
    // of the same type on different blockchains.
    //
    // Map (token contract address on this blockchain =>
    //  Map (destination blockchain Id => address on remote contract)
    mapping (address => mapping (uint256 => address)) private tokenContractAddressMapping;


    // Addresses of ERC 20 bridges on other blockchains.
    mapping (uint256 => address) private remoteErc20Bridges;


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
     * Pauses the bridge.
     *
     * Requirements:
     * - the caller must have the `PAUSER_ROLE`.
     */
    function pause() external {
        require(hasRole(PAUSER_ROLE, _msgSender()), "ERC20 Bridge: Must have PAUSER role");
        _pause();
    }


    /**
     * Unpauses the bridge.
     *
     * Requirements:
     * - the caller must have the `PAUSER_ROLE`.
     */
    function unpause() external {
        require(hasRole(PAUSER_ROLE, _msgSender()), "ERC20 Bridge: Must have PAUSER role");
        _unpause();
    }



    /**
     * @dev Add a token mapping and set the token contract configuration. This can
     * only be called if the token has not been set-up yet.
     *
     * Requirements:
     * - the caller must have the `MAPPING_ROLE`.
     *
     * @param _thisBcTokenContract  Address of ERC 20 contract on this blockchain.
     * @param _otherBcId            Blockchain ID where the corresponding ERC 20 contract resides.
     * @param _otherTokenContract   Address of ERC 20 contract on the other blockchain.
     * @param _thisBcMassC          True if the token contract on this blockchain uses mass conservation.
     */
    function addContractFirstMapping(address _thisBcTokenContract, uint256 _otherBcId, address _otherTokenContract, bool _thisBcMassC) external {
        require(hasRole(MAPPING_ROLE, _msgSender()), "ERC20 Bridge: Must have MAPPING role");
        require(!tokenExists(_thisBcTokenContract), "ERC20 Bridge: token already configured");

        setTokenConfigInternal(_thisBcTokenContract, _thisBcMassC);
        changeContractMappingInternal(_thisBcTokenContract, _otherBcId, _otherTokenContract);
    }

    /**
     * @dev Set the token configuration after the token has first been added. The ONLY reason
     * to call this function is that when the contract was first added, it was added with
     * the wrong value.
     *
     * Requirements:
     * - the caller must have the `MAPPING_ROLE`.
     *
     * @param _thisBcTokenContract      Address of ERC 20 contract on this blockchain.
     * @param _thisBcMassC              True if the token contract on this blockchain uses mass conservation.
     */
    function setTokenConfig(address _thisBcTokenContract, bool _thisBcMassC) external {
        require(hasRole(MAPPING_ROLE, _msgSender()), "ERC20 Bridge: Must have MAPPING role");
        require(tokenExists(_thisBcTokenContract), "ERC20 Bridge: token not configured");
        setTokenConfigInternal(_thisBcTokenContract, _thisBcMassC);
    }



    /**
     * @dev Update the mapping between an ERC 20 contract on this blockchain and an ERC 20
     * contract on another blockchain.
     *
     * Requirements:
     * - the caller must have the `MAPPING_ROLE`.
     *
     * @param _thisBcTokenContract  Address of ERC 20 contract on this blockchain.
     * @param _otherBcId            Blockchain ID where the corresponding ERC 20 contract resides.
     * @param _otherTokenContract  Address of ERC 20 contract on the other blockchain.
     */
    function changeContractMapping(address _thisBcTokenContract, uint256 _otherBcId, address _otherTokenContract) external {
        require(hasRole(MAPPING_ROLE, _msgSender()), "ERC20 Bridge: Must have MAPPING role");
        require(tokenExists(_thisBcTokenContract), "ERC20 Bridge: token not configured");
        changeContractMappingInternal(_thisBcTokenContract, _otherBcId, _otherTokenContract);
    }


    /**
     * Connect this ERC20 Bridge contract to an ERC20 Bridge contract on another blockchain.
     *
     * Requirements:
     * - the caller must have the `MAPPING_ROLE`.
     *
     * @param _otherBcId            Blockchain ID where the corresponding ERC 20 bridge contract resides.
     * @param _otherErc20Bridge     Address of ERC 20 Bridge contract on other blockchain.
     */
    function changeBlockchainMapping(uint256 _otherBcId, address _otherErc20Bridge) external {
        require(hasRole(MAPPING_ROLE, _msgSender()), "ERC20 Bridge: Must have MAPPING role");
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
    function transferToOtherBlockchain(uint256 _destBcId, address _srcTokenContract, address _recipient, uint256 _amount) whenNotPaused public {
        address destErc20BridgeContract = remoteErc20Bridges[_destBcId];
        require(destErc20BridgeContract != address(0), "ERC20 Bridge: Blockchain not supported");

        // The token must be able to be transferred to the target blockchain.
        address destTokenContract = tokenContractAddressMapping[_srcTokenContract][_destBcId];
        require(destTokenContract != address(0), "ERC20 Bridge: Token not transferable to requested blockchain");

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
    function receiveFromOtherBlockchain(address _destTokenContract, address _recipient, uint256 _amount) whenNotPaused external {
        require(_msgSender() == address(crosschainBridge), "ERC20 Bridge: Can not process transfers from contracts other than the bridge contract");

        (uint256 sourceBcId, uint256 sourceContract1) = extractTwoHiddenParams();
        address sourceContract = address(uint160(sourceContract1));
        // The source blockchain id is validated at the function call layer. No need to check
        // that it isn't zero.

        require(sourceContract != address(0), "ERC 20 Bridge: caller contract is 0");
        address remoteErc20Bridge = remoteErc20Bridges[sourceBcId];
        require(remoteErc20Bridge != address(0), "ERC20 Bridge: No ERC 20 Bridge supported for source blockchain");
        require(sourceContract == remoteErc20Bridge, "ERC20 Bridge: Incorrect source ERC 20 Bridge");

        transferOrMint(_destTokenContract, _recipient, _amount);

        emit ReceivedFrom(sourceBcId, sourceContract, _destTokenContract, _recipient, _amount);
    }


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
     * Requirements:
     * - the caller must have the `ADMINTRANSFER_ROLE`.
     *
     * @param _erc20Contract    Token to transfer.
     * @param _recipient        Address to transfer the tokens to.
     * @param _amount           Number of tokens to transfer.
     */
    function adminTransfer(address _erc20Contract, address _recipient, uint256 _amount) external {
        require(hasRole(ADMINTRANSFER_ROLE, _msgSender()), "ERC20 Bridge: Must have ADMINTRANSFER role");
        transferOrMint(_erc20Contract, _recipient, _amount);
        emit AdminTransfer(_erc20Contract, _recipient, _amount);
    }

    /**
     * Indicates whether a token can be transferred to (or from) a blockchain.
     *
     * @param _bcId          Blockchain id of other blockchain.
     * @param _tokenContract Address of ERC 20 token contract on this blockchain.
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
     * @param _tokenContract Address of ERC 20 token contract on this blockchain.
     * @return address       Contract address of ERC 20 token contract on other blockchain.
     */
    function getBcIdTokenMaping(uint256 _bcId, address _tokenContract) public view returns(address) {
        return tokenContractAddressMapping[_tokenContract][_bcId];
    }

    function getRemoteErc20BridgeContract(uint256 _bcId) external view returns(address) {
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
    function transferOrMint(address _tokenContract, address _recipient, uint256 _amount) private {
        if (tokenContractConfiguration[_tokenContract] == TOKEN_CONTRACT_CONF_MASSC) {
            if (!IERC20(_tokenContract).transfer(_recipient, _amount)) {
                revert("transfer failed");
            }
        }
        else {
            ERC20PresetMinterPauser(_tokenContract).mint(_recipient, _amount);
        }
    }

    /**
     * Mass Conservation: TransferFrom tokens from a spender to this contract.
     * OR
     * Minting Burning: BurnFrom a spender's tokens.
     *
     * @param _tokenContract      ERC 20 contract of the token being transferred or burned.
     * @param _spender            Account to transfer ownership of the tokens from.
     * @param _amount             The number of tokens to be transferred.
     */
    function transferOrBurn(address _tokenContract, address _spender, uint256 _amount) private {
        if (tokenContractConfiguration[_tokenContract] == TOKEN_CONTRACT_CONF_MASSC) {
            if (!IERC20(_tokenContract).transferFrom(_spender, address(this), _amount)) {
                revert("transferFrom failed");
            }
        }
        else {
            ERC20PresetMinterPauser(_tokenContract).burnFrom(_spender, _amount);
        }
    }


    function tokenExists(address _tokenContract) private view returns (bool) {
        return tokenContractConfiguration[_tokenContract] != TOKEN_CONTRACT_CONF_NONE;
    }

    function setTokenConfigInternal(address _thisBcTokenContract, bool _thisBcMassC) private {
        tokenContractConfiguration[_thisBcTokenContract] =
            _thisBcMassC ? TOKEN_CONTRACT_CONF_MASSC : TOKEN_CONTRACT_CONF_MINTER;
    }

    function changeContractMappingInternal(address _thisBcTokenContract, uint256 _otherBcId, address _othercTokenContract) private {
        tokenContractAddressMapping[_thisBcTokenContract][_otherBcId] = _othercTokenContract;
        emit TokenContractAddressMappingChanged(_thisBcTokenContract, _otherBcId, _othercTokenContract);
    }
}