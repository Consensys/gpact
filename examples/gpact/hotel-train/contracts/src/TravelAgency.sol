/*
 * Copyright 2021 ConsenSys Software Inc.
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

import "./Hotel.sol";
import "./Train.sol";
import "../../../../../contracts/contracts/src/functioncall/interface/CrosschainFunctionCallInterface.sol";
import "../../../../../contracts/contracts/src/functioncall/interface/CrosschainFunctionCallReturnInterface.sol";

contract TravelAgency is LockableStorage {
    address owner;

    uint256 public hotelBlockchainId;
    Hotel public hotelContract;

    uint256 public trainBlockchainId;
    Train public trainContract;

    // Confirmed bookings.
    // Map (bookingId => date)
    // mapping (uint256 => uint256) private confirmedBookigs;
    uint256 private constant CONFIRMED_BOOKINGS_MAP = 0;

    constructor(
        uint256 _hotelBlockchainId,
        address _hotelContract,
        uint256 _trainBlockchainId,
        address _trainContract,
        address _cbc
    ) LockableStorage(_cbc) {
        owner = msg.sender;
        hotelBlockchainId = _hotelBlockchainId;
        hotelContract = Hotel(_hotelContract);
        trainBlockchainId = _trainBlockchainId;
        trainContract = Train(_trainContract);
    }

    function bookHotelAndTrain(uint256 _date, uint256 _bookingId) public {
        require(
            msg.sender == address(cbc),
            "Should only be called by Crosschain Control Contract"
        );
        require(tx.origin == owner, "Only owner can do bookings");

        CrosschainFunctionCallInterface(address(cbc)).crossBlockchainCall(
            hotelBlockchainId,
            address(hotelContract),
            abi.encodeWithSelector(
                hotelContract.bookRoom.selector,
                _date,
                _bookingId,
                100
            )
        );
        CrosschainFunctionCallInterface(address(cbc)).crossBlockchainCall(
            trainBlockchainId,
            address(trainContract),
            abi.encodeWithSelector(
                trainContract.bookSeat.selector,
                _date,
                _bookingId,
                100
            )
        );

        setMapValue(CONFIRMED_BOOKINGS_MAP, _bookingId, _date);
    }

    function bookingConfirmed(uint256 _bookingId) public view returns (bool) {
        uint256 date = getMapValue(CONFIRMED_BOOKINGS_MAP, _bookingId);
        return date != 0;
    }
}
