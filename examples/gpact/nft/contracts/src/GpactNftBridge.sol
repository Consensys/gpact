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

import "./ListingStorage.sol";
import "../../../../../contracts/contracts/src/functioncall/interface/CrosschainFunctionCallInterface.sol";
import "../../../../../contracts/contracts/src/functioncall/interface/AtomicHiddenAuthParameters.sol";
import "../../../../../contracts/contracts/src/functioncall/interface/CrosschainLockingInterface.sol";
import "../../../../../contracts/contracts/src/functioncall/interface/LockableStorageInterface.sol";
import "../../../../../contracts/contracts/src/openzeppelin/access/AccessControl.sol";
import "../../../../../contracts/contracts/src/openzeppelin/token/ERC721/IERC721.sol";
import "../../../../../contracts/contracts/src/openzeppelin/token/ERC20/IERC20.sol";

// AtomicBridge is for crosschain purchase of NFT.
contract AtomicBridge is
    AccessControl,
    AtomicHiddenAuthParameters,
    LockableStorageInterface
{
    event StartListing(address _nftContract, uint256 _nftId);

    event StopListing(address _nftContract, uint256 _nftId);

    event UpsertAsking(
        address _nftContract,
        uint256 _nftId,
        uint256 _otherBcId,
        address _otherTokenContract,
        uint256 _amount,
        address _otherTokenRecipient
    );

    event RemoveAsking(
        address _nftContract,
        uint256 _nftId,
        uint256 _otherBcId,
        address _otherTokenContract
    );

    event SuccessfulPurchase(
        address _buyer,
        address _nftContract,
        uint256 _nftId,
        uint256 _otherBcId,
        address _otherTokenContract,
        uint256 _amount,
        address _tokenRecipient
    );

    event ProcessTokenTransfer(
        address _tokenContract,
        uint256 _amount,
        address _from,
        address _to
    );

    event CommitTokenTransfer(
        address _tokenContract,
        uint256 _amount,
        address _tokenRecipient
    );

    event RevertTokenTransfer(
        address _tokenContract,
        uint256 _amount,
        address _tokenRecipient
    );

    // Crosschain function call contracts.
    CrosschainFunctionCallInterface private cbc;
    CrosschainLockingInterface private cbcLocking;

    ListingStorage public listingStorage;

    // Provisional Token transfers.
    struct ProvisionalTransfer {
        uint256 index;
        address tokenContract;
        uint256 amount;
        address owner;
        address recipient;
    }
    mapping(bytes32 => ProvisionalTransfer) public provisionalUpdates;
    bytes32[] public provisionalUpdatesKeys;

    // Bridge mapping.
    mapping(uint256 => address) public remoteBridges;

    constructor(address _gpactContract) {
        _setupRole(DEFAULT_ADMIN_ROLE, _msgSender());
        cbc = CrosschainFunctionCallInterface(_gpactContract);
        cbcLocking = CrosschainLockingInterface(_gpactContract);

        listingStorage = new ListingStorage();
    }

    function getStorageAddress() public view returns (address _storageAddress) {
        return address(listingStorage);
    }

    // Register a remote bridge.
    function registerRemoteBridges(
        uint256[] calldata _otherBcIds,
        address[] calldata _otherBridgeAddrs
    ) external {
        require(
            hasRole(DEFAULT_ADMIN_ROLE, _msgSender()),
            "Bridge: must have admin role"
        );
        require(
            _otherBcIds.length == _otherBridgeAddrs.length,
            "Bridge: params length not match"
        );
        for (uint256 i = 0; i < _otherBcIds.length; i++) {
            remoteBridges[_otherBcIds[i]] = _otherBridgeAddrs[i];
        }
    }

    // Remove a remote bridge.
    function removeRemoteBridges(uint256[] calldata _otherBcIds) external {
        require(
            hasRole(DEFAULT_ADMIN_ROLE, _msgSender()),
            "Bridge: must have admin role"
        );
        for (uint256 i = 0; i < _otherBcIds.length; i++) {
            delete remoteBridges[_otherBcIds[i]];
        }
    }

    // Start listing an NFT.
    function startListingNFT(address _nftContract, uint256 _nftId) public {
        address sender = _msgSender();

        // Transfer NFT from sender to bridge.
        require(
            IERC721(_nftContract).getApproved(_nftId) == address(this),
            "Bridge: nft not approved"
        );

        listingStorage.createListing(_nftContract, _nftId, sender);

        // Emit an event for start listing.
        emit StartListing(_nftContract, _nftId);
    }

    // Stop listing an NFT.
    function stopListingNFT(address _nftContract, uint256 _nftId) public {
        address sender = _msgSender();

        ListingStorage.Listing memory listing;
        try listingStorage.findListing(_nftContract, _nftId) returns (
            ListingStorage.Listing memory _listing
        ) {
            listing = _listing;
        } catch Error(string memory revertReason) {
            revert(revertReason);
        }

        // Check if sender is the original owner of the nft.
        require(sender == listing.owner, "Bridge: only owner can call");

        listingStorage.deleteListing(_nftContract, _nftId);

        // Emit an event for stop listing.
        emit StopListing(_nftContract, _nftId);
    }

    // Upsert an asking for a listed NFT.
    function upsertAsking(
        address _nftContract,
        uint256 _nftId,
        uint256 _otherBcId,
        address _otherTokenContract,
        uint256 _amount,
        address _otherTokenRecipient
    ) public {
        address sender = _msgSender();

        ListingStorage.Listing memory listing = listingStorage.findListing(
            _nftContract,
            _nftId
        );

        require(sender == listing.owner, "Bridge: only owner can call");

        listingStorage.upsertAsking(
            _nftContract,
            _nftId,
            _otherBcId,
            _otherTokenContract,
            _amount,
            _otherTokenRecipient
        );

        // Emit an event for upsert asking.
        emit UpsertAsking(
            _nftContract,
            _nftId,
            _otherBcId,
            _otherTokenContract,
            _amount,
            _otherTokenRecipient
        );
    }

    // Remove an asking for a listed NFT.
    function removeAsking(
        address _nftContract,
        uint256 _nftId,
        uint256 _otherBcId,
        address _otherTokenContract
    ) public {
        address sender = _msgSender();

        ListingStorage.Listing memory listing;
        try listingStorage.findListing(_nftContract, _nftId) returns (
            ListingStorage.Listing memory _listing
        ) {
            listing = _listing;
        } catch Error(string memory revertReason) {
            revert(revertReason);
        }

        ListingStorage.Ask memory asking;
        try
            listingStorage.findAsking(
                _nftContract,
                _nftId,
                _otherBcId,
                _otherTokenContract
            )
        returns (ListingStorage.Ask memory _asking) {
            asking = _asking;
        } catch Error(string memory revertReason) {
            revert(revertReason);
        }

        // Sender needs to be the owner.
        require(sender == listing.owner, "Bridge: only owner can call");

        listingStorage.deleteAsking(
            _nftContract,
            _nftId,
            _otherBcId,
            _otherTokenContract
        );

        // Emit an event for remove asking.
        emit RemoveAsking(
            _nftContract,
            _nftId,
            _otherBcId,
            _otherTokenContract
        );
    }

    function startListingNFTWithAsking(
        address _nftContract,
        uint256 _nftId,
        uint256 _otherBcId,
        address _otherTokenContract,
        uint256 _amount,
        address _otherTokenRecipient
    ) public {
        startListingNFT(_nftContract, _nftId);
        upsertAsking(
            _nftContract,
            _nftId,
            _otherBcId,
            _otherTokenContract,
            _amount,
            _otherTokenRecipient
        );
    }

    function buyNFTUsingRemoteFunds(
        address _buyer,
        address _nftContract,
        uint256 _nftId,
        uint256 _otherBcId,
        address _otherTokenContract
    ) external {
        // Only linked cbc can call this function.
        address sender = _msgSender();
        require(sender == address(cbc), "Only linked cbc can call");

        // Check destination chain.
        address destBridge = remoteBridges[_otherBcId];
        require(destBridge != address(0), "Bridge: dest chain not supported");

        // Find listing.
        ListingStorage.Listing memory listing;
        try listingStorage.findListing(_nftContract, _nftId) returns (
            ListingStorage.Listing memory _listing
        ) {
            listing = _listing;
        } catch Error(string memory revertReason) {
            revert(revertReason);
        }

        // Find asking.
        ListingStorage.Ask memory asking;
        try
            listingStorage.findAsking(
                _nftContract,
                _nftId,
                _otherBcId,
                _otherTokenContract
            )
        returns (ListingStorage.Ask memory _asking) {
            asking = _asking;
        } catch Error(string memory revertReason) {
            revert(revertReason);
        }

        // Perform remote token transfer.
        bytes memory functionCallData = abi.encodeWithSelector(
            this.processTokenTransfer.selector,
            _otherTokenContract,
            asking.amount,
            _buyer,
            asking.recipient
        );
        cbc.crossBlockchainCall(_otherBcId, destBridge, functionCallData);

        // Payment succeed, transfer NFT.
        IERC721(_nftContract).transferFrom(listing.owner, _buyer, _nftId);
        listingStorage.deleteListing(_nftContract, _nftId);

        emit SuccessfulPurchase(
            _buyer,
            _nftContract,
            _nftId,
            _otherBcId,
            _otherTokenContract,
            asking.amount,
            asking.recipient
        );
    }

    function processTokenTransfer(
        address _tokenContract,
        uint256 _amount,
        address _from,
        address _to
    ) external {
        // Only linked cbc can call this function.
        address sender = _msgSender();
        require(sender == address(cbc), "Only linked cbc can call");

        // Check source chain.
        (
            ,
            // The root chain id is not part of the logic, so ignored here.
            uint256 sourceBcId,
            address sourceContract
        ) = decodeAtomicAuthParams();
        require(
            sourceContract == remoteBridges[sourceBcId],
            "Bridge: Contract does not match"
        );

        // Lock contract.
        bytes32 txId = cbcLocking.getActiveCallCrosschainRootTxId();
        cbcLocking.addToListOfLockedContracts(address(this));

        // Do provisional token transfer.
        IERC20(_tokenContract).transferFrom(_from, address(this), _amount);
        provisionalUpdates[txId].index = provisionalUpdatesKeys.length;
        provisionalUpdates[txId].tokenContract = _tokenContract;
        provisionalUpdates[txId].amount = _amount;
        provisionalUpdates[txId].owner = _from;
        provisionalUpdates[txId].recipient = _to;
        provisionalUpdatesKeys.push(txId);

        emit ProcessTokenTransfer(_tokenContract, _amount, _from, _to);
    }

    // finalise a transaction.
    function finalise(bool _commit, bytes32 _crossRootTxId) external {
        address sender = _msgSender();
        require(sender == address(cbc), "Only linked cbc can call");

        // Finalise provisional token transfer.
        ProvisionalTransfer memory update = provisionalUpdates[_crossRootTxId];
        if (_commit) {
            // commit, transfer to recipient.
            IERC20(update.tokenContract).transfer(
                update.recipient,
                update.amount
            );

            emit CommitTokenTransfer(
                update.tokenContract,
                update.amount,
                update.recipient
            );
        } else {
            // revert, transfer back to owner.
            IERC20(update.tokenContract).transfer(update.owner, update.amount);

            emit RevertTokenTransfer(
                update.tokenContract,
                update.amount,
                update.owner
            );
        }

        // Clean provisional updates.
        delete provisionalUpdates[_crossRootTxId];
        if (provisionalUpdatesKeys.length > 1) {
            // shift last element to "gap" in array
            uint256 index = update.index;
            provisionalUpdates[
                provisionalUpdatesKeys[provisionalUpdatesKeys.length - 1]
            ].index = index;
            provisionalUpdatesKeys[index] = provisionalUpdatesKeys[
                provisionalUpdatesKeys.length - 1
            ];
        }
        provisionalUpdatesKeys.pop();
    }

    // Helper function to get current provisional updates count.
    function getProvisionalUpdatesCount() public view returns (uint256 _count) {
        return provisionalUpdatesKeys.length;
    }
}
