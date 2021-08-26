/*
 * Copyright 2019 ConsenSys Software Inc
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

import "../../../../registrar/src/main/solidity/Registrar.sol";
import "./CrosschainVerifier.sol";
import "../../../../rlp/src/main/solidity/RLP.sol";
import "../../../../common/src/main/solidity/BytesUtil.sol";

contract CrosschainVerifierSign is CrosschainVerifier,  RLP, BytesUtil{
    Registrar registrar;

    constructor(address _registrar) {
        registrar = Registrar(_registrar);
    }


    function decodeAndVerifyEvent(uint256 _expectedBlockchainId, bytes32 _expectedEventSignature,
            bytes calldata _signedEventInfo, bytes calldata _signature)
        external view override returns(EventInfo memory _eventInfo) {

        RLP.RLPItem[] memory signedEventInfo = RLP.toList(RLP.toRLPItem(_signedEventInfo));
        EventInfo memory eventInfo;
        uint256 decodedBlockchainId = BytesUtil.bytesToUint256(RLP.toData(signedEventInfo[0]), 0);
        require(_expectedBlockchainId == decodedBlockchainId, "Event not emitted by expected blockchain");
        eventInfo.cbcContract = BytesUtil.bytesToAddress(RLP.toData(signedEventInfo[1]));
        bytes32 decodedEventSignature = BytesUtil.bytesToBytes32(RLP.toData(signedEventInfo[2]), 0);
        require(_expectedEventSignature == decodedEventSignature, "Event does not have expected event signature");
        eventInfo.eventData = RLP.toData(signedEventInfo[3]);

        verifyEventSignature(_expectedBlockchainId, _signedEventInfo, _signature);
        registrar.verifyContract(_expectedBlockchainId, eventInfo.cbcContract);
        return(eventInfo);
    }



    function verifyEventSignature(uint256 _blockchainId, bytes memory _eventInfo, bytes memory _signature) private view {
        // The code is arranged a little unusually to reduce the number of in-scope local variables so code will compile.
        address[] memory signers;
        bytes32[] memory sigRs;
        bytes32[] memory sigSs;
        uint8[] memory sigVs;

        {
            RLP.RLPItem[] memory signature = RLP.toList(RLP.toRLPItem(_signature));
            RLP.RLPItem[] memory signersRlp = RLP.toList(signature[0]);
            uint256 length = signersRlp.length;

            signers = new address[](length);
            RLP.RLPItem[] memory sigRsRlp = RLP.toList(signature[1]);
            sigRs = new bytes32[](length);
            RLP.RLPItem[] memory sigSsRlp = RLP.toList(signature[2]);
            sigSs = new bytes32[](length);
            RLP.RLPItem[] memory sigVsRlp = RLP.toList(signature[3]);
            sigVs = new uint8[](length);

            require(length == sigRs.length, "Length of sigR and signers does not match");
            require(length == sigSs.length, "Length of sigS and signers does not match");
            require(length == sigVs.length, "Length of sigV and signers does not match");

            for (uint256 i = 0; i < length; i++) {
                signers[i] = BytesUtil.bytesToAddress(RLP.toData(signersRlp[i]));
                sigRs[i] = BytesUtil.bytesToBytes32(RLP.toData(sigRsRlp[i]), 0);
                sigSs[i] = BytesUtil.bytesToBytes32(RLP.toData(sigSsRlp[i]), 0);
                sigVs[i] = BytesUtil.bytesToUint8(RLP.toData(sigVsRlp[i]), 0);
            }
        }

        registrar.verify(_blockchainId, signers, sigRs, sigSs, sigVs, _eventInfo);
    }
}