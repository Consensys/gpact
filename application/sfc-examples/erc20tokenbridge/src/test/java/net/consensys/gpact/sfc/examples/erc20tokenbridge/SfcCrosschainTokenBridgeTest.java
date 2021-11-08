package net.consensys.gpact.sfc.examples.erc20tokenbridge;

import net.consensys.gpact.exampletest.AbstractExampleTest;
import org.junit.Test;

public class SfcCrosschainTokenBridgeTest extends AbstractExampleTest {

  @Test
  public void directSignMultipleBlockchainMinting() throws Exception {
    String tempPropsFile = createPropertiesFile(true, false, false);
    TokenBridge.main(new String[] {tempPropsFile}, false);
  }

  @Test
  public void directSignMultipleBlockchainMassC() throws Exception {
    String tempPropsFile = createPropertiesFile(true, false, false);
    TokenBridge.main(new String[] {tempPropsFile}, true);
  }
}
