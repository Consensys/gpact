package net.consensys.gpact.exampletest;

import org.junit.Test;

/** Run all of the single blockchain tests. */
public class SingleChainHotelTrainTest extends AbstractExampleTest {

  @Test
  public void singlebcHotelTrain() throws Exception {
    String tempPropsFile = createPropertiesFile(true, true, true);
    net.consensys.gpact.examples.singlebc.hoteltrain.HotelTrain.main(new String[] {tempPropsFile});
  }
}
