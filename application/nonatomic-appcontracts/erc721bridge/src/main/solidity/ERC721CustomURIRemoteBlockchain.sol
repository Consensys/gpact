// SPDX-License-Identifier: MIT

pragma solidity ^0.8.0;

import "../../../../../../common/openzeppelin/src/main/solidity/utils/Context.sol";
import "../../../../../../common/openzeppelin/src/main/solidity/access/AccessControlEnumerable.sol";
import "../../../../../../common/openzeppelin/src/main/solidity/token/ERC721/extensions/ERC721Enumerable.sol";
import "../../../../../../common/openzeppelin/src/main/solidity/token/ERC721/extensions/ERC721Burnable.sol";
import "../../../../../../common/openzeppelin/src/main/solidity/token/ERC721/extensions/ERC721Pausable.sol";
import "../../../../../../common/openzeppelin/src/main/solidity/token/ERC721/extensions/ERC721URIStorage.sol";
import "./ERC721RemoteBlockchain.sol";

contract ERC721CustomURIRemoteBlockchain is
    ERC721URIStorage,
    ERC721RemoteBlockchain
{
    string private _baseTokenURI;

    /**
     * @dev Grants `DEFAULT_ADMIN_ROLE`, `MINTER_ROLE` and `PAUSER_ROLE` to the
     * account that deploys the contract.
     *
     */
    constructor(
        string memory name,
        string memory symbol,
        string memory baseTokenURI
    )
        ERC721RemoteBlockchain(
            name,
            symbol,
            _msgSender(),
            _msgSender(),
            _msgSender()
        )
    {}

    /**
     * @dev Creates a new token with the specified token ID. This should be called by the
     * crosschain control contract / function call bridge.
     *
     * @param _recipient    Address to assign the token to.
     * @param _tokenId      Token to create.
     *
     * Requirements:
     * - the caller must have the `MINTER_ROLE`.
     */
    function mint(
        address _recipient,
        uint256 _tokenId,
        bytes memory _data
    ) public virtual override {
        require(
            hasRole(MINTER_ROLE, _msgSender()),
            "ERC721CrosschainEndPoint: must have minter role to mint"
        );
        _mint(_recipient, _tokenId);
        _setTokenURI(_tokenId, string(_data));
    }

    function _beforeTokenTransfer(
        address from,
        address to,
        uint256 tokenId
    ) internal override(ERC721, ERC721RemoteBlockchain) {
        ERC721RemoteBlockchain._beforeTokenTransfer(from, to, tokenId);
    }

    /**
     * @dev See {IERC165-supportsInterface}.
     */
    function supportsInterface(bytes4 interfaceId)
        public
        view
        virtual
        override(ERC721, ERC721RemoteBlockchain)
        returns (bool)
    {
        return ERC721RemoteBlockchain.supportsInterface(interfaceId);
    }

    function tokenURI(uint256 tokenId)
        public
        view
        virtual
        override(ERC721, ERC721URIStorage)
        returns (string memory)
    {
        return ERC721URIStorage.tokenURI(tokenId);
    }

    function _burn(uint256 tokenId)
        internal
        virtual
        override(ERC721, ERC721URIStorage)
    {
        super.burn(tokenId);
    }
}
