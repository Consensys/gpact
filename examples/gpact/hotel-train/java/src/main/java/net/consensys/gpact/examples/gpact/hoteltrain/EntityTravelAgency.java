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
package net.consensys.gpact.examples.gpact.hoteltrain;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import net.consensys.gpact.common.*;
import net.consensys.gpact.common.AbstractBlockchain;
import net.consensys.gpact.functioncall.gpact.CrossControlManagerGroup;
import net.consensys.gpact.functioncall.gpact.CrosschainExecutor;
import net.consensys.gpact.functioncall.gpact.calltree.CallExecutionTree;
import net.consensys.gpact.functioncall.gpact.engine.ExecutionEngine;
import net.consensys.gpact.messaging.MessagingVerificationInterface;
import net.consensys.gpact.soliditywrappers.examples.gpact.hoteltrain.LockableERC20PresetFixedSupply;
import net.consensys.gpact.soliditywrappers.examples.gpact.hoteltrain.TravelAgency;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;
import org.web3j.tx.response.TransactionReceiptProcessor;

/** TODO */
public class EntityTravelAgency extends AbstractBlockchain {
  private static final Logger LOG = LogManager.getLogger(EntityTravelAgency.class);

  private BigInteger notRandom = BigInteger.ONE;
  String travelAgencyAddress;
  private static final String PKEY = "40000000000000000000000000000000000000000000000000000000003";

  Simulator sim = new Simulator();

  BlockchainId hotelBcId;
  String hotelContractAddress;
  BlockchainId trainBcId;
  String trainContractAddress;

  CrossControlManagerGroup crossControlManagerGroup;

  public EntityTravelAgency(
      BlockchainId bcId, String uri, DynamicGasProvider.Strategy gasPriceStrategy, int blockPeriod)
      throws IOException {
    super(Credentials.create(PKEY), bcId, uri, gasPriceStrategy, blockPeriod);
  }

  public void deploy(
      final String cbcAddress,
      final BlockchainId hotelBcId,
      final String hotelContractAddress,
      final BlockchainId trainBcId,
      final String trainContractAddress)
      throws Exception {
    LOG.info(" Deploying travel agency contract");
    TravelAgency travelAgency =
        TravelAgency.deploy(
                this.web3j,
                this.tm,
                this.gasProvider,
                hotelBcId.asBigInt(),
                hotelContractAddress,
                trainBcId.asBigInt(),
                trainContractAddress,
                cbcAddress)
            .send();
    this.travelAgencyAddress = travelAgency.getContractAddress();
    LOG.info(
        "  Travel Agency Contract deployed on blockchain {}, at address: {}",
        this.blockchainId,
        this.travelAgencyAddress);

    this.hotelBcId = hotelBcId;
    this.hotelContractAddress = hotelContractAddress;
    this.trainBcId = trainBcId;
    this.trainContractAddress = trainContractAddress;
  }

  public void createCbcManager(
      BlockchainInfo bcInfoTravel,
      List<String> infratructureContractAddressOnBcTravel,
      MessagingVerificationInterface msgVerTravel,
      BlockchainInfo bcInfoHotel,
      List<String> infrastructureContractAddressOnBcHotel,
      MessagingVerificationInterface msgVerHotel,
      BlockchainInfo bcInfoTrain,
      List<String> infrastructureContractAddressOnBcTrain,
      MessagingVerificationInterface msgVerTrain)
      throws Exception {
    this.crossControlManagerGroup = new CrossControlManagerGroup();
    this.crossControlManagerGroup.addBlockchainAndLoadContracts(
        this.credentials, bcInfoTravel, infratructureContractAddressOnBcTravel, msgVerTravel);
    this.crossControlManagerGroup.addBlockchainAndLoadContracts(
        this.credentials, bcInfoHotel, infrastructureContractAddressOnBcHotel, msgVerHotel);
    this.crossControlManagerGroup.addBlockchainAndLoadContracts(
        this.credentials, bcInfoTrain, infrastructureContractAddressOnBcTrain, msgVerTrain);
  }

  public BigInteger book(final int dateInt, GpactExampleSystemManager exampleManager)
      throws Exception {
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

    ArrayList<CallExecutionTree> rootCalls = new ArrayList<>();
    CallExecutionTree trainBookRoom =
        new CallExecutionTree(trainBcId, trainContractAddress, rlpTrainBookRoom);
    CallExecutionTree hotelBookRoom =
        new CallExecutionTree(hotelBcId, hotelContractAddress, rlpHotelBookRoom);
    rootCalls.add(hotelBookRoom);
    rootCalls.add(trainBookRoom);
    CallExecutionTree rootCall =
        new CallExecutionTree(
            this.blockchainId, this.travelAgencyAddress, rlpBookHotelAndTrain, rootCalls);

    CrosschainExecutor executor = new CrosschainExecutor(this.crossControlManagerGroup);
    ExecutionEngine executionEngine = exampleManager.getExecutionEngine(executor);
    boolean success = executionEngine.execute(rootCall, 300);

    LOG.info("Success: {}", success);

    if (!success) {
      throw new Exception("Crosschain Execution failed. See log for details");
    }

    return uniqueBookingId;
  }

  public void grantAllowance(EntityBase entity, int amount) throws Exception {
    TransactionReceiptProcessor txrProcessor =
        new PollingTransactionReceiptProcessor(entity.web3j, this.pollingInterval, RETRY);
    FastTxManager atm =
        TxManagerCache.getOrCreate(
            entity.web3j, this.credentials, entity.getBlockchainId().asLong(), txrProcessor);
    LockableERC20PresetFixedSupply erc20 =
        LockableERC20PresetFixedSupply.load(
            entity.getErc20ContractAddress(), entity.web3j, atm, entity.gasProvider);
    erc20.increaseAllowance(entity.getHotelContractAddress(), BigInteger.valueOf(amount)).send();
    LOG.info(
        " Increased allowance of {} contract for account {} by {}",
        entity.entity,
        this.credentials.getAddress(),
        amount);
  }

  public String getTravelAgencyAccount() {
    return this.credentials.getAddress();
  }

  public String getTravelContractAddress() {
    return this.travelAgencyAddress;
  }
}
