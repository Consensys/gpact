package tech.pegasys.ltacfc.exampletest;

import org.junit.Test;

public class CrosschainRead extends AbstractExampleTest {

  @Test
  public void directSignSerialSingleBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(true, true, true);
    tech.pegasys.ltacfc.examples.read.Main.main(new String[]{tempPropsFile});
  }

  @Test
  public void directSignParallelSingleBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(true, false, true);
    tech.pegasys.ltacfc.examples.read.Main.main(new String[]{tempPropsFile});
  }

  @Test
  public void transferSerialSingleBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(false, true, true);
    tech.pegasys.ltacfc.examples.read.Main.main(new String[]{tempPropsFile});
  }

  @Test
  public void transferParallelSingleBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(false, false, true);
    tech.pegasys.ltacfc.examples.read.Main.main(new String[]{tempPropsFile});
  }

  @Test
  public void directSignSerialMultiBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(true, true, false);
    tech.pegasys.ltacfc.examples.read.Main.main(new String[]{tempPropsFile});
  }

  @Test
  public void directSignParallelMultiBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(true, false, false);
    tech.pegasys.ltacfc.examples.read.Main.main(new String[]{tempPropsFile});
  }

  @Test
  public void transferSerialMultiBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(false, true, false);
    tech.pegasys.ltacfc.examples.read.Main.main(new String[]{tempPropsFile});
  }

  @Test
  public void transferParallelMultiBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(false, false, false);
    tech.pegasys.ltacfc.examples.read.Main.main(new String[]{tempPropsFile});
  }
}
