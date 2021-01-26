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
package tech.pegasys.ltacfc.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthGetBlockTransactionCountByHash;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.tx.gas.ContractGasProvider;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DynamicGasProvider implements ContractGasProvider {
  public enum Strategy {
    ETH_GAS_PRICE,
    LOWEST,
    HIGHEST,
    AVERAGE,
    MEDIAN,
    FREE
  }

  private static final Logger LOG = LogManager.getLogger(DynamicGasProvider.class);

  Web3j web3j;
  String uri;
  Strategy pricingStrategy;

  BigInteger lowest;
  BigInteger highest;
  BigInteger average;
  BigInteger median;
  BigInteger ethGasPrice;

  public DynamicGasProvider(Web3j web3j, String uri, String pricingStrategy) throws IOException {
    this(web3j, uri, Strategy.valueOf(pricingStrategy));
  }


  public DynamicGasProvider(Web3j web3j, String uri, Strategy pricingStrategy) throws IOException {
    this.pricingStrategy = pricingStrategy;
    this.uri = uri;
    this.web3j = web3j;
    detertermineNewGasPrice();
  }

  public void changeStrategy(Strategy pricingStrategy) throws IOException {
    this.pricingStrategy = pricingStrategy;
    detertermineNewGasPrice();
  }


  private void detertermineNewGasPrice() throws IOException {
    if (this.pricingStrategy == Strategy.FREE) {
      return;
    }

    EthGasPrice ethGasPriceObj = this.web3j.ethGasPrice().send();
    this.ethGasPrice = ethGasPriceObj.getGasPrice();

    EthBlock ethBlock = this.web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send();
    String blockHash = ethBlock.getBlock().getHash();
    EthGetBlockTransactionCountByHash transactionCountByHash = web3j.ethGetBlockTransactionCountByHash(blockHash).send();
    BigInteger txCount = transactionCountByHash.getTransactionCount();

    if (txCount.compareTo(BigInteger.ZERO) == 0) {
      throw new RuntimeException("No transactions in the last block. Can't work out gas price");
    }

    List<BigInteger> sortedList = new ArrayList<>();
    BigInteger total = BigInteger.ZERO;
    this.highest = BigInteger.ZERO;
    this.lowest = BigInteger.TWO.pow(256);
    BigInteger transactionIndex = BigInteger.ZERO;
    do {
      EthTransaction ethTransaction = this.web3j.ethGetTransactionByBlockHashAndIndex(blockHash, transactionIndex).send();
      Optional<Transaction> transaction = ethTransaction.getTransaction();
      assert(transaction.isPresent());
      BigInteger gasPrice = transaction.get().getGasPrice();

      sortedList.add(gasPrice);

      total = total.add(gasPrice);

      if (gasPrice.compareTo(this.highest) > 0) {
        this.highest = gasPrice;
      }
      if (gasPrice.compareTo(this.lowest) < 0) {
        this.lowest = gasPrice;
      }

      // Increment for the next time through the loop.
      transactionIndex = transactionIndex.add(BigInteger.ONE);
    } while (transactionIndex.compareTo(txCount) != 0);

    this.average = total.divide(txCount);

    sortedList.sort(BigInteger::compareTo);
    this.median = sortedList.get(sortedList.size() / 2);

    LOG.info("Gas Price for Ethereum network: {}", this.uri);
    LOG.info(" EthGasPrice: {}", this.ethGasPrice);
    LOG.info(" Lowest:      {}", this.lowest);
    LOG.info(" Average:     {}", this.average);
    LOG.info(" Median:      {}", this.median);
    LOG.info(" Highest:     {}", this.highest);
    LOG.info(" Strategy selected: {}", this.pricingStrategy);
  }

  @Override
  public BigInteger getGasPrice(String functionName) {
    return getGasPrice();
  }

  @Override
  public BigInteger getGasPrice() {
    switch (this.pricingStrategy) {
      case ETH_GAS_PRICE:
        return this.ethGasPrice;
      case LOWEST:
        return this.lowest;
      case HIGHEST:
        return this.highest;
      case AVERAGE:
        return this.average;
      case MEDIAN:
        return this.median.add(BigInteger.ONE);
      case FREE:
        return BigInteger.ZERO;
      default:
        throw new RuntimeException("Unknown pricing strategy: " + this.pricingStrategy);
    }
  }

  @Override
  public BigInteger getGasLimit(String functionName) {
    return getGasLimit();
  }

  // The gas limit has to be below the block gas limit.
  // Ropsten test net gas limit is 8,000,000
  public static final BigInteger GAS_LIMIT = BigInteger.valueOf(8000000L);


  @Override
  public BigInteger getGasLimit() {
    return GAS_LIMIT;
  }
}
