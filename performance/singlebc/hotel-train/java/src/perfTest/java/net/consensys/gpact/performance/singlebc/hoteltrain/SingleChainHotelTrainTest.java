package net.consensys.gpact.performance.singlebc.hoteltrain;

import net.consensys.gpact.helpers.AbstractExampleTest;
import org.junit.jupiter.api.Test;

/** Run all of the single blockchain tests. */
public class SingleChainHotelTrainTest extends AbstractExampleTest {

  @Test
  public void singlebcHotelTrain() throws Exception {
    String tempPropsFile = createPropertiesFile(true, true, true);
    HotelTrain.main(new String[] {tempPropsFile});
  }
}
