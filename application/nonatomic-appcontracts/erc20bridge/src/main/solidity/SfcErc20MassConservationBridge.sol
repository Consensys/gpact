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
import "./SfcErc20BridgeInterface.sol";
import "./AbstractSfcErc20Bridge.sol";

/**
 * ERC 20 bridge using the Simple Function Call protocol. This bridge is the owner
 * of tokens that are transferred to other blockchains. When tokens are
 * transferred to another blockchain, the user approves the contract using some
 * of their tokens. The contract is then able to execute a transferFrom() for the
 * tokens to be transferred. When tokens are transferred back to this chain,
 * the contract transfers tokens to the recipient.
 */
contract SfcErc20MassConservationBridge is SfcErc20BridgeInterface, AbstractSfcErc20Bridge {

    /**
     * @param _sfcCbcContract  Simple Function Call protocol implementation.
     */
    constructor (address _sfcCbcContract) AbstractSfcErc20Bridge (_sfcCbcContract){
    }


    function transferOrMint(address _tokenContract, address _recipient, uint256 _amount) internal override {
        if (!IERC20(_tokenContract).transfer(_recipient, _amount)) {
            revert("transfer failed");
        }
    }

    function transferOrBurn(address _tokenContract, address _spender, uint256 _amount) internal override {
        if (!IERC20(_tokenContract).transferFrom(_spender, address(this), _amount)) {
            revert("transferFrom failed");
        }
    }


}