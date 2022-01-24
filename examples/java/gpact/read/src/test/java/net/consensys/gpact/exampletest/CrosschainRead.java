package net.consensys.gpact.exampletest;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class CrosschainRead extends AbstractExampleTest {

  @Disabled
  @Test
  public void directSignSerialSingleBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(true, true, true);
    net.consensys.gpact.examples.read.Main.main(new String[] {tempPropsFile});
  }

  @Disabled
  @Test
  public void directSignParallelSingleBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(true, false, true);
    net.consensys.gpact.examples.read.Main.main(new String[] {tempPropsFile});
  }

  @Disabled
  @Test
  public void transferSerialSingleBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(false, true, true);
    net.consensys.gpact.examples.read.Main.main(new String[] {tempPropsFile});
  }

  @Disabled
  @Test
  public void transferParallelSingleBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(false, false, true);
    net.consensys.gpact.examples.read.Main.main(new String[] {tempPropsFile});
  }

  @Test
  public void directSignSerialMultiBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(true, true, false);
    net.consensys.gpact.examples.read.Main.main(new String[] {tempPropsFile});
  }

  @Disabled
  @Test
  public void directSignParallelMultiBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(true, false, false);
    net.consensys.gpact.examples.read.Main.main(new String[] {tempPropsFile});
  }

  @Test
  public void transferSerialMultiBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(false, true, false);
    net.consensys.gpact.examples.read.Main.main(new String[] {tempPropsFile});
  }

  @Disabled
  @Test
  public void transferParallelMultiBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(false, false, false);
    net.consensys.gpact.examples.read.Main.main(new String[] {tempPropsFile});
  }
}
