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

import "../../../../../../functioncall/gpact/src/main/solidity/LockableStorage.sol";
import "../../../../../../common/openzeppelin/src/main/solidity/token/ERC20/IERC20.sol";

contract Hotel is LockableStorage {
    // Room rate.
    // Map (room number => room rate)
    // mapping (uint256 => uint256) private roomRate;
    uint256 constant private ROOM_RATE_MAP = 0;

    // Booked by.
    // Map (room number => map (date => who booked room))
    // Map (uint256 => map (uint256 => address)) bookedBy;
    uint256 constant private ROOM_BOOKED_BY_2MAP = 1;

    // Map (booking reference => room number)
    // mapping(uint256 => uint256) bookingRefToRoomNumber;
    uint256 constant private BOOKING_REF_TO_ROOM_NUMBER = 2;

    // Map (booking reference => date)
    // mapping(uint256 => uint256) bookingRefToDate;
    uint256 constant private BOOKING_REF_TO_DATE = 3;

    // Number of rooms.
    // For this example, the number of rooms can never be smaller. If
    // the number of rooms could be reduced, then the system would need
    // to be carefully designed for locking.
    uint256 numRooms;

    // For this example, new agencies can be added to the approved
    // list. However, old agencies can never be removed. Additionally,
    // the spending address can't be changed. If this functionality
    // was required, the system would need to be carefully designed
    // for locking.
    // Map (address of travel agency contract => spender address for the travel agency)
    mapping(address => address) travelAgencySpender;
    // Map (blockchain id => address of travel agency contract)
    mapping(uint256 => address) approvedTravelAgencies;

    address owner;
    IERC20 erc20;

    modifier onlyOwner() {
        require(msg.sender == owner);
        _;
    }

    constructor(address _erc20, address _cbc) LockableStorage(_cbc) {
        owner = msg.sender;
        erc20 = IERC20(_erc20);
    }

    function addApprovedTravelAgency(uint256 _blockchainId, address _travelAgencyContract, address spendingAccount) onlyOwner external {
        approvedTravelAgencies[_blockchainId] = _travelAgencyContract;
        travelAgencySpender[_travelAgencyContract] = spendingAccount;
    }

    function addRooms(uint256 _roomRate, uint256 _numberOfRooms) onlyOwner external {
        for (uint i=0; i < _numberOfRooms; i++) {
            setMapValue(ROOM_RATE_MAP, numRooms++, _roomRate);
        }
    }

    function changeRoomRate(uint256 _roomNumber, uint256 _roomRate) onlyOwner external {
        setMapValue(ROOM_RATE_MAP, _roomNumber, _roomRate);
    }


    function bookRoom(uint256 _date, uint256 _uniqueId, uint256 _maxAmountToPay) external {
        require(!crossBlockchainControl.isSingleBlockchainCall(), "Must be crosschain call");

        // Check that the calling contract was the travel agency linked to this one from
        // the source blockchain.
        // TODO check that the root blockchain is trusted
        (, uint256 sourceBlockchainId, address sourceContract) = crossBlockchainControl.whoCalledMe();
        require(sourceContract == approvedTravelAgencies[sourceBlockchainId], "Sender is not an approved travel agency");

        require(_date != 0, "Date can not be zero");
        for (uint i = 0; i < numRooms; i++) {
            uint256 rate = getMapValue(ROOM_RATE_MAP, i);
            // If amount is OK and the room is available.
            if (!isDoubleMapValueLocked(ROOM_BOOKED_BY_2MAP, i, _date) &&
                    rate <= _maxAmountToPay &&
                    address(uint160(getDoubleMapValue(ROOM_BOOKED_BY_2MAP, i, _date))) == address(0)) {
                // Book room
                setDoubleMapValue(ROOM_BOOKED_BY_2MAP, i, _date, uint160(tx.origin));
                setMapValue(BOOKING_REF_TO_ROOM_NUMBER, _uniqueId, i);
                setMapValue(BOOKING_REF_TO_DATE, _uniqueId, _date);
                // Pay for room.
                erc20.transferFrom(travelAgencySpender[sourceContract], owner, rate);
                return;
            }
        }
        require(false, "No rooms available");
    }

    function getBookingInformation(uint256 _uniqueId) external view returns (uint256 amountPaid, uint256 roomId, uint256 date) {
        date = getMapValue(BOOKING_REF_TO_DATE, _uniqueId);
        if (date == 0) {
            amountPaid = 0;
            roomId = 0;
        }
        else {
            roomId = getMapValue(BOOKING_REF_TO_ROOM_NUMBER, _uniqueId);
            amountPaid = getMapValue(ROOM_RATE_MAP, roomId);
        }
    }


    function getNumberRoomsAvailable(uint256 _date) public view returns (uint256 numRoomsAvailable) {
        for (uint i=0; i<numRooms; i++) {
            if (!isDoubleMapValueLocked(ROOM_BOOKED_BY_2MAP, i, _date) &&
                getDoubleMapValue(ROOM_BOOKED_BY_2MAP, i, _date) == 0) {
                numRoomsAvailable++;
            }
        }
    }

    function getBookings(uint256 _date) external view returns (address[] memory bookings) {
        uint256 numAvailable = getNumberRoomsAvailable(_date);
        bookings = new address[](numRooms - numAvailable);
        uint256 index = 0;
        for (uint i=0; i<numRooms; i++) {
            if (!isDoubleMapValueLocked(ROOM_BOOKED_BY_2MAP, i, _date)) {
                address bookedBy = address(uint160(getDoubleMapValue(ROOM_BOOKED_BY_2MAP, i, _date)));
                if (bookedBy != address(0)) {
                    bookings[index++] = bookedBy;
                }
            }
        }
    }
}
