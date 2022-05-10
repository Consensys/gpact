package net.consensys.gpact.examples.sfc.erc20bridge;

import net.consensys.gpact.helpers.AbstractExampleTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class SfcCrosschainTokenBridgeTest extends AbstractExampleTest {

  // TODO Remove disable
  @Disabled
  @Test
  public void directSignMultipleBlockchainMinting() throws Exception {
    String tempPropsFile = createPropertiesFile(true, false, false);
    ERC20TokenBridgeExample.main(new String[] {tempPropsFile}, false);
  }

  // TODO Remove disable
  @Disabled
  @Test
  public void directSignMultipleBlockchainMassC() throws Exception {
    String tempPropsFile = createPropertiesFile(true, false, false);
    ERC20TokenBridgeExample.main(new String[] {tempPropsFile}, true);
  }
}
