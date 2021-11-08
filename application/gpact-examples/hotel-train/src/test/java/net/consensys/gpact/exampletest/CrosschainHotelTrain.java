package net.consensys.gpact.exampletest;

import net.consensys.gpact.examples.hoteltrain.HotelTrain;
import org.junit.Test;

public class CrosschainHotelTrain extends AbstractExampleTest {

  //  @Test
  //  public void directSignSerialSingleBlockchain() throws Exception {
  //    String tempPropsFile = createPropertiesFile(true, true, true);
  //    Main.main(new String[]{tempPropsFile});
  //  }

  @Test
  public void directSignSerialMultiBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(true, true, false);
    HotelTrain.main(new String[] {tempPropsFile});
  }

  @Test
  public void transferSerialMultiBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(false, true, false);
    HotelTrain.main(new String[] {tempPropsFile});
  }

  @Test
  public void directParallelMultiBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(true, false, false);
    HotelTrain.main(new String[] {tempPropsFile});
  }
}
