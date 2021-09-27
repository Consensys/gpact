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
import net.consensys.gpact.common.AbstractBlockchain;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.examples.hoteltrain.soliditywrappers.Hotel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;
import org.web3j.tuples.generated.Tuple3;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;


public class EntityBase extends AbstractBlockchain {
    private static final Logger LOG = LogManager.getLogger(EntityBase.class);

    // Total number of tokens issued for booking.
    public static final BigInteger TOKEN_SUPPLY = BigInteger.valueOf(1000);

    LockableERC20PresetFixedSupply erc20;
    Hotel hotelContract;
    public String entity;


    public EntityBase(final String entity,
                      Credentials credentials, BlockchainId bcId, String uri, String gasPriceStrategy, String blockPeriod) throws IOException {
        super(credentials, bcId, uri, gasPriceStrategy, blockPeriod);
        this.entity = entity;
    }

    public void deployContracts(String cbcAddress) throws Exception {
        LOG.info(" Deploy ERC20 contract for {}", this.entity);
        LOG.info(" Setting total supply as {} tokens", TOKEN_SUPPLY);
        String name = "ABC";
        String symbol= "ABC";
        String owner = this.credentials.getAddress();
        this.erc20 = LockableERC20PresetFixedSupply.deploy(this.web3j, this.tm, this.gasProvider, name, symbol, cbcAddress, TOKEN_SUPPLY, owner).send();

        LOG.info(" Deploy {} contract", this.entity);
        this.hotelContract = Hotel.deploy(this.web3j, this.tm, this.gasProvider, this.erc20.getContractAddress(), cbcAddress).send();
    }


    public String getHotelContractAddress() {
        return this.hotelContract.getContractAddress();
    }
    public String getErc20ContractAddress() {
        return this.erc20.getContractAddress();
    }

    public void buyTokens(final String account, final int number) throws Exception {
        LOG.info("Buy some tokens to be used for bookings: Account: {}, Number: {}", account, number);
        String myAccount = this.credentials.getAddress();

        this.erc20.transfer(account, BigInteger.valueOf(number)).send();
        BigInteger balance1 = this.erc20.balanceOf(account).send();
        LOG.info(" New balance of account {}: {}", account, balance1);
    }

    public void addTravelAgency(BlockchainId travelAgencyBcId, String travelAgencyContractAddress, String tokenHoldingAccount) throws Exception {
        this.hotelContract.addApprovedTravelAgency(travelAgencyBcId.asBigInt(), travelAgencyContractAddress, tokenHoldingAccount).send();
    }

    public void showErc20Balances(String[] accounts) throws Exception {
        LOG.info(" {} ERC 20 Balances", this.entity);
        BigInteger myBal = this.erc20.balanceOf(this.credentials.getAddress()).send();
        LOG.info(" Owner {} balance: {}", this.credentials.getAddress(), myBal);

        for (String acc: accounts) {
            BigInteger bal = this.erc20.balanceOf(acc).send();
            LOG.info(" Account {} balance: {}", acc, bal);
        }
    }
    public void showErc20Allowance(String owner, String spender) throws Exception {
        BigInteger allowance = this.erc20.allowance(owner, spender).send();
        LOG.info(" {}: Owner {}: Spender: {}: Allowance: {}", this.entity, owner, spender, allowance);

    }

    public void showBookingInformation(BigInteger bookingId) throws Exception {
        Tuple3<BigInteger, BigInteger, BigInteger> retVal = this.hotelContract.getBookingInformation(bookingId).send();
        BigInteger amountPaid = retVal.component1();
        BigInteger roomId = retVal.component2();
        BigInteger date = retVal.component3();

        LOG.info(" {} Booking: Date: {}, Room/Seat: {}, Amount: {}", this.entity, date, roomId, amountPaid);
    }


    public void showBookings(int date) throws Exception {
        LOG.info(" Hotel Bookings for date: {}", date);
        List<String> hotelBookings = this.hotelContract.getBookings(BigInteger.valueOf(date)).send();
        for (String booking: hotelBookings) {
            LOG.info("  Room booked for {}", booking);
        }

        LOG.info(" Train Bookings for date: {}", date);
        List<String> trainBookings = this.hotelContract.getBookings(BigInteger.valueOf(date)).send();
        for (String booking: trainBookings) {
            LOG.info("  Seat booked for {}", booking);
        }
    }
}
