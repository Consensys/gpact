package net.consensys.gpact.examples.gpact.trade;

import net.consensys.gpact.functioncall.gpact.GpactCrossControlManagerGroup;
import net.consensys.gpact.helpers.AbstractExampleTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class TradeTest extends AbstractExampleTest {

  @Test
  public void directSignSerialSingleBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(true, true, true);
    TradeExample.main(
        new String[] {GpactCrossControlManagerGroup.GpactVersion.V1.toString(), tempPropsFile});
  }

  // Does not work
  @Disabled
  @Test
  public void directSignParallelSingleBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(true, false, true);
    TradeExample.main(
        new String[] {GpactCrossControlManagerGroup.GpactVersion.V1.toString(), tempPropsFile});
  }

  @Test
  public void transferSerialSingleBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(false, true, true);
    TradeExample.main(
        new String[] {GpactCrossControlManagerGroup.GpactVersion.V1.toString(), tempPropsFile});
  }

  // Does not work
  @Disabled
  @Test
  public void transferParallelSingleBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(false, false, true);
    TradeExample.main(
        new String[] {GpactCrossControlManagerGroup.GpactVersion.V1.toString(), tempPropsFile});
  }

  // Needs five blockchains configured
  @Disabled
  @Test
  public void directSignSerialMultiBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(true, true, false);
    TradeExample.main(
        new String[] {GpactCrossControlManagerGroup.GpactVersion.V1.toString(), tempPropsFile});
  }

  // Needs five blockchains configured
  @Disabled
  @Test
  public void directSignParallelMultiBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(true, false, false);
    TradeExample.main(
        new String[] {GpactCrossControlManagerGroup.GpactVersion.V1.toString(), tempPropsFile});
  }

  // Needs five blockchains configured
  @Disabled
  @Test
  public void transferSerialMultiBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(false, true, false);
    TradeExample.main(
        new String[] {GpactCrossControlManagerGroup.GpactVersion.V1.toString(), tempPropsFile});
  }

  // Does not currently work.
  @Disabled
  @Test
  public void transferParallelMultiBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(false, false, false);
    TradeExample.main(
        new String[] {GpactCrossControlManagerGroup.GpactVersion.V1.toString(), tempPropsFile});
  }

  @Test
  public void fakeMessagingSerialSingleBlockchainGpactV2() throws Exception {
    String tempPropsFile = createPropertiesFile(MessagingType.FAKE, true, true);
    TradeExample.main(
        new String[] {GpactCrossControlManagerGroup.GpactVersion.V2.toString(), tempPropsFile});
  }
}
