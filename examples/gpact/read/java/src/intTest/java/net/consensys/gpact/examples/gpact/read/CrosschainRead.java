package net.consensys.gpact.examples.gpact.read;

import net.consensys.gpact.functioncall.gpact.GpactCrossControlManagerGroup;
import net.consensys.gpact.helpers.AbstractExampleTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class CrosschainRead extends AbstractExampleTest {

  @Disabled
  @Test
  public void directSignSerialSingleBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(true, true, true);
    Main.main(
        new String[] {GpactCrossControlManagerGroup.GpactVersion.V1.toString(), tempPropsFile});
  }

  @Disabled
  @Test
  public void directSignParallelSingleBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(true, false, true);
    Main.main(
        new String[] {GpactCrossControlManagerGroup.GpactVersion.V1.toString(), tempPropsFile});
  }

  @Disabled
  @Test
  public void transferSerialSingleBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(false, true, true);
    Main.main(
        new String[] {GpactCrossControlManagerGroup.GpactVersion.V1.toString(), tempPropsFile});
  }

  @Disabled
  @Test
  public void transferParallelSingleBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(false, false, true);
    Main.main(
        new String[] {GpactCrossControlManagerGroup.GpactVersion.V1.toString(), tempPropsFile});
  }

  @Test
  public void directSignSerialMultiBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(true, true, false);
    Main.main(
        new String[] {GpactCrossControlManagerGroup.GpactVersion.V1.toString(), tempPropsFile});
  }

  @Disabled
  @Test
  public void directSignParallelMultiBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(true, false, false);
    Main.main(
        new String[] {GpactCrossControlManagerGroup.GpactVersion.V1.toString(), tempPropsFile});
  }

  @Test
  public void transferSerialMultiBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(false, true, false);
    Main.main(
        new String[] {GpactCrossControlManagerGroup.GpactVersion.V1.toString(), tempPropsFile});
  }

  @Disabled
  @Test
  public void transferParallelMultiBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(false, false, false);
    Main.main(
        new String[] {GpactCrossControlManagerGroup.GpactVersion.V1.toString(), tempPropsFile});
  }

  @Test
  public void fakeMessagingSerialMultiBlockchainGpactV2() throws Exception {
    String tempPropsFile = createPropertiesFile(MessagingType.FAKE, true, false);
    Main.main(
        new String[] {GpactCrossControlManagerGroup.GpactVersion.V2.toString(), tempPropsFile});
  }
}
