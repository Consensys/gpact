package net.consensys.gpact.examples.sfc.erc721bridge;

import net.consensys.gpact.helpers.AbstractExampleTest;
import org.junit.jupiter.api.Test;

public class SfcErc721BridgeExampleTest extends AbstractExampleTest {

  @Test
  public void directSignMultipleBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(MessagingType.EVENT_SIGNING, false, false);
    Erc721TokenBridgeExample.main(new String[] {tempPropsFile});
  }

  @Test
  public void eventRelayMultipleBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(MessagingType.EVENT_RELAY, false, false);
    Erc721TokenBridgeExample.main(new String[] {tempPropsFile});
  }
}
