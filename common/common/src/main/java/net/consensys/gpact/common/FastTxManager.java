package net.consensys.gpact.common;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.response.TransactionReceiptProcessor;

public class FastTxManager extends RawTransactionManager {
  private static final Logger LOG = LogManager.getLogger(FastTxManager.class);
  private final String address;
  private final long chainId;

  private static Set<String> exists = new HashSet<>();

  private volatile BigInteger nonce = BigInteger.valueOf(-1L);

  FastTxManager(
      Web3j web3j,
      Credentials credentials,
      long chainId,
      TransactionReceiptProcessor transactionReceiptProcessor) {
    super(web3j, credentials, chainId, transactionReceiptProcessor);
    this.address = credentials.getAddress();
    this.chainId = chainId;
    LOG.info("Create transaction manager for Bc: {}, Address: {}", this.chainId, this.address);

    String key = this.address + this.chainId;
    if (exists.contains(key)) {
      LOG.error(
          "Transaction manager previously created for Bc: {}, Address: {}",
          this.chainId,
          this.address);
      throw new RuntimeException("Transaction manager previously created");
    }
    exists.add(key);
  }

  protected synchronized BigInteger getNonce() throws IOException {
    if (this.nonce.signum() == -1) {
      this.nonce = super.getNonce();
    } else {
      this.nonce = this.nonce.add(BigInteger.ONE);
    }
    LOG.debug("BcId: {}, Acc: {}, Nonce: {}", this.chainId, this.address, this.nonce);
    return this.nonce;
  }

  public BigInteger getCurrentNonce() {
    return this.nonce;
  }

  public synchronized void resetNonce() throws IOException {
    // The nonce stored in this contract is the nonce before the
    // next nonce, and not the expected nonce.
    this.nonce = super.getNonce().subtract(BigInteger.ONE);
    LOG.debug("Reset Nonce: BcId* {}, Acc: {}, Nonce: {}", this.chainId, this.address, this.nonce);
  }

  public synchronized void setNonce(BigInteger value) {
    this.nonce = value;
  }
}
