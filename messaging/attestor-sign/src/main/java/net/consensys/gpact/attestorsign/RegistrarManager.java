/*
 * Copyright 2021 ConsenSys Software Inc
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
package net.consensys.gpact.attestorsign;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import net.consensys.gpact.attestorsign.soliditywrappers.AttestorSignRegistrar;
import net.consensys.gpact.common.AbstractBlockchain;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.common.DynamicGasProvider;
import net.consensys.gpact.common.RevertReason;
import net.consensys.gpact.messaging.MessagingManagerInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;

/** Manages the registrar contract on a blockchain. */
public abstract class RegistrarManager extends AbstractBlockchain
    implements MessagingManagerInterface {
  static final Logger LOG = LogManager.getLogger(RegistrarManager.class);

  protected AttestorSignRegistrar registrarContract;

  public RegistrarManager(
      Credentials credentials,
      BlockchainId bcId,
      String uri,
      DynamicGasProvider.Strategy gasPriceStrategy,
      int blockPeriod)
      throws IOException {
    super(credentials, bcId, uri, gasPriceStrategy, blockPeriod);
  }

  @Override
  public void deployContracts() throws Exception {
    this.registrarContract =
        AttestorSignRegistrar.deploy(this.web3j, this.tm, this.gasProvider).send();
    LOG.debug(" Registrar Contract: {}", this.registrarContract.getContractAddress());
  }

  @Override
  public ArrayList<String> getContractAddresses() {
    ArrayList<String> addresses = new ArrayList<>();
    addresses.add(this.registrarContract.getContractAddress());
    return addresses;
  }

  @Override
  public void loadContracts(ArrayList<String> addresses) {
    this.registrarContract =
        AttestorSignRegistrar.load(addresses.get(0), this.web3j, this.tm, this.gasProvider);
  }

  @Override
  public void registerSigner(BlockchainId bcId, String signersAddress) throws Exception {
    LOG.debug(
        "Registering signer 0x{} as signer for blockchain {} in registration contract on blockchain {}",
        signersAddress,
        bcId,
        this.blockchainId);
    TransactionReceipt txr;
    try {
      txr = this.registrarContract.addSigner(bcId.asBigInt(), signersAddress).send();
    } catch (TransactionException ex) {
      LOG.error(
          " Revert Reason: {}",
          RevertReason.decodeRevertReason(ex.getTransactionReceipt().get().getRevertReason()));
      throw ex;
    }
    assert (txr.isStatusOK());
  }

  @Override
  public void registerSigner(BlockchainId bcId, String signer, BigInteger newThreshold)
      throws Exception {
    LOG.debug(
        "Registering signer 0x{} as signer for blockchain {} in registration contract on blockchain {}",
        signer,
        bcId,
        this.blockchainId);
    TransactionReceipt txr;
    try {
      txr =
          this.registrarContract
              .addSignerSetThreshold(bcId.asBigInt(), signer, newThreshold)
              .send();
    } catch (TransactionException ex) {
      LOG.error(
          " Revert Reason: {}",
          RevertReason.decodeRevertReason(ex.getTransactionReceipt().get().getRevertReason()));
      throw ex;
    }
    assert (txr.isStatusOK());
  }

  @Override
  public void registerSigners(BlockchainId bcId, List<String> signers, BigInteger newThreshold)
      throws Exception {
    LOG.debug(
        "Registering signers as signers for blockchain {} in registration contract on blockchain {}",
        bcId,
        this.blockchainId);
    TransactionReceipt txr =
        this.registrarContract
            .addSignersSetThreshold(bcId.asBigInt(), signers, newThreshold)
            .send();
    assert (txr.isStatusOK());
  }

  @Override
  public void removeSigner(BlockchainId bcId, String signer) throws Exception {
    LOG.debug(
        "Remove signer as signer for blockchain {} in registration contract on blockchain {}",
        bcId,
        this.blockchainId);
    TransactionReceipt txr = this.registrarContract.removeSigner(bcId.asBigInt(), signer).send();
    assert (txr.isStatusOK());
  }

  @Override
  public void removeSigner(BlockchainId bcId, String signer, BigInteger newThreshold)
      throws Exception {
    LOG.debug(
        "Remove signer as signer for blockchain {} in registration contract on blockchain {}",
        bcId,
        this.blockchainId);
    TransactionReceipt txr =
        this.registrarContract
            .removeSignerSetThreshold(bcId.asBigInt(), signer, newThreshold)
            .send();
    assert (txr.isStatusOK());
  }
}
