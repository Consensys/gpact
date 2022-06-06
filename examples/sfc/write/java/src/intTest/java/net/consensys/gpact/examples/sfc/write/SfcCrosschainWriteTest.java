package net.consensys.gpact.examples.sfc.write;

import net.consensys.gpact.helpers.AbstractExampleTest;
import org.junit.jupiter.api.Test;

public class SfcCrosschainWriteTest extends AbstractExampleTest {

  // Currently, the Relayer does not support event attestation for SFC.
  @Test
  public void directSignMultipleBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(true, false, false);
    SfcCrosschainWrite.main(new String[] {tempPropsFile});
  }

  @Test
  public void eventRelayMultipleBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(MessagingType.EVENT_RELAY, false, false);
    SfcCrosschainWrite.main(new String[] {tempPropsFile});
  }
}
