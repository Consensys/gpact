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

contract TravelAgency {
    address owner;

    uint256 public hotelBlockchainId;
    Hotel public hotelContract;

    uint256 public trainBlockchainId;
    Hotel public trainContract;

    uint256[] public confirmedBookingIds;
    uint256[] public confirmedBookingDates;

    constructor(
        uint256 _hotelBlockchainId,
        address _hotelContract,
        uint256 _trainBlockchainId,
        address _trainContract
    ) {
        owner = msg.sender;
        hotelBlockchainId = _hotelBlockchainId;
        hotelContract = Hotel(_hotelContract);
        trainBlockchainId = _trainBlockchainId;
        trainContract = Hotel(_trainContract);
    }

    function bookHotelAndTrain(uint256 _date, uint256 _uniqueId) public {
        require(msg.sender == owner, "Only owner can do bookings");
        hotelContract.bookRoom(_date, _uniqueId, 100);
        trainContract.bookRoom(_date, _uniqueId, 100);

        confirmedBookingIds.push(_uniqueId);
        confirmedBookingDates.push(_date);
    }

    function separatedBookHotelAndTrain(uint256 _date, uint256 _uniqueId)
        public
    {
        require(msg.sender == owner, "Only owner can do bookings");
        confirmedBookingIds.push(_uniqueId);
        confirmedBookingDates.push(_date);
    }

    function bookingConfirmed(uint256 _bookingId) public view returns (bool) {
        // TODO use a maps as well as array
        for (uint256 i = 0; i < confirmedBookingDates.length; i++) {
            if (confirmedBookingIds[i] == _bookingId) {
                return true;
            }
        }
        return false;
    }
}
