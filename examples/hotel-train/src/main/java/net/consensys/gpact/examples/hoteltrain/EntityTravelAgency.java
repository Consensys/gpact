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
package net.consensys.gpact.examples.hoteltrain;

import net.consensys.gpact.appcontracts.erc20.soliditywrappers.LockableERC20PresetFixedSupply;
import net.consensys.gpact.cbc.AbstractBlockchain;
import net.consensys.gpact.cbc.CbcManager;
import net.consensys.gpact.cbc.engine.*;
import net.consensys.gpact.common.CrossBlockchainConsensusType;
import net.consensys.gpact.common.ExecutionEngineType;
import net.consensys.gpact.common.RevertReason;
import net.consensys.gpact.common.StatsHolder;
import net.consensys.gpact.examples.hoteltrain.soliditywrappers.TravelAgency;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpType;
import org.web3j.tx.RawTransactionManager;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static net.consensys.gpact.cbc.CallGraphHelper.*;


/**
 * TODO
 */
public class EntityTravelAgency extends AbstractBlockchain {
    private static final Logger LOG = LogManager.getLogger(EntityTravelAgency.class);

    private BigInteger notRandom = BigInteger.ONE;
    TravelAgency travelAgency;
    private static final String PKEY = "40000000000000000000000000000000000000000000000000000000003";

    Simulator sim = new Simulator();

    BigInteger hotelBcId;
    String hotelContractAddress;
    BigInteger trainBcId;
    String trainContractAddress;

    public EntityTravelAgency(Credentials creds, String bcId, String uri, String gasPriceStrategy, String blockPeriod) throws IOException {
        super(creds, bcId, uri, gasPriceStrategy, blockPeriod);
    }

    public void deploy(final String cbcAddress,
                       final BigInteger hotelBcId, final String hotelContractAddress,
                       final BigInteger trainBcId, final String trainContractAddress) throws Exception {
        LOG.info(" Deploying travel agency contract");
        this.travelAgency = TravelAgency.deploy(this.web3j, this.tm, this.gasProvider,
                hotelBcId, hotelContractAddress, trainBcId, trainContractAddress, cbcAddress).send();
        LOG.info("  Travel Agency Contract deployed on blockchain {}, at address: {}",
            this.blockchainId, this.travelAgency.getContractAddress());

        this.hotelBcId = hotelBcId;
        this.hotelContractAddress = hotelContractAddress;
        this.trainBcId = trainBcId;
        this.trainContractAddress = trainContractAddress;
    }

    public BigInteger book(final int dateInt,
            CrossBlockchainConsensusType consensusMethodology,
            ExecutionEngineType engineType,
            CbcManager cbcManager) throws Exception {
        LOG.info(" Book for date: {} ", dateInt);
        BigInteger date = BigInteger.valueOf(dateInt);
        BigInteger uniqueBookingId = this.notRandom;
        this.notRandom = this.notRandom.add(BigInteger.ONE);

        // Run simulator.
        sim.executeSimulateBookHotelAndTrain(date, uniqueBookingId);

        // Build the call execution tree.
        LOG.info("Function Calls");
        String rlpBookHotelAndTrain = sim.getBookHotelAndTrainRLP();
        LOG.info(" Travel Agency: bookHotelAndRTrain: {}", rlpBookHotelAndTrain);
        String rlpHotelBookRoom = sim.getHotelBookRoomRLP();
        LOG.info(" Hotel: bookRoom: {}", rlpHotelBookRoom);
        String rlpTrainBookRoom = sim.getTrainBookRoomRLP();
        LOG.info(" Train: bookRoom: {}", rlpTrainBookRoom);

        RlpList trainBookRoom = createLeafFunctionCall(trainBcId, trainContractAddress, rlpTrainBookRoom);
        RlpList hotelBookRoom = createLeafFunctionCall(hotelBcId, hotelContractAddress, rlpHotelBookRoom);
        List<RlpType> rootCalls = new ArrayList<>();
        rootCalls.add(hotelBookRoom);
        rootCalls.add(trainBookRoom);
        RlpList callTree = createRootFunctionCall(this.blockchainId, this.travelAgency.getContractAddress(), rlpBookHotelAndTrain, rootCalls);

        AbstractCbcExecutor executor;
        switch (consensusMethodology) {
            case TRANSACTION_RECEIPT_SIGNING:
                executor = new CbcExecutorTxReceiptRootTransfer(cbcManager);
                break;
            case EVENT_SIGNING:
                executor = new CbcExecutorSignedEvents(cbcManager);
                break;
            default:
                throw new RuntimeException("Not implemented yet");
        }

        ExecutionEngine executionEngine;
        switch (engineType) {
            case SERIAL:
                executionEngine = new SerialExecutionEngine(executor);
                break;
            case PARALLEL:
                executionEngine = new ParallelExecutionEngine(executor);
                break;
            default:
                throw new RuntimeException("Not implemented yet");
        }
        boolean success = executionEngine.execute(callTree, 300);

        LOG.info("Success: {}", success);

        if (!success) {
            throw new Exception("Crosschain Execution failed. See log for details");
        }

        return uniqueBookingId;
    }

    public void grantAllowance(EntityBase entity, int amount) throws Exception {
        RawTransactionManager atm = new RawTransactionManager(entity.web3j, this.credentials, entity.getBlockchainId().longValue(), RETRY, this.pollingInterval);
        LockableERC20PresetFixedSupply erc20 = LockableERC20PresetFixedSupply.load(entity.getErc20ContractAddress(), entity.web3j, atm, entity.gasProvider);
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
