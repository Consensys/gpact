package net.consensys.gpact.messaging.common;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import net.consensys.gpact.common.test.AbstractWeb3Test;
import net.consensys.gpact.common.test.DummyAddressGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

public class SignatureBlobTest extends AbstractWeb3Test {
  SignatureEncodingTest signatureEncodingTest;

  @BeforeEach
  public void setup() throws Exception {
    setupWeb3();
    this.signatureEncodingTest =
        SignatureEncodingTest.deploy(this.web3j, this.credentials, this.freeGasProvider).send();
  }

  // This prints out the encoded signature blob.
  //    @Test
  //    public void dump() throws Exception {
  //        TransactionReceipt txr = this.signatureEncodingTest.testEmitSignatureBlob().send();
  //        String out = txr.getLogs().get(0).getData();
  //        System.out.println("Out: " + out);
  //    }

  @Test
  public void decodeType() throws Exception {
    String[] theSigners = new String[] {};
    byte[][] sigRs = new byte[][] {};
    byte[][] sigSs = new byte[][] {};
    byte[] sigVs = new byte[] {};

    SignatureBlob blob = new SignatureBlob(theSigners, sigRs, sigSs, sigVs);
    byte[] encoded = blob.encode();

    TransactionReceipt txr = this.signatureEncodingTest.testDecodeSignatureType(encoded).send();
    List<SignatureEncodingTest.SigTypeEventResponse> sigTypes =
        this.signatureEncodingTest.getSigTypeEvents(txr);
    assertEquals(1, sigTypes.size());

    SignatureEncodingTest.SigTypeEventResponse sigType = sigTypes.get(0);
    assertEquals(1, sigType.typ.intValue());
  }

  @Test
  public void decodeEmpty() throws Exception {
    String[] theSigners = new String[] {};
    byte[][] sigRs = new byte[][] {};
    byte[][] sigSs = new byte[][] {};
    byte[] sigVs = new byte[] {};

    SignatureBlob blob = new SignatureBlob(theSigners, sigRs, sigSs, sigVs);
    byte[] encoded = blob.encode();

    TransactionReceipt txr = this.signatureEncodingTest.testDecodeSignature(encoded).send();
    List<SignatureEncodingTest.SigInfo1EventResponse> sigInfo1s =
        this.signatureEncodingTest.getSigInfo1Events(txr);
    assertEquals(1, sigInfo1s.size());

    SignatureEncodingTest.SigInfo1EventResponse sigInfo1 = sigInfo1s.get(0);
    assertEquals(1, sigInfo1.typ.intValue());
    assertEquals(0, sigInfo1.len.intValue());

    List<SignatureEncodingTest.SigInfo2EventResponse> sigInfo2s =
        this.signatureEncodingTest.getSigInfo2Events(txr);
    assertEquals(0, sigInfo2s.size());
  }

  @Test
  public void decodeOneSig() throws Exception {
    String[] theSigners = new String[] {DummyAddressGenerator.gen()};
    byte[][] sigRs =
        new byte[][] {
          {
            0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xA, 0xB, 0xC, 0xD, 0xE, 0xF, 0x10, 0x11,
            0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x1A, 0x1B, 0x1C, 0x1D, 0x1E, 0x1F, 0x20
          }
        };
    byte[][] sigSs =
        new byte[][] {
          {
            0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xA, 0xB, 0xC, 0xD, 0xE, 0xF, 0x10, 0x11,
            0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x1A, 0x1B, 0x1C, 0x1D, 0x1E, 0x1F, 0x21
          }
        };
    byte[] sigVs = new byte[] {0x22};

    SignatureBlob blob = new SignatureBlob(theSigners, sigRs, sigSs, sigVs);
    byte[] encoded = blob.encode();

    TransactionReceipt txr = this.signatureEncodingTest.testDecodeSignature(encoded).send();
    List<SignatureEncodingTest.SigInfo1EventResponse> sigInfo1s =
        this.signatureEncodingTest.getSigInfo1Events(txr);
    assertEquals(1, sigInfo1s.size());

    SignatureEncodingTest.SigInfo1EventResponse sigInfo1 = sigInfo1s.get(0);
    assertEquals(1, sigInfo1.typ.intValue());
    assertEquals(1, sigInfo1.len.intValue());

    checkAll(this.signatureEncodingTest.getSigInfo2Events(txr), theSigners, sigRs, sigSs, sigVs);
  }

  @Test
  public void decodeTwoSigs() throws Exception {
    String[] theSigners = new String[] {DummyAddressGenerator.gen(), DummyAddressGenerator.gen()};
    byte[][] sigRs =
        new byte[][] {
          {
            0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xA, 0xB, 0xC, 0xD, 0xE, 0xF, 0x10, 0x11,
            0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x1A, 0x1B, 0x1C, 0x1D, 0x1E, 0x1F, 0x20
          },
          {
            0x2, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xA, 0xB, 0xC, 0xD, 0xE, 0xF, 0x10, 0x11,
            0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x1A, 0x1B, 0x1C, 0x1D, 0x1E, 0x1F, 0x20
          }
        };
    byte[][] sigSs =
        new byte[][] {
          {
            0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xA, 0xB, 0xC, 0xD, 0xE, 0xF, 0x10, 0x11,
            0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x1A, 0x1B, 0x1C, 0x1D, 0x1E, 0x1F, 0x21
          },
          {
            0x2, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xA, 0xB, 0xC, 0xD, 0xE, 0xF, 0x10, 0x11,
            0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x1A, 0x1B, 0x1C, 0x1D, 0x1E, 0x1F, 0x21
          }
        };
    byte[] sigVs = new byte[] {0x22, 0x23};

    SignatureBlob blob = new SignatureBlob(theSigners, sigRs, sigSs, sigVs);
    byte[] encoded = blob.encode();

    TransactionReceipt txr = this.signatureEncodingTest.testDecodeSignature(encoded).send();
    List<SignatureEncodingTest.SigInfo1EventResponse> sigInfo1s =
        this.signatureEncodingTest.getSigInfo1Events(txr);
    assertEquals(1, sigInfo1s.size());

    SignatureEncodingTest.SigInfo1EventResponse sigInfo1 = sigInfo1s.get(0);
    assertEquals(1, sigInfo1.typ.intValue());
    assertEquals(2, sigInfo1.len.intValue());

    checkAll(this.signatureEncodingTest.getSigInfo2Events(txr), theSigners, sigRs, sigSs, sigVs);
  }

  private void checkAll(
      List<SignatureEncodingTest.SigInfo2EventResponse> sigInfos,
      String[] theSigners,
      byte[][] sigRs,
      byte[][] sigSs,
      byte[] sigVs) {
    final byte[] none = new byte[] {};
    assertEquals(theSigners.length, sigInfos.size());
    for (int i = 0; i < theSigners.length; i++) {
      SignatureEncodingTest.SigInfo2EventResponse sigInfo = sigInfos.get(i);
      assertTrue(theSigners[i].equalsIgnoreCase(sigInfo.by));
      assertArrayEquals(sigRs[i], sigInfo.sigR);
      assertArrayEquals(sigSs[i], sigInfo.sigS);
      assertEquals(sigVs[i], sigInfo.sigV.intValue());
      assertArrayEquals(none, sigInfo.meta);
    }
  }
}
