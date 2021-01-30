package net.consensys.gpact.exampletest;

import net.consensys.gpact.examples.twochain.Main;
import org.junit.Test;


public class CrosschainConditional extends AbstractExampleTest {

  @Test
  public void directSignSerialSingleBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(true, true, true);
    Main.main(new String[]{tempPropsFile});
  }

  @Test
  public void transferSerialSingleBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(false, true, true);
    Main.main(new String[]{tempPropsFile});
  }

  @Test
  public void directSignSerialMultiBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(true, true, false);
    Main.main(new String[]{tempPropsFile});
  }

  @Test
  public void transferSerialMultiBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(false, true, false);
    Main.main(new String[]{tempPropsFile});
  }
}
