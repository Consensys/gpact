package net.consensys.gpact.examples.gpact.returnmultivalues;

import net.consensys.gpact.functioncall.gpact.GpactCrossControlManagerGroup;
import net.consensys.gpact.helpers.AbstractExampleTest;
import org.junit.jupiter.api.Test;

public class ReturnMultipleValues extends AbstractExampleTest {

  @Test
  public void fakeMessagingSerialMultiBlockchainGpactV1() throws Exception {
    String tempPropsFile = createPropertiesFile(MessagingType.FAKE, true, false);
    ReturnMultiExample.main(
        new String[] {GpactCrossControlManagerGroup.GpactVersion.V1.toString(), tempPropsFile});
  }

  @Test
  public void fakeMessagingSerialMultiBlockchainGpactV2() throws Exception {
    String tempPropsFile = createPropertiesFile(MessagingType.FAKE, true, false);
    ReturnMultiExample.main(
        new String[] {GpactCrossControlManagerGroup.GpactVersion.V2.toString(), tempPropsFile});
  }
}
