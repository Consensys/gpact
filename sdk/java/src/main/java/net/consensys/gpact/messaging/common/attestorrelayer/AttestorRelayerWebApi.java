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
import net.consensys.gpact.common.CrosschainProtocolStackException;
import net.consensys.gpact.functioncall.CrosschainFunctionCallException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tuweni.bytes.Bytes;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;


/**
 * Configures Attestor / Relayer components.
 *
 */
public class AttestorRelayerConfigurer {
  static final Logger LOG = LogManager.getLogger(AttestorRelayerConfigurer.class);

  // Observer API
  public static final byte START_OBSERVE_REQ_TYPE = 1;
  public static final byte STOP_OBSERVE_REQ_TYPE = 2;

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


  public static void setupObserver(String observerUrl, BlockchainId bcId, String bcWsUrl, String contractType, String crosschainControlAddr) throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode user = mapper.createObjectNode();
    user.put("chain_id", bcId.toPlainBase10String());
    user.put("chain_ap", bcWsUrl);
    user.put("contract_type", contractType);
    user.put("contract_addr", crosschainControlAddr);
//    String jsonStr1 = mapper.writer().writeValueAsString(user);
    byte[] json = mapper.writer().writeValueAsBytes(user);

    Bytes type = Bytes.of(START_OBSERVE_REQ_TYPE);
    Bytes body = Bytes.wrap(json);
    Bytes all = Bytes.concatenate(type, body);
    byte[] requestBody = all.toArray();

    config("Observer", observerUrl, requestBody);
  }

  public static void setupRelayer(String relayerUrl, BlockchainId bcId, String gpactContractAddr, byte[] pKey) throws Exception {
    byte keyType = SECP256K1_KEY_TYPE;

    ObjectMapper mapper = new ObjectMapper();
    ObjectNode user = mapper.createObjectNode();
    user.put("chain_id", bcId.toPlainBase10String());
    user.put("contract_addr", gpactContractAddr);
    user.put("key_type", keyType);
    user.put("key", pKey);
//    String jsonStr1 = mapper.writer().writeValueAsString(user);
    byte[] json = mapper.writer().writeValueAsBytes(user);

    Bytes type = Bytes.of(SET_KET_REQ_TYPE);
    Bytes body = Bytes.wrap(json);
    Bytes all = Bytes.concatenate(type, body);
    byte[] requestBody = all.toArray();

    config("Relayer", relayerUrl, requestBody);
  }

  public static void setupDispatcher(String msgDispatcherUrl, String msgStoreAddr) throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode user = mapper.createObjectNode();
    user.put("msg_store_url", msgStoreAddr);
//    String jsonStr1 = mapper.writer().writeValueAsString(user);
    byte[] json = mapper.writer().writeValueAsBytes(user);

    Bytes type = Bytes.of(SET_MSG_STORE_ADDR_REQ_TYPE);
    Bytes body = Bytes.wrap(json);
    Bytes all = Bytes.concatenate(type, body);
    byte[] requestBody = all.toArray();

    config("MessageStore", msgDispatcherUrl, requestBody);
  }


  private static void config(String component, String uri, byte[] requestBody) throws CrosschainProtocolStackException {
    HttpResponse<String> response;
    try {
      response = httpPost(uri, requestBody);
    } catch (InterruptedException | IOException ex1) {
      throw new CrosschainProtocolStackException("Error while configuring " + component, ex1);
    }

    if (response.statusCode() != 200) {
      throw new CrosschainProtocolStackException("Observer config returned HTTP status: " + response.statusCode());
    }
    if (response.body().compareToIgnoreCase("{\"success\":true}") != 0) {
      throw new CrosschainProtocolStackException("Observer config did not return success. Status: " + response.body());
    }
  }

  private static HttpResponse<String> httpPost(String uri, byte[] requestBody) throws InterruptedException, IOException {
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


