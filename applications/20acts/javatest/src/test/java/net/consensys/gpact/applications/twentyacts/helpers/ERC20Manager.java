/*
 * Copyright 2022 ConsenSys Software Inc
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
package net.consensys.gpact.applications.twentyacts.helpers;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import net.consensys.gpact.common.*;
import net.consensys.gpact.soliditywrappers.applications.twentyacts.ERC20PresetFixedSupply;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

/** Controls ERC 20 contract for a blockchain. */
public class ERC20Manager extends AbstractBlockchain {
  private static final Logger LOG = LogManager.getLogger(ERC20Manager.class);

  private ERC20PresetFixedSupply erc20;

  public ERC20Manager(
      Credentials credentials,
      BlockchainId bcId,
      String uri,
      DynamicGasProvider.Strategy gasPriceStrategy,
      int blockPeriod)
      throws IOException {
    super(credentials, bcId, uri, gasPriceStrategy, blockPeriod);
  }

  public void deployErc20Contract(
      String name, String symbol, BigInteger initialSupply, String owner) throws Exception {
    this.erc20 =
        ERC20PresetFixedSupply.deploy(
                this.web3j, this.tm, this.gasProvider, name, symbol, initialSupply, owner)
            .send();
    LOG.info(
        "ERC20 deployed to {} on blockchain {}",
        this.erc20.getContractAddress(),
        this.blockchainId);
  }

  private void loadErc20Contract(String address) {
    this.erc20 = ERC20PresetFixedSupply.load(address, this.web3j, this.tm, this.gasProvider);
  }

  public void erc20Faucet(String account, BigInteger amount) throws Exception {
    this.erc20.transfer(account, amount).send();
  }

  public void erc20Approve(String spender, BigInteger amount) throws Exception {
    TransactionReceipt txR = this.erc20.approve(spender, amount).send();
    StatsHolder.logGas("ERC20 Approve", txR.getGasUsed());
  }

  public String getErc20ContractAddress() {
    return this.erc20.getContractAddress();
  }

  public BigInteger balanceOf(String account) throws Exception {
    return this.erc20.balanceOf(account).send();
  }

  public ERC20Manager forUser(Credentials user) throws IOException {
    ERC20Manager bc =
        new ERC20Manager(
            user, this.blockchainId, this.rpcUri, this.gasPriceStrategy, this.pollingInterval);
    bc.loadErc20Contract(this.erc20.getContractAddress());
    return bc;
  }

  /**
   * Create a signed ERC 20 Approve transaction with the crosschain transaction hash as an
   * additional parameter.
   *
   * @param spender Spender for the ERC 20 Approve.
   * @param amount Amount for the ERC 20 Approve.
   * @param crosschainTxHash Value to be appended to the ERC 20 Approve data.
   * @return signed transaction.
   */
  public String createSpecialErc20Approve(
      String spender, BigInteger amount, byte[] crosschainTxHash) throws IOException {
    final org.web3j.abi.datatypes.Function function =
        new org.web3j.abi.datatypes.Function(
            "approve",
            Arrays.<Type>asList(
                new org.web3j.abi.datatypes.Address(160, spender),
                new org.web3j.abi.datatypes.generated.Uint256(amount)),
            Collections.<TypeReference<?>>emptyList());
    String encodedFunction = org.web3j.abi.FunctionEncoder.encode(function);
    byte[] encodedFunctionBytes = FormatConversion.hexStringToByteArray(encodedFunction);

    ByteBuffer byteBuffer =
        ByteBuffer.allocate(encodedFunctionBytes.length + crosschainTxHash.length);
    byteBuffer.put(encodedFunctionBytes);
    byteBuffer.put(crosschainTxHash);
    byte[] erc20ApproveTxData = byteBuffer.array();

    BigInteger nonce = this.tm.getNonceWithIncrement();
    BigInteger gasPrice = this.gasProvider.getGasPrice();
    BigInteger gasLimit = this.gasProvider.getGasLimit();
    String to = this.erc20.getContractAddress();
    String data = FormatConversion.byteArrayToString(erc20ApproveTxData);

    RawTransaction rawTransaction =
        RawTransaction.createTransaction(nonce, gasPrice, gasLimit, to, data);
    return this.tm.sign(rawTransaction);
  }

  /**
   * Submit a raw transaction using the transaction manager associated with this class.
   *
   * @param signedTransaction Signed Ethereum transaction.
   * @return Transaction Receipt for the transaction.
   * @throws Exception if the transaction fails.
   */
  public TransactionReceipt sendRawTransaction(String signedTransaction) throws Exception {
    return this.tm.sendRawTransaction(signedTransaction);
  }
}
