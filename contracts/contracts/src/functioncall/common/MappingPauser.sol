/*
 * Copyright 2021 ConsenSys Software Inc
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

import "../../openzeppelin/access/AccessControl.sol";
import "../../openzeppelin/security/Pausable.sol";


/**
 * Common code for function call and application layer bridges.
 *
 * Defines abilities:
 * - Mapping between this contract on one blockchain and an instance of it on a remote blockchain.
 * - Pausing.
 */
abstract contract MappingPauser is Pausable, AccessControl {
    // In addition to DEFAULT_ADMIN_ROLE, the following roles are defined.
    bytes32 public constant BRIDGE_MAPPING_ROLE = keccak256("BRIDGE_MAPPING_ROLE");
    bytes32 public constant PAUSER_ROLE = keccak256("PAUSER_ROLE");


    // Addresses of bridges on other blockchains. These contracts could be
    // application bridges or function call crosschain control contracts,
    // depending on the usage.
    mapping(uint256 => address) internal remoteBridgeContracts;

    modifier onlyBridgeMapper() {
        require(
            hasRole(BRIDGE_MAPPING_ROLE, _msgSender()),
            "Must have BRIDGE MAPPING role"
        );
        _;
    }

    modifier onlyPauser() {
        require(
            hasRole(PAUSER_ROLE, _msgSender()),
            "Must have PAUSER role"
        );
        _;
    }


    constructor() {
        address sender = msg.sender;
        _setupRole(DEFAULT_ADMIN_ROLE, sender);
        _setupRole(BRIDGE_MAPPING_ROLE, sender);
        _setupRole(PAUSER_ROLE, sender);
    }

    /**
     * @dev Adds a mapping between the bridge contract on this chain and the bridge contract on a remote chain.
     * The bridge contract could be an application bridge or a function call layer crosschain control contract,
     * depending on the usage.
     * NOTE: caller must have the BRIDGE_MAPPING_ROLE.
     */
    function addRemoteBridge(uint256 _blockchainId, address _cbc)
        external onlyBridgeMapper {
        remoteBridgeContracts[_blockchainId] = _cbc;
    }

    /**
     * @dev Adds a mapping between the control contract on this chain and the control contract on a remote chain.
     * NOTE: caller must have the MAPPING_ROLE.
     */
    function removeRemoteCrosschainControl(uint256 _blockchainId)
        external onlyBridgeMapper {
        remoteBridgeContracts[_blockchainId] = address(0);
    }

    /**
     * @dev Pauses the bridge.
     * NOTE: caller must have the PAUSER_ROLE.
     */
    function pause() external onlyPauser {
        _pause();
    }

    /**
     * @dev Unpauses the bridge.
     * NOTE: caller must have the PAUSER_ROLE.
     */
    function unpause() external onlyPauser {
        _unpause();
    }
}
