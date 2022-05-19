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
import java.util.List;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.common.DynamicGasProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;
import org.web3j.tuples.generated.Tuple3;

public class EntityTrain extends EntityBase {
  private static final Logger LOG = LogManager.getLogger(EntityTrain.class);

  private static final String NAME = "train";

  public static final BigInteger STANDARD_RATE = BigInteger.valueOf(9);
  public static final BigInteger NUM_SEATS = BigInteger.valueOf(4);
  private static final String PKEY = "40000000000000000000000000000000000000000000000000000000002";

  private Train trainContract;

  public EntityTrain(
      BlockchainId bcId, String uri, DynamicGasProvider.Strategy gasPriceStrategy, int blockPeriod)
      throws IOException {
    super(NAME, Credentials.create(PKEY), bcId, uri, gasPriceStrategy, blockPeriod);
  }

  public void deployContracts(String cbcAddress) throws Exception {
    String erc20Address = deployERC20Contract(cbcAddress);

    LOG.info(" Deploy {} contract", this.entity);
    this.trainContract =
        Train.deploy(this.web3j, this.tm, this.gasProvider, erc20Address, cbcAddress).send();
  }

  public String getTrainContractAddress() {
    return this.trainContract.getContractAddress();
  }

  public void addSeats() throws Exception {
    LOG.info("Adding {} seats at rate {}", NUM_SEATS, STANDARD_RATE);
    this.trainContract.addSeats(STANDARD_RATE, NUM_SEATS).send();
  }

  public void addTravelAgency(
      BlockchainId travelAgencyBcId, String travelAgencyContractAddress, String tokenHoldingAccount)
      throws Exception {
    this.trainContract
        .addApprovedTravelAgency(
            travelAgencyBcId.asBigInt(), travelAgencyContractAddress, tokenHoldingAccount)
        .send();
  }

  public void showBookingInformation(BigInteger bookingId) throws Exception {
    Tuple3<BigInteger, BigInteger, BigInteger> retVal =
        this.trainContract.getBookingInformation(bookingId).send();
    BigInteger amountPaid = retVal.component1();
    BigInteger seatId = retVal.component2();
    BigInteger date = retVal.component3();

    LOG.info(" {} Booking: Date: {}, Seat: {}, Amount: {}", this.entity, date, seatId, amountPaid);
  }

  public void showBookings(int date) throws Exception {
    LOG.info(" Train Bookings for date: {}", date);
    List<String> trainBookings = this.trainContract.getBookings(BigInteger.valueOf(date)).send();
    for (String booking : trainBookings) {
      LOG.info("  Seat booked for {}", booking);
    }
  }
}
