package net.consensys.gpact.messaging.common.attestorrelayer;

import java.math.BigInteger;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.common.test.DummyAddressGenerator;
import org.junit.jupiter.api.Test;

public class AttestorRelayerTest {

  @Test
  public void testDockerConfig() throws Exception {
    byte[] signingKey = new byte[32];

    BlockchainId bcId31 = new BlockchainId(BigInteger.valueOf(31));
    BlockchainId bcId32 = new BlockchainId(BigInteger.valueOf(32));
    BlockchainId bcId33 = new BlockchainId(BigInteger.valueOf(33));

    String crosschainControlAddr31 = DummyAddressGenerator.gen();
    String crosschainControlAddr32 = DummyAddressGenerator.gen();
    String crosschainControlAddr33 = DummyAddressGenerator.gen();

    AttestorRelayer relayer = new AttestorRelayer("http://127.0.0.1:9625", signingKey);
    relayer.addNewSource(
        bcId31, crosschainControlAddr31, "GPACT", "http://127.0.0.1:9525", "ws://bc31node1:8546");
    relayer.addNewSource(
        bcId32, crosschainControlAddr32, "GPACT", "http://127.0.0.1:9526", "ws://bc32node1:8546");
    relayer.addNewSource(
        bcId33, crosschainControlAddr33, "GPACT", "http://127.0.0.1:9527", "ws://bc33node1:8546");
    relayer.addMessageStore("http://127.0.0.1:9725", "msgstore:8080", "127.0.0.1:8080");
  }
}
