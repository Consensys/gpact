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
import tech.pegasys.ltacfc.cbc.AbstractBlockchain;
import tech.pegasys.ltacfc.examples.trade.soliditywrappers.TradeWallet;
import tech.pegasys.ltacfc.lockablestorage.soliditywrappers.LockableStorage;

import java.io.IOException;
import java.math.BigInteger;


public class Bc1TradeWallet extends AbstractBlockchain {
  private static final Logger LOG = LogManager.getLogger(Bc1TradeWallet.class);

  TradeWallet tradeWalletContract;
  private LockableStorage lockableStorageContract;

  public Bc1TradeWallet(Credentials credentials, String bcId, String uri, String gasPriceStrategy, String blockPeriod) throws IOException {
    super(credentials, bcId, uri, gasPriceStrategy, blockPeriod);
  }

  public void deployContracts(String cbcContractAddress, BigInteger busLogicBlockchainId, String busLogicContractAddress) throws Exception {
    this.lockableStorageContract = LockableStorage.deploy(this.web3j, this.tm, this.gasProvider, cbcContractAddress).send();
    this.tradeWalletContract =
        TradeWallet.deploy(this.web3j, this.tm, this.gasProvider,
            cbcContractAddress,
            busLogicBlockchainId,
            busLogicContractAddress,
            this.lockableStorageContract.getContractAddress()).send();
    this.lockableStorageContract.setBusinessLogicContract(this.tradeWalletContract.getContractAddress()).send();
    LOG.info("Trade Wallet contract deployed to {} on blockchain 0x{}",
        this.tradeWalletContract.getContractAddress(), this.blockchainId.toString(16));
    LOG.info("Lockable Storage contract for Trade Wallet deployed to {} on blockchain 0x{}",
        this.lockableStorageContract.getContractAddress(), this.blockchainId.toString(16));
  }

  public String getRlpFunctionSignature_ExecuteTrade(String buyFrom, BigInteger quantity) {
    return this.tradeWalletContract.getRLP_executeTrade(buyFrom, quantity);
  }

  public void showAllTrades() throws Exception {
    boolean storageIsLocked = this.lockableStorageContract.locked().send();
    if (storageIsLocked) {
      throw new Exception("Root contract lockable storage is locked");
    }

    LOG.info("Trades:");
    BigInteger numTradesBig = this.tradeWalletContract.getNumTrades().send();
    int numTrades = (int) numTradesBig.longValue();
    if (numTrades == 0) {
      LOG.info(" None");
    }

    for (int i = 0; i < numTrades; i++) {
      BigInteger trade = this.tradeWalletContract.getTrade(BigInteger.valueOf(i)).send();
      LOG.info(" 0x{}", trade.toString(16));
    }
  }

  public boolean storageIsLocked() throws Exception {
    return  this.lockableStorageContract.locked().send();
  }
}
