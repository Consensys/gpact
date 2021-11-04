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

import "../../../../../../../common/openzeppelin/src/main/solidity/token/ERC20/IERC20.sol";
import "../LockableERC20.sol";

/**
 * Implementation of the {IERC20} interface that allows tokens to be transferred
 * across blockchains using the GPACT.
 */
abstract contract TraditionalERC20Adaptor is LockableERC20 {
    IERC20 public traditionalERC20;

    function setTraditionalERC20(address _traditionalERC20) external {
        // TODO Should some checks be done to ensure they are for the same token?
        traditionalERC20 = IERC20(_traditionalERC20);
    }

    /**
    * Transfer tokens from the traditional, non-lockable ERC20 to this ERC 20.
    *
    */
    function transferFromTraditionalContract(uint256 amount) external {
        require(cbc.isSingleBlockchainCall(), "Must be single blockchain call");

        address owner = _msgSender();
        traditionalERC20.transferFrom(owner, address(this), amount);
        mintInternal(owner, amount);
    }

    /**
    * Transfer tokens from the traditional, non-lockable ERC20 to this ERC 20.
    *
    */
    function transferFromTraditionalContractAndApprove(address spender, uint256 amount) external {
        require(cbc.isSingleBlockchainCall(), "Must be single blockchain call");

        address owner = _msgSender();
        traditionalERC20.transferFrom(owner, address(this), amount);
        mintInternal(owner, amount);
        approveInternal(owner, spender, amount);
    }

    /**
    * Transfer tokens from the traditional, non-lockable ERC20 to this ERC 20.
    *
    */
    function transferToTraditionalContract(uint256 amount) external {
        require(cbc.isSingleBlockchainCall(), "Must be single blockchain call");

        address owner = _msgSender();
        burnInternal(owner, amount);
        traditionalERC20.transfer(owner, amount);
    }
}
