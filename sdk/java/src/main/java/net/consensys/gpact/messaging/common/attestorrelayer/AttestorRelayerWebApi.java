/*
 * Copyright 2022 ConsenSys Software Inc
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
package net.consensys.gpact.messaging.common.attestorrelayer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.common.CrosschainProtocolStackException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tuweni.bytes.Bytes;

/** Configures Attestor / Relayer components. */
public class AttestorRelayerWebApi {
  static final Logger LOG = LogManager.getLogger(AttestorRelayerWebApi.class);

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

  private AttestorRelayerWebApi() {}

  public static void setupObserver(
      String observerUrl,
      BlockchainId bcId,
      String bcWsUrl,
      String contractType,
      String crosschainControlAddr)
      throws CrosschainProtocolStackException {
    LOG.info(
        "SetupObserver: ChainId: {}, ChainAP: {}, ContractType: {}, ContractAddr: {}, ObserverURL: {}",
        bcId.toDecimalString(),
        bcWsUrl,
        contractType,
        crosschainControlAddr,
        observerUrl);

    ObjectMapper mapper = new ObjectMapper();
    ObjectNode user = mapper.createObjectNode();
    user.put("chain_id", bcId.toDecimalString());
    user.put("chain_ap", bcWsUrl);
    user.put("contract_type", contractType);
    user.put("contract_addr", crosschainControlAddr);
    //    String jsonStr1 = mapper.writer().writeValueAsString(user);
    byte[] json;
    try {
      json = mapper.writer().writeValueAsBytes(user);
    } catch (JsonProcessingException ex) {
      throw new CrosschainProtocolStackException("Observer", ex);
    }

    Bytes type = Bytes.of(START_OBSERVE_REQ_TYPE);
    Bytes body = Bytes.wrap(json);
    Bytes all = Bytes.concatenate(type, body);
    byte[] requestBody = all.toArray();

    config("Observer", observerUrl, requestBody);
  }

  public static void stopObserver(String observerUrl) throws CrosschainProtocolStackException {
    LOG.info("StopObserver: ObserverURL: {}", observerUrl);

    Bytes type = Bytes.of(STOP_OBSERVE_REQ_TYPE);
    byte[] requestBody = type.toArray();

    config("Observer", observerUrl, requestBody);
  }

  public static void setupRelayer(
      String relayerUrl, BlockchainId bcId, String crosschainControlAddr, byte[] pKey)
      throws CrosschainProtocolStackException {
    byte keyType = SECP256K1_KEY_TYPE;

    LOG.info(
        "SetupRelayer: ChainId: {}, ContractAddr: {}, RelayerURL: {}",
        bcId.toDecimalString(),
        crosschainControlAddr,
        relayerUrl);

    ObjectMapper mapper = new ObjectMapper();
    ObjectNode user = mapper.createObjectNode();
    //    user.put("chain_id", bcId.toDecimalString());
    //    user.put("contract_addr", crosschainControlAddr);
    user.put("chain_id", "0");
    user.put("contract_addr", "0x0");
    user.put("key_type", keyType);
    user.put("key", pKey);
    //    String jsonStr1 = mapper.writer().writeValueAsString(user);
    byte[] json;
    try {
      json = mapper.writer().writeValueAsBytes(user);
    } catch (JsonProcessingException ex) {
      throw new CrosschainProtocolStackException("Relayer", ex);
    }

    Bytes type = Bytes.of(SET_KET_REQ_TYPE);
    Bytes body = Bytes.wrap(json);
    Bytes all = Bytes.concatenate(type, body);
    byte[] requestBody = all.toArray();

    config("Relayer", relayerUrl, requestBody);
  }

  public static void setupDispatcher(String msgDispatcherUrl, String msgStoreAddr)
      throws CrosschainProtocolStackException {
    LOG.info("SetupDispatcher: MsgStore: {}, DispatcherURL: {}", msgStoreAddr, msgDispatcherUrl);

    ObjectMapper mapper = new ObjectMapper();
    ObjectNode user = mapper.createObjectNode();
    user.put("msg_store_url", msgStoreAddr);
    //    String jsonStr1 = mapper.writer().writeValueAsString(user);
    byte[] json;
    try {
      json = mapper.writer().writeValueAsBytes(user);
    } catch (JsonProcessingException ex) {
      throw new CrosschainProtocolStackException("Dispatcher", ex);
    }

    Bytes type = Bytes.of(SET_MSG_STORE_ADDR_REQ_TYPE);
    Bytes body = Bytes.wrap(json);
    Bytes all = Bytes.concatenate(type, body);
    byte[] requestBody = all.toArray();

    config("Dispatcher", msgDispatcherUrl, requestBody);
  }

  private static void config(String component, String uri, byte[] requestBody)
      throws CrosschainProtocolStackException {
    HttpResponse<String> response;
    try {
      response = httpPost(uri, requestBody);
    } catch (InterruptedException | IOException ex1) {
      throw new CrosschainProtocolStackException(
          "Error while configuring " + component + ", at: " + uri, ex1);
    }

    if (response.statusCode() != 200) {
      throw new CrosschainProtocolStackException(
          component + " config returned HTTP status: " + response.statusCode());
    }
    if (response.body().compareToIgnoreCase("{\"success\":true}") != 0) {
      throw new CrosschainProtocolStackException(
          component + " config did not return success. Status: " + response.body());
    }
  }

  private static class Proof {
    public String proof_type;
    public long created;
    public String proof;
  }

  public static String fetchSignedEvent(String msgStoreURL, String eventId)
      throws CrosschainProtocolStackException {
    String uriStr = msgStoreURL + "/messages/" + eventId + "/proofs";

    long backOffTime = 1000;
    final double backOffScale = 1.5;
    int backOffCountDown = 10;

    String body = null;
    boolean done = false;
    while (!done) {
      try {
        HttpResponse<String> response = httpGet(uriStr);
        if (response.statusCode() != 200) {
          throw new CrosschainProtocolStackException(
              "Fetch signed event returned HTTP status: "
                  + response.statusCode()
                  + ", "
                  + response.body());
        }
        body = response.body();

        done = true;
      } catch (InterruptedException | IOException | CrosschainProtocolStackException ex1) {
        if (backOffCountDown == 0) {
          LOG.warn("Error while fetching signed event: {}, Error: {}", uriStr, ex1.toString());
          throw new CrosschainProtocolStackException("Error while fetching signed event ", ex1);
        }
        LOG.info(" Fetch signed event failed. Waiting another {} ms", backOffTime);
        try {
          Thread.sleep(backOffTime);
        } catch (Exception ex2) {
          // Do nothing
        }
        backOffTime = (long) (backOffScale * backOffTime);
        backOffCountDown--;
      }
    }

    // LOG.info(" Fetched Signed Event: {}" + body);
    Proof[] proofs;
    try {
      ObjectMapper mapper = new ObjectMapper();
      proofs = mapper.readValue(body, Proof[].class);
    } catch (Exception e) {
      throw new CrosschainProtocolStackException("Fetch signed event JSON decoding: ", e);
    }

    if (proofs.length == 0) {
      final String msg = "Fetch signed event contains no proofs";
      LOG.error(msg);
      throw new CrosschainProtocolStackException(msg);
    }
    if (proofs.length != 1) {
      final String msg =
          "Fetch signed event contains more than one proofs. SDK can currently only handle one proof";
      LOG.error(msg);
      throw new CrosschainProtocolStackException(msg);
    }

    return proofs[0].proof;
  }

  private static HttpResponse<String> httpPost(String uri, byte[] requestBody)
      throws InterruptedException, IOException {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(URI.create(uri))
            .timeout(Duration.ofSeconds(20)) // Hard code the timeout as 20 seconds
            .POST(HttpRequest.BodyPublishers.ofByteArray(requestBody))
            .build();

    return client.send(request, HttpResponse.BodyHandlers.ofString());
  }

  private static HttpResponse<String> httpGet(String uri) throws InterruptedException, IOException {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).GET().build();
    return client.send(request, HttpResponse.BodyHandlers.ofString());
  }
}
