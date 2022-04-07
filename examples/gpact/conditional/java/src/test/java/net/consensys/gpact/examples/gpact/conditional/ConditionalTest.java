package net.consensys.gpact.examples.gpact.conditional;

import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.helpers.AbstractExampleTest;
import net.consensys.gpact.messaging.common.AttestorRelayerConfigurer;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

public class ConditionalTest extends AbstractExampleTest {

  @Disabled
  @Test
  public void directSignSerialSingleBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(true, true, true);
    Main.main(new String[] {tempPropsFile});
  }

  @Disabled
  @Test
  public void transferSerialSingleBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(false, true, true);
    Main.main(new String[] {tempPropsFile});
  }

  @Test
  public void directSignSerialMultiBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(true, true, false);
    Main.main(new String[] {tempPropsFile});
  }

  @Disabled
  @Test
  public void transferSerialMultiBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(false, true, false);
    Main.main(new String[] {tempPropsFile});
  }


  @Test
  public void test() throws Exception {
    AttestorRelayerConfigurer.setupMessageStore("http://127.0.0.1:9725","msgstore:8080");


    AttestorRelayerConfigurer.setupRelayer("http://127.0.0.1:9625", new BlockchainId(BigInteger.valueOf(31)),
            "1AB401234567722200112233445566778899AB22",  new byte[32]);

  }


}
