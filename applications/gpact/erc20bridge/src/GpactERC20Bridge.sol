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

import "./presets/LockableERC20PresetMinterPauser.sol";
import "./LockableERC20.sol";
import "../../../../contracts/src/functioncall/interface/AtomicHiddenAuthParameters.sol";
import "../../../../contracts/src/openzeppelin/security/Pausable.sol";
import "../../../../contracts/src/openzeppelin/access/AccessControl.sol";
import "../../../../contracts/src/functioncall/interface/CrosschainFunctionCallInterface.sol";

/**
 * ERC 20 bridge using the General Purpose Atomic Crosschain Transaction protocol.
 *
 */
contract GpactERC20Bridge is
    AtomicHiddenAuthParameters,
    Pausable,
    AccessControl
{
    bytes32 public constant MAPPING_ROLE = keccak256("MAPPING_ROLE");
    bytes32 public constant PAUSER_ROLE = keccak256("PAUSER_ROLE");

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
    CrosschainFunctionCallInterface private gpactCbcBridge;

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
    mapping(address => mapping(uint256 => address))
        private tokenContractAddressMapping;

    // Addresses of ERC 20 bridges on other blockchains.
    mapping(uint256 => address) private remoteErc20Bridges;

    // Blockchain Ids that are approved to be used as root blockchains.
    mapping(uint256 => bool) private approvedRootBlockchains;

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
    event TransferTo(
        uint256 _destBcId,
        address _srcTokenContract,
        address _destTokenContract,
        address _sender,
        address _recipient,
        uint256 _amount
    );

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
    event ReceivedFrom(
        uint256 _srcBcId,
        address _srcTokenContract,
        address _destTokenContract,
        address _recipient,
        uint256 _amount
    );

    /**
     * Update the mapping between an ERC 20 contract on this blockchain and an ERC 20
     * contract on another blockchain.
     *
     * @param _thisBcTokenContract  Address of ERC 20 contract on this blockchain.
     * @param _otherBcId            Blockchain ID where the corresponding ERC 20 contract resides.
     * @param _othercTokenContract  Address of ERC 20 contract on the other blockchain.
     */
    event TokenContractAddressMappingChanged(
        address _thisBcTokenContract,
        uint256 _otherBcId,
        address _othercTokenContract
    );

    /**
     * Token contract configuration has been set / been changed.
     *
     * @param _thisBcTokenContract  Address of ERC 20 contract on this blockchain.
     * @param _config               Configuration value for the contract.
     */
    event TokenContractConfig(address _thisBcTokenContract, uint256 _config);

    /**
     * Approve or remove approval for a blockchain to be used as a root blockchain.
     *
     * @param _bcId             Blockchain identifier.
     * @param _approved         True if the blockchain has been approved.
     */
    event ApprovedRootBcId(uint256 _bcId, bool _approved);

    /**
     * @param _gpactCbcContract  Simple Function Call protocol implementation.
     */
    constructor(address _gpactCbcContract) {
        address sender = _msgSender();
        _setupRole(DEFAULT_ADMIN_ROLE, sender);

        _setupRole(MAPPING_ROLE, sender);
        _setupRole(PAUSER_ROLE, sender);

        gpactCbcBridge = CrosschainFunctionCallInterface(_gpactCbcContract);
    }

    /**
     * Pauses the bridge.
     *
     * Requirements:
     * - the caller must have the `PAUSER_ROLE`.
     */
    function pause() external {
        require(
            hasRole(PAUSER_ROLE, _msgSender()),
            "ERC20 Bridge: Must have PAUSER role"
        );
        _pause();
    }

    /**
     * Unpauses the bridge.
     *
     * Requirements:
     * - the caller must have the `PAUSER_ROLE`.
     */
    function unpause() external {
        require(
            hasRole(PAUSER_ROLE, _msgSender()),
            "ERC20 Bridge: Must have PAUSER role"
        );
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
    function addContractFirstMapping(
        address _thisBcTokenContract,
        uint256 _otherBcId,
        address _otherTokenContract,
        bool _thisBcMassC
    ) external {
        require(
            hasRole(MAPPING_ROLE, _msgSender()),
            "ERC20 Bridge: Must have MAPPING role"
        );
        require(
            !tokenExists(_thisBcTokenContract),
            "ERC20 Bridge: token already configured"
        );

        setTokenConfigInternal(_thisBcTokenContract, _thisBcMassC);
        changeContractMappingInternal(
            _thisBcTokenContract,
            _otherBcId,
            _otherTokenContract
        );
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
    function changeContractMapping(
        address _thisBcTokenContract,
        uint256 _otherBcId,
        address _otherTokenContract
    ) external {
        require(
            hasRole(MAPPING_ROLE, _msgSender()),
            "ERC20 Bridge: Must have MAPPING role"
        );
        require(
            tokenExists(_thisBcTokenContract),
            "ERC20 Bridge: token not configured"
        );
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
     * Requirements:
     * - the caller must have the `MAPPING_ROLE`.
     *
     * @param _thisBcTokenContract      Address of ERC 20 contract on this blockchain.
     * @param _thisBcMassC              True if the token contract on this blockchain uses mass conservation.
     */
    function setTokenConfig(address _thisBcTokenContract, bool _thisBcMassC)
        external
    {
        require(
            hasRole(MAPPING_ROLE, _msgSender()),
            "ERC20 Bridge: Must have MAPPING role"
        );
        require(
            tokenExists(_thisBcTokenContract),
            "ERC20 Bridge: token not configured"
        );
        setTokenConfigInternal(_thisBcTokenContract, _thisBcMassC);
    }

    /**
     * @dev Indicates that a blockchain can be used as a root blockchain. This implies
     * the application has access to the blockchain, and can call the Root function
     * to finalise transactions and fetch the Root Event to unlock locked storage
     * on this blochain.
     *
     * @param _bcId   Blockchain that have been approved as a root blockchain.
     */
    function addApprovedRootBcId(uint256 _bcId) external {
        require(
            hasRole(MAPPING_ROLE, _msgSender()),
            "ERC20 Bridge: Must have MAPPING role"
        );
        approvedRootBlockchains[_bcId] = true;
        emit ApprovedRootBcId(_bcId, true);
    }

    /**
     * @dev Indicates that a blockchain can no longer be used as a root blockchain.
     *
     * @param _bcId   Blockchain whose approval as a root blockchain has been cancelled.
     */
    function removeApprovedRootBcId(uint256 _bcId) external {
        require(
            hasRole(MAPPING_ROLE, _msgSender()),
            "ERC20 Bridge: Must have MAPPING role"
        );
        approvedRootBlockchains[_bcId] = false;
        emit ApprovedRootBcId(_bcId, false);
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
    function addRemoteERC20Bridge(uint256 _otherBcId, address _otherErc20Bridge)
        external
    {
        require(
            hasRole(MAPPING_ROLE, _msgSender()),
            "ERC20 Bridge: Must have MAPPING role"
        );
        remoteErc20Bridges[_otherBcId] = _otherErc20Bridge;
    }

    /**
     * Transfer tokens from tx.origin to this contract on this blockchain,
     * and request tokens on the remote blockchain be given to the requested
     * account on the destination blockchain from the bridge contract.
     * (or burn on this blockchain and mint on the other blockchain).
     *
     * The msg.sender MUST be the crosschain control contract.
     *
     * NOTE: tx.origin must have called approve() on the token contract.
     *
     * @param _destBcId         Blockchain id of destination blockchain.
     * @param _srcTokenContract Address of ERC 20 contract on this blockchain.
     * @param _recipient        Address of account to transfer tokens to on the destination blockchain.
     * @param _amount           The number of tokens to transfer.
     */
    function transferToOtherBlockchain(
        uint256 _destBcId,
        address _srcTokenContract,
        address _recipient,
        uint256 _amount
    ) public whenNotPaused {
        require(
            msg.sender == address(gpactCbcBridge),
            "Can only be called directly from Crosschain Control Contract"
        );

        address destErc20BridgeContract = remoteErc20Bridges[_destBcId];
        require(
            destErc20BridgeContract != address(0),
            "ERC20 Bridge: Blockchain not supported"
        );

        // The token must be able to be transferred to the target blockchain.
        address destTokenContract = tokenContractAddressMapping[
            _srcTokenContract
        ][_destBcId];
        require(
            destTokenContract != address(0),
            "ERC20 Bridge: Token not transferable to requested blockchain"
        );

        // Transfer tokens from the user to this contract.
        // The transfer will revert if the account has inadequate balance or if adequate
        // allowance hasn't been set-up.
        transferOrBurn(_srcTokenContract, tx.origin, _amount);

        gpactCbcBridge.crossBlockchainCall(
            _destBcId,
            destErc20BridgeContract,
            abi.encodeWithSelector(
                this.receiveFromOtherBlockchain.selector,
                destTokenContract,
                _recipient,
                _amount
            )
        );

        // NOTE: This event could be emitted, but the overall crosschain transaction
        // could still fail.
        emit TransferTo(
            _destBcId,
            _srcTokenContract,
            destTokenContract,
            tx.origin,
            _recipient,
            _amount
        );
    }

    /**
     * The use-case this handles is EOA -> Crosschain Control -> business logic -> ERC 20 bridge
     * where the business logic contract has an allowance for the specified ERC 20 contract.
     *
     * Transfer tokens from _sender to this contract on this blockchain,
     * and request tokens on the remote blockchain be given to the requested
     * account on the destination blockchain.
     *
     * NOTE: msg.sender must have called approve() on the token contract.
     *
     * @param _sender           Address of account who owns the tokens before the transfer.
     * @param _destBcId         Blockchain id of destination blockchain.
     * @param _srcTokenContract Address of ERC 20 contract on this blockchain.
     * @param _recipient        Address of account to transfer tokens to on the destination blockchain.
     * @param _amount           The number of tokens to transfer.
     */
    function transferFromAccountToOtherBlockchain(
        address _sender,
        uint256 _destBcId,
        address _srcTokenContract,
        address _recipient,
        uint256 _amount
    ) public whenNotPaused {
        address destErc20BridgeContract = remoteErc20Bridges[_destBcId];
        require(
            destErc20BridgeContract != address(0),
            "ERC20 Bridge: Blockchain not supported"
        );

        // The token must be able to be transferred to the target blockchain.
        address destTokenContract = tokenContractAddressMapping[
            _srcTokenContract
        ][_destBcId];
        require(
            destTokenContract != address(0),
            "ERC20 Bridge: Token not transferable to requested blockchain"
        );

        // Transfer tokens from the user to this contract.
        // The transfer will revert if the account has inadequate balance or if adequate
        // allowance hasn't been set-up.
        transferFromOrBurnFrom(msg.sender, _srcTokenContract, _sender, _amount);

        gpactCbcBridge.crossBlockchainCall(
            _destBcId,
            destErc20BridgeContract,
            abi.encodeWithSelector(
                this.receiveFromOtherBlockchain.selector,
                destTokenContract,
                _recipient,
                _amount
            )
        );

        // NOTE: This event could be emitted, but the overall crosschain transaction
        // could still fail.
        emit TransferTo(
            _destBcId,
            _srcTokenContract,
            destTokenContract,
            _sender,
            _recipient,
            _amount
        );
    }

    /**
     * Transfer tokens that are owned by this contract to a recipient. The tokens have
     * effectively been transferred from another blockchain to this blockchain.
     *
     * @param _destTokenContract  ERC 20 contract of the token being transferred.
     * @param _recipient          Account to transfer ownership of the tokens to.
     * @param _amount             The number of tokens to be transferred.
     */
    function receiveFromOtherBlockchain(
        address _destTokenContract,
        address _recipient,
        uint256 _amount
    ) external whenNotPaused {
        require(
            _msgSender() == address(gpactCbcBridge),
            "ERC20 Bridge: Can not process transfers from contracts other than the bridge contract"
        );

        (
            uint256 rootBcId,
            uint256 sourceBcId,
            address sourceContract
        ) = decodeAtomicAuthParams();
        // The source blockchain id is validated at the function call layer. No need to check
        // that it isn't zero.

        require(
            approvedRootBlockchains[rootBcId],
            "ERC 20 Bridge: Root blockchain not approved"
        );
        require(
            sourceContract != address(0),
            "ERC 20 Bridge: caller contract is 0"
        );
        address remoteErc20Bridge = remoteErc20Bridges[sourceBcId];
        require(
            remoteErc20Bridge != address(0),
            "ERC20 Bridge: No ERC 20 Bridge supported for source blockchain"
        );
        require(
            sourceContract == remoteErc20Bridge,
            "ERC20 Bridge: Incorrect source ERC 20 Bridge"
        );

        transferOrMint(_destTokenContract, _recipient, _amount);

        // NOTE: This event could be emitted, but the overall crosschain transaction
        // could still fail.
        emit ReceivedFrom(
            sourceBcId,
            sourceContract,
            _destTokenContract,
            _recipient,
            _amount
        );
    }

    /**
     * Indicates whether a token can be transferred to (or from) a blockchain.
     *
     * @param _bcId          Blockchain id of other blockchain.
     * @param _tokenContract Address of ERC 20 token contract on this blockchain.
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
     * @param _tokenContract Address of ERC 20 token contract on this blockchain.
     * @return address       Contract address of ERC 20 token contract on other blockchain.
     */
    function getBcIdTokenMaping(uint256 _bcId, address _tokenContract)
        public
        view
        returns (address)
    {
        return tokenContractAddressMapping[_tokenContract][_bcId];
    }

    function getRemoteErc20BridgeContract(uint256 _bcId)
        external
        view
        returns (address)
    {
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
    function transferOrMint(
        address _tokenContract,
        address _recipient,
        uint256 _amount
    ) private {
        if (
            tokenContractConfiguration[_tokenContract] ==
            TOKEN_CONTRACT_CONF_MASSC
        ) {
            if (!LockableERC20(_tokenContract).transfer(_recipient, _amount)) {
                revert("transfer failed");
            }
        } else {
            LockableERC20PresetMinterPauser(_tokenContract).mint(
                _recipient,
                _amount
            );
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
    function transferOrBurn(
        address _tokenContract,
        address _spender,
        uint256 _amount
    ) private {
        if (
            tokenContractConfiguration[_tokenContract] ==
            TOKEN_CONTRACT_CONF_MASSC
        ) {
            if (
                !IERC20(_tokenContract).transferFrom(
                    _spender,
                    address(this),
                    _amount
                )
            ) {
                revert("transferFrom failed");
            }
        } else {
            LockableERC20PresetMinterPauser(_tokenContract).burnFrom(
                _spender,
                _amount
            );
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
    function transferFromOrBurnFrom(
        address sender,
        address _tokenContract,
        address _spender,
        uint256 _amount
    ) private {
        if (
            tokenContractConfiguration[_tokenContract] ==
            TOKEN_CONTRACT_CONF_MASSC
        ) {
            LockableERC20(_tokenContract).transferFromAccount(
                sender,
                _spender,
                address(this),
                _amount
            );
        } else {
            LockableERC20PresetMinterPauser(_tokenContract).burnFromAccount(
                sender,
                _spender,
                _amount
            );
        }
    }

    function tokenExists(address _tokenContract) private view returns (bool) {
        return
            tokenContractConfiguration[_tokenContract] !=
            TOKEN_CONTRACT_CONF_NONE;
    }

    function setTokenConfigInternal(
        address _thisBcTokenContract,
        bool _thisBcMassC
    ) private {
        uint256 config = _thisBcMassC
            ? TOKEN_CONTRACT_CONF_MASSC
            : TOKEN_CONTRACT_CONF_MINTER;
        tokenContractConfiguration[_thisBcTokenContract] = config;
        emit TokenContractConfig(_thisBcTokenContract, config);
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
