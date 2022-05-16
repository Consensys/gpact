package net.consensys.gpact.examples.sfc.erc20bridge;

import net.consensys.gpact.helpers.AbstractExampleTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class SfcCrosschainTokenBridgeTest extends AbstractExampleTest {

  // Currently, the Relayer does not support event attestation for SFC.
  @Disabled
  @Test
  public void directSignMultipleBlockchainMinting() throws Exception {
    String tempPropsFile = createPropertiesFile(true, false, false);
    ERC20TokenBridgeExample.main(new String[] {tempPropsFile}, false);
  }

  // Currently, the Relayer does not support event attestation for SFC.
  @Disabled
  @Test
  public void directSignMultipleBlockchainMassC() throws Exception {
    String tempPropsFile = createPropertiesFile(true, false, false);
    ERC20TokenBridgeExample.main(new String[] {tempPropsFile}, true);
  }

  @Test
  public void eventRelayMultipleBlockchainMinting() throws Exception {
    String tempPropsFile = createPropertiesFile(MessagingType.EVENT_RELAY, false, false);
    ERC20TokenBridgeExample.main(new String[] {tempPropsFile}, false);
  }

  @Test
  public void eventRelayMultipleBlockchainMassC() throws Exception {
    String tempPropsFile = createPropertiesFile(MessagingType.EVENT_RELAY, false, false);
    ERC20TokenBridgeExample.main(new String[] {tempPropsFile}, true);
  }
}
