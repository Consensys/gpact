package net.consensys.gpact.exampletest;

import net.consensys.gpact.examples.write.GpactCrosschainWrite;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class CrosschainWrite extends AbstractExampleTest {

  @Disabled
  @Test
  public void directSignSerialSingleBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(true, true, true);
    GpactCrosschainWrite.main(new String[] {tempPropsFile});
  }

  @Disabled
  @Test
  public void directSignParallelSingleBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(true, false, true);
    GpactCrosschainWrite.main(new String[] {tempPropsFile});
  }

  @Disabled
  @Test
  public void transferSerialSingleBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(false, true, true);
    GpactCrosschainWrite.main(new String[] {tempPropsFile});
  }

  @Disabled
  @Test
  public void transferParallelSingleBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(false, false, true);
    GpactCrosschainWrite.main(new String[] {tempPropsFile});
  }

  @Test
  public void directSignSerialMultiBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(true, true, false);
    GpactCrosschainWrite.main(new String[] {tempPropsFile});
  }

  @Test
  public void directSignParallelMultiBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(true, false, false);
    GpactCrosschainWrite.main(new String[] {tempPropsFile});
  }

  @Test
  public void transferSerialMultiBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(false, true, false);
    GpactCrosschainWrite.main(new String[] {tempPropsFile});
  }

  @Test
  public void transferParallelMultiBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(false, false, false);
    GpactCrosschainWrite.main(new String[] {tempPropsFile});
  }
}
