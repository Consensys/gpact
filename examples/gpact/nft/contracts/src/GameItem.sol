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
pragma solidity ^0.8.0;

import "../../../../../contracts/contracts/src/openzeppelin/token/ERC721/ERC721.sol";
import "../../../../../contracts/contracts/src/openzeppelin/token/ERC721/extensions/ERC721URIStorage.sol";
import "../../../../../contracts/contracts/src/openzeppelin/token/ERC721/extensions/ERC721Enumerable.sol";
import "../../../../../contracts/contracts/src/openzeppelin/utils/Counters.sol";

contract GameItem is ERC721URIStorage, ERC721Enumerable {
    using Counters for Counters.Counter;
    Counters.Counter private _tokenIds;

    // solhint-disable-next-line no-empty-blocks
    constructor() ERC721("GameItem", "ITM") {}

    function awardItem(address player) public returns (uint256) {
        _tokenIds.increment();

        uint256 newItemId = _tokenIds.current();
        _mint(player, newItemId);

        return newItemId;
    }

    function awardItem(address player, string calldata uri)
        public
        returns (uint256)
    {
        _tokenIds.increment();

        uint256 newItemId = _tokenIds.current();
        _mint(player, newItemId);
        _setTokenURI(newItemId, uri);

        return newItemId;
    }

    function findAllNFTsOwnedBy(address account)
        public
        view
        returns (uint256[] memory)
    {
        uint256 count = balanceOf(account);
        uint256[] memory nftIds = new uint256[](count);
        for (uint256 i = 0; i < count; i++) {
            uint256 id = tokenOfOwnerByIndex(account, i);
            nftIds[i] = id;
        }

        return nftIds;
    }

    function _beforeTokenTransfer(
        address from,
        address to,
        uint256 tokenId
    ) internal override(ERC721, ERC721Enumerable) {
        super._beforeTokenTransfer(from, to, tokenId);
    }

    function _burn(uint256 tokenId)
        internal
        override(ERC721, ERC721URIStorage)
    {
        super._burn(tokenId);
    }

    function tokenURI(uint256 tokenId)
        public
        view
        override(ERC721, ERC721URIStorage)
        returns (string memory)
    {
        return super.tokenURI(tokenId);
    }

    function supportsInterface(bytes4 interfaceId)
        public
        view
        override(ERC721, ERC721Enumerable)
        returns (bool)
    {
        return super.supportsInterface(interfaceId);
    }
}
