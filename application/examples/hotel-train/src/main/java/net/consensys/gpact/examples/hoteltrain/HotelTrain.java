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

import net.consensys.gpact.cbc.CrossControlManagerGroup;
import net.consensys.gpact.common.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;

/**
 * Main Class for sample code.
 *
 * Book a hotel room and a train seat atomically.
 */
public class HotelTrain {
    private static final Logger LOG = LogManager.getLogger(HotelTrain.class);

    static int NUM_TIMES_EXECUTE = 1;

    public static void main(String[] args) throws Exception {
        StatsHolder.log("Example: Single Blockchain: Hotel Train");
        LOG.info("Started");

        if (args.length != 1) {
            LOG.info("Usage: [properties file name]");
            return;
        }

        ExampleSystemManager exampleManager = new ExampleSystemManager(args[0]);
        exampleManager.standardExampleConfig(3);

        BlockchainInfo root = exampleManager.getRootBcInfo();
        BlockchainInfo bc2 = exampleManager.getBc2Info();
        BlockchainInfo bc3 = exampleManager.getBc3Info();
        CrossControlManagerGroup crossControlManagerGroup = exampleManager.getCrossControlManagerGroup();

        // Set-up classes to manage blockchains.
        EntityHotel hotel = new EntityHotel(bc2.bcId, bc2.uri, bc2.gasPriceStrategy, bc2.period);
        EntityTrain train = new EntityTrain(bc3.bcId, bc3.uri, bc3.gasPriceStrategy, bc3.period);
        EntityTravelAgency travelAgency = new EntityTravelAgency(root.bcId, root.uri, root.gasPriceStrategy, root.period);

        // Deploy application contracts.
        BlockchainId hotelBcId = hotel.getBlockchainId();
        hotel.deployContracts(crossControlManagerGroup.getCbcAddress(hotelBcId));
        BlockchainId trainBcId = train.getBlockchainId();
        train.deployContracts(crossControlManagerGroup.getCbcAddress(trainBcId));
        BlockchainId travelBcId = travelAgency.getBlockchainId();
        travelAgency.deploy(crossControlManagerGroup.getCbcAddress(travelBcId), hotel.getBlockchainId(), hotel.getHotelContractAddress(), train.getBlockchainId(), train.getHotelContractAddress());

        travelAgency.createCbcManager(
                root, crossControlManagerGroup.getInfrastructureAddresses(travelBcId), crossControlManagerGroup.getMessageVerification(travelBcId),
                bc2, crossControlManagerGroup.getInfrastructureAddresses(hotelBcId), crossControlManagerGroup.getMessageVerification(hotelBcId),
                bc3, crossControlManagerGroup.getInfrastructureAddresses(trainBcId), crossControlManagerGroup.getMessageVerification(trainBcId));


        // Set-up application contracts.
        hotel.addRooms();
        hotel.addTravelAgency(travelBcId, travelAgency.getTravelContractAddress(), travelAgency.getTravelAgencyAccount());

        train.addSeats();
        train.addTravelAgency(travelBcId, travelAgency.getTravelContractAddress(), travelAgency.getTravelAgencyAccount());

        // Give tokens to the travel agency, and have it authorise the tokens to be spent
        // by the hotel and train contract.
        hotel.buyTokens(travelAgency.getTravelAgencyAccount(), 200);
        travelAgency.grantAllowance(hotel, 200);
        train.buyTokens(travelAgency.getTravelAgencyAccount(), 200);
        travelAgency.grantAllowance(train, 200);

        int date = 1;

        String[] accounts = new String[]{travelAgency.getTravelAgencyAccount(), hotel.getHotelContractAddress(), train.getHotelContractAddress()};

        hotel.showErc20Balances(accounts);
        train.showErc20Balances(accounts);
        hotel.showErc20Allowance(travelAgency.getTravelAgencyAccount(), hotel.getHotelContractAddress());
        train.showErc20Allowance(travelAgency.getTravelAgencyAccount(), train.getHotelContractAddress());


        for (int numExecutions = 0; numExecutions <= NUM_TIMES_EXECUTE; numExecutions++) {
            LOG.info("Execution: {}  *****************", numExecutions);
            StatsHolder.log("Execution: " + numExecutions + " **************************");

            BigInteger bookingId = travelAgency.book(date, exampleManager);

            hotel.showBookingInformation(bookingId);
            train.showBookingInformation(bookingId);
            hotel.showErc20Balances(accounts);
            train.showErc20Balances(accounts);
            hotel.showErc20Allowance(travelAgency.getTravelAgencyAccount(), hotel.getHotelContractAddress());
            train.showErc20Allowance(travelAgency.getTravelAgencyAccount(), train.getHotelContractAddress());

            travelAgency.book(date, exampleManager);
            travelAgency.book(date, exampleManager);

            hotel.showErc20Balances(accounts);
            train.showErc20Balances(accounts);
            hotel.showErc20Allowance(travelAgency.getTravelAgencyAccount(), hotel.getHotelContractAddress());
            train.showErc20Allowance(travelAgency.getTravelAgencyAccount(), train.getHotelContractAddress());
            try {
                travelAgency.book(date, exampleManager);
                throw new Exception("Exception now thrown as expected");
            } catch (Exception ex) {
                LOG.info("Exception thrown as expected: not enough train seats");
            }

            hotel.showErc20Balances(accounts);
            train.showErc20Balances(accounts);
            hotel.showErc20Allowance(travelAgency.getTravelAgencyAccount(), hotel.getHotelContractAddress());
            train.showErc20Allowance(travelAgency.getTravelAgencyAccount(), train.getHotelContractAddress());

            date++;
        }


        hotel.shutdown();
        train.shutdown();
        travelAgency.shutdown();

        StatsHolder.log("End");
        StatsHolder.print();
    }



}
