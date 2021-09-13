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
package net.consensys.gpact.examples.singlebc.hoteltrain;

import net.consensys.gpact.common.FastTxManager;
import net.consensys.gpact.common.RevertReason;
import net.consensys.gpact.common.StatsHolder;
import net.consensys.gpact.common.TxManagerCache;
import net.consensys.gpact.examples.singlebc.hoteltrain.soliditywrappers.TravelAgency;
import net.consensys.gpact.openzeppelin.soliditywrappers.ERC20PresetFixedSupply;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;
import org.web3j.tx.response.TransactionReceiptProcessor;

import java.io.IOException;
import java.math.BigInteger;


/**
 * TODO
 */
public class EntityTravelAgency extends AbstractBlockchain {
    private static final Logger LOG = LogManager.getLogger(EntityTravelAgency.class);

    private BigInteger notRandom = BigInteger.ONE;
    TravelAgency travelAgency;
    private static final String PKEY = "40000000000000000000000000000000000000000000000000000000003";


    public EntityTravelAgency(String bcId, String uri, String gasPriceStrategy, String blockPeriod) throws IOException {
        super(Credentials.create(PKEY), bcId, uri, gasPriceStrategy, blockPeriod);
    }

    public void deploy(final BigInteger hotelBcId, final String hotelContractAddress,
                       final BigInteger trainBcId, final String trainContractAddress) throws Exception {
        LOG.info(" Deploying travel agency contract");
        this.travelAgency = TravelAgency.deploy(this.web3j, this.tm, this.gasProvider,
                hotelBcId, hotelContractAddress, trainBcId, trainContractAddress).send();
        LOG.info("  Travel Agency Contract deployed on blockchain {}, at address: {}",
            this.blockchainId, this.travelAgency.getContractAddress());
    }

    public BigInteger book(final int date) throws Exception {
        LOG.info(" Book for date: {} ", date);
        BigInteger uniqueBookingId = this.notRandom;
        this.notRandom = this.notRandom.add(BigInteger.ONE);

        try {
            TransactionReceipt txR = this.travelAgency.bookHotelAndTrain(BigInteger.valueOf(date), uniqueBookingId).send();
            StatsHolder.logGas("bookHotelAndTrain", txR.getGasUsed());
            return uniqueBookingId;
        } catch (TransactionException ex) {
            System.err.println(" Revert Reason: " + RevertReason.decodeRevertReason(ex.getTransactionReceipt().get().getRevertReason()));
            throw ex;
        }
    }

    public void grantAllowance(EntityBase entity, int amount) throws Exception {
        TransactionReceiptProcessor txrProcessor = new PollingTransactionReceiptProcessor(entity.web3j, this.pollingInterval, RETRY);
        FastTxManager atm = TxManagerCache.getOrCreate(entity.web3j, this.credentials, entity.getBlockchainId().longValue(), txrProcessor);
        ERC20PresetFixedSupply erc20 = ERC20PresetFixedSupply.load(entity.getErc20ContractAddress(), entity.web3j, atm, entity.gasProvider);
        erc20.increaseAllowance(entity.getHotelContractAddress(), BigInteger.valueOf(amount)).send();
        LOG.info(" Increased allowance of {} contract for account {} by {}", entity.entity, this.credentials.getAddress(), amount);
    }

    public String getTravelAgencyAccount() {
        return this.credentials.getAddress();
    }
    public String getTravelContractAddress() {
        return this.travelAgency.getContractAddress();
    }
}
