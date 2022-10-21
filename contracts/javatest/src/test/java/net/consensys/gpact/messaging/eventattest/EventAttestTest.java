package net.consensys.gpact.messaging.eventattest;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.consensys.gpact.common.AnIdentity;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.common.RevertReason;
import net.consensys.gpact.common.crypto.Hash;
import net.consensys.gpact.common.test.AbstractWeb3Test;
import net.consensys.gpact.messaging.common.MessagingRegistrar;
import net.consensys.gpact.messaging.fake.FakeRelayer;
import org.apache.tuweni.bytes.Bytes;
import org.junit.jupiter.api.Test;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tx.exceptions.ContractCallException;

/** Test cases for Event Attestation Verifier. */
public class EventAttestTest extends AbstractWeb3Test {
  private MessagingRegistrar registrarContract;
  private EventAttestationVerifier verifier;

  private void deployContracts() throws Exception {
    this.registrarContract =
        MessagingRegistrar.deploy(this.web3j, this.tm, this.freeGasProvider).send();
    this.verifier =
        EventAttestationVerifier.deploy(
                this.web3j,
                this.tm,
                this.freeGasProvider,
                this.registrarContract.getContractAddress())
            .send();
  }

  @Test
  public void happyPathOneSigner() throws Exception {
    final String fixedPrivateKey1 = "1";
    List<AnIdentity> signers = new ArrayList<>();
    signers.add(new AnIdentity(fixedPrivateKey1));

    checkVerify(signers, signers, 1);
  }

  @Test
  public void happyPathThreeSignersThresholdOne() throws Exception {
    final String fixedPrivateKey1 = "1";
    final String fixedPrivateKey2 = "2";
    final String fixedPrivateKey3 = "3";
    List<AnIdentity> signers = new ArrayList<>();
    signers.add(new AnIdentity(fixedPrivateKey1));
    signers.add(new AnIdentity(fixedPrivateKey2));
    signers.add(new AnIdentity(fixedPrivateKey3));

    checkVerify(signers, signers, 1);
  }

  @Test
  public void happyPathThreeSignersThresholdTwo() throws Exception {
    final String fixedPrivateKey1 = "1";
    final String fixedPrivateKey2 = "2";
    final String fixedPrivateKey3 = "3";
    List<AnIdentity> signers = new ArrayList<>();
    signers.add(new AnIdentity(fixedPrivateKey1));
    signers.add(new AnIdentity(fixedPrivateKey2));
    signers.add(new AnIdentity(fixedPrivateKey3));

    checkVerify(signers, signers, 2);
  }

  @Test
  public void happyPathThreeSignersThresholdThree() throws Exception {
    final String fixedPrivateKey1 = "1";
    final String fixedPrivateKey2 = "2";
    final String fixedPrivateKey3 = "3";
    List<AnIdentity> signers = new ArrayList<>();
    signers.add(new AnIdentity(fixedPrivateKey1));
    signers.add(new AnIdentity(fixedPrivateKey2));
    signers.add(new AnIdentity(fixedPrivateKey3));

    checkVerify(signers, signers, 3);
  }

  @Test
  public void belowThreshold() throws Exception {
    final String fixedPrivateKey1 = "1";
    final String fixedPrivateKey2 = "2";
    final String fixedPrivateKey3 = "3";
    List<AnIdentity> registeredSigners = new ArrayList<>();
    registeredSigners.add(new AnIdentity(fixedPrivateKey1));
    registeredSigners.add(new AnIdentity(fixedPrivateKey2));
    registeredSigners.add(new AnIdentity(fixedPrivateKey3));

    List<AnIdentity> signers = new ArrayList<>();
    signers.add(new AnIdentity(fixedPrivateKey1));
    signers.add(new AnIdentity(fixedPrivateKey2));

    checkVerifyFail("Not enough signers", registeredSigners, signers, 3);
  }

  @Test
  public void notASigner() throws Exception {
    final String fixedPrivateKey1 = "1";
    final String fixedPrivateKey2 = "2";
    final String fixedPrivateKey3 = "3";
    List<AnIdentity> registeredSigners = new ArrayList<>();
    registeredSigners.add(new AnIdentity(fixedPrivateKey1));
    registeredSigners.add(new AnIdentity(fixedPrivateKey2));

    List<AnIdentity> signers = new ArrayList<>();
    signers.add(new AnIdentity(fixedPrivateKey1));
    signers.add(new AnIdentity(fixedPrivateKey3));

    checkVerifyFail("Signer not registered for this blockchain", registeredSigners, signers, 1);
  }

  @Test
  public void duplicateSigner() throws Exception {
    final String fixedPrivateKey1 = "1";
    final String fixedPrivateKey2 = "2";
    final String fixedPrivateKey3 = "3";
    List<AnIdentity> registeredSigners = new ArrayList<>();
    registeredSigners.add(new AnIdentity(fixedPrivateKey1));
    registeredSigners.add(new AnIdentity(fixedPrivateKey2));
    registeredSigners.add(new AnIdentity(fixedPrivateKey3));

    List<AnIdentity> signers = new ArrayList<>();
    signers.add(new AnIdentity(fixedPrivateKey1));
    signers.add(new AnIdentity(fixedPrivateKey3));
    signers.add(new AnIdentity(fixedPrivateKey3));

    checkVerifyFail("Duplicate signer", registeredSigners, signers, 2);
  }

  private void checkVerify(
      List<AnIdentity> signersToRegister, List<AnIdentity> signersToSign, int threshold)
      throws Exception {
    setupWeb3();
    deployContracts();

    BigInteger bcIdB = BigInteger.TEN;
    BlockchainId bcId = new BlockchainId(bcIdB);
    List<String> signerAddresses = new ArrayList<>();
    for (AnIdentity signer : signersToRegister) {
      signerAddresses.add(signer.getAddress());
    }

    this.registrarContract
        .addSignersSetThreshold(bcIdB, signerAddresses, BigInteger.valueOf(threshold))
        .send();

    FakeRelayer signatureCreator = new FakeRelayer(signersToSign);

    String contractAddress = "6D1e0220914f4fb73aF954694564e77024de3693";
    byte[] eventFunctionSignature =
        Hash.keccak256(Bytes.wrap("FuncEvent(uint256)".getBytes())).toArray();
    // The actual event data will depend on the event emitted.
    byte[] rawEventData = new byte[1];
    byte[] signature =
        signatureCreator.fetchedSignedEvent(
            bcId, contractAddress, eventFunctionSignature, rawEventData);
    byte[] encodedEventData =
        FakeRelayer.abiEncodePackedEvent(
            bcId, contractAddress, eventFunctionSignature, rawEventData);

    try {
      // Ignore return value. Exception will be thrown if an error
      this.verifier
          .call_decodeAndVerifyEvent(
              bcId.asBigInt(), eventFunctionSignature, encodedEventData, signature)
          .send();
    } catch (ContractCallException callException) {
      try {
        // Ignore the returned transaction receipt: this will throw an exception
        this.verifier
            .send_decodeAndVerifyEvent(
                bcId.asBigInt(), eventFunctionSignature, encodedEventData, signature)
            .send();
      } catch (TransactionException transactionException) {
        Optional<TransactionReceipt> txrO = transactionException.getTransactionReceipt();
        if (txrO.isEmpty()) {
          throw new Exception("Unknown error");
        }
        String revertReason = RevertReason.decodeRevertReason(txrO.get().getRevertReason());
        throw new Exception("Reverted: " + revertReason);
      }
    }
  }

  private void checkVerifyFail(
      String expectedRevert,
      List<AnIdentity> signersToRegister,
      List<AnIdentity> signersToSign,
      int threshold)
      throws Exception {
    try {
      checkVerify(signersToRegister, signersToSign, threshold);
      throw new Exception("No exception thrown");
    } catch (Exception ex) {
      String revert = ex.getMessage();
      String expectedRevertWithPrefix = "Reverted: " + expectedRevert;
      if (!expectedRevertWithPrefix.equals(revert)) {
        throw new Exception(
            "Unexpected revert. Expected: " + expectedRevertWithPrefix + ", Actual: " + revert);
      }
    }
  }

  @Test
  public void showDebugInfo() throws Exception {
    final String fixedPrivateKey1 = "1";
    final String fixedPrivateKey2 = "2";
    final String fixedPrivateKey3 = "3";
    List<AnIdentity> signers = new ArrayList<>();
    signers.add(new AnIdentity(fixedPrivateKey1));
    signers.add(new AnIdentity(fixedPrivateKey2));
    signers.add(new AnIdentity(fixedPrivateKey3));

    List<AnIdentity> signersToRegister = signers;
    List<AnIdentity> signersToSign = signers;
    int threshold = 2;

    setupWeb3();
    deployContracts();

    BigInteger bcIdB = BigInteger.TEN;
    BlockchainId bcId = new BlockchainId(bcIdB);
    List<String> signerAddresses = new ArrayList<>();
    for (AnIdentity signer : signersToRegister) {
      signerAddresses.add(signer.getAddress());
    }

    this.registrarContract
        .addSignersSetThreshold(bcIdB, signerAddresses, BigInteger.valueOf(threshold))
        .send();

    FakeRelayer signatureCreator = new FakeRelayer(signersToSign);

    String contractAddress = "6D1e0220914f4fb73aF954694564e77024de3693";
    byte[] eventFunctionSignature =
        Hash.keccak256(Bytes.wrap("FuncEvent(uint256)".getBytes())).toArray();
    // The actual event data will depend on the event emitted.
    byte[] rawEventData = new byte[1];
    byte[] signature =
        signatureCreator.fetchedSignedEvent(
            bcId, contractAddress, eventFunctionSignature, rawEventData);
    byte[] encodedEventData =
        FakeRelayer.abiEncodePackedEvent(
            bcId, contractAddress, eventFunctionSignature, rawEventData);

    String abi =
        this.verifier.getABI_call_decodeAndVerifyEvent(
            bcId.asBigInt(), eventFunctionSignature, encodedEventData, signature);
    System.out.println(abi);
  }
}
