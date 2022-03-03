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
import "../../../../contracts/contracts/src/common/ResponseProcessUtil.sol";


/**
 * 20ACTS protocol: Facilitate ERC 20 transfers from source blockchains to target blockchains by users.
 * This is achieved by Liquidity Providers transferring from the target blockchain back to the source
 * blockchain.
 * TODO: Explain about incentivizations.
 * TODO: Make sure I am enforcing this reverse transfer in the protocol.
 *
 */
contract TwentyActs is Pausable, AccessControl, CbcDecVer, ResponseProcessUtil {
    // In addition to DEFAULT_ADMIN_ROLE, the following roles are defined.
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


    event SetWithdrawalWaitPeriod(uint256 _waitPeriodInSeconds);
    event SetInfrastructureAccount(address _account);

    /**
     * Indicate an account's banned status has changed.
     *
     * @param _user    The affected account.
     * @param _banned  True if the account is now banned from making transfers or acting as a liquidity provider.
     */
    event Banned(address _user, bool _banned);

    /**
     * Indicate an account's frozen status has changed.
     *
     * @param _user    The affected account.
     * @param _frozen  True if the account is now stopped from withdrawing money.
     */
    event Frozen(address _user, bool _frozen);


    /**
     * Indicate a liquidity provider has deposited funds.
     *
     * @param _lp      The liquidity provider's account.
     * @param _erc20   The ERC 20 contract address.
     * @param _amount  The number of tokens.
     */
    event Deposited(address _lp, address _erc20, uint256 _amount);

    /**
     * Indicates a withdrawal request has been received.
     *
     * @param _lp      The liquidity provider's account.
     * @param _erc20   The ERC 20 contract address.
     * @param _amount  The number of tokens.
     */
    event WithdrawalRequested(address _lp, address _erc20, uint256 _amount);

    /**
     * Indicates a withdrawal has been finalised.
     *
     * @param _lp      The liquidity provider's account.
     * @param _erc20   The ERC 20 contract address.
     * @param _amount  The number of tokens.
     */
    event FinalizeWithdrawal(address _lp, address _erc20, uint256 _amount);



    event PrepareOnTarget(bytes32 _txInfoDigest);
    bytes32 internal constant PREPARE_ON_TARGET_EVENT_SIGNATURE =
        keccak256("PrepareOnTarget(bytes32)");

    // Failure reasons.
    uint256 private constant NONE = 0;
    uint256 private constant BC_NOT_SUPPORTED = 1;
    uint256 private constant WRONG_ERC20 = 2;
    uint256 private constant TIMEOUT = 3;
    uint256 private constant TRANSFER_FROM_FAILED = 4;
    uint256 private constant SENDER_BANNED = 5;
    uint256 private constant RECIPIENT_BANNED = 6;

    event PrepareOnSource(bytes32 _txInfoDigest, bool _success, uint256 _failureReason, string _msg);
    bytes32 internal constant PREPARE_ON_SOURCE_EVENT_SIGNATURE =
        keccak256("PrepareOnSource(bytes32,bool,uint256,string)");

    event FinalizeOnTarget(bytes32 _txInfoDigest);
    bytes32 internal constant FINALIZE_ON_TARGET_EVENT_SIGNATURE =
        keccak256("FinalizeOnTarget(bytes32)");

    event FinalizeOnSource(bytes32 _txInfoDigest);




    uint256 public myBlockchainId;

    struct TxInfo {
        uint256 crosschainTxId;         // Crosschain Transaction id created by the sender.
        uint256 sourceBcId;
        address sourceErc20Address;
        uint256 targetBcId;
        address targetErc20Address;
        address sender;                 //   Sender address on source
        address recipient;              //   Recipient address on target
        address liquidityProvider;      //   Liquidity Provider address on both chains.
        uint256 amount;                 //   Amount on target
        uint256 lpFee;                  //   Liquidity Provider Fee: Amount on source = amount + lpFee + inFee
        uint256 inFee;                  //   Infrastructure Provider Fee
        uint256 biddingPeriodEnd;       //   Bidding Period end in seconds
        uint256 timeout;                //   Timeout by which time Prepare On Source must be mined by
    }


    // Mapping of cross-blockchain transaction info digest to state.
    uint256 private constant NOT_USED = 0;
    uint256 private constant IN_PROGRESS = 1;
    uint256 private constant COMPLETED_FAIL = 2;
    uint256 private constant COMPLETED_SUCCESS = 3;
    mapping(bytes32 => uint256) public txState;

    // True when a finalize on target or source has occurred
    mapping(uint256 => bool) public txComplete;



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
    uint256 public withdrawalWaitPeriod;

    address public infrastructureAccount;


    /**
     *
     * @param _withdrawalWaitPeriod Period between requesting a withdraw and being able to action the withdraw in seconds.
     */
    constructor(uint256 _myBlockchainId, uint256 _withdrawalWaitPeriod, address _infrastructureAccount) {
        address sender = msg.sender;
        _setupRole(DEFAULT_ADMIN_ROLE, sender);

        _setupRole(MAPPING_ROLE, sender);
        _setupRole(PAUSER_ROLE, sender);


        myBlockchainId = _myBlockchainId;
        withdrawalWaitPeriod = _withdrawalWaitPeriod;
        infrastructureAccount = _infrastructureAccount;
    }


    /**
     * @dev Set the period of time in seconds that an entity must wait prior to withdrawing funds.
     * NOTE: Caller must have DEFAULT_ADMIN_ROLE
     *
     * @param _withdrawalWaitPeriod Period between requesting a withdraw and being able to action the withdraw in seconds.
     */
    function setWithdrawalWaitPeriod(uint256 _withdrawalWaitPeriod) external {
        require(
            hasRole(DEFAULT_ADMIN_ROLE, _msgSender()),
            "20ACTS:Must have ADMIN role"
        );

        withdrawalWaitPeriod = _withdrawalWaitPeriod;
        emit SetWithdrawalWaitPeriod(_withdrawalWaitPeriod);
    }



    /**
     * @dev Pauses the bridge.
     * NOTE: caller must have the PAUSER_ROLE.
     */
    function pause() external {
        require(
            hasRole(PAUSER_ROLE, _msgSender()),
            "20ACTS:Must have PAUSER role"
        );
        _pause();
    }

    /**
     * @dev Unpauses the bridge.
     * NOTE: caller must have the PAUSER_ROLE.
     */
    function unpause() external {
        require(
            hasRole(PAUSER_ROLE, _msgSender()),
            "20ACTS:Must have PAUSER role"
        );
        _unpause();
    }


    /**
     * @dev Set or update the mapping between an ERC 20 contract on this blockchain and an ERC 20
     * contract on another blockchain.
     * NOTE: caller must have the MAPPING_ROLE.
     *
     * @param _thisBcErc20          Address of ERC 20 contract on this blockchain.
     * @param _otherBcId            Blockchain ID where the corresponding ERC 20 contract resides.
     * @param _otherBcErc20         Address of ERC 20 contract on the other blockchain. Set to 0 to
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
            "20ACTS:Must have MAPPING role"
        );

        // Indicate the token us supported.
        erc20Supported[_thisBcErc20] = true;

        erc20AddressMapping[_thisBcErc20][_otherBcId] = _otherBcErc20;
        emit ERC20AddressMappingChanged(_thisBcErc20, _otherBcId, _otherBcErc20);
    }


    /**
     * Set the account that receives the infrastructure provider fees.
     * NOTE: caller must have DEFAULT_ADMIN_ROLE
     *
     * @param _account      Account to receive the fees.
     */
    function setInfrastructureAccount(
        address _account
    ) external {
        require(
            hasRole(DEFAULT_ADMIN_ROLE, msg.sender),
            "20ACTS:Must have ADMIN role"
        );
        infrastructureAccount = _account;

        emit SetInfrastructureAccount(_account);
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
            "20ACTS:Must have ADMIN role"
        );
        banned[_user] = _banned;

        emit Banned(_user, _banned);
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
            "20ACTS:Must have ADMIN role"
        );
        frozen[_user] = _frozen;

        emit Frozen(_user, _frozen);
    }



    /**
     * Deposit tokens into the contract / transfer ownership of some ERC 20 tokens from a user
     * or liquidity provider (msg.sender) to this contract.
     *
     * NOTE: msg.sender must have called Approve on the ERC20 contract prior to this call.
     *
     * @param _erc20        Address of ERC 20 contract.
     * @param _amount       Number of tokens to transfer.
     */
    function deposit(address _erc20, uint256 _amount) external {
        require(erc20Supported[_erc20], "20ACTS:Deposits not allowed for ERC 20 contract");
        require(!banned[msg.sender], "20ACTS:Account is banned from depositing funds");

        deposits[msg.sender][_erc20] += _amount;

        IERC20 tokenContract = IERC20(_erc20);
        tokenContract.transferFrom(msg.sender, address(this), _amount);

        emit Deposited(msg.sender, _erc20, _amount);
    }


    /**
     * @dev Liquidity Providers use this function to request withdrawals. Each liquidity provider
     * can only make one withdrawal at a time.
     * NOTE: A two step withdrawal process is used to provide time for a liquidity provider's account
     * that has been taken over by an attacker to be frozen.
     *
     * @param _erc20        Address of ERC 20 contract.
     * @param _amount       Number of tokens to withdrawal.
     */
    function requestWithdrawal(address _erc20, uint256 _amount) external {
        require(withdrawalsTime[msg.sender][_erc20] == 0, "20ACTS:Active withdrawal");
        uint256 amountDeposited = deposits[msg.sender][_erc20];
        uint256 amountAllocated = allocated[msg.sender][_erc20];
        require(amountDeposited - amountAllocated >= _amount, "20ACTS:Amount exceeds unallocated deposits");
        withdrawals[msg.sender][_erc20] = _amount;
        withdrawalsTime[msg.sender][_erc20] = withdrawalWaitPeriod + block.timestamp;

        emit WithdrawalRequested(msg.sender, _erc20, _amount);
    }


    /**
     * @dev Liquidity Providers use this function to finalize a previously requested withdrawal.
     * NOTE: A two step withdrawal process is used to provide time for a liquidity provider's account
     * that has been taken over by an attacker to be frozen.
     *
     * @param _erc20  Address of ERC 20 contract.
     */
    function finalizeWithdrawal(address _erc20) external {
        require(withdrawalsTime[msg.sender][_erc20] != 0, "20ACTS:No active withdrawal");
        require(withdrawalsTime[msg.sender][_erc20] > block.timestamp, "20ACTS:Attempting to withdraw early");
        require(!frozen[msg.sender], "20ACTS:Account frozen");

        uint256 amountDeposited = deposits[msg.sender][_erc20];
        uint256 amountWithdrawals = withdrawals[msg.sender][_erc20];

        // This should be impossible.
        require(amountDeposited > amountWithdrawals, "20ACTS:Withdrawal amount > deposited amount");

        withdrawals[msg.sender][_erc20] = 0;
        deposits[msg.sender][_erc20] = amountDeposited - amountWithdrawals;

        // Execute the transfer. If this reverts, it will revert the entire transaction.
        IERC20 tokenContract = IERC20(_erc20);
        tokenContract.transfer(msg.sender, amountWithdrawals);

        emit FinalizeWithdrawal(msg.sender, _erc20, amountWithdrawals);
    }

    /**
     * Check that the Crosschain Transaction Id is not currently in use.
     * Check that the Liquidity Provider has X unallocated tokens in the 20ACTS contract.
     * Keep a record that the token transfer is in progress. This includes a time-out and a crosschain transaction id. Allocate X tokens to this transfer.
     * Emit a Prepare on Target Chain event.
     */
    function prepareOnTarget(
        TxInfo calldata _txInfo
    ) external {
        // Validate transaction information.
        // No direct validation for crosschain transaction id: Check indirectly via the digest.
        bytes32 txInfoDigest = keccak256(abi.encode(_txInfo));
        require(txState[txInfoDigest] == NOT_USED, "20ACTS:Transaction already registered");
        // Validate the source blockchain id: Check that there is a corresponding bridge on that blockchain.
        address source20ActsContract = remoteCrosschainControlContracts[_txInfo.sourceBcId];
        require(
            source20ActsContract != address(0),
            "20ACTS:Source blockchain not supported by target 20ACTS"
        );
        // Validate the ERC 20 address on the source chain and on target chain: The two contracts
        // must be linked to each other.
        address targetErc20Address = _txInfo.targetErc20Address;
        address sourceErc20Address = erc20AddressMapping[_txInfo.targetErc20Address][_txInfo.sourceBcId];
        require(
            sourceErc20Address == _txInfo.sourceErc20Address,
            "20ACTS:Token not transferable to requested ERC20"
        );
        // Validate targetBcId: It must be this chain.
        require(_txInfo.targetBcId == myBlockchainId, "20ACTS:Not for this blockchain");
        // No validation possible for txInfo.sender or txInfo.recipient
        // Validate liquidityProvider: they must be the party that has submitted this transaction.
        // Doing this ensures the address is valid.
        require(msg.sender == _txInfo.liquidityProvider, "20ACTS:msg.sender must be liquidity provider");
        // Validate amount. No validation possible on lpFee or inFee.
        // Check that the Liquidity Provider has enough unallocated tokens in the 20ACTS contract.
        uint256 amount = _txInfo.amount;
        uint256 amountDeposited = deposits[msg.sender][targetErc20Address];
        uint256 amountAllocated = allocated[msg.sender][targetErc20Address];
        uint256 amountWithdrawals = withdrawals[msg.sender][targetErc20Address];
        require(amountDeposited - amountAllocated - amountWithdrawals >= amount, "20ACTS:Amount exceeds unallocated deposits");
        // Validate biddingPeriodEnd: Must be in the past.
        require(_txInfo.biddingPeriodEnd < block.timestamp, "20ACTS:Bidding period still in progress");
        // Validate timeout: Must be in the future.
        require(_txInfo.timeout > block.timestamp, "20ACTS:Transaction has timed out");

        require(!banned[_txInfo.sender], "20ACTS:Sender is banned");
        require(!banned[_txInfo.recipient], "20ACTS:Recipient is banned");

        // Allocate the funds.
        allocated[msg.sender][targetErc20Address] = amountAllocated + amount;

        // Keep a record of the transfer.
        txState[txInfoDigest] = IN_PROGRESS;

        emit PrepareOnTarget(txInfoDigest);
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
        TxInfo calldata _txInfo,
        bytes calldata _eventData,
        bytes calldata _signatureOrProof,
        bytes calldata /*_signedErc20ApproveTx*/
    ) external {
        // Validate transaction information that will cause a revert first.
        // No direct validation for crosschain transaction id: Check indirectly via the digest.
        bytes32 txInfoDigest = keccak256(abi.encode(_txInfo));
        {
            require(txState[txInfoDigest] == NOT_USED, "20ACTS:Transaction already registered");
            // Validate sourceBcId: It must be this chain.
            require(_txInfo.sourceBcId == myBlockchainId, "20ACTS:Not for this blockchain");

            // TODO Validation for txInfo.sender: Check that the signer of the transaction is the sender, and that the transaction
            // TODO contains the txInfoDigest  / decode ERC 20 Approve transaction with additional data
            // TODO check that values match


            // Validate biddingPeriodEnd: Must be in the past.
            // TODO this has already bee done on target chain. Don't need to do it here again.
            require(_txInfo.biddingPeriodEnd < block.timestamp, "20ACTS:Bidding period still in progress");
        }

        // Validate the target blockchain id: Check that there is a corresponding bridge on that blockchain.

// TODO this is handled by decodeAndVerifyEvent - however it reverts when we need the logic below.
        // TODO need different way of confirming source of event: a param?

        address target20ActsContract = remoteCrosschainControlContracts[_txInfo.targetBcId];
        if (target20ActsContract == address(0)) {
            // Transfer to target blockchain not supported.
            emit PrepareOnSource(txInfoDigest, false, BC_NOT_SUPPORTED, "");
            txState[txInfoDigest] = COMPLETED_FAIL;
            return;
        }

        // Check that PrepareOnTarget has happened.
        {
            decodeAndVerifyEvent(
                _txInfo.targetBcId,
                target20ActsContract,
                PREPARE_ON_TARGET_EVENT_SIGNATURE,
                _eventData,
                _signatureOrProof
            );
            bytes32 txInfoDigestPrepareOnTarget = abi.decode(_eventData,(bytes32));
            require(txInfoDigest == txInfoDigestPrepareOnTarget, "20ACTS:Incorrect Prepare On Target event");
        }


        // Validate information that will cause a failure, but not a revert.

        // Validate the ERC 20 address on the source chain and on target chain: The two contracts
        // must be linked to each other.
        address sourceErc20Address = _txInfo.sourceErc20Address;
        {
            address targetErc20Address = erc20AddressMapping[sourceErc20Address][_txInfo.targetBcId];
            if (targetErc20Address != _txInfo.targetErc20Address) {
                // Token not transferable to the requested ERC 20 contract.
                emit PrepareOnSource(txInfoDigest, false, WRONG_ERC20, "");
                txState[txInfoDigest] = COMPLETED_FAIL;
                return;
            }
        }

        // No validation possible for txInfo.recipient
        // No validation possible for the liquidityProvider: they may not be the party to submit this transaction.

        // Fail timed out transfers. Create an event so that the overall crosschain transaction
        // can be cancelled on the target chain.
        if (_txInfo.timeout < block.timestamp) {
            // Transaction has timed out.
            emit PrepareOnSource(txInfoDigest, false, TIMEOUT, "");
            txState[txInfoDigest] = COMPLETED_FAIL;
            return;
        }

        if (banned[_txInfo.sender]) {
            emit PrepareOnSource(txInfoDigest, false, SENDER_BANNED, "");
            txState[txInfoDigest] = COMPLETED_FAIL;
            return;
        }

        if (banned[_txInfo.recipient]) {
            emit PrepareOnSource(txInfoDigest, false, RECIPIENT_BANNED, "");
            txState[txInfoDigest] = COMPLETED_FAIL;
            return;
        }


        // Validate amounts: This is done by doing a transfer. It will work if the approve has been done for the amount.
        uint256 totalAmount = _txInfo.amount + _txInfo.lpFee + _txInfo.inFee;
//        IERC20 tokenContract = IERC20(sourceErc20Address);
        // Transfer ownership in the ERC 20 contract from the user to the 20ACTS contract
        (bool success, bytes memory errorMsg) = sourceErc20Address.call(
            abi.encodeWithSelector(IERC20.transferFrom.selector, _txInfo.sender, address(this), totalAmount));
        if (!success) {
            emit PrepareOnSource(txInfoDigest, false, TRANSFER_FROM_FAILED, getRevertMsg(errorMsg));
            txState[txInfoDigest] = COMPLETED_FAIL;
            return;
        }

        // Allocate the funds.
        deposits[_txInfo.sender][sourceErc20Address] +=  totalAmount;
        allocated[_txInfo.sender][sourceErc20Address] += totalAmount;

        // Keep a record of the transfer.
        txState[txInfoDigest] = IN_PROGRESS;

        emit PrepareOnSource(txInfoDigest, true, NONE, "");
    }


    /**
     *
     * Check that the Crosschain Transaction Id exists.
   *   Present proof that the “prepare on source chain” has occurred. That is, submit  Prepare on
     *     Source Chain event such that it is trusted.
     *   If the event indicates success:
     *     Delete the record indicating that the transfer is in progress.
     *     Transfer ownership of the tokens from the 20ACTS contract to the User.
     *     Emit a Finalize on Target Chain event, indicating success.
     *   IF the event indicates failure:
     *    The records indicating transfers are in progress are deleted.
     *    The transfer on the target chain is reversed: the value is transferred from the ERC 20 bridge back to the liquidity provider.
     *    Emit a Finalize on Target Chain event, indicating failure.
     * Update reputations of User (the To address of the transfer) and Liquidity Provider
     * (identified by the Liquidity Provider ID) on target chain.
     */
    function finalizeOnTarget(
        TxInfo calldata _txInfo,
        bytes calldata _eventData,
        bytes calldata _signatureOrProof
    ) external {
        bytes32 txInfoDigest = keccak256(abi.encode(_txInfo));
        require(txState[txInfoDigest] == IN_PROGRESS, "20ACTS:Transaction not in progress");
        // Validate targetBcId: It must be this chain.
        // TODO: Is this check required? The targetBcId is covered by the digest, and this was checked in prepare.
        require(_txInfo.targetBcId == myBlockchainId, "20ACTS:Not for this blockchain");

        // TODO need different way of confirming source of event: a param?
        // TODO source contract is part of what is signed. Hence, this shouldn't be needed here.
        address source20ActsAddress = remoteCrosschainControlContracts[_txInfo.sourceBcId];

        decodeAndVerifyEvent(
            _txInfo.sourceBcId,
            source20ActsAddress,
            PREPARE_ON_SOURCE_EVENT_SIGNATURE,
            _eventData,
            _signatureOrProof
        );
        bytes32 txInfoDigestPrepareOnSource;
        bool success;
        (txInfoDigestPrepareOnSource, success, , ) = abi.decode(_eventData,(bytes32, bool, uint256, string));
        require(txInfoDigest == txInfoDigestPrepareOnSource, "20ACTS: Finalize On Target: Incorrect Prepare On Source event");

        address liquidityProvider = _txInfo.liquidityProvider;
        address targetErc20Address = _txInfo.targetErc20Address;

        allocated[liquidityProvider][targetErc20Address] -= _txInfo.amount;

        if (success) {
            txState[txInfoDigest] = COMPLETED_SUCCESS;

            deposits[liquidityProvider][targetErc20Address] -= _txInfo.amount;

            IERC20 tokenContract = IERC20(targetErc20Address);
            tokenContract.transfer(_txInfo.recipient, _txInfo.amount);
        }
        else {
            txState[txInfoDigest] = COMPLETED_FAIL;
        }
        emit FinalizeOnTarget(txInfoDigest);
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
    function finalizeOnSource(
        TxInfo calldata _txInfo,
        bytes calldata _eventData,
        bytes calldata _signatureOrProof
    ) external {
        bytes32 txInfoDigest = keccak256(abi.encode(_txInfo));
        require(txState[txInfoDigest] == IN_PROGRESS, "20ACTS:Transaction not in progress");
        // Validate targetBcId: It must be this chain.
        // TODO: Is this check required? The sourceBcId is covered by the digest, and this was checked in prepare.
        require(_txInfo.sourceBcId == myBlockchainId, "20ACTS:Not for this blockchain");

        // TODO it doesn't make sense to get this and then check it in decode.
        address target20ActsAddress = remoteCrosschainControlContracts[_txInfo.targetBcId];

        decodeAndVerifyEvent(
            _txInfo.targetBcId,
            target20ActsAddress,
            FINALIZE_ON_TARGET_EVENT_SIGNATURE,
            _eventData,
            _signatureOrProof
        );
        bytes32 txInfoDigestFinalizeOnTarget = abi.decode(_eventData,(bytes32));
        require(txInfoDigest == txInfoDigestFinalizeOnTarget, "20ACTS:Incorrect Finalize on Target event");

        uint256 totalAmount = _txInfo.amount + _txInfo.lpFee + _txInfo.inFee;

        // Allocate the funds.
        address sourceErc20Address = _txInfo.sourceErc20Address;
        allocated[_txInfo.sender][sourceErc20Address] -= totalAmount;
        deposits[_txInfo.sender][sourceErc20Address] -= totalAmount;
        deposits[_txInfo.liquidityProvider][sourceErc20Address] += _txInfo.amount + _txInfo.lpFee;
        deposits[infrastructureAccount][sourceErc20Address] += _txInfo.inFee;

        txState[txInfoDigest] = COMPLETED_SUCCESS;

        emit FinalizeOnSource(txInfoDigest);
    }
}
