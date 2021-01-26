package tech.pegasys.ltacfc.exampletest;

import org.junit.Test;


public class CrosschainConditional extends AbstractExampleTest {

  @Test
  public void directSignSerialSingleBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(true, true, true);
    tech.pegasys.ltacfc.examples.twochain.Main.main(new String[]{tempPropsFile});
  }

  @Test
  public void transferSerialSingleBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(false, true, true);
    tech.pegasys.ltacfc.examples.twochain.Main.main(new String[]{tempPropsFile});
  }

  @Test
  public void directSignSerialMultiBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(true, true, false);
    tech.pegasys.ltacfc.examples.twochain.Main.main(new String[]{tempPropsFile});
  }

  @Test
  public void transferSerialMultiBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(false, true, false);
    tech.pegasys.ltacfc.examples.twochain.Main.main(new String[]{tempPropsFile});
  }
}
