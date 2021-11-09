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

import "../LockableERC20.sol";
import "../extensions/LockableERC20Burnable.sol";

/**
 * @dev This contract aims to mirror the Open Zeppelin ERC20PresetFixedSupply:
 * https://github.com/OpenZeppelin/openzeppelin-contracts/blob/master/contracts/token/ERC20/presets/ERC20PresetFixedSupply.sol
 *
 * {ERC20} token, including:
 *
 *  - Preminted initial supply
 *  - Ability for holders to burn (destroy) their tokens
 *  - No access control mechanism (for minting/pausing) and hence no governance
 *
 * This contract uses {LockableERC20Burnable} to include burn capabilities - head to
 * its documentation for details.
 */
contract LockableERC20PresetFixedSupply is LockableERC20Burnable {
    /**
     * @dev Mints `initialSupply` amount of token and transfers them to `owner`.
     *
     * See {ERC20-constructor}.
     */
    constructor(
        string memory _name,
        string memory _symbol,
        address _cbc,
        uint256 _initialSupply,
        address _owner
    ) LockableERC20(_name, _symbol, _cbc) {
        mintInternal(_owner, _initialSupply);
    }
}
