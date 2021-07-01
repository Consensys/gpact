// SPDX-License-Identifier: MIT

pragma solidity ^0.8.0;

import "./CrosschainERC20.sol";

/**
 * Implementation of the {IERC20} interface that allows tokens to be transferred
 * across blockchains using the GPACT.
 */
contract TraditionalERC20Adaptor is CrosschainERC20 {
    IERC20 public traditionalERC20;

    constructor (string memory _name, string memory _symbol, address _cbc, address _traditionalERC20)
        CrosschainERC20(_name, _symbol, _cbc) {
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
