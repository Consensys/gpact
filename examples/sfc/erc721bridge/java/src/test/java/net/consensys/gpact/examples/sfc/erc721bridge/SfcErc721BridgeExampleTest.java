package net.consensys.gpact.examples.sfc.erc721bridge;

import net.consensys.gpact.helpers.AbstractExampleTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class SfcErc721BridgeExampleTest extends AbstractExampleTest {

  // TODO Remove disable
  @Disabled
  @Test
  public void directSignMultipleBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(true, false, false);
    Erc721TokenBridgeExample.main(new String[] {tempPropsFile});
  }
}
