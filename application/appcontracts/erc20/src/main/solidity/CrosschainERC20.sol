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
pragma solidity ^0.8.0;

import "./LockableERC20.sol";

/**
 * Implementation of the {IERC20} interface that allows tokens to be transferred
 * across blockchains using the GPACT.
 */
contract CrosschainERC20 is LockableERC20 {
    // ERC 20 contracts for the token represented by this contract
    // on remote blockchains.
    mapping (uint256 => address) public remoteERC20s;

    constructor (string memory _name, string memory _symbol, address _cbc)
        LockableERC20 (_name, _symbol, _cbc) {
    }

    /**
    * Set the address of an ERC 20 contract on a remote blockchain that funds can be transferred to.
    */
    function setRemoteERC20(uint256 blockchainId, address erc20) onlyOwner external {
        require(cbc.isSingleBlockchainCall(), "Must be single blockchain call");
        remoteERC20s[blockchainId] = erc20;
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

    function crosschainTransferFrom(address sender, uint256 recipientBlockchainId, address recipient, uint256 amount) external {
        // Determine the spender. If the account that called this function
        // is the Cross-Blockchain Control Contract, then the spender is the EOA. Otherwise,
        // the transfer is being instigated by another contract, probably a wallet.
        address spender = _msgSender();
        if (spender == address(cbc)) {
            spender = tx.origin;
        }

        crosschainTransferInternal(recipientBlockchainId, sender, recipient, amount);

        uint256 currentAllowance = allowanceMin(sender, spender);
        require(currentAllowance >= amount, "ERC20: transfer amount exceeds allowance");
        decreaseAllowanceInternal(sender, spender, amount);
    }


    function crosschainReceiver(address recipient, uint256 amount) external {
        require(!cbc.isSingleBlockchainCall(), "Must be part of crosschain call");

        // Check that the calling contract was the ERC 20 linked to this one from
        // the source blockchain.
        // TODO only allow approved Root Blockchains
        (, uint256 sourceBlockchainId, address sourceContract) = cbc.whoCalledMe();
        require(sourceContract == remoteERC20s[sourceBlockchainId], "Source is not correct ERC20");

        mintInternal(recipient, amount);
    }


    function crosschainTransferInternal(uint256 blockchainId, address sender, address recipient, uint256 amount) internal {
        require(!cbc.isSingleBlockchainCall(), "Must be part of crosschain call");

        burnInternal(sender, amount);

        address remoteERC20Contract = remoteERC20s[blockchainId];
        require(remoteERC20Contract != address(0), "No ERC 20 registered for remote blockchain");

        CbcLockableStorageInterface(address(crossBlockchainControl)).crossBlockchainCall(blockchainId, address(remoteERC20Contract),
            abi.encodeWithSelector(this.crosschainReceiver.selector, recipient, amount));
    }
}
