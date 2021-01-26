package tech.pegasys.ltacfc.exampletest;

import org.junit.Test;

/**
 * Run all of the single blockchain tests.
 */
public class SingleChainTest extends AbstractExampleTest {

  @Test
  public void singlebcRead() throws Exception {
    String tempPropsFile = createPropertiesFile(true, true, true);
    tech.pegasys.ltacfc.examples.singlebc.read.Main.main(new String[]{tempPropsFile});
  }

  @Test
  public void singlebcWrite() throws Exception {
    String tempPropsFile = createPropertiesFile(true, true, true);
    tech.pegasys.ltacfc.examples.singlebc.write.Main.main(new String[]{tempPropsFile});
  }

  @Test
  public void singlebcTrade() throws Exception {
    String tempPropsFile = createPropertiesFile(true, true, true);
    tech.pegasys.ltacfc.examples.singlebc.trade.Main.main(new String[]{tempPropsFile});
  }


}
