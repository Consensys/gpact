/*
 * Copyright 2022 ConsenSys Software Inc
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

import "../../../../../contracts/contracts/src/openzeppelin/access/Ownable.sol";

contract ListingStorage is Ownable {
    struct Listing {
        uint256 index;
        address nftContract;
        uint256 nftId;
        address owner;
        bytes[] askingsKeys;
    }

    mapping(bytes => Listing) public listings;
    mapping(bytes => bool) public listingsContains;
    bytes[] public listingsKeys;

    struct Ask {
        uint256 index;
        uint256 bcId;
        address tokenAddr;
        uint256 amount;
        address recipient;
    }

    mapping(bytes => Ask) public askings;
    mapping(bytes => bool) public askingsContains;
    bytes[] public askingsKeys;

    //region Listing CRUD methods

    function getListingsCount() public view returns (uint256 _count) {
        return listingsKeys.length;
    }

    function createListing(
        address _nftContract,
        uint256 _nftId,
        address _nftOwner
    ) public onlyOwner {
        bytes memory listingKey = getListingKey((_nftContract), _nftId);

        if (listingsContains[listingKey]) {
            revert("Duplicated listing");
        }

        listings[listingKey].index = listingsKeys.length;
        listings[listingKey].nftContract = _nftContract;
        listings[listingKey].nftId = _nftId;
        listings[listingKey].owner = _nftOwner;
        listingsKeys.push(listingKey);
        listingsContains[listingKey] = true;
    }

    function deleteListing(address _nftContract, uint256 _nftId)
        public
        onlyOwner
    {
        bytes memory _listingKey = getListingKey(_nftContract, _nftId);

        if (!listingsContains[_listingKey]) {
            revert("Listing not found");
        }

        Listing storage listing = listings[_listingKey];
        for (uint256 i = 0; i < listing.askingsKeys.length; i++) {
            deleteAskingByKey(_nftContract, _nftId, listing.askingsKeys[i]);
        }

        if (listingsKeys.length > 1) {
            // shift last element to "gap" in array
            uint256 index = listing.index;
            listings[listingsKeys[listingsKeys.length - 1]].index = index;
            listingsKeys[index] = listingsKeys[listingsKeys.length - 1];
        }

        listingsKeys.pop();
        listingsContains[_listingKey] = false;
        delete listings[_listingKey];
    }

    function findListing(address _nftContract, uint256 _nftId)
        public
        view
        returns (Listing memory _listing)
    {
        bytes memory listingKey = getListingKey((_nftContract), _nftId);
        if (listingsContains[listingKey]) {
            return listings[listingKey];
        } else {
            revert("Listing not found");
        }
    }

    function findListings() public view returns (Listing[] memory) {
        Listing[] memory _listings = new Listing[](listingsKeys.length);

        for (uint256 i = 0; i < listingsKeys.length; i++) {
            _listings[i] = listings[listingsKeys[i]];
        }

        return _listings;
    }

    function getListingKey(address _nftContract, uint256 _nftId)
        internal
        pure
        returns (bytes memory)
    {
        return abi.encode(_nftContract, _nftId);
    }

    //endregion

    //region Asking CRUD methods

    function getAskingKey(
        address _nftContract,
        uint256 _nftId,
        uint256 _otherBcId,
        address _otherTokenContract
    ) internal pure returns (bytes memory) {
        return
            abi.encode(_nftContract, _nftId, _otherBcId, _otherTokenContract);
    }

    function upsertAsking(
        address _nftContract,
        uint256 _nftId,
        uint256 _otherBcId,
        address _otherTokenContract,
        uint256 _amount,
        address _otherTokenRecipient
    ) public onlyOwner {
        bytes memory listingKey = getListingKey(_nftContract, _nftId);
        bytes memory askingKey = getAskingKey(
            _nftContract,
            _nftId,
            _otherBcId,
            _otherTokenContract
        );

        Listing storage listing = listings[listingKey];

        askings[askingKey].bcId = _otherBcId;
        askings[askingKey].tokenAddr = _otherTokenContract;
        askings[askingKey].amount = _amount;
        askings[askingKey].recipient = _otherTokenRecipient;

        if (!askingsContains[askingKey]) {
            askings[askingKey].index = listing.askingsKeys.length;
            askingsKeys.push(askingKey);
            askingsContains[askingKey] = true;
            listing.askingsKeys.push(askingKey);
        }
    }

    function findAsking(
        address _nftContract,
        uint256 _nftId,
        uint256 _otherBcId,
        address _otherTokenContract
    ) public view returns (Ask memory _ask) {
        bytes memory askingKey = getAskingKey(
            _nftContract,
            _nftId,
            _otherBcId,
            _otherTokenContract
        );
        if (askingsContains[askingKey]) {
            return askings[askingKey];
        } else {
            revert("Asking not found");
        }
    }

    function findAskings(address _nftContract, uint256 _nftId)
        public
        view
        returns (Ask[] memory _askings)
    {
        Listing memory listing = findListing(_nftContract, _nftId);

        Ask[] memory askingsList = new Ask[](listing.askingsKeys.length);

        for (uint256 i = 0; i < listing.askingsKeys.length; i++) {
            askingsList[i] = askings[listing.askingsKeys[i]];
        }

        return askingsList;
    }

    function deleteAsking(
        address _nftContract,
        uint256 _nftId,
        uint256 _otherBcId,
        address _otherTokenContract
    ) public onlyOwner {
        bytes memory _askingKey = getAskingKey(
            _nftContract,
            _nftId,
            _otherBcId,
            _otherTokenContract
        );

        deleteAskingByKey(_nftContract, _nftId, _askingKey);
    }

    function deleteAskingByKey(
        address _nftContract,
        uint256 _nftId,
        bytes memory _askingKey
    ) internal onlyOwner {
        bytes memory _listingKey = getListingKey(_nftContract, _nftId);

        if (!askingsContains[_askingKey]) {
            revert("Asking not found");
        }

        if (!listingsContains[_listingKey]) {
            revert("No listing found");
        }

        ListingStorage.Listing storage listing = listings[_listingKey];
        if (listing.askingsKeys.length > 1) {
            // shift last element to "gap" in array
            uint256 index = askings[_askingKey].index;
            listing.askingsKeys[index] = listing.askingsKeys[
                listing.askingsKeys.length - 1
            ];
        }
        listing.askingsKeys.pop();

        if (_askingKey.length > 1) {
            // shift last element to "gap" in array
            uint256 index = askings[_askingKey].index;
            askings[askingsKeys[askingsKeys.length - 1]].index = index;
            askingsKeys[index] = askingsKeys[askingsKeys.length - 1];
        }

        askingsKeys.pop();
        askingsContains[_askingKey] = false;
        delete askings[_askingKey];
    }

    //endregion
}
