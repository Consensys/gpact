/**
 * Copyright (c) 2017 Andreas Olofsson (andreas@ohalo.co / androlo1980@gmail.com))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 *
 * RLPReader is used to read and parse RLP encoded data in memory.
 *
 *
 * Copied from:
 *  https://github.com/androlo/standard-contracts/blob/master/contracts/src/codec/RLP.sol
 *
 * Test code is here:
 *  https://github.com/androlo/standard-contracts/blob/master/contracts/test/RLPReaderTest.sol
 * and here:
 *  https://github.com/androlo/standard-contracts/blob/master/integration_tests/rlp_test.js
 *
 * SPDX-License-Identifier: MIT
 */
pragma solidity >=0.7.1;

contract RLP {
    uint256 constant DATA_SHORT_START = 0x80;
    uint256 constant DATA_LONG_START = 0xB8;
    uint256 constant LIST_SHORT_START = 0xC0;
    uint256 constant LIST_LONG_START = 0xF8;

    uint256 constant DATA_LONG_OFFSET = 0xB7;
    uint256 constant LIST_LONG_OFFSET = 0xF7;

    struct RLPItem {
        uint256 _unsafe_memPtr; // Pointer to the RLP-encoded bytes.
        uint256 _unsafe_length; // Number of bytes. This is the full length of the string.
    }

    struct Iterator {
        RLPItem _unsafe_item; // Item that's being iterated over.
        uint256 _unsafe_nextPtr; // Position of the next item in the list.
    }

    /* Iterator */

    function next(Iterator memory self)
        internal
        pure
        returns (RLPItem memory subItem)
    {
        if (hasNext(self)) {
            uint256 ptr = self._unsafe_nextPtr;
            uint256 itemLength = _itemLength(ptr);
            subItem._unsafe_memPtr = ptr;
            subItem._unsafe_length = itemLength;
            self._unsafe_nextPtr = ptr + itemLength;
        } else revert();
    }

    function next(Iterator memory self, bool strict)
        internal
        pure
        returns (RLPItem memory subItem)
    {
        subItem = next(self);
        if (strict && !_validate(subItem)) revert();
    }

    function hasNext(Iterator memory self) internal pure returns (bool) {
        RLPItem memory item = self._unsafe_item;
        return self._unsafe_nextPtr < item._unsafe_memPtr + item._unsafe_length;
    }

    /* RLPItem */

    /**
     * @dev Creates an RLPItem from an array of RLP encoded bytes.
     * @param self The RLP encoded bytes.
     * @return An RLPItem
     */
    function toRLPItem(bytes memory self)
        internal
        pure
        returns (RLPItem memory)
    {
        uint256 len = self.length;
        if (len == 0) {
            return RLPItem(0, 0);
        }
        uint256 memPtr;
        assembly {
            memPtr := add(self, 0x20)
        }
        return RLPItem(memPtr, len);
    }

    /**
     * @dev Creates an RLPItem from an array of RLP encoded bytes.
     * @param self The RLP encoded bytes.
     * @param strict Will revert() if the data is not RLP encoded.
     * @return An RLPItem
     */
    function toRLPItem(bytes memory self, bool strict)
        internal
        pure
        returns (RLPItem memory)
    {
        RLPItem memory item = toRLPItem(self);
        if (strict) {
            uint256 len = self.length;
            if (_payloadOffset(item) > len) revert();
            if (_itemLength(item._unsafe_memPtr) != len) revert();
            if (!_validate(item)) revert();
        }
        return item;
    }

    /**
     * @dev Check if the RLP item is null.
     * @param self The RLP item.
     * @return true if the item is null.
     */
    function isNull(RLPItem memory self) internal pure returns (bool) {
        return self._unsafe_length == 0;
    }

    /**
     * @dev Check if the RLP item is a list.
     * @param self The RLP item.
     * @return ret 'true' if the item is a list.
     */
    function isList(RLPItem memory self) internal pure returns (bool ret) {
        if (self._unsafe_length == 0) return false;
        uint256 memPtr = self._unsafe_memPtr;
        assembly {
            ret := iszero(lt(byte(0, mload(memPtr)), 0xC0))
        }
    }

    /**
     * @dev Check if the RLP item is data.
     * @param self The RLP item.
     * @return ret 'true' if the item is data.
     */
    function isData(RLPItem memory self) internal pure returns (bool ret) {
        if (self._unsafe_length == 0) return false;
        uint256 memPtr = self._unsafe_memPtr;
        assembly {
            ret := lt(byte(0, mload(memPtr)), 0xC0)
        }
    }

    /**
     * @dev Check if the RLP item is empty (string or list).
     * @param self The RLP item.
     * @return 'true' if the item is null.
     */
    function isEmpty(RLPItem memory self) internal pure returns (bool) {
        if (isNull(self)) return false;
        uint256 b0;
        uint256 memPtr = self._unsafe_memPtr;
        assembly {
            b0 := byte(0, mload(memPtr))
        }
        return (b0 == DATA_SHORT_START || b0 == LIST_SHORT_START);
    }

    /**
     * @dev Get the number of items in an RLP encoded list.
     * @param self The RLP item.
     * @return The number of items.
     */
    function items(RLPItem memory self) internal pure returns (uint256) {
        if (!isList(self)) return 0;
        uint256 b0;
        uint256 memPtr = self._unsafe_memPtr;
        assembly {
            b0 := byte(0, mload(memPtr))
        }
        uint256 pos = memPtr + _payloadOffset(self);
        uint256 last = memPtr + self._unsafe_length - 1;
        uint256 itms;
        while (pos <= last) {
            pos += _itemLength(pos);
            itms++;
        }
        return itms;
    }

    /**
     * @dev Create an iterator.
     * @param self The RLP item.
     * @return it An 'Iterator' over the item.
     */
    function iterator(RLPItem memory self)
        internal
        pure
        returns (Iterator memory it)
    {
        if (!isList(self)) revert();
        uint256 ptr = self._unsafe_memPtr + _payloadOffset(self);
        it._unsafe_item = self;
        it._unsafe_nextPtr = ptr;
    }

    /**
     * @dev Return the RLP encoded bytes.
     * @param self The RLPItem.
     * @return bts The bytes.
     */
    function toBytes(RLPItem memory self)
        internal
        pure
        returns (bytes memory bts)
    {
        uint256 len = self._unsafe_length;
        bts = new bytes(len);
        if (len != 0) {
            _copyToBytes(self._unsafe_memPtr, bts, len);
        }
    }

    /**
     * @dev Decode an RLPItem into bytes. This will not work if the RLPItem is a list.
     *
     * @param self The RLPItem.
     * @return bts The decoded string.
     */
    function toData(RLPItem memory self)
        internal
        pure
        returns (bytes memory bts)
    {
        if (!isData(self)) revert();
        uint256 rStartPos;
        uint256 len;
        (rStartPos, len) = _decode(self);
        bts = new bytes(len);
        _copyToBytes(rStartPos, bts, len);
    }

    /**
     * @dev Get the list of sub-items from an RLP encoded list.
     * Warning: This is inefficient, as it requires that the list is read twice.
     *
     * @param self The RLP item.
     * @return list Array of RLPItems.
     */
    function toList(RLPItem memory self)
        internal
        pure
        returns (RLPItem[] memory list)
    {
        if (!isList(self)) revert();
        uint256 numItems = items(self);
        list = new RLPItem[](numItems);
        Iterator memory it = iterator(self);
        uint256 idx;
        while (hasNext(it)) {
            list[idx] = next(it);
            idx++;
        }
    }

    /**
     * @dev Decode an RLPItem into an ascii string. This will not work if the RLPItem is a list.
     *
     * @param self The RLPItem.
     * @return str The decoded string.
     */
    function toAscii(RLPItem memory self)
        internal
        pure
        returns (string memory str)
    {
        if (!isData(self)) revert();
        uint256 rStartPos;
        uint256 len;
        (rStartPos, len) = _decode(self);
        bytes memory bts = new bytes(len);
        _copyToBytes(rStartPos, bts, len);
        str = string(bts);
    }

    /**
     * @dev Decode an RLPItem into a uint. This will not work if the RLPItem is a list.
     *
     * @param self The RLPItem.
     * @return data The decoded uint256.
     */
    function toUint(RLPItem memory self) internal pure returns (uint256 data) {
        if (!isData(self)) revert();
        uint256 rStartPos;
        uint256 len;
        (rStartPos, len) = _decode(self);
        if (len > 32) revert();
        else if (len == 0) return 0;
        assembly {
            data := div(mload(rStartPos), exp(256, sub(32, len)))
        }
    }

    /**
     * @dev Decode an RLPItem into a boolean. This will not work if the RLPItem is a list.
     *
     * @param self The RLPItem.
     * @return The decoded bool. Is true if the item is 1.
     */
    function toBool(RLPItem memory self) internal pure returns (bool) {
        if (!isData(self)) revert();
        uint256 rStartPos;
        uint256 len;
        (rStartPos, len) = _decode(self);
        if (len != 1) revert();
        uint256 temp;
        assembly {
            temp := byte(0, mload(rStartPos))
        }
        if (temp > 1) revert();
        return temp == 1 ? true : false;
    }

    /**
     * @dev Decode an RLPItem into a byte. This will not work if the RLPItem is a list.
     * The RLP Item's data must be 1 byte long.
     *
     * @param self The RLPItem.
     * @return The decoded  byte.
     */
    //    function toByte(RLPItem memory self) internal pure returns (byte) {
    //        if(!isData(self))
    //            revert();
    //        uint rStartPos;
    //        uint len;
    //        (rStartPos, len) = _decode(self);
    //        if (len != 1)
    //            revert();
    //        byte temp;
    //        assembly {
    //            temp := byte(0, mload(rStartPos))
    //        }
    //        return temp;
    //    }

    /**
     * @dev Decode an RLPItem into an int. This will not work if the RLPItem is a list.
     *
     * @param self The RLPItem.
     * @return The decoded int.
     */
    function toInt(RLPItem memory self) internal pure returns (int256) {
        return int256(toUint(self));
    }

    /**
     * @dev Decode an RLPItem into a bytes32. This will not work if the RLPItem is a list.
     *
     * @param self The RLPItem.
     * @return The decoded bytes32.
     */
    function toBytes32(RLPItem memory self) internal pure returns (bytes32) {
        return bytes32(toUint(self));
    }

    /**
     * @dev Decode an RLPItem into an address. This will not work if the RLPItem is a list.
     *
     * @param self The RLPItem.
     * @return addr The decoded address.
     */
    function toAddress(RLPItem memory self)
        internal
        pure
        returns (address addr)
    {
        if (!isData(self)) revert();
        uint256 rStartPos;
        uint256 len;
        (rStartPos, len) = _decode(self);
        if (len != 20) revert();
        assembly {
            addr := div(mload(rStartPos), exp(256, 12))
        }
    }

    // Get the payload offset.
    function _payloadOffset(RLPItem memory self)
        private
        pure
        returns (uint256)
    {
        if (self._unsafe_length == 0) return 0;
        uint256 b0;
        uint256 memPtr = self._unsafe_memPtr;
        assembly {
            b0 := byte(0, mload(memPtr))
        }
        if (b0 < DATA_SHORT_START) return 0;
        if (
            b0 < DATA_LONG_START ||
            (b0 >= LIST_SHORT_START && b0 < LIST_LONG_START)
        ) return 1;
        if (b0 < LIST_SHORT_START) return b0 - DATA_LONG_OFFSET + 1;
        return b0 - LIST_LONG_OFFSET + 1;
    }

    // Get the full length of an RLP item.
    function _itemLength(uint256 memPtr) private pure returns (uint256 len) {
        uint256 b0;
        assembly {
            b0 := byte(0, mload(memPtr))
        }
        if (b0 < DATA_SHORT_START) len = 1;
        else if (b0 < DATA_LONG_START) len = b0 - DATA_SHORT_START + 1;
        else if (b0 < LIST_SHORT_START) {
            assembly {
                let bLen := sub(b0, 0xB7) // bytes length (DATA_LONG_OFFSET)
                let dLen := div(mload(add(memPtr, 1)), exp(256, sub(32, bLen))) // data length
                len := add(1, add(bLen, dLen)) // total length
            }
        } else if (b0 < LIST_LONG_START) len = b0 - LIST_SHORT_START + 1;
        else {
            assembly {
                let bLen := sub(b0, 0xF7) // bytes length (LIST_LONG_OFFSET)
                let dLen := div(mload(add(memPtr, 1)), exp(256, sub(32, bLen))) // data length
                len := add(1, add(bLen, dLen)) // total length
            }
        }
    }

    // Get start position and length of the data.
    function _decode(RLPItem memory self)
        private
        pure
        returns (uint256 memPtr, uint256 len)
    {
        if (!isData(self)) revert();
        uint256 b0;
        uint256 start = self._unsafe_memPtr;
        assembly {
            b0 := byte(0, mload(start))
        }
        if (b0 < DATA_SHORT_START) {
            memPtr = start;
            len = 1;
            return (memPtr, len);
        }
        if (b0 < DATA_LONG_START) {
            len = self._unsafe_length - 1;
            memPtr = start + 1;
        } else {
            uint256 bLen;
            assembly {
                bLen := sub(b0, 0xB7) // DATA_LONG_OFFSET
            }
            len = self._unsafe_length - 1 - bLen;
            memPtr = start + bLen + 1;
        }
        return (memPtr, len);
    }

    // Assumes that enough memory has been allocated to store in target.
    function _copyToBytes(
        uint256 btsPtr,
        bytes memory tgt,
        uint256 btsLen
    ) private pure {
        // Exploiting the fact that 'tgt' was the last thing to be allocated,
        // we can write entire words, and just overwrite any excess.
        assembly {
            {
                let words := div(add(btsLen, 31), 32)
                let rOffset := btsPtr
                let wOffset := add(tgt, 0x20)
                for {
                    let i := 0
                } lt(i, words) {
                    // Start at arr + 0x20
                    i := add(i, 1)
                } {
                    let offset := mul(i, 0x20)
                    mstore(add(wOffset, offset), mload(add(rOffset, offset)))
                }
                mstore(add(tgt, add(0x20, mload(tgt))), 0)
            }
        }
    }

    // Check that an RLP item is valid.
    function _validate(RLPItem memory self) private pure returns (bool ret) {
        // Check that RLP is well-formed.
        uint256 b0;
        uint256 b1;
        uint256 memPtr = self._unsafe_memPtr;
        assembly {
            b0 := byte(0, mload(memPtr))
            b1 := byte(1, mload(memPtr))
        }
        if (b0 == DATA_SHORT_START + 1 && b1 < DATA_SHORT_START) return false;
        return true;
    }
}
