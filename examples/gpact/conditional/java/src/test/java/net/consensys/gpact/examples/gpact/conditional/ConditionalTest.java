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
}
