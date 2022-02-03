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
package net.consensys.gpact.performance.singlebc.hoteltrain;

import java.math.BigInteger;
import net.consensys.gpact.common.BlockchainConfig;
import net.consensys.gpact.common.StatsHolder;
import net.consensys.gpact.helpers.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.protocol.exceptions.TransactionException;

/**
 * Main Class for sample code.
 *
 * <p>Book a hotel room and a train seat atomically.
 */
public class HotelTrain {
  private static final Logger LOG = LogManager.getLogger(HotelTrain.class);

  static int NUM_TIMES_EXECUTE = 3;

  public static void main(String[] args) throws Exception {
    StatsHolder.log("Example: Single Blockchain: Read");
    LOG.info("Started");

    if (args.length != 1) {
      LOG.info("Usage: [properties file name]");
      return;
    }

    PropertiesLoader propsLoader = new PropertiesLoader(args[0]);
    BlockchainConfig root = propsLoader.getBlockchainInfo("ROOT");

    EntityHotel hotel = new EntityHotel(root.bcId, root.uri, root.gasPriceStrategy, root.period);
    hotel.deployContracts();
    hotel.addRooms();
    EntityTrain train = new EntityTrain(root.bcId, root.uri, root.gasPriceStrategy, root.period);
    train.deployContracts();
    train.addSeats();

    EntityTravelAgency travelAgency =
        new EntityTravelAgency(root.bcId, root.uri, root.gasPriceStrategy, root.period);
    travelAgency.deploy(
        hotel.getBlockchainId(),
        hotel.getHotelContractAddress(),
        train.getBlockchainId(),
        train.getHotelContractAddress());

    hotel.buyTokens(travelAgency.getTravelAgencyAccount(), 1000);
    travelAgency.grantAllowance(hotel, 1000);
    hotel.addTravelAgency(
        travelAgency.getTravelContractAddress(), travelAgency.getTravelAgencyAccount());
    train.buyTokens(travelAgency.getTravelAgencyAccount(), 1000);
    travelAgency.grantAllowance(train, 1000);
    train.addTravelAgency(
        travelAgency.getTravelContractAddress(), travelAgency.getTravelAgencyAccount());

    int date = 1;

    String[] accounts =
        new String[] {
          travelAgency.getTravelAgencyAccount(),
          hotel.getHotelContractAddress(),
          train.getHotelContractAddress()
        };

    for (int numExecutions = 0; numExecutions < NUM_TIMES_EXECUTE; numExecutions++) {
      LOG.info("Execution: {}  *****************", numExecutions);
      StatsHolder.log("Execution: " + numExecutions + " **************************");

      hotel.showErc20Balances(accounts);
      train.showErc20Balances(accounts);
      hotel.showErc20Allowance(
          travelAgency.getTravelAgencyAccount(), hotel.getHotelContractAddress());
      train.showErc20Allowance(
          travelAgency.getTravelAgencyAccount(), train.getHotelContractAddress());
      // TODO show availability
      travelAgency.book(date);

      hotel.showErc20Balances(accounts);
      train.showErc20Balances(accounts);
      hotel.showErc20Allowance(
          travelAgency.getTravelAgencyAccount(), hotel.getHotelContractAddress());
      train.showErc20Allowance(
          travelAgency.getTravelAgencyAccount(), train.getHotelContractAddress());
      travelAgency.book(date);

      hotel.showErc20Balances(accounts);
      train.showErc20Balances(accounts);
      hotel.showErc20Allowance(
          travelAgency.getTravelAgencyAccount(), hotel.getHotelContractAddress());
      train.showErc20Allowance(
          travelAgency.getTravelAgencyAccount(), train.getHotelContractAddress());
      travelAgency.book(date);

      hotel.showErc20Balances(accounts);
      train.showErc20Balances(accounts);
      hotel.showErc20Allowance(
          travelAgency.getTravelAgencyAccount(), hotel.getHotelContractAddress());
      train.showErc20Allowance(
          travelAgency.getTravelAgencyAccount(), train.getHotelContractAddress());
      try {
        travelAgency.book(date);
        throw new Exception("Exception now thrown as expected");
      } catch (TransactionException ex) {
        LOG.info("Exception thrown as expected: not enough train seats");
      }

      hotel.showErc20Balances(accounts);
      train.showErc20Balances(accounts);
      hotel.showErc20Allowance(
          travelAgency.getTravelAgencyAccount(), hotel.getHotelContractAddress());
      train.showErc20Allowance(
          travelAgency.getTravelAgencyAccount(), train.getHotelContractAddress());

      date++;
      String spender = travelAgency.getTravelAgencyAccount();
      BigInteger bookingId = travelAgency.separatedBookHotelAndTrain(date);
      train.separatedBook(date, bookingId, 15, spender);
      hotel.separatedBook(date, bookingId, 15, spender);

      date++;
    }

    hotel.shutdown();
    train.shutdown();
    travelAgency.shutdown();

    StatsHolder.log("End");
    StatsHolder.print();
  }
}
