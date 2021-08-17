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

import "../../../../../../appcontracts/solidity/openzeppelin/src/main/solidity/token/ERC20/IERC20.sol";

contract Hotel {
    struct HotelRoom {
        uint256 roomRate;
        // Map: date to who booked room
        mapping(uint256 => address) bookedBy;
    }
    mapping (uint256 => HotelRoom) private rooms;
    uint256 numRooms;

    // Map: booking reference to room number
    mapping(uint256 => uint256) bookingRefToRoomNumber;
    // Map: booking reference to date
    mapping(uint256 => uint256) bookingRefToDate;


    address owner;
    IERC20 erc20;

    modifier onlyOwner() {
        require(msg.sender == owner);
        _;
    }

    constructor(address _erc20) {
        owner = msg.sender;
        erc20 = IERC20(_erc20);
    }

    function addRooms(uint256 _roomRate, uint256 _numberOfRooms) onlyOwner external {
        for (uint i=0; i < _numberOfRooms; i++) {
            HotelRoom storage room = rooms[numRooms++];
            room.roomRate = _roomRate;
        }
    }

    function changeRoomRate(uint256 _roomNumber, uint256 _roomRate) external {
        rooms[_roomNumber].roomRate = _roomRate;
    }


    // TODO improve data structures so for loop not needed.
    function bookRoom(uint256 _date, uint256 _uniqueId, uint256 _maxAmountToPay) external {
        // TODO only allow calling from approved travel agency contracts. This is super important given the use of tx.origin below.

        require(_date != 0, "Date can not be zero");
        for (uint i = 0; i < numRooms; i++) {
            uint256 rate = rooms[i].roomRate;
            // If amount is OK and the room is available.
            if (rate <= _maxAmountToPay && rooms[i].bookedBy[_date] == address(0)) {
                // Book room
                rooms[i].bookedBy[_date] = tx.origin;
                bookingRefToRoomNumber[_uniqueId] = i;
                bookingRefToDate[_uniqueId] = _date;
                // Pay for room.
                erc20.transferFrom(tx.origin, owner, rate);
                return;
            }
        }
        require(false, "No rooms available");
    }

    function getBookingInformation(uint256 _uniqueId) external view returns (uint256 amountPaid, uint256 roomId, uint256 date) {
        roomId = bookingRefToRoomNumber[_uniqueId];
        date = bookingRefToDate[_uniqueId];
        if (date == 0) {
            amountPaid = 0;
        }
        else {
            amountPaid = rooms[roomId].roomRate;
        }
    }


    function getNumberRoomsAvailable(uint256 _date) public view returns (uint256 numRoomsAvailable) {
        for (uint i=0; i<numRooms; i++) {
            address bookedBy = rooms[i].bookedBy[_date];
            if (bookedBy == address(0)) {
                numRoomsAvailable++;
            }
        }
    }

    function getBookings(uint256 _date) external view returns (address[] memory bookings) {
        uint256 numAvailable = getNumberRoomsAvailable(_date);
        bookings = new address[](numRooms - numAvailable);
        uint256 index = 0;
        for (uint i=0; i<numRooms; i++) {
            address bookedBy = rooms[i].bookedBy[_date];
            if (bookedBy != address(0)) {
                bookings[index++] = bookedBy;
            }
        }
    }

}
