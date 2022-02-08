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

import "../../../../../contracts/contracts/src/application/lockablestorage/LockableStorage.sol";
import "../../../../../contracts/contracts/src/functioncall/interface/AtomicHiddenAuthParameters.sol";
import "../../../../../contracts/contracts/src/openzeppelin/token/ERC20/IERC20.sol";

contract Train is LockableStorage, AtomicHiddenAuthParameters {
    // Seat rate.
    // Map (seat number => seat rate)
    // mapping (uint256 => uint256) private roomRate;
    uint256 private constant SEAT_RATE_MAP = 0;

    // Booked by.
    // Map (seat number => map (date => who booked seat))
    // Map (uint256 => map (uint256 => address)) bookedBy;
    uint256 private constant SEAT_BOOKED_BY_2MAP = 1;

    // Map (booking reference => seat number)
    // mapping(uint256 => uint256) bookingRefToSeatNumber;
    uint256 private constant BOOKING_REF_TO_SEAT_NUMBER = 2;

    // Map (booking reference => date)
    // mapping(uint256 => uint256) bookingRefToDate;
    uint256 private constant BOOKING_REF_TO_DATE = 3;

    // Number of seats.
    // For this example, the number of seats can never be smaller. If
    // the number of seats could be reduced, then the system would need
    // to be carefully designed for locking.
    uint256 numSeats;

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

    function addApprovedTravelAgency(
        uint256 _blockchainId,
        address _travelAgencyContract,
        address spendingAccount
    ) external onlyOwner {
        approvedTravelAgencies[_blockchainId] = _travelAgencyContract;
        travelAgencySpender[_travelAgencyContract] = spendingAccount;
    }

    function addSeats(uint256 _seatRate, uint256 _numberOfSeats)
        external
        onlyOwner
    {
        for (uint256 i = 0; i < _numberOfSeats; i++) {
            setMapValue(SEAT_RATE_MAP, numSeats++, _seatRate);
        }
    }

    function changeSeatRate(uint256 _seatNumber, uint256 _seatRate)
        external
        onlyOwner
    {
        setMapValue(SEAT_RATE_MAP, _seatNumber, _seatRate);
    }

    function bookSeat(
        uint256 _date,
        uint256 _uniqueId,
        uint256 _maxAmountToPay
    ) external {
        require(address(cbc) == msg.sender, "Must be crosschain call");

        // Check that the calling contract was the travel agency linked to this one from
        // the source blockchain.
        // TODO check that the root blockchain is trusted
        (
            ,
            uint256 sourceBlockchainId,
            address sourceContract
        ) = decodeAtomicAuthParams();
        require(
            sourceContract == approvedTravelAgencies[sourceBlockchainId],
            "Sender is not an approved travel agency"
        );

        require(_date != 0, "Date can not be zero");
        for (uint256 i = 0; i < numSeats; i++) {
            uint256 rate = getMapValue(SEAT_RATE_MAP, i);
            // If amount is OK and the room is available.
            if (
                !isDoubleMapValueLocked(SEAT_BOOKED_BY_2MAP, i, _date) &&
                rate <= _maxAmountToPay &&
                address(
                    uint160(getDoubleMapValue(SEAT_BOOKED_BY_2MAP, i, _date))
                ) ==
                address(0)
            ) {
                // Book seat
                setDoubleMapValue(
                    SEAT_BOOKED_BY_2MAP,
                    i,
                    _date,
                    uint160(tx.origin)
                );
                setMapValue(BOOKING_REF_TO_SEAT_NUMBER, _uniqueId, i);
                setMapValue(BOOKING_REF_TO_DATE, _uniqueId, _date);
                // Pay for room.
                erc20.transferFrom(
                    travelAgencySpender[sourceContract],
                    owner,
                    rate
                );
                return;
            }
        }
        require(false, "No seats available");
    }

    function getBookingInformation(uint256 _uniqueId)
        external
        view
        returns (
            uint256 amountPaid,
            uint256 seatId,
            uint256 date
        )
    {
        date = getMapValue(BOOKING_REF_TO_DATE, _uniqueId);
        if (date == 0) {
            amountPaid = 0;
            seatId = 0;
        } else {
            seatId = getMapValue(BOOKING_REF_TO_SEAT_NUMBER, _uniqueId);
            amountPaid = getMapValue(SEAT_RATE_MAP, seatId);
        }
    }

    function getNumberSeatsAvailable(uint256 _date)
        public
        view
        returns (uint256 numSeatsAvailable)
    {
        numSeatsAvailable = 0;
        for (uint256 i = 0; i < numSeats; i++) {
            if (
                !isDoubleMapValueLocked(SEAT_BOOKED_BY_2MAP, i, _date) &&
                getDoubleMapValue(SEAT_BOOKED_BY_2MAP, i, _date) == 0
            ) {
                numSeatsAvailable++;
            }
        }
    }

    function getBookings(uint256 _date)
        external
        view
        returns (address[] memory bookings)
    {
        uint256 numAvailable = getNumberSeatsAvailable(_date);
        bookings = new address[](numSeats - numAvailable);
        uint256 index = 0;
        for (uint256 i = 0; i < numSeats; i++) {
            if (!isDoubleMapValueLocked(SEAT_BOOKED_BY_2MAP, i, _date)) {
                address bookedBy = address(
                    uint160(getDoubleMapValue(SEAT_BOOKED_BY_2MAP, i, _date))
                );
                if (bookedBy != address(0)) {
                    bookings[index++] = bookedBy;
                }
            }
        }
    }
}
