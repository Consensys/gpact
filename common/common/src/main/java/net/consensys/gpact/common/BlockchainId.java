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
package net.consensys.gpact.common;

import org.apache.tuweni.units.bigints.UInt256;

import java.math.BigInteger;

public class BlockchainId {
    private BigInteger bcId;

    public BlockchainId(String blockchainId) {
        this.bcId = new BigInteger(blockchainId, 16);
    }

    public BlockchainId(BigInteger blockchainId) {
        this.bcId = blockchainId;
    }

    public BigInteger asBigInt() {
        return this.bcId;
    }

    public long asLong() {
        return this.bcId.longValue();
    }

    public byte[] asBytes() {
        UInt256 blockchainIdUint256 = UInt256.valueOf(this.bcId);
        return blockchainIdUint256.toBytes().toArray();
    }

    public String toPlainString() {
        return this.bcId.toString(16);
    }


    @Override
    public String toString() {
        return "0x" + this.bcId.toString(16);
    }

    @Override
    public boolean equals(Object other) {
        if (! (other instanceof BlockchainId)) {
            return false;
        }
        return ((BlockchainId) other).bcId.compareTo(this.bcId) == 0;
    }

    @Override
    public int hashCode() {
        return this.bcId.hashCode();
    }

    @Override
    public Object clone() {
        return new BlockchainId(this.bcId);
    }

}
