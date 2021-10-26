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

import "../../../utils/Context.sol";
import "../LockableERC20.sol";

/**
 * @dev Extension of {ERC20} that allows token holders to destroy both their own
 * tokens and those that they have an allowance for, in a way that can be
 * recognized off-chain (via event analysis).
 */
abstract contract LockableERC20Burnable is Context, LockableERC20 {
    /**
     * @dev Destroys `amount` tokens from the caller.
     *
     * See {ERC20-_burn}.
     */
    function burn(uint256 amount) public virtual {
        _burn(_msgSender(), amount);
    }

    /**
     * @dev Destroys `amount` tokens from `account`, deducting from the caller's
     * allowance.
     *
     * See {ERC20-_burn} and {ERC20-allowance}.
     *
     * Requirements:
     *
     * - the caller must have allowance for ``accounts``'s tokens of at least
     * `amount`.
     */
    function burnFrom(address account, uint256 amount) public virtual {
        burnFromInternal(_msgSender(), sender, recipient, amount);
    }

    function burnFromAccount(address spender, address sender, address recipient, uint256 amount) public virtual {
        require(trustedErc20Bridges[msg.sender], "ERC20: not trusted bridge");
        burnFromInternal(spender, sender, recipient, amount);
    }

    function burnFromInternal(address burner, address account, uint256 amount) public virtual {
        uint256 currentAllowance = allowance(account, burner);
        require(currentAllowance >= amount, "ERC20: burn amount exceeds allowance");
        _approve(account, burner, currentAllowance - amount);
        _burn(account, amount);
    }


}
