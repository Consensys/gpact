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
package net.consensys.gpact.messaging.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import net.consensys.gpact.common.BlockchainId;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tuweni.bytes.Bytes;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;


/**
 * Configures Attestor / Relayer components.
 *
 */
public class AttestorRelayerConfigurer {
  static final Logger LOG = LogManager.getLogger(AttestorRelayerConfigurer.class);

  // Message Dispatcher API
  public static final byte SET_TRANSACTION_OPT_REQ_TYPE = 1;
  public static final byte GET_CHAIN_AP_REQ_TYPE = 2;
  public static final byte GET_AUTH_ADDR_REQ_TYPE = 3;
  public static final byte SET_VERIFIER_ADDR_REQ_TYPE = 4;
  public static final byte GET_VERIFIER_ADDR_REQ_TYPE = 5;
  public static final byte SET_MSG_STORE_ADDR_REQ_TYPE = 6;

  // Relayer API
  public static final byte SET_KET_REQ_TYPE = 1;
  public static final byte GET_ADDR_REQ_TYPE = 2;

  public static final byte SECP256K1_KEY_TYPE = 1;

  private AttestorRelayerConfigurer() {
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
    // setupRelayer sets up relayer.
    public static void setupRelayer(String relayerUrl, BlockchainId bcId, String gpactContractAddr, byte[] pKey) throws Exception {
      byte keyType = SECP256K1_KEY_TYPE;

      ObjectMapper mapper = new ObjectMapper();

      // create a JSON object
      ObjectNode user = mapper.createObjectNode();
      user.put("chain_id", bcId.toPlainString());
      user.put("contract_addr", gpactContractAddr);
      user.put("key_type", SECP256K1_KEY_TYPE);
      user.put("key", pKey);

      // convert `ObjectNode` to pretty-print JSON
      // without pretty-print, use `user.toString()` method
      byte[] json = mapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(user);

      Bytes type = Bytes.of(SET_KET_REQ_TYPE);
      Bytes body = Bytes.wrap(json);
      Bytes all = Bytes.concatenate(type, body);
      byte[] requestBody = all.toArray();

      HttpResponse<String> response = httpPost(relayerUrl, requestBody);

      LOG.info("Status code: {}" , response.statusCode());
      LOG.info("Response: {}", response.body());


    }

  // setupMessageStore sets up message store.
  public static void setupMessageStore(String msgDispatcherUrl, String msgStoreAddr) throws Exception {
    Bytes type = Bytes.of(SET_MSG_STORE_ADDR_REQ_TYPE);
    Bytes body = Bytes.wrap(msgStoreAddr.getBytes(StandardCharsets.UTF_8));
    Bytes all = Bytes.concatenate(type, body);
    byte[] requestBody = all.toArray();

    HttpResponse<String> response = httpPost(msgDispatcherUrl, requestBody);

    LOG.info("Status code: {}" , response.statusCode());
    LOG.info("Response: {}", response.body());
  }

  private static HttpResponse<String> httpPost(String uri, byte[] requestBody) throws Exception {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(uri))
            .POST(HttpRequest.BodyPublishers.ofByteArray(requestBody))
            .build();
    return client.send(request, HttpResponse.BodyHandlers.ofString());
  }
}




//	assert.Empty(t, setupObserver("127.0.0.1:9527", big.NewInt(31), "ws://bc31node1:8546", "GPACT", gpactAddrA))
//            assert.Empty(t, setupObserver("127.0.0.1:9528", big.NewInt(32), "ws://bc32node1:8546", "GPACT", gpactAddrB))
//            assert.Empty(t, setupRelayer("127.0.0.1:9625", big.NewInt(0), common.Address{}, signer.SECP256K1_KEY_TYPE, relayerKey))
//            assert.Empty(t, setupMessageStore("127.0.0.1:9725", "msgstore:8080"))


