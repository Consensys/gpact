/*
 * Copyright 2019 ConsenSys AG.
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
package tech.pegasys.ltacfc.examples.trade;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import tech.pegasys.ltacfc.cbc.AbstractBlockchain;
import tech.pegasys.ltacfc.examples.trade.soliditywrappers.BusLogic;


import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public class Bc2BusLogic extends AbstractBlockchain {
  private static final Logger LOG = LogManager.getLogger(Bc2BusLogic.class);

  BusLogic busLogicContract;

  public Bc2BusLogic(Credentials credentials, String bcId, String uri, String gasPriceStrategy, String blockPeriod) throws IOException {
    super(credentials, bcId, uri, gasPriceStrategy, blockPeriod);
  }

  public void deployContracts(
      String cbc,
      BigInteger balancesBcId, String balances,
      BigInteger oracleBcId, String oracle,
      BigInteger stockBcId, String stock) throws Exception {
    this.busLogicContract = BusLogic.deploy(this.web3j, this.tm, this.gasProvider,
        cbc, balancesBcId, balances, oracleBcId, oracle, stockBcId, stock).send();
    LOG.info("Business Logic contract deployed to {} on blockchain 0x{}",
        this.busLogicContract.getContractAddress(), this.blockchainId.toString(16));
  }

  public String getRlpFunctionSignature_StockShipment(String seller, String buyer, BigInteger quantity) {
    return this.busLogicContract.getRLP_stockShipment(seller, buyer, quantity);
  }

  public void showEvents(TransactionReceipt txR) {
    LOG.info("Business Logic: Stock Shipment Events");
    List<BusLogic.StockShipmentEventResponse> events = this.busLogicContract.getStockShipmentEvents(txR);
    for (BusLogic.StockShipmentEventResponse e: events) {
      LOG.info(" Seller: {}", e._seller);
      LOG.info(" Buyer: {}", e._buyer);
      LOG.info(" Quantity: {}", e._quantity);
      LOG.info(" Price: {}", e._price);
    }
  }
}
