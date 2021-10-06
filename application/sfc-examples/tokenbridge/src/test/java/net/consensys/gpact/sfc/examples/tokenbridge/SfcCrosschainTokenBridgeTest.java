package net.consensys.gpact.sfc.examples.tokenbridge;

import net.consensys.gpact.exampletest.AbstractExampleTest;
import org.junit.Test;

public class SfcCrosschainTokenBridgeTest extends AbstractExampleTest {

  @Test
  public void directSignMultipleBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(true, false, false);
    TokenBridge.main(new String[]{tempPropsFile});
  }
}
