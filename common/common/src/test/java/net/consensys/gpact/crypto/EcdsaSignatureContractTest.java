package net.consensys.gpact.crypto;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.math.BigInteger;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import net.consensys.gpact.common.TxManagerCache;
import net.consensys.gpact.common.soliditywrappers.EcdsaSignatureTest;
import net.consensys.gpact.utils.crypto.EcdsaSignatureConversion;
import net.consensys.gpact.utils.crypto.KeyPairGen;
import org.junit.jupiter.api.Test;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;
import org.web3j.tx.response.TransactionReceiptProcessor;
import org.web3j.utils.Numeric;

public class EcdsaSignatureContractTest {
  EcdsaSignatureTest ecdsaTestContract;

  protected static final BigInteger BLOCKCHAIN_ID = BigInteger.valueOf(31);
  private static final String IP_PORT = "127.0.0.1:8310";
  private static final String URI = "http://" + IP_PORT + "/";

  // Have the polling interval half of the block time to have quicker test time.
  protected static final int POLLING_INTERVAL = 1000;
  // Retry requests to Ethereum Clients many times. The servers may be slow to respond during
  // parallel testing.
  protected static final int RETRY = 100;

  Web3j web3j;
  TransactionManager tm;
  Credentials credentials;
  // A gas provider which indicates no gas is charged for transactions.
  ContractGasProvider freeGasProvider =
      new StaticGasProvider(BigInteger.ZERO, DefaultGasProvider.GAS_LIMIT);

  public void setupWeb3() throws Exception {
    String privateKey = new KeyPairGen().generateKeyPairGetPrivateKey();
    //    System.out.println("Priv2: " + privateKey);
    this.credentials = Credentials.create(privateKey);

    this.web3j =
        Web3j.build(new HttpService(URI), POLLING_INTERVAL, new ScheduledThreadPoolExecutor(5));
    TransactionReceiptProcessor txrProcessor =
        new PollingTransactionReceiptProcessor(this.web3j, POLLING_INTERVAL, RETRY);
    this.tm =
        TxManagerCache.getOrCreate(
            this.web3j, this.credentials, BLOCKCHAIN_ID.longValue(), txrProcessor);
  }

  @Test
  public void happyCase() throws Exception {
    final byte[] plainText = new byte[] {0x00};

    setupWeb3();
    deployContract();

    KeyPairGen keyGen = new KeyPairGen();
    String privateKey = keyGen.generateKeyPairGetPrivateKey();
    ECKeyPair keyPair = ECKeyPair.create(Numeric.toBigInt(privateKey));
    Sign.SignatureData signatureData = Sign.signMessage(plainText, keyPair);
    byte[] signature = EcdsaSignatureConversion.convert(signatureData);

    String address = Keys.getAddress(keyPair.getPublicKey().toString(16));

    Boolean result = this.ecdsaTestContract.verify2(address, plainText, signature).send();
    assert (result);
  }

  @Test
  public void sadCase() throws Exception {
    final byte[] plainText = new byte[] {0x00};
    final byte[] fraudPlainText = new byte[] {0x01};

    setupWeb3();
    deployContract();

    KeyPairGen keyGen = new KeyPairGen();
    String privateKey = keyGen.generateKeyPairGetPrivateKey();
    ECKeyPair keyPair = ECKeyPair.create(Numeric.toBigInt(privateKey));
    Sign.SignatureData signatureData = Sign.signMessage(plainText, keyPair);
    byte[] signature = EcdsaSignatureConversion.convert(signatureData);

    String address = Keys.getAddress(keyPair.getPublicKey().toString(16));

    Boolean result = this.ecdsaTestContract.verify2(address, fraudPlainText, signature).send();
    assertFalse(result);
  }

  @Test
  public void happyCaseComponents() throws Exception {
    final byte[] plainText = new byte[] {0x00};

    setupWeb3();
    deployContract();

    KeyPairGen keyGen = new KeyPairGen();
    String privateKey = keyGen.generateKeyPairGetPrivateKey();
    ECKeyPair keyPair = ECKeyPair.create(Numeric.toBigInt(privateKey));
    Sign.SignatureData signatureData = Sign.signMessage(plainText, keyPair);

    String address = Keys.getAddress(keyPair.getPublicKey().toString(16));

    Boolean result =
        this.ecdsaTestContract
            .verifySigComponents2(
                address,
                plainText,
                signatureData.getR(),
                signatureData.getS(),
                BigInteger.valueOf(signatureData.getV()[0]))
            .send();
    assert (result);
  }

  @Test
  public void sadCaseComponents() throws Exception {
    final byte[] plainText = new byte[] {0x00};
    final byte[] fraudPlainText = new byte[] {0x01};

    setupWeb3();
    deployContract();

    KeyPairGen keyGen = new KeyPairGen();
    String privateKey = keyGen.generateKeyPairGetPrivateKey();
    ECKeyPair keyPair = ECKeyPair.create(Numeric.toBigInt(privateKey));
    Sign.SignatureData signatureData = Sign.signMessage(plainText, keyPair);

    String address = Keys.getAddress(keyPair.getPublicKey().toString(16));

    Boolean result =
        this.ecdsaTestContract
            .verifySigComponents2(
                address,
                fraudPlainText,
                signatureData.getR(),
                signatureData.getS(),
                BigInteger.valueOf(signatureData.getV()[0]))
            .send();
    assertFalse(result);
  }

  private void deployContract() throws Exception {
    //    LOG.info("Deploying contracts");
    this.ecdsaTestContract =
        EcdsaSignatureTest.deploy(this.web3j, this.tm, this.freeGasProvider).send();
  }
}
