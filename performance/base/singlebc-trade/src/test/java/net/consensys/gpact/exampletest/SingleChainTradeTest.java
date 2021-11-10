package net.consensys.gpact.exampletest;

import org.junit.jupiter.api.Test;

/** Run all of the single blockchain tests. */
public class SingleChainTradeTest extends AbstractExampleTest {

  @Test
  public void singlebcTrade() throws Exception {
    String tempPropsFile = createPropertiesFile(true, true, true);
    net.consensys.gpact.examples.singlebc.trade.Main.main(new String[] {tempPropsFile});
  }
}
