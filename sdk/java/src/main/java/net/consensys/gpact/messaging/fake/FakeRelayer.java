package net.consensys.gpact.messaging.fake;

import static net.consensys.gpact.common.FormatConversion.addressStringToBytes;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import net.consensys.gpact.common.AnIdentity;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.common.FormatConversion;
import org.web3j.crypto.Sign;

public class FakeRelayer {

  AnIdentity signer;

  public FakeRelayer(final AnIdentity signer) {
    this.signer = signer;
  }

  public String fetchedSignedEvent(
      BlockchainId bcId, String contractAddress, byte[] eventFunctionSignature, byte[] eventData) {

    byte[] encodedEventInformation =
        abiEncodePackedEvent(bcId, contractAddress, eventFunctionSignature, eventData);

    // Add the transaction receipt root for the blockchain
    // Sign the txReceiptRoot
    List<String> theSigners = new ArrayList<>();
    List<byte[]> sigR = new ArrayList<>();
    List<byte[]> sigS = new ArrayList<>();
    List<Byte> sigV = new ArrayList<>();
    Sign.SignatureData signatureData = signer.sign(encodedEventInformation);
    theSigners.add(signer.getAddress());
    sigR.add(signatureData.getR());
    sigS.add(signatureData.getS());
    sigV.add(signatureData.getV()[0]);

    byte[] encodedSignatures = abiEncodePackedSignatures(theSigners, sigR, sigS, sigV);
    return FormatConversion.byteArrayToString(encodedSignatures);
  }

  private static byte[] abiEncodePackedEvent(
      BlockchainId blockchainId, String contractAddress, byte[] eventSignature, byte[] eventData) {
    byte[] blockchainIdBytes = blockchainId.asBytes();

    byte[] address = addressStringToBytes(contractAddress);

    byte[] abiEncodePacked =
        new byte
            [blockchainIdBytes.length + address.length + eventSignature.length + eventData.length];
    System.arraycopy(blockchainIdBytes, 0, abiEncodePacked, 0, blockchainIdBytes.length);
    System.arraycopy(address, 0, abiEncodePacked, blockchainIdBytes.length, address.length);
    System.arraycopy(
        eventSignature,
        0,
        abiEncodePacked,
        blockchainIdBytes.length + address.length,
        eventSignature.length);
    System.arraycopy(
        eventData,
        0,
        abiEncodePacked,
        blockchainIdBytes.length + address.length + eventSignature.length,
        eventData.length);

    return abiEncodePacked;
  }

  private byte[] abiEncodePackedSignatures(
      List<String> theSigners, List<byte[]> sigR, List<byte[]> sigS, List<Byte> sigV) {
    final int LEN_OF_LEN = 4;
    final int LEN_OF_SIG = 20 + 32 + 32 + 1;

    int len = theSigners.size();

    ByteBuffer bb = ByteBuffer.allocate(LEN_OF_LEN + LEN_OF_SIG * len);
    bb.putInt(len);

    for (int i = 0; i < len; i++) {
      bb.put(addressStringToBytes(theSigners.get(i)));
      bb.put(sigR.get(i));
      bb.put(sigS.get(i));
      bb.put(sigV.get(i));
    }
    return bb.array();
  }
}
