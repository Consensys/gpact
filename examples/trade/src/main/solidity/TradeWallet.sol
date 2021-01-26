/*
 * Copyright 2020 ConsenSys AG.
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
pragma solidity >=0.7.1;

import "../../../../../crossblockchaincontrol/src/main/solidity/CbcLockableStorageInterface.sol";
import "../../../../../lockablestorage/src/main/solidity/LockableStorageWrapper.sol";
import "./BusLogic.sol";

contract TradeWallet is LockableStorageWrapper {
    CbcLockableStorageInterface private crossBlockchainControl;

    uint256 busLogicBcId;
    BusLogic busLogicContract;


    uint256 constant private KEY_TRADES_ARRAY = 0;

    event Trade(bytes32 _tradeId);

    constructor (address _cbc, uint256 _busLogicBcId, address _busLogicContract, address _storageContract)
        LockableStorageWrapper(_storageContract) {
        crossBlockchainControl = CbcLockableStorageInterface(_cbc);
        busLogicBcId = _busLogicBcId;
        busLogicContract = BusLogic(_busLogicContract);
    }

    function executeTrade(address _seller, uint256 _quantity) public {
        crossBlockchainControl.crossBlockchainCall(busLogicBcId, address(busLogicContract),
            abi.encodeWithSelector(busLogicContract.stockShipment.selector, _seller, tx.origin, _quantity));

        bytes32 tradeId = keccak256(abi.encodePacked(_seller, tx.origin, _quantity));

        pushArrayValue(KEY_TRADES_ARRAY, uint256(tradeId));

        emit Trade(tradeId);
    }

    function getNumTrades() external view returns (uint256) {
        return getArrayLength(KEY_TRADES_ARRAY);
    }

    function getTrade(uint256 _index) external view returns (uint256) {
        return getArrayValue(KEY_TRADES_ARRAY, _index);
    }
}
