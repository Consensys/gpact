// SPDX-License-Identifier: MIT

pragma solidity ^0.8.0;

import "../../../../openzeppelin/src/main/solidity/access/Ownable.sol";
import "../../../../openzeppelin/src/main/solidity/utils/Context.sol";
import "../../../../openzeppelin/src/main/solidity/token/ERC20/IERC20.sol";
import "../../../../openzeppelin/src/main/solidity/token/ERC20/extensions/IERC20Metadata.sol";
import "../../../../../../contracts/solidity/crossblockchaincontrol/src/main/solidity/CbcLockableStorageInterface.sol";
import "../../../../../../contracts/solidity/lockablestorage/src/main/solidity/LockableStorage.sol";

/**
 * @dev Implementation of the {IERC20} interface that provides lockable storage for use with GPACT.
 *
 * This implementation is agnostic to the way tokens are created. This means
 * that a supply mechanism has to be added in a derived contract using {mintInternal}.
 * For a generic mechanism see {ERC20PresetMinterPauser}.
 *
 * TIP: For a detailed writeup see our guide
 * https://forum.zeppelin.solutions/t/how-to-implement-erc20-supply-mechanisms/226[How
 * to implement supply mechanisms].
 *
 * We have followed general OpenZeppelin guidelines: functions revert instead
 * of returning `false` on failure. This behavior is nonetheless conventional
 * and does not conflict with the expectations of ERC20 applications.
 *
 * Additionally, an {Approval} event is emitted on calls to {transferFrom}.
 * This allows applications to reconstruct the allowance for all accounts just
 * by listening to said events. Other implementations of the EIP may not emit
 * these events, as it isn't required by the specification.
 *
 * Finally, the non-standard {decreaseAllowance} and {increaseAllowance}
 * functions have been added to mitigate the well-known issues around setting
 * allowances. See {IERC20-approve}.
 */
contract LockableERC20 is Context, IERC20, IERC20Metadata, Ownable, LockableStorage {
    // Number of crosschain transfers to or from an account that can
    // execute in parallel.
    uint256 public accountPallelizationFactor;

    // Number of crosschain transfers to or from all accounts held by this
    // ERC20 contract that can execute in parallel.
    uint256 public erc20PallelizationFactor;

    // Balances, allowances, and totoal supply are stored are two
    // separate structures: an ADD and a SUB. Each has a mapping as
    // the inner map which is really an array, where the length is
    // controlled by the Parallelization Factor. The value held is:
    // SUM(_ADD(for all indices)) - SUM(_SUB(for all indices))

    // mapping (address => mapping (index => uint256))
    uint256 constant private BALANCES_ADD = 0;
    uint256 constant private BALANCES_SUB = 1;

    // mapping (address => mapping (address => mapping(index => uint256))) private _allowances;
    uint256 constant private ALLOWANCES_ADD = 2;
    uint256 constant private ALLOWANCES_SUB = 3;

    // mapping(index => uint256)
    uint256 constant private TOTAL_SUPPLY_ADD = 4;
    uint256 constant private TOTAL_SUPPLY_SUB = 5;

    // Token name and symbol are immutable.
    string private erc20Name;
    string private erc20Symbol;

    // Cross-blockchain Control Contract on this blockchain.
    CbcLockableStorageInterface public cbc;

    // ERC 20 contracts for the token represented by this contract
    // on remote blockchains.
    mapping (uint256 => address) public remoteERC20s;

    /**
     * @dev Sets the values for {name} and {symbol}.
     *
     * The defaut value of {decimals} is 18. To select a different value for
     * {decimals} you should overload it.
     *
     * All two of these values are immutable: they can only be set once during
     * construction.
     */
    constructor (string memory _name, string memory _symbol, address _cbc) LockableStorage(_cbc) {
        erc20Name = _name;
        erc20Symbol = _symbol;
        cbc = CbcLockableStorageInterface(_cbc);

        accountPallelizationFactor = 5;
        erc20PallelizationFactor = 10;
    }

    /**
    * Set the address of an ERC 20 contract on a remote blockchain that funds can be transferred to.
    */
    function setRemoteERC20(uint256 blockchainId, address erc20) onlyOwner external {
        require(cbc.isSingleBlockchainCall(), "Must be single blockchain call");
        remoteERC20s[blockchainId] = erc20;
    }

    function increaseAccountParallelizartionFactor(uint256 amount) onlyOwner external {
        accountPallelizationFactor += amount;
    }

    function increaseERC20ParallelizartionFactor(uint256 amount) onlyOwner external {
        erc20PallelizationFactor += amount;
    }

    /**
    * Crosschain transfer from account on this blockchain to an account on a remote blockchain.
    *
    * Requirements:
    *
    * - `recipient` cannot be the zero address.
    * - the caller must have a balance of at least `amount`.
    */
    function crosschainTransfer(uint256 blockchainId, address recipient, uint256 amount) external {
        // Determine the account to take funds from. If the account that called this function
        // is the Cross-Blockchain Control Contract, then the spender is the EOA. Otherwise,
        // the transfer is being instigated by another contract, probably a wallet.
        address spender = _msgSender();
        if (spender == address(cbc)) {
            spender = tx.origin;
        }

        crosschainTransferInternal(blockchainId, spender, recipient, amount);
    }

    function crosschainTransferFrom(uint256 blockchainId, address sender, address recipient, uint256 amount) external {
        // Determine the spender. If the account that called this function
        // is the Cross-Blockchain Control Contract, then the spender is the EOA. Otherwise,
        // the transfer is being instigated by another contract, probably a wallet.
        address spender = _msgSender();
        if (spender == address(cbc)) {
            spender = tx.origin;
        }

        crosschainTransferInternal(blockchainId, sender, recipient, amount);

        uint256 currentAllowance = allowanceMin(sender, spender);
        require(currentAllowance >= amount, "ERC20: transfer amount exceeds allowance");
        decreaseAllowanceInternal(sender, spender, amount);
    }


    function crosschainReceiver(address recipient, uint256 amount) external {
        require(!cbc.isSingleBlockchainCall(), "Must be part of crosschain call");

        // Check that the calling contract was the ERC 20 linked to this one from
        // the source blockchain.
        (uint256 sourceBlockchainId, address sourceContract) = cbc.whoCalledMe();
        require(sourceContract == remoteERC20s[sourceBlockchainId], "Source is not correct ERC20");

        mintInternal(recipient, amount);
    }

    /**
     * @dev See {IERC20-transfer}.
     *
     * Requirements:
     *
     * - `recipient` cannot be the zero address.
     * - the caller must have a balance of at least `amount`.
     */
    function transfer(address recipient, uint256 amount) public virtual override returns (bool) {
        require(cbc.isSingleBlockchainCall(), "Must be single blockchain call");
        transferInternal(_msgSender(), recipient, amount);
        return true;
    }

    /**
     * @dev See {IERC20-approve}.
     *
     * Requirements:
     *
     * - `spender` cannot be the zero address.
     */
    function approve(address spender, uint256 amount) public virtual override returns (bool) {
        require(cbc.isSingleBlockchainCall(), "Must be single blockchain call");
        approveInternal(_msgSender(), spender, amount);
        return true;
    }

    /**
     * @dev See {IERC20-transferFrom}.
     *
     * Emits an {Approval} event indicating the updated allowance. This is not
     * required by the EIP. See the note at the beginning of {ERC20}.
     *
     * Requirements:
     *
     * - `sender` and `recipient` cannot be the zero address.
     * - `sender` must have a balance of at least `amount`.
     * - the caller must have allowance for ``sender``'s tokens of at least
     * `amount`.
     */
    function transferFrom(address sender, address recipient, uint256 amount) public virtual override returns (bool) {
        require(cbc.isSingleBlockchainCall(), "Must be single blockchain call");
        transferInternal(sender, recipient, amount);

        address spender = _msgSender();

        uint256 currentAllowance = allowanceMin(sender, spender);
        require(currentAllowance >= amount, "ERC20: transfer amount exceeds allowance");
        decreaseAllowanceInternal(sender, spender, amount);

        return true;
    }

    /**
     * @dev Atomically increases the allowance granted to `spender` by the caller.
     *
     * This is an alternative to {approve} that can be used as a mitigation for
     * problems described in {IERC20-approve}.
     *
     * Emits an {Approval} event indicating the updated allowance.
     *
     * Requirements:
     *
     * - `spender` cannot be the zero address.
     */
    function increaseAllowance(address spender, uint256 addedValue) public virtual returns (bool) {
        require(cbc.isSingleBlockchainCall(), "Must be single blockchain call");
        increaseAllowanceInternal(_msgSender(), spender, addedValue);
        return true;
    }

    /**
     * @dev Atomically decreases the allowance granted to `spender` by the caller.
     *
     * This is an alternative to {approve} that can be used as a mitigation for
     * problems described in {IERC20-approve}.
     *
     * Emits an {Approval} event indicating the updated allowance.
     *
     * Requirements:
     *
     * - `spender` cannot be the zero address.
     * - `spender` must have allowance for the caller of at least
     * `subtractedValue`.
     */
    function decreaseAllowance(address spender, uint256 subtractedValue) public virtual returns (bool) {
        require(cbc.isSingleBlockchainCall(), "Must be single blockchain call");
        uint256 currentAllowance = allowanceMin(_msgSender(), spender);
        require(currentAllowance >= subtractedValue, "ERC20: decreased allowance below zero");
        decreaseAllowanceInternal(_msgSender(), spender, subtractedValue);

        return true;
    }


    /************************************************************************/
    /************************************************************************/
    /*****                        VIEW BELOW HERE                       *****/
    /************************************************************************/
    /************************************************************************/


    /**
     * @dev Returns the name of the token.
     */
    function name() public view virtual override returns (string memory) {
        return erc20Name;
    }

    /**
     * @dev Returns the symbol of the token, usually a shorter version of the
     * name.
     */
    function symbol() public view virtual override returns (string memory) {
        return erc20Symbol;
    }

    /**
     * @dev Returns the number of decimals used to get its user representation.
     * For example, if `decimals` equals `2`, a balance of `505` tokens should
     * be displayed to a user as `5,05` (`505 / 10 ** 2`).
     *
     * Tokens usually opt for a value of 18, imitating the relationship between
     * Ether and Wei. This is the value {ERC20} uses, unless this function is
     * overloaded;
     *
     * NOTE: This information is only used for _display_ purposes: it in
     * no way affects any of the arithmetic of the contract, including
     * {IERC20-balanceOf} and {IERC20-transfer}.
     */
    function decimals() public view virtual override returns (uint8) {
        return 18;
    }

    /**
     * Returns the total supply, ignoring any provisional updates.
     */
    function totalSupply() public view virtual override returns (uint256) {
        uint256 total = 0;

        for (uint256 i=0; i < erc20PallelizationFactor; i++) {
            total += getMapValueCommitted(TOTAL_SUPPLY_ADD, i);
        }
        // There is no checks for totalSupply going negative. It should be
        // impossible for total supply to go negative, as it is only adjusted
        // in line with balances being burned.
        for (uint256 i=0; i < erc20PallelizationFactor; i++) {
            total -= getMapValueCommitted(TOTAL_SUPPLY_SUB, i);
        }
        return total;
    }

    /**
    * Returns the total supply, assuming all provisional updates are applied.
    */
    function totalSupplyProvisional() public view returns (uint256) {
        uint256 total = 0;

        for (uint256 i=0; i < erc20PallelizationFactor; i++) {
            total += getMapValueProvisional(TOTAL_SUPPLY_ADD, i);
        }
        // There is no checks for totalSupply going negative. It should be
        // impossible for total supply to go negative, as it is only adjusted
        // in line with balances being burned.
        for (uint256 i=0; i < erc20PallelizationFactor; i++) {
            total -= getMapValueProvisional(TOTAL_SUPPLY_SUB, i);
        }
        return total;
    }

    /**
     * Balance assuming no provisional updates are applied.
     */
    function balanceOf(address account) public view virtual override returns (uint256) {
        uint256 acc = uint256(uint160(account));
        uint256 balance = 0;
        for (uint256 i=0; i < accountPallelizationFactor; i++) {
            balance += getDoubleMapValueCommitted(BALANCES_ADD, acc, i);
        }
        // There are no checks for balance going negative as this should be
        // impossible.
        for (uint256 i=0; i < accountPallelizationFactor; i++) {
            balance -= getDoubleMapValueCommitted(BALANCES_SUB, acc, i);
        }
        return balance;
    }

    /**
    * Balance assuming all provisional updates are applied.
    */
    function balanceOfProvisional(address account) public view returns (uint256) {
        uint256 acc = uint256(uint160(account));
        uint256 balance = 0;
        for (uint256 i=0; i < accountPallelizationFactor; i++) {
            balance += getDoubleMapValueProvisional(BALANCES_ADD, acc, i);
        }
        // There are no checks for balance going negative as this should be
        // impossible.
        for (uint256 i=0; i < accountPallelizationFactor; i++) {
            balance -= getDoubleMapValueProvisional(BALANCES_SUB, acc, i);
        }
        return balance;
    }

    /**
    * Balance assuming all provisional withdraws are applied and no provisional
    * deposits are applied.
    */
    function balanceOfMin(address account) public view returns (uint256) {
        uint256 acc = uint256(uint160(account));
        uint256 balance = 0;
        for (uint256 i=0; i < accountPallelizationFactor; i++) {
            balance += getDoubleMapValueCommitted(BALANCES_ADD, acc, i);
        }
        // There are no checks for balance going negative as this should be
        // impossible.
        for (uint256 i=0; i < accountPallelizationFactor; i++) {
            balance -= getDoubleMapValueProvisional(BALANCES_SUB, acc, i);
        }
        return balance;
    }

    /**
     * Return the amount committed, ignoring any provisional updates.
     *
     */
    function allowance(address owner, address spender) public view virtual override returns (uint256) {
        require(owner != address(0), "ERC20: approve from the zero address");
        require(spender != address(0), "ERC20: approve to the zero address");

        uint256 ownerAcc = uint256(uint160(owner));
        uint256 spenderAcc = uint256(uint160(spender));
        uint256 total = 0;
        for (uint256 i=0; i < accountPallelizationFactor; i++) {
            total += getTripleMapValueCommitted(ALLOWANCES_ADD, ownerAcc, spenderAcc, i);
        }
        // There are no checks for balance going negative as this should be
        // impossible.
        for (uint256 i=0; i < accountPallelizationFactor; i++) {
            total -= getTripleMapValueCommitted(ALLOWANCES_SUB, ownerAcc, spenderAcc, i);
        }
        return total;
    }

    function allowanceMin(address owner, address spender) public view returns (uint256) {
        require(owner != address(0), "ERC20: approve from the zero address");
        require(spender != address(0), "ERC20: approve to the zero address");

        uint256 ownerAcc = uint256(uint160(owner));
        uint256 spenderAcc = uint256(uint160(spender));
        uint256 total = 0;
        for (uint256 i=0; i < accountPallelizationFactor; i++) {
            total += getTripleMapValueCommitted(ALLOWANCES_ADD, ownerAcc, spenderAcc, i);
        }
        // There are no checks for balance going negative as this should be
        // impossible.
        for (uint256 i=0; i < accountPallelizationFactor; i++) {
            total -= getTripleMapValueProvisional(ALLOWANCES_SUB, ownerAcc, spenderAcc, i);
        }
        return total;
    }

    function allowanceMax(address owner, address spender) public view returns (uint256) {
        require(owner != address(0), "ERC20: approve from the zero address");
        require(spender != address(0), "ERC20: approve to the zero address");

        uint256 ownerAcc = uint256(uint160(owner));
        uint256 spenderAcc = uint256(uint160(spender));
        uint256 total = 0;
        for (uint256 i=0; i < accountPallelizationFactor; i++) {
            total += getTripleMapValueProvisional(ALLOWANCES_ADD, ownerAcc, spenderAcc, i);
        }
        // There are no checks for balance going negative as this should be
        // impossible.
        for (uint256 i=0; i < accountPallelizationFactor; i++) {
            total -= getTripleMapValueCommitted(ALLOWANCES_SUB, ownerAcc, spenderAcc, i);
        }
        return total;
    }

    function allowanceProvisional(address owner, address spender) public view returns (uint256) {
        require(owner != address(0), "ERC20: approve from the zero address");
        require(spender != address(0), "ERC20: approve to the zero address");

        uint256 ownerAcc = uint256(uint160(owner));
        uint256 spenderAcc = uint256(uint160(spender));
        uint256 total = 0;
        for (uint256 i=0; i < accountPallelizationFactor; i++) {
            total += getTripleMapValueProvisional(ALLOWANCES_ADD, ownerAcc, spenderAcc, i);
        }
        // There are no checks for balance going negative as this should be
        // impossible.
        for (uint256 i=0; i < accountPallelizationFactor; i++) {
            total -= getTripleMapValueProvisional(ALLOWANCES_SUB, ownerAcc, spenderAcc, i);
        }
        return total;
    }


    /************************************************************************/
    /************************************************************************/
    /*****                PRIVATE AND INTERNAL BELOW HERE               *****/
    /************************************************************************/
    /************************************************************************/


    /**
     * @dev Moves tokens `amount` from `sender` to `recipient`.
     *
     * This is internal function is equivalent to {transfer}, and can be used to
     * e.g. implement automatic token fees, slashing mechanisms, etc.
     *
     * Emits a {Transfer} event.
     *
     * Requirements:
     *
     * - `sender` cannot be the zero address.
     * - `recipient` cannot be the zero address.
     * - `sender` must have a balance of at least `amount`.
     */
    function transferInternal(address sender, address recipient, uint256 amount) internal virtual {
        require(sender != address(0), "ERC20: transfer from the zero address");
        require(recipient != address(0), "ERC20: transfer to the zero address");

        beforeTokenTransfer(sender, recipient, amount);
        decreaseBalance(sender, amount);
        increaseBalance(recipient, amount);
        emit Transfer(sender, recipient, amount);
    }


    function crosschainTransferInternal(uint256 blockchainId, address sender, address recipient, uint256 amount) internal {
        require(!cbc.isSingleBlockchainCall(), "Must be part of crosschain call");

        burnInternal(sender, amount);

        address remoteERC20Contract = remoteERC20s[blockchainId];
        require(remoteERC20Contract != address(0), "No ERC 20 registered for remote blockchain");

        crossBlockchainControl.crossBlockchainCall(blockchainId, address(remoteERC20Contract),
            abi.encodeWithSelector(this.crosschainReceiver.selector, recipient, amount));
    }


    /** @dev Creates `amount` tokens and assigns them to `account`, increasing
     * the total supply.
     *
     * Emits a {Transfer} event with `from` set to the zero address.
     *
     * Requirements:
     *
     * - `to` cannot be the zero address.
     */
    function mintInternal(address account, uint256 amount) internal virtual {
        require(account != address(0), "ERC20: mint to the zero address");

        beforeTokenTransfer(address(0), account, amount);
        increaseTotalSupply(amount);
        increaseBalance(account, amount);
        emit Transfer(address(0), account, amount);
    }

    /**
     * @dev Destroys `amount` tokens from `account`, reducing the
     * total supply.
     *
     * Emits a {Transfer} event with `to` set to the zero address.
     *
     * Requirements:
     *
     * - `account` cannot be the zero address.
     * - `account` must have at least `amount` tokens.
     */
    function burnInternal(address account, uint256 amount) internal virtual {
        require(account != address(0), "ERC20: burn from the zero address");

        beforeTokenTransfer(account, address(0), amount);
        decreaseTotalSupply(amount);
        decreaseBalance(account, amount);
        emit Transfer(account, address(0), amount);
    }

    /**
     * @dev Sets `amount` as the allowance of `spender` over the `owner` s tokens.
     *
     * Emits an {Approval} event.
     *
     * Requirements:
     * - All allowance slots must be unlocked.
     * - `owner` cannot be the zero address.
     * - `spender` cannot be the zero address.
     */
    function approveInternal(address owner, address spender, uint256 amount) internal virtual {
        require(owner != address(0), "ERC20: approve from the zero address");
        require(spender != address(0), "ERC20: approve to the zero address");

        uint256 ownerAcc = uint256(uint160(owner));
        uint256 spenderAcc = uint256(uint160(spender));
        uint256 total = 0;
        for (uint256 i=0; i<accountPallelizationFactor; i++) {
            require(!isTripleMapValueLocked(ALLOWANCES_ADD, ownerAcc, spenderAcc, i), "Increase allowance slot locked");
            total += getTripleMapValue(ALLOWANCES_ADD, ownerAcc, spenderAcc, i);
        }
        for (uint256 i=0; i<accountPallelizationFactor; i++) {
            require(!isTripleMapValueLocked(ALLOWANCES_SUB, ownerAcc, spenderAcc, i), "Decrease allowance slot locked");
            total -= getTripleMapValue(ALLOWANCES_SUB, ownerAcc, spenderAcc, i);
        }
        if (total < amount) {
            uint256 cur = getTripleMapValue(ALLOWANCES_ADD, ownerAcc, spenderAcc, 0);
            setTripleMapValue(ALLOWANCES_ADD, ownerAcc, spenderAcc, 0, cur + amount - total);
        }
        else if (total > amount) {
            uint256 cur = getTripleMapValue(ALLOWANCES_SUB, ownerAcc, spenderAcc, 0);
            setTripleMapValue(ALLOWANCES_SUB, ownerAcc, spenderAcc, 0, cur + total - amount);
        }
        emit Approval(owner, spender, amount);
    }

    function increaseAllowanceInternal(address owner, address spender, uint256 amount) internal virtual {
        require(owner != address(0), "ERC20: approve from the zero address");
        require(spender != address(0), "ERC20: approve to the zero address");

        uint256 ownerAcc = uint256(uint160(owner));
        uint256 spenderAcc = uint256(uint160(spender));
        bool foundUnlockedSlot = false;
        for (uint256 i=0; i<accountPallelizationFactor; i++) {
            if (!isTripleMapValueLocked(ALLOWANCES_ADD, ownerAcc, spenderAcc, i)) {
                foundUnlockedSlot = true;
                uint256 cur = getTripleMapValue(ALLOWANCES_ADD, ownerAcc, spenderAcc, i);
                setTripleMapValue(ALLOWANCES_ADD, ownerAcc, spenderAcc, i, cur + amount);
                break;
            }
        }
        require(foundUnlockedSlot, "All increase allowance slots in use");
        emit ApprovalIncrease(owner, spender, amount);
    }

    function decreaseAllowanceInternal(address owner, address spender, uint256 amount) internal virtual {
        require(owner != address(0), "ERC20: approve from the zero address");
        require(spender != address(0), "ERC20: approve to the zero address");

        uint256 ownerAcc = uint256(uint160(owner));
        uint256 spenderAcc = uint256(uint160(spender));
        bool foundUnlockedSlot = false;
        for (uint256 i=0; i<accountPallelizationFactor; i++) {
            if (!isTripleMapValueLocked(ALLOWANCES_SUB, ownerAcc, spenderAcc, i)) {
                foundUnlockedSlot = true;
                uint256 cur = getTripleMapValue(ALLOWANCES_SUB, ownerAcc, spenderAcc, i);
                setTripleMapValue(ALLOWANCES_SUB, ownerAcc, spenderAcc, i, cur + amount);
                break;
            }
        }
        require(foundUnlockedSlot, "All decrease allowance slots in use");
        emit ApprovalDecrease(owner, spender, amount);
    }



    function increaseBalance(address account, uint256 amount) internal {
        require(account != address(0), "ERC20: recipient account is zero address");

        uint256 accAddr = uint256(uint160(account));
        bool foundUnlockedSlot = false;
        for (uint256 i=0; i<accountPallelizationFactor; i++) {
            if (!isDoubleMapValueLocked(BALANCES_ADD, accAddr, i)) {
                foundUnlockedSlot = true;
                uint256 cur = getDoubleMapValue(BALANCES_ADD, accAddr, i);
                setDoubleMapValue(BALANCES_ADD, accAddr, i, cur + amount);
                break;
            }
        }
        require(foundUnlockedSlot, "All increase balance slots in use");
    }


    function decreaseBalance(address account, uint256 amount) internal {
        require(account != address(0), "ERC20: From account: zero address");

        uint256 accountBalance = balanceOfMin(account);
        require(accountBalance >= amount, "ERC20: amount exceeds min balance of FROM sender");

        uint256 accAddr = uint256(uint160(account));
        bool foundUnlockedSlot = false;
        for (uint256 i=0; i<accountPallelizationFactor; i++) {
            if (!isDoubleMapValueLocked(BALANCES_SUB, accAddr, i)) {
                foundUnlockedSlot = true;
                uint256 cur = getDoubleMapValue(BALANCES_SUB, accAddr, i);
                setDoubleMapValue(BALANCES_SUB, accAddr, i, cur + amount);
                break;
            }
        }
        require(foundUnlockedSlot, "All decrease balance slots in use");
    }

    function increaseTotalSupply(uint256 amount) internal {
        bool foundUnlockedSlot = false;
        for (uint256 i=0; i<erc20PallelizationFactor; i++) {
            if (!isMapValueLocked(TOTAL_SUPPLY_ADD, i)) {
                foundUnlockedSlot = true;
                uint256 cur = getMapValue(TOTAL_SUPPLY_ADD, i);
                setMapValue(TOTAL_SUPPLY_ADD, i, cur + amount);
                break;
            }
        }
        require(foundUnlockedSlot, "All increase total supply slots in use");
    }

    function decreaseTotalSupply(uint256 amount) internal {
        bool foundUnlockedSlot = false;
        for (uint256 i=0; i<erc20PallelizationFactor; i++) {
            if (!isMapValueLocked(TOTAL_SUPPLY_SUB, i)) {
                foundUnlockedSlot = true;
                uint256 cur = getMapValue(TOTAL_SUPPLY_SUB, i);
                setMapValue(TOTAL_SUPPLY_SUB, i, cur + amount);
                break;
            }
        }
        require(foundUnlockedSlot, "All decrease total supply slots in use");
    }

    /**
    * @dev Hook that is called before any transfer of tokens. This includes
    * minting and burning.
    *
    * Calling conditions:
    *
    * - when `from` and `to` are both non-zero, `amount` of ``from``'s tokens
    * will be to transferred to `to`.
    * - when `from` is zero, `amount` tokens will be minted for `to`.
    * - when `to` is zero, `amount` of ``from``'s tokens will be burned.
    * - `from` and `to` are never both zero.
    *
    * To learn more about hooks, head to xref:ROOT:extending-contracts.adoc#using-hooks[Using Hooks].
    */
    function beforeTokenTransfer(address from, address to, uint256 amount) internal virtual { }

    event ApprovalIncrease(address indexed owner, address indexed spender, uint256 value);
    event ApprovalDecrease(address indexed owner, address indexed spender, uint256 value);
}
