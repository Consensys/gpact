package net.consensys.gpact.messaging.common;

import net.consensys.gpact.common.BlockchainId;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

public class AttestorRelayerConfigurerTest {
  @Test
  public void testSetupMessageStore() throws Exception {
    System.out.println("test");
    AttestorRelayerConfigurer.setupRelayer("http://127.0.0.1:9625", new BlockchainId(BigInteger.valueOf(31)),
            "1AB401234567722200112233445566778899AB22",  new byte[32]);
  }


  @Test
  public void testSetupRelayer() throws Exception {
    AttestorRelayerConfigurer.setupRelayer("http://127.0.0.1:9625", new BlockchainId(BigInteger.valueOf(31)),
            "1AB401234567722200112233445566778899AB22",  new byte[32]);
  }


}
