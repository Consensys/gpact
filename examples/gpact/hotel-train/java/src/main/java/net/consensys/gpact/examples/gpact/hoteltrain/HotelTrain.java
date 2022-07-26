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

import java.math.BigInteger;
import net.consensys.gpact.common.*;
import net.consensys.gpact.functioncall.CrossControlManagerGroup;
import net.consensys.gpact.helpers.GpactExampleBase;
import net.consensys.gpact.helpers.GpactExampleSystemManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Main Class for sample code.
 *
 * <p>Book a hotel room and a train seat atomically.
 */
public class HotelTrain extends GpactExampleBase {
  private static final Logger LOG = LogManager.getLogger(HotelTrain.class);

  static int NUM_TIMES_EXECUTE = 1;

  public static void main(String[] args) throws Exception {
    StatsHolder.log("Example: Single Blockchain: Hotel Train");
    LOG.info("Started");

    GpactExampleSystemManager exampleManager = getExampleSystemManager(args);
    exampleManager.standardExampleConfig(3);

    BlockchainConfig root = exampleManager.getRootBcInfo();
    BlockchainConfig bc2 = exampleManager.getBc2Info();
    BlockchainConfig bc3 = exampleManager.getBc3Info();
    CrossControlManagerGroup crossControlManagerGroup =
        exampleManager.getCrossControlManagerGroup();

    // Set-up classes to manage blockchains.
    EntityHotel hotel = new EntityHotel(bc2);
    EntityTrain train = new EntityTrain(bc3);
    EntityTravelAgency travelAgency = new EntityTravelAgency(root);

    // Deploy application contracts.
    BlockchainId hotelBcId = hotel.getBlockchainId();
    hotel.deployContracts(crossControlManagerGroup.getCbcAddress(hotelBcId));
    BlockchainId trainBcId = train.getBlockchainId();
    train.deployContracts(crossControlManagerGroup.getCbcAddress(trainBcId));
    BlockchainId travelBcId = travelAgency.getBlockchainId();
    travelAgency.deploy(
        crossControlManagerGroup.getCbcAddress(travelBcId),
        hotel.getBlockchainId(),
        hotel.getHotelContractAddress(),
        train.getBlockchainId(),
        train.getTrainContractAddress());

    travelAgency.createCbcManager(
        exampleManager.getFunctionCallImplName(),
        root,
        crossControlManagerGroup.getCbcAddress(travelBcId),
        crossControlManagerGroup.getMessageVerification(travelBcId),
        bc2,
        crossControlManagerGroup.getCbcAddress(hotelBcId),
        crossControlManagerGroup.getMessageVerification(hotelBcId),
        bc3,
        crossControlManagerGroup.getCbcAddress(trainBcId),
        crossControlManagerGroup.getMessageVerification(trainBcId));

    // Set-up application contracts.
    hotel.addRooms();
    hotel.addTravelAgency(
        travelBcId, travelAgency.getTravelContractAddress(), travelAgency.getTravelAgencyAccount());

    train.addSeats();
    train.addTravelAgency(
        travelBcId, travelAgency.getTravelContractAddress(), travelAgency.getTravelAgencyAccount());

    // Give tokens to the travel agency, and have it authorise the tokens to be spent
    // by the hotel and train contract.
    hotel.buyTokens(travelAgency.getTravelAgencyAccount(), 200);
    travelAgency.grantAllowance(hotel, 200, hotel.getHotelContractAddress());
    train.buyTokens(travelAgency.getTravelAgencyAccount(), 200);
    travelAgency.grantAllowance(train, 200, train.getTrainContractAddress());

    int date = 1;

    String[] accounts =
        new String[] {
          travelAgency.getTravelAgencyAccount(),
          hotel.getHotelContractAddress(),
          train.getTrainContractAddress()
        };

    hotel.showErc20Balances(accounts);
    train.showErc20Balances(accounts);
    hotel.showErc20Allowance(
        travelAgency.getTravelAgencyAccount(), hotel.getHotelContractAddress());
    train.showErc20Allowance(
        travelAgency.getTravelAgencyAccount(), train.getTrainContractAddress());

    for (int numExecutions = 0; numExecutions <= NUM_TIMES_EXECUTE; numExecutions++) {
      LOG.info("Execution: {}  *****************", numExecutions);
      StatsHolder.log("Execution: " + numExecutions + " **************************");

      BigInteger bookingId = travelAgency.book(date, exampleManager);

      hotel.showBookingInformation(bookingId);
      train.showBookingInformation(bookingId);
      hotel.showErc20Balances(accounts);
      train.showErc20Balances(accounts);
      hotel.showErc20Allowance(
          travelAgency.getTravelAgencyAccount(), hotel.getHotelContractAddress());
      train.showErc20Allowance(
          travelAgency.getTravelAgencyAccount(), train.getTrainContractAddress());

      travelAgency.book(date, exampleManager);
      travelAgency.book(date, exampleManager);

      hotel.showErc20Balances(accounts);
      train.showErc20Balances(accounts);
      hotel.showErc20Allowance(
          travelAgency.getTravelAgencyAccount(), hotel.getHotelContractAddress());
      train.showErc20Allowance(
          travelAgency.getTravelAgencyAccount(), train.getTrainContractAddress());
      try {
        travelAgency.book(date, exampleManager);
        throw new Exception("Exception now thrown as expected");
      } catch (Exception ex) {
        LOG.info("Exception thrown as expected: not enough train seats");
      }

      hotel.showErc20Balances(accounts);
      train.showErc20Balances(accounts);
      hotel.showErc20Allowance(
          travelAgency.getTravelAgencyAccount(), hotel.getHotelContractAddress());
      train.showErc20Allowance(
          travelAgency.getTravelAgencyAccount(), train.getTrainContractAddress());

      date++;
    }

    hotel.shutdown();
    train.shutdown();
    travelAgency.shutdown();

    StatsHolder.log("End");
    StatsHolder.print();
  }
}
