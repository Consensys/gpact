package net.consensys.gpact.examples.sfc.erc721bridge;

import net.consensys.gpact.examplehelpers.AbstractExampleTest;
import org.junit.jupiter.api.Test;

public class SfcErc721BridgeExampleTest extends AbstractExampleTest {

  @Test
  public void directSignMultipleBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(true, false, false);
    Erc721TokenBridgeExample.main(new String[] {tempPropsFile});
  }
}
