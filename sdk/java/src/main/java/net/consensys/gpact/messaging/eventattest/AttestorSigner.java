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
package net.consensys.gpact.messaging.eventattest;

import static net.consensys.gpact.common.FormatConversion.addressStringToBytes;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import net.consensys.gpact.common.AnIdentity;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.common.FormatConversion;
import net.consensys.gpact.messaging.MessagingVerificationInterface;
import net.consensys.gpact.messaging.SignedEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tuweni.bytes.Bytes;
import org.web3j.crypto.Sign;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;


/**
 * Manages the interaction between the application library and attestors for a certain blockchain.
 * The class asks an attestor to give the threshold signed event data. The attestor should have
 * cooperated with other attestors already to threshold sign the event data.
 *
 * <p>TODO the Attestor service has not been implemented yet. As such, this class simulates calling
 * out to the Attestor service to get the threshold signed event data.
 */
public class AttestorSigner implements MessagingVerificationInterface {
  static final Logger LOG = LogManager.getLogger(AttestorSigner.class);

  List<AnIdentity> signers = new ArrayList<>();

  BlockchainId bcId;

  String msgStoreURL;

  public AttestorSigner(BlockchainId blockchainId, String msgStoreURL) {
    this.bcId = blockchainId;
    this.msgStoreURL = msgStoreURL;
  }

  // TODO this method won't be needed / should be removed once the Attestor service has been
  // created.
  // Perhaps at that point, addSigner should be specifying the URL of an attestor.
  public void addSigner(AnIdentity signer) throws Exception {
    // Add the signer (their private key) to app for the blockchain
    signers.add(signer);
  }

  @Override
  public SignedEvent getSignedEvent(
      List<BlockchainId> targetBlockchainIds,
      TransactionReceipt txReceipt,
      byte[] eventData,
      String contractAddress,
      byte[] eventFunctionSignature)
      throws Exception {

    String eventId = getEventID(this.bcId, txReceipt, contractAddress, eventFunctionSignature);
    LOG.info("EventId: {}", eventId);
    String encodedSignaturesStr = fetchSignedEvent(eventId);
    LOG.info("EncodedSig: {}", encodedSignaturesStr);




    byte[] encodedEventInformation =
        abiEncodePackedEvent(this.bcId, contractAddress, eventFunctionSignature, eventData);

    // Add the transaction receipt root for the blockchain
    // Sign the txReceiptRoot
    List<String> theSigners = new ArrayList<>();
    List<byte[]> sigR = new ArrayList<>();
    List<byte[]> sigS = new ArrayList<>();
    List<Byte> sigV = new ArrayList<>();
    //    List<BigInteger> sigV = new ArrayList<>();
    for (AnIdentity signer : this.signers) {
      Sign.SignatureData signatureData = signer.sign(encodedEventInformation);
      theSigners.add(signer.getAddress());
      sigR.add(signatureData.getR());
      sigS.add(signatureData.getS());
      sigV.add(signatureData.getV()[0]);
    }

    byte[] encodedSignatures = abiEncodePackedSignatures(theSigners, sigR, sigS, sigV);
    return new SignedEvent(
        this.bcId, contractAddress, eventFunctionSignature, eventData, encodedSignatures);
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


  private String fetchSignedEvent(String eventId) throws IOException  {
    String urlStr = "http://" + this.msgStoreURL + "/messages/" + eventId + "/proofs";
    InputStream input = new URL(urlStr).openStream();
    // Input Stream Object To Start Streaming.
    try {                                 // try catch for checked exception
      BufferedReader re = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
      // Buffer Reading In UTF-8
      String Text = read(re);         // Handy Method To Read Data From BufferReader
//      JSONObject json = new JSONObject(Text);    //Creating A JSON
//
//      "0x" + resp[0].proof

      return null;
    } catch (Exception e) {
      return null;
    } finally {
      input.close();
    }
  }


  public String read(Reader re) throws IOException {     // class Declaration
    StringBuilder str = new StringBuilder();     // To Store Url Data In String.
    int temp;
    do {

      temp = re.read();       //reading Charcter By Chracter.
      str.append((char) temp);

    } while (temp != -1);
    //  re.read() return -1 when there is end of buffer , data or end of file.

    return str.toString();

  }


  private String getEventID(BlockchainId bcId, TransactionReceipt txr, String contractAddress, byte[] eventFunctionSignature) {
    String chainId = bcId.toPlainString();

    List<Log> logs = txr.getLogs();
    for (Log log: logs) {
      if (log.getAddress().equalsIgnoreCase(contractAddress) &&
        log.getTopics().get(0).equalsIgnoreCase(FormatConversion.byteArrayToString(eventFunctionSignature))) {

        String eventAddr = log.getAddress();
        String blockNumber = log.getBlockNumberRaw();
        String txIndex = log.getTransactionIndexRaw();
        String logIndex = log.getLogIndexRaw();
        return "chain" + chainId + "-" + eventAddr + "-" + blockNumber + "-" + txIndex + "-" + logIndex;
      }
    }
    throw new RuntimeException("Event not found");
  }
//
//
//  // setupObserver sets up observer.
//  func setupObserver(url string, chainID *big.Int, chainAP string, contractType string, contractAddr common.Address) error {
//    success, err := observerapi.RequestStartObserve(url, chainID, chainAP, contractType, contractAddr)
//    if err != nil {
//      return err
//    }
//    if !success {
//      return fmt.Errorf("failed.")
//    }
//    return nil
//  }
//
//  // setupRelayer sets up relayer.
//  func setupRelayer(url string, chainID *big.Int, contractAddr common.Address, keyType byte, key []byte) error {
//    success, err := relayerapi.RequestSetKey(url, chainID, contractAddr, keyType, key)
//    if err != nil {
//      return err
//    }
//    if !success {
//      return fmt.Errorf("failed.")
//    }
//    return nil
//  }
//
//  // setupDispatcher sets up dispatcher.
//  func setupDispatcher(url string, chainID *big.Int, chainAP string, key []byte, contractAddr common.Address, esAddr common.Address) error {
//    success, err := dispatcherapi.RequestSetTransactionOpts(url, chainID, chainAP, key)
//    if err != nil {
//      return err
//    }
//    if !success {
//      return fmt.Errorf("failed.")
//    }
//    success, err = dispatcherapi.RequestSetVerifierAddr(url, chainID, contractAddr, esAddr)
//    if err != nil {
//      return err
//    }
//    if !success {
//      return fmt.Errorf("failed.")
//    }
//    return nil
//  }

  // setupMessageStore sets up message store.
  private void setupMessageStore(String msgDispatcherUrl, String msgStoreAddr) throws Exception {

    final byte SetMsgStoreAddrReqType = 6;
    Bytes type = Bytes.of(SetMsgStoreAddrReqType);
    Bytes body = Bytes.wrap(msgStoreAddr.getBytes(StandardCharsets.UTF_8));
    Bytes all = Bytes.concatenate(type, body);
    byte[] requestBody = all.toArray();

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(msgDispatcherUrl))
            .POST(HttpRequest.BodyPublishers.ofByteArray(requestBody))
            .build();

    HttpResponse<String> response = client.send(request,
            HttpResponse.BodyHandlers.ofString());

    System.out.println(response.body());
  }


  public static void main(String args[]) throws Exception {
    AttestorSigner a = new AttestorSigner(null, null);
    a.setupMessageStore("127.0.0.1:9725","msgstore:8080");
  }
}




//	assert.Empty(t, setupObserver("127.0.0.1:9527", big.NewInt(31), "ws://bc31node1:8546", "GPACT", gpactAddrA))
//            assert.Empty(t, setupObserver("127.0.0.1:9528", big.NewInt(32), "ws://bc32node1:8546", "GPACT", gpactAddrB))
//            assert.Empty(t, setupRelayer("127.0.0.1:9625", big.NewInt(0), common.Address{}, signer.SECP256K1_KEY_TYPE, relayerKey))
//            assert.Empty(t, setupMessageStore("127.0.0.1:9725", "msgstore:8080"))


