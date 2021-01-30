package net.consensys.gpact.exampletest;

import net.consensys.gpact.examples.singlebc.write.Main;
import org.junit.Test;

/**
 * Run all of the single blockchain tests.
 */
public class SingleChainTest extends AbstractExampleTest {

  @Test
  public void singlebcRead() throws Exception {
    String tempPropsFile = createPropertiesFile(true, true, true);
    net.consensys.gpact.examples.singlebc.read.Main.main(new String[]{tempPropsFile});
  }

  @Test
  public void singlebcWrite() throws Exception {
    String tempPropsFile = createPropertiesFile(true, true, true);
    Main.main(new String[]{tempPropsFile});
  }

  @Test
  public void singlebcTrade() throws Exception {
    String tempPropsFile = createPropertiesFile(true, true, true);
    net.consensys.gpact.examples.singlebc.trade.Main.main(new String[]{tempPropsFile});
  }


}
