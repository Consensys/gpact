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

import "../../../../../../common/openzeppelin/src/main/solidity/token/ERC20/presets/ERC20PresetMinterPauser.sol";
import "./SfcErc20BridgeInterface.sol";
import "./AbstractSfcErc20Bridge.sol";

/**
 * ERC 20 bridge using the Simple Function Call protocol. This bridge mints new tokens
 * when they are transferred to this chain and burns tokens when they are transferred
 * from this chain.
 */
contract SfcErc20MintingBurningBridge is SfcErc20BridgeInterface, AbstractSfcErc20Bridge {

    /**
     * @param _sfcCbcContract  Simple Function Call protocol implementation.
     */
    constructor (address _sfcCbcContract) AbstractSfcErc20Bridge (_sfcCbcContract){
    }


    function transferOrMint(address _tokenContract, address _recipient, uint256 _amount) internal override {
        ERC20PresetMinterPauser(_tokenContract).mint(_recipient, _amount);
    }

    function transferOrBurn(address _tokenContract, address _spender, uint256 _amount) internal override {
        ERC20PresetMinterPauser(_tokenContract).burnFrom(_spender, _amount);
    }

}