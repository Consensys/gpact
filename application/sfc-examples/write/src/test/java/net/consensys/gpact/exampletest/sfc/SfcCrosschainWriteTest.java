package net.consensys.gpact.exampletest.sfc;

import net.consensys.gpact.exampletest.AbstractExampleTest;
import net.consensys.gpact.sfc.examples.write.SfcCrosschainWrite;
import org.junit.Test;

public class SfcCrosschainWriteTest extends AbstractExampleTest {

  @Test
  public void directSignMultipleBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(true, false, false);
    SfcCrosschainWrite.main(new String[] {tempPropsFile});
  }
}
