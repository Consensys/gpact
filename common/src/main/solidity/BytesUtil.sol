/*
 * Copyright 2020 ConsenSys AG.
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
pragma solidity >=0.7.1;

abstract contract BytesUtil {

    // Based on stack overflow here: https://ethereum.stackexchange.com/questions/15350/how-to-convert-an-bytes-to-address-in-solidity
    function bytesToAddress1(bytes memory _b, uint256 _startOffset) internal pure returns (address addr) {
        assembly {
            addr := mload(add(_b, add(32, _startOffset)))
        }
    }

    function bytesToAddress(bytes memory _b) internal pure returns (address addr) {
        assembly {
            addr := mload(add(_b, 20))
        }
    }


    // TODO find something faster than this.
    // From stack overflow here: https://ethereum.stackexchange.com/questions/7702/how-to-convert-byte-array-to-bytes32-in-solidity
    function bytesToBytes32CallData(bytes calldata b, uint offset) internal pure returns (bytes32) {
        bytes32 out;

        for (uint i = 0; i < 32; i++) {
            out |= bytes32(b[offset + i] & 0xFF) >> (i * 8);
        }
        return out;
    }
    function bytesToBytes32(bytes memory b, uint offset) internal pure returns (bytes32) {
        bytes32 out;

        for (uint i = 0; i < 32; i++) {
            out |= bytes32(b[offset + i] & 0xFF) >> (i * 8);
        }
        return out;
    }

    // Starting point was this, but with some modifications.
    // https://ethereum.stackexchange.com/questions/49185/solidity-conversion-bytes-memory-to-uint
    function bytesToUint256(bytes memory _b, uint256 _startOffset) internal pure returns (uint256) {
        require(_b.length >= _startOffset + 32, "slicing out of range (uint256)");
        uint256 x;
        assembly {
            x := mload(add(_b, add(32, _startOffset)))
        }
        return x;
    }

    function bytesToUint64(bytes memory _b, uint256 _startOffset) internal pure returns (uint64) {
        require(_b.length >= _startOffset + 8, "slicing out of range (uint64)");
        uint256 x;
        assembly {
            x := mload(add(_b, add(8, _startOffset)))
        }
        return uint64(x);
    }

    function bytesToUint8(bytes memory _b, uint256 _startOffset) internal pure returns (uint8) {
        require(_b.length >= _startOffset + 1, "slicing out of range (uint8)");
        uint256 x;
        assembly {
            x := mload(add(_b, add(1, _startOffset)))
        }
        return uint8(x);
    }

    // From https://github.com/GNSPS/solidity-bytes-utils/blob/master/contracts/BytesLib.sol
    function sliceAsm(bytes memory _bytes, uint256 _start, uint256 _length) internal pure returns (bytes memory) {
        require(_bytes.length >= (_start + _length), "Read out of bounds");

        bytes memory tempBytes;

        assembly {
            switch iszero(_length)
            case 0 {
            // Get a location of some free memory and store it in tempBytes as
            // Solidity does for memory variables.
            tempBytes := mload(0x40)

            // The first word of the slice result is potentially a partial
            // word read from the original array. To read it, we calculate
            // the length of that partial word and start copying that many
            // bytes into the array. The first word we copy will start with
            // data we don't care about, but the last `lengthmod` bytes will
            // land at the beginning of the contents of the new array. When
            // we're done copying, we overwrite the full first word with
            // the actual length of the slice.
            let lengthmod := and(_length, 31)

            // The multiplication in the next line is necessary
            // because when slicing multiples of 32 bytes (lengthmod == 0)
            // the following copy loop was copying the origin's length
            // and then ending prematurely not copying everything it should.
            let mc := add(add(tempBytes, lengthmod), mul(0x20, iszero(lengthmod)))
            let end := add(mc, _length)

                for {
                // The multiplication in the next line has the same exact purpose
                // as the one above.
                    let cc := add(add(add(_bytes, lengthmod), mul(0x20, iszero(lengthmod))), _start)
                } lt(mc, end) {
                    mc := add(mc, 0x20)
                    cc := add(cc, 0x20)
                } {
                    mstore(mc, mload(cc))
                }

                mstore(tempBytes, _length)

            //update free-memory pointer
            //allocating the array padded to 32 bytes like the compiler does now
                mstore(0x40, and(add(mc, 31), not(31)))
            }
            //if we want a zero-length slice let's just return a zero-length array
            default {
                tempBytes := mload(0x40)

                mstore(0x40, add(tempBytes, 0x20))
            }
        }

        return tempBytes;
    }

    // https://ethereum.stackexchange.com/questions/78559/how-can-i-slice-bytes-strings-and-arrays-in-solidity
    function slice(bytes memory _bytes, uint256 _start, uint256 _length) internal pure returns (bytes memory) {
        bytes memory a = new bytes(_length);
        for(uint i=0; i < _length; i++){
            a[i] = _bytes[_start+i];
        }
        return a;
    }

    function slice(bytes memory _bytes, uint256 _start) internal pure returns (bytes memory) {
        return slice(_bytes, _start, (_bytes.length - _start));
    }

    function compare(bytes memory _a, bytes memory _b) internal pure returns (bool) {
        if(_a.length != _b.length) {
            return false;
        } else {
            return keccak256(_a) == keccak256(_b);
        }
    }
}
