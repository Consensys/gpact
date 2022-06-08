package net.consensys.gpact.messaging.common.attestorrelayer;

import java.math.BigInteger;
import net.consensys.gpact.common.BlockchainId;
import org.junit.jupiter.api.Test;

public class AttestorRelayerConfigurerTest {
  @Test
  public void testSetupObserver() throws Exception {
    AttestorRelayerWebApi.setupObserver(
        "http://127.0.0.1:9525",
        new BlockchainId(BigInteger.valueOf(31)),
        "ws://bc31node1:8546",
        "GPACT",
        "1AB401234567722200112233445566778899AB22", AttestorRelayer.WatcherType.REALTIME);
  }

  @Test
  public void testSetupRelayer() throws Exception {
    AttestorRelayerWebApi.setupRelayer(
        "http://127.0.0.1:9625",
        new BlockchainId(BigInteger.valueOf(31)),
        "1AB401234567722200112233445566778899AB22",
        new byte[32]);
  }

  @Test
  public void testSetupDispatcher() throws Exception {
    AttestorRelayerWebApi.setupDispatcherForMsgStore("http://127.0.0.1:9725", "msgstore:8080");
  }
}
