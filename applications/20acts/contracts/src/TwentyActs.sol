/*
 * Copyright 2022 ConsenSys Software Inc
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
pragma solidity >=0.8;

import "../../../../contracts/contracts/src/functioncall/common/CbcDecVer.sol";
import "../../../../contracts/contracts/src/openzeppelin/access/AccessControl.sol";
import "../../../../contracts/contracts/src/openzeppelin/security/Pausable.sol";
import "../../../../contracts/contracts/src/openzeppelin/token/ERC20/IERC20.sol";


/**
 * 20ACTS protocol: Facilitate ERC 20 transfers from source blockchains to target blockchains by users.
 * This is achieved by Liquidity Providers transferring from the target blockchain back to the source
 * blockchain.
 * TODO: Explain about incentivizations.
 * TODO: Make sure I am enforcing this reverse transfer in the protocol.
 *
 */
contract TwentyActs is Pausable, AccessControl, CbcDecVer {

    bytes32 public constant MAPPING_ROLE = keccak256("MAPPING_ROLE");
    bytes32 public constant PAUSER_ROLE = keccak256("PAUSER_ROLE");


    /**
 * Update the mapping between an ERC 20 contract on this blockchain and an ERC 20
 * contract on another blockchain.
 *
 * @param _thisBcTokenContract  Address of ERC 20 contract on this blockchain.
     * @param _otherBcId            Blockchain ID where the corresponding ERC 20 contract resides.
     * @param _othercTokenContract  Address of ERC 20 contract on the other blockchain.
     */
    event ERC20AddressMappingChanged(
        address _thisBcTokenContract,
        uint256 _otherBcId,
        address _othercTokenContract
    );

    event PrepareOnTarget(
        uint256 _crosschainTxId,
        address _receiverLiquidityProvider,
        uint256 _txTimeoutSeconds,
        uint256 _sourceBcId,
        address _sourceBcTokenContract,
        address _userSender,
        uint256 _amount
    );
    bytes32 internal constant PREPARE_ON_TARGET_EVENT_SIGNATURE =
        keccak256("PrepareOnTarget(uint256,address,uint256,uint256,address,address,uint256)");


    event PrepareOnSource(
        uint256 _targetBcId,
        uint256 _crosschainTxId,
        bool _success);
    bytes32 internal constant PREPARE_ON_SOURCE_EVENT_SIGNATURE =
        keccak256("PrepareOnSource(uint256,uint256,bool)");


    event FinalizeOnTarget(uint256 _crosschainTxId, bool _success);





    uint256 public myBlockchainId;


    struct TxInfo {
        uint256 timeout;
        uint256 otherBcId;
        address erc20ThisBc;
        uint256 amount;
    }

    // Mapping of cross-blockchain transaction id to transaction information.
    // 0: Never used.
    // 1: The transaction has completed and was successful.
    // 2: The transaction has completed and was not successful.
    // Otherwise: time-out for the transaction: as seconds since unix epoch.
//    uint256 private constant NOT_USED = 0;
//    uint256 private constant SUCCESS = 1;
//    uint256 private constant FAILURE = 2;
    mapping(uint256 => TxInfo) public txInfo;



    // Mapping of ERC 20 contracts on this blockchain to ERC 20 contracts
    // of the same type on different blockchains.
    //
    // Map (token contract address on this blockchain =>
    //  Map (destination blockchain Id => address on remote contract)
    mapping(address => mapping(uint256 => address)) public erc20AddressMapping;

    // Mapping of ERC 20 contracts which deposits are allowed for.
    //
    // Map (token contract address on this blockchain => bool)
    mapping(address => bool) public erc20Supported;

    // Addresses of ERC 20 bridges on other blockchains.
    mapping(uint256 => address) public remoteErc20Bridges;



    // List of addresses that are banned from using the service either as a
    // liquidity provider or as a user. The user may be able to withdraw funds
    // from the service.
    // Map (address of user  => true if banned)
    mapping(address => bool) public banned;

    // List of addresses that are not allowed to withdraw funds earlier deposited.
    mapping(address => bool) public frozen;


    // Map of who has how many tokens deposited with the contract.
    // Map (address of user or liquidity provider => ERC 20 contract => amount)
    mapping(address => mapping(address => uint256)) public deposits;

    // Map of who has how many tokens allocated in active transfers.
    // Map (address of user or liquidity provider => ERC 20 contract => amount)
    mapping(address => mapping(address => uint256)) public allocated;

    // Map of who has how many tokens allocated in active withdrawals.
    // Map (address of user or liquidity provider => ERC 20 contract => amount)
    mapping(address => mapping(address => uint256)) public withdrawals;

    // Map of earliest withdrawal time for active withdrawals.
    // Map (address of user or liquidity provider => ERC 20 contract => earliest withdrawal time in seconds)
    mapping(address => mapping(address => uint256)) public withdrawalsTime;

    // How long liquidity providers need to wait between when they request
    // a withdrawal and when they can action the withdrawal.
    uint256 public withdrawWaitPeriod;

    /**
     *
     * @param _withdrawWaitPeriod Period between requesting a withdraw and being able to action the withdraw in seconds.
     */
    constructor(uint256 _myBlockchainId, uint256 _withdrawWaitPeriod) {
        address sender = msg.sender;
        _setupRole(DEFAULT_ADMIN_ROLE, sender);

        _setupRole(MAPPING_ROLE, sender);
        _setupRole(PAUSER_ROLE, sender);


        myBlockchainId = _myBlockchainId;
        withdrawWaitPeriod = _withdrawWaitPeriod;
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
            "20ACTS: Pause: Must have PAUSER role"
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
            "20ACTS: Unpause: Must have PAUSER role"
        );
        _unpause();
    }


    /**
     * @dev Set or update the mapping between an ERC 20 contract on this blockchain and an ERC 20
     * contract on another blockchain.
     *
     * Requirements:
     * - the caller must have the `MAPPING_ROLE`.
     *
     * @param _thisBcTokenContract  Address of ERC 20 contract on this blockchain.
     * @param _otherBcId            Blockchain ID where the corresponding ERC 20 contract resides.
     * @param _otherTokenContract   Address of ERC 20 contract on the other blockchain. Set to 0 to
     *                              remove the mapping, thus indicating that the token can not be
     *                              transferred to blockchain _otherBcId.
     */
    function setErc20Mapping(
        address _thisBcErc20,
        uint256 _otherBcId,
        address _otherBcErc20
    ) external {
        require(
            hasRole(MAPPING_ROLE, _msgSender()),
            "20ACTS: set ERC 20 Mapping: Must have MAPPING role"
        );

        // Indicate the token us supported.
        erc20Supported[_thisBcErc20] = true;

        erc20AddressMapping[_thisBcErc20][_otherBcId] = _othercTokenContract;
        emit ERC20AddressMappingChanged(_thisBcErc20, _otherBcId, _othercTokenContract);
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
    function setBridgeMapping(
        uint256 _otherBcId,
        address _otherErc20Bridge
    ) external {
        require(
            hasRole(MAPPING_ROLE, _msgSender()),
            "20ACTS: Set Bridge Mapping: Must have MAPPING role"
        );
        remoteErc20Bridges[_otherBcId] = _otherErc20Bridge;
    }


    /**
     * Ban a user / remove a ban against a user. Banning a user stops them from depositing funds.
     *
     * Requirements:
     * - the caller must have the `DEFAULT_ADMIN_ROLE`.
     *
     * @param _user       The address of the user or liquidity provider.
     * @param _banned     The value to set banned to for the user.
     */
    function setBanned(
        address _user,
        bool _banned
    ) external {
        require(
            hasRole(DEFAULT_ADMIN_ROLE, msg.sender),
            "20ACTS: Set Banned: Must have ADMIN role"
        );
        banned[_user] = _banned;
    }

    /**
     * Freeze a user or liquidity provider's assets, preventing them from withdrawing funds. This
     * function can also be used to un-freeze a user's assets.
     *
     * Requirements:
     * - the caller must have the `DEFAULT_ADMIN_ROLE`.
     *
     * @param _user       The address of the user or liquidity provider.
     * @param _frozen     The value to set frozen to for the user.
     */
    function setFrozen(
        address _user,
        bool _frozen
    ) external {
        require(
            hasRole(DEFAULT_ADMIN_ROLE, msg.sender),
            "20ACTS: Set Frozen: Must have ADMIN role"
        );
        frozen[_user] = _frozen;
    }



    /**
     * Deposit tokens into the contract / transfer ownership of some ERC 20 tokens from a user
     * or liquidity provider (msg.sender) to this contract.
     *
     * NOTE: msg.sender must have called Approve on the ERC20 contract prior to this call.
     *
     * @param _thisBcErc20  Address of ERC 20 contract.
     * @param _amount       Number of tokens to transfer.
     */
    function deposit(address _thisBcErc20, uint256 _amount) external {
        require(erc20Supported[_thisBcErc20], "20ACTS: Deposit: Deposits not allowed for ERC 20 contract");
        require(!banned[msg.sender], "20ACTS: Deposit: Account is banned from depositing funds");

        deposits[msg.sender][_thisBcErc20] += _amount;

        IERC20 tokenContract = IERC20(_thisBcErc20);
        tokenContract.transferFrom(msg.sender, address(this), _amount);

        // TODO emit a depost event.
    }


    /**
     * Liquidity Providers use this function to request withdrawals.
     *
     * @param _thisBcErc20  Address of ERC 20 contract.
     * @param _amount       Number of tokens to withdrawal.
     */
    function requestWithdrawal(address _thisBcErc20, uint256 _amount) external {
        require(withdrawalsTime[msg.sender][_thisBcErc20] == 0, "20ACTS: Request Withdrawal: Active withdrawal");
        uint256 amountDeposited = deposits[msg.sender][_thisBcErc20];
        uint256 amountAllocated = allocated[msg.sender][_thisBcErc20];
        uint256 amountWithdrawals = withdrawals[msg.sender][_thisBcErc20];
        require(amountDeposited - amountAllocated - amountWithdrawals <= _amount, "20ACTS: Request Withdraw: Amount exceeds unallocated deposits");
        withdrawals[msg.sender][_thisBcErc20] = amountWithdrawals + _amount;
        withdrawalsTime[msg.sender][_thisBcErc20] = withdrawWaitPeriod + block.timestamp;

        // TODO emit an event
    }


    /**
     * Liquidity Providers use this function to request withdrawals.
     *
     * @param _thisBcErc20  Address of ERC 20 contract.
     * @param _amount       Number of tokens to withdrawal.
     */
    function finalizeWithdrawal(address _thisBcErc20) external {
        require(withdrawalsTime[msg.sender][_thisBcErc20] != 0, "20ACTS: Finalize Withdrawal: No active withdrawal");
        require(withdrawalsTime[msg.sender][_thisBcErc20] <= block.timestamp, "20ACTS: Finalize Withdrawal: Attempting to withdraw early");
        require(!frozen[msg.sender], "20ACTS: Finalize Withdrawal: Account frozen");

        // Execute the transfer. If this reverts, it will revert the entire transaction.
        IERC20 tokenContract = IERC20(_thisBcErc20);
        tokenContract.transfer(msg.sender, _amount);


        uint256 amountDeposited = deposits[msg.sender][_thisBcErc20];
        uint256 amountWithdrawals = withdrawals[msg.sender][_thisBcErc20];

        // This should be impossible.
        assert(amountDeposited > amountWithdrawals, "20ACTS: Withdraw: Withdrawal amount > deposited amount");

        withdrawals[msg.sender][_thisBcErc20] = 0;
        deposits[msg.sender][_thisBcErc20] = amountDeposited - amountWithdrawals;


        // TODO emit an event
    }

    /**
     * Check that the Crosschain Transaction Id is not currently in use.
     * Check that the Liquidity Provider has X unallocated tokens in the 20ACTS contract.
     * Keep a record that the token transfer is in progress. This includes a time-out and a crosschain transaction id. Allocate X tokens to this transfer.
     * Emit a Prepare on Target Chain event.
     */
    function prepareOnTarget(
        uint256 _crosschainTxId,
        uint256 _timeout,
        uint256 _sourceBcId,
        address _thisBcErc20,
        address _userSender,
        uint256 _amount
    ) external {
        // TODO one of the parameters has to be the signed blob from the user.

        require(txInfo[_crosschainTxId].timeout == 0, "20ACTS: Prepare On Target: Transaction already registered");
        uint256 txTimeoutSeconds = _timeout + block.timestamp;

        address sourceErc20BridgeContract = remoteErc20Bridges[_sourceBcId];
        require(
            sourceErc20BridgeContract != address(0),
            "20ACTS: Source blockchain not supported by target 20ACTS"
        );

        // The token must be able to be transferred to the source blockchain.
        address sourceBcTokenContract = erc20AddressMapping[_thisBcErc20][_sourceBcId];
        require(
            sourceBcTokenContract != address(0),
            "20ACTS: Token not transferable to requested blockchain"
        );


        // Check that the Liquidity Provider enough unallocated tokens in the 20ACTS contract.
        uint256 amountDeposited = deposits[msg.sender][_thisBcErc20];
        uint256 amountAllocated = allocated[msg.sender][_thisBcErc20];
        uint256 amountWithdrawals = withdrawals[msg.sender][_thisBcErc20];
        require(amountDeposited - amountAllocated - amountWithdrawals <= _amount, "20ACTS: Prepare Target: Amount exceeds unallocated deposits");

        // Allocate the funds.
        allocated[msg.sender][_thisBcErc20] = amountAllocated + _amount;

        // Keep a record of the transfer.
        TxInfo memory txInf = TxInfo(txTimeoutSeconds, _sourceBcId, _thisBcErc20, _amount);
        txInfo[_crosschainTxId] = txInf;

        // TODO add in fee for liquidity provider and original liquidity provider.
        emit PrepareOnTarget(
            _crosschainTxId,
            msg.sender,
            txTimeoutSeconds,
            _sourceBcId,
            sourceBcTokenContract,
            _userSender,
            _amount
        );
    }




    /**
     * Prepare transaction on source chain.
     *
     * The process is:
     * * The liquidity provider is expected to submit the transaction that calls this function.
     * * Validate the “Prepare on target chain” has happened.
     * * Check that the crosschain transaction id is not currently in use.
     * * Check that Prepare on Target Chain is not too old.
     * * Transfer ownership in the ERC 20 contract from the user to the 20ACTS contract
     * * 20ACTS contract: Record that the X + Y + Z tokens are now allocated.
     * * Emit a Prepare on Source Chain event.
     * * If there was a revert during execution of the bridge contract, indicate failure in the Prepare on Source Chain event.
     */
    function prepareOnSource(
        uint256 _targetBcId,
        address _target20ActsAddress,
        bytes calldata _eventData,
        bytes calldata _signatureOrProof
    ) external {
        decodeAndVerifyEvent(
            _targetBcId,
            _target20ActsAddress,
            PREPARE_ON_TARGET_EVENT_SIGNATURE,
            _eventData,
            _signatureOrProof
        );

        // Decode _eventData
        uint256 crosschainTxId;
        address receiverLiquidityProvider;
        uint256 txTimeoutSeconds;
        uint256 sourceBcId;
        address sourceBcTokenContract;
        address userSender;
        uint256 amount;
        (crosschainTxId, receiverLiquidityProvider, txTimeoutSeconds, sourceBcId, sourceBcTokenContract, userSender, amount) =
            abi.decode(_eventData,(uint256, address, uint256, uint256, address, address, uint256));

        // Ignore events not targeting this blockchain
        require(
            sourceBcId == myBlockchainId,
            "20ACTS: Prepare on Source: Incorrect destination blockchain id"
        );

        uint256 targetBcTxId = calcTargetBcTxId(_targetBcId, crosschainTxId);

        // Replay attack protection
        require(replayPrevention[targetBcTxId] == 0, "20ACTS: Prepare On Source: Transaction already exists");

        // Fail timed out transfers. Create an event so that the overall crosschain transaction
        // can be cancelled on the target chain.
        if (txTimeoutSeconds < block.timestamp) {
            // Transaction has timed out.
            emit PrepareOnSource(_targetBcId, crosschainTxId, false);
        }

        // Register the transfer on this chain.
        replayPrevention[targetBcTxId] = txTimeoutSeconds;

        // TODO fail these gracefully:
        // TODO check that the 20ACTS contract on target is trusted.
        // TODO check that ERC 20 on this chain is supported.

        //     * * Transfer ownership in the ERC 20 contract from the user to the 20ACTS contract
        //     * * 20ACTS contract: Record that the X + Y + Z tokens are now allocated.
        //     * * Emit a Prepare on Source Chain event.
        // TODO when does the fee get introduced. Probably on the target chain.

        IERC20 tokenContract = IERC20(_thisBcErc20);
        tokenContract.transferFrom(msg.sender, _amount);

        uint256 amountDeposited = deposits[msg.sender][_thisBcErc20];
        uint256 amountAllocated = allocated[msg.sender][_thisBcErc20];

        require(amountDeposited - amountAllocated - amountWithdrawals <= _amount, "20ACTS: Prepare Target: Amount exceeds unallocated deposits");

        // Allocate the funds.
        allocated[msg.sender][_thisBcErc20] = amountAllocated + _amount;
        allocated[msg.sender][_thisBcErc20] = amountAllocated + _amount;



    }



    /**
    Liquidity provider (or anyone else) submits: Finalize on target chain:
Check that the Crosschain Transaction Id exists.
IF before timeout:
Present proof that the “prepare on source chain” has occurred. That is, submit  Prepare on Source Chain event such that it is trusted.
If the event indicates success:
Delete the record indicating that the transfer is in progress.
Transfer ownership of the tokens from the 20ACTS contract to the User.
Emit a Finalize on Target Chain event, indicating success.
IF the event indicates failure: Do the same things as the timeout flow shown below.
IF after timeout:
The records indicating transfers are in progress are deleted.
The transfer on the target chain is reversed: the value is transferred from the ERC 20 bridge back to the liquidity provider.
Emit a Finalize on Target Chain event, indicating failure.
Update reputations of User (the To address of the transfer) and Liquidity Provider (identified by the Liquidity Provider ID) on target chain.

    */
    function finalizeOnTarget() external {
        }


    /**
    *
    * Liquidity provider (or anyone else) submits: Finalize on source chain:
Present proof that the “finalize on target chain” has occurred. That is, submit  Prepare on Target Chain event such that it is trusted.
Check that the crosschain transaction id exists.
IF the event indicates success:
Delete the record indicating that the transfer is in progress and do the transfer.
Transfer X + Z tokens to the liquidity provider.
Transfer Y tokens to the service provider (MetaMask).
Transfer M to the original liquidity provider (in the case of bidding)
IF the event indicates failure:
Delete the record indicating that the transfer is in progress and do the transfer.
Transfer the tokens to the user.
Update reputations of User and the Liquidity Provider (identified by the Liquidity Provider ID) on target chain.

    */
    function finalizeOnSource() external {
    }


    /**
     * Calculate the combined Target Blockchain and Crosschain Transaction Id.
     */
    function calcTargetBcTxId(uint256 _targetBcId, uint256 _crosschainTxId)
    private
    pure
    returns (bytes32)
    {
        return keccak256(abi.encodePacked(_targetBcId, _crosschainTxId));
    }


}
