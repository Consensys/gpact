package net.consensys.gpact.exampletest;


import net.consensys.gpact.examples.trade.Main;
import org.junit.Ignore;
import org.junit.Test;

public class CrosschainTrade extends AbstractExampleTest {

  @Test
  public void directSignSerialSingleBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(true, true, true);
    Main.main(new String[]{tempPropsFile});
  }

  // Does not work
  @Ignore
  @Test
  public void directSignParallelSingleBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(true, false, true);
    Main.main(new String[]{tempPropsFile});
  }

  @Ignore
  @Test
  public void transferSerialSingleBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(false, true, true);
    Main.main(new String[]{tempPropsFile});
  }

  // Does not work
  @Ignore
  @Test
  public void transferParallelSingleBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(false, false, true);
    Main.main(new String[]{tempPropsFile});
  }

  // Needs five blockchains configured
  @Ignore
  @Test
  public void directSignSerialMultiBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(true, true, false);
    Main.main(new String[]{tempPropsFile});
  }

  // Needs five blockchains configured
  @Ignore
  @Test
  public void directSignParallelMultiBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(true, false, false);
    Main.main(new String[]{tempPropsFile});
  }

  // Needs five blockchains configured
  @Ignore
  @Test
  public void transferSerialMultiBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(false, true, false);
    Main.main(new String[]{tempPropsFile});
  }

  // Does not currently work.
  @Ignore
  @Test
  public void transferParallelMultiBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(false, false, false);
    Main.main(new String[]{tempPropsFile});
  }
}
