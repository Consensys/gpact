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

import net.consensys.gpact.cbc.CbcManager;
import net.consensys.gpact.common.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.exceptions.TransactionException;

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
        StatsHolder.log("Example: Single Blockchain: Read");
        LOG.info("Started");

        if (args.length != 1) {
            LOG.info("Usage: [properties file name]");
            return;
        }

        PropertiesLoader propsLoader = new PropertiesLoader(args[0]);
        Credentials creds = propsLoader.getCredentials();
        PropertiesLoader.BlockchainInfo root = propsLoader.getBlockchainInfo("ROOT");
        PropertiesLoader.BlockchainInfo bc2 = propsLoader.getBlockchainInfo("BC2");
        PropertiesLoader.BlockchainInfo bc3 = propsLoader.getBlockchainInfo("BC3");
        CrossBlockchainConsensusType consensusMethodology = propsLoader.getConsensusMethodology();
        StatsHolder.log(consensusMethodology.name());
        ExecutionEngineType engineType = propsLoader.getExecutionEnngine();
        StatsHolder.log(engineType.name());

        // Set-up GPACT contracts: Deploy Crosschain Control and Registrar contracts on
        // each blockchain.
        CbcManager cbcManager = new CbcManager(consensusMethodology);
        cbcManager.addBlockchainAndDeployContracts(creds, root);
        cbcManager.addBlockchainAndDeployContracts(creds, bc2);
        cbcManager.addBlockchainAndDeployContracts(creds, bc3);
        // Have each Crosschain Control contract trust the Crosschain Control
        // contracts on the other blockchains.
        cbcManager.setupCrosschainTrust();
        // To keep the example simple, just have one signer for all blockchains.
        cbcManager.registerSignerOnAllBlockchains(new AnIdentity());


        // Set-up classes to manage blockchains.
        EntityHotel hotel = new EntityHotel(bc2.bcId, bc2.uri, bc2.gasPriceStrategy, bc2.period);
        EntityTrain train = new EntityTrain(bc3.bcId, bc3.uri, bc3.gasPriceStrategy, bc3.period);
        EntityTravelAgency travelAgency = new EntityTravelAgency(creds, root.bcId, root.uri, root.gasPriceStrategy, root.period);

        // Deploy application contracts.
        BigInteger hotelBcId = hotel.getBlockchainId();
        hotel.deployContracts(cbcManager.getCbcAddress(hotelBcId));
        BigInteger trainBcId = hotel.getBlockchainId();
        train.deployContracts(cbcManager.getCbcAddress(trainBcId));
        BigInteger travelBcId = travelAgency.getBlockchainId();
        travelAgency.deploy(cbcManager.getCbcAddress(travelBcId), hotel.getBlockchainId(), hotel.getHotelContractAddress(), train.getBlockchainId(), train.getHotelContractAddress());

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

            BigInteger bookingId = travelAgency.book(date, consensusMethodology,engineType, cbcManager);

            hotel.showBookingInformation(bookingId);
            train.showBookingInformation(bookingId);
            hotel.showErc20Balances(accounts);
            train.showErc20Balances(accounts);
            hotel.showErc20Allowance(travelAgency.getTravelAgencyAccount(), hotel.getHotelContractAddress());
            train.showErc20Allowance(travelAgency.getTravelAgencyAccount(), train.getHotelContractAddress());

            travelAgency.book(date, consensusMethodology,engineType, cbcManager);
            travelAgency.book(date, consensusMethodology,engineType, cbcManager);

            hotel.showErc20Balances(accounts);
            train.showErc20Balances(accounts);
            hotel.showErc20Allowance(travelAgency.getTravelAgencyAccount(), hotel.getHotelContractAddress());
            train.showErc20Allowance(travelAgency.getTravelAgencyAccount(), train.getHotelContractAddress());
            try {
                travelAgency.book(date, consensusMethodology,engineType, cbcManager);
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
