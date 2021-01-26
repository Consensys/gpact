package tech.pegasys.ltacfc.test.crypto;

import org.junit.Test;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;
import tech.pegasys.ltacfc.soliditywrappers.EcdsaSignatureTest;
import tech.pegasys.ltacfc.test.AbstractWeb3Test;
import tech.pegasys.ltacfc.utils.crypto.EcdsaSignatureConversion;
import tech.pegasys.ltacfc.utils.crypto.KeyPairGen;

import java.math.BigInteger;

import static junit.framework.TestCase.assertFalse;

public class EcdsaSignatureContractTest extends AbstractWeb3Test {

  EcdsaSignatureTest ecdsaTestContract;

  @Test
  public void happyCase() throws Exception {
    final byte[] plainText = new byte[]{0x00};

    setupWeb3();
    deployContract();

    KeyPairGen keyGen = new KeyPairGen();
    String privateKey = keyGen.generateKeyPairGetPrivateKey();
    ECKeyPair keyPair = ECKeyPair.create(Numeric.toBigInt(privateKey));
    Sign.SignatureData signatureData = Sign.signMessage(plainText, keyPair);
    byte[] signature = EcdsaSignatureConversion.convert(signatureData);

    String address = Keys.getAddress(keyPair.getPublicKey().toString(16));

    Boolean result = this.ecdsaTestContract.verify2(address, plainText, signature).send();
    assert(result);
  }

  @Test
  public void sadCase() throws Exception {
    final byte[] plainText = new byte[]{0x00};
    final byte[] fraudPlainText = new byte[]{0x01};

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
    final byte[] plainText = new byte[]{0x00};

    setupWeb3();
    deployContract();

    KeyPairGen keyGen = new KeyPairGen();
    String privateKey = keyGen.generateKeyPairGetPrivateKey();
    ECKeyPair keyPair = ECKeyPair.create(Numeric.toBigInt(privateKey));
    Sign.SignatureData signatureData = Sign.signMessage(plainText, keyPair);

    String address = Keys.getAddress(keyPair.getPublicKey().toString(16));

    Boolean result = this.ecdsaTestContract.verifySigComponents2(address, plainText, signatureData.getR(), signatureData.getS(), BigInteger.valueOf(signatureData.getV()[0])).send();
    assert(result);
  }

  @Test
  public void sadCaseComponents() throws Exception {
    final byte[] plainText = new byte[]{0x00};
    final byte[] fraudPlainText = new byte[]{0x01};

    setupWeb3();
    deployContract();

    KeyPairGen keyGen = new KeyPairGen();
    String privateKey = keyGen.generateKeyPairGetPrivateKey();
    ECKeyPair keyPair = ECKeyPair.create(Numeric.toBigInt(privateKey));
    Sign.SignatureData signatureData = Sign.signMessage(plainText, keyPair);

    String address = Keys.getAddress(keyPair.getPublicKey().toString(16));

    Boolean result = this.ecdsaTestContract.verifySigComponents2(address, fraudPlainText, signatureData.getR(), signatureData.getS(), BigInteger.valueOf(signatureData.getV()[0])).send();
    assertFalse(result);
  }



  private void deployContract() throws Exception {
//    LOG.info("Deploying contracts");
    this.ecdsaTestContract = EcdsaSignatureTest.deploy(this.web3j, this.tm, this.freeGasProvider).send();
  }

}
