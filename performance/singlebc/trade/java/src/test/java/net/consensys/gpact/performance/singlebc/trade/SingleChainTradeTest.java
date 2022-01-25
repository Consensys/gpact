package net.consensys.gpact.performance.singlebc.trade;

import net.consensys.gpact.common.AbstractExampleTest;
import org.junit.jupiter.api.Test;

/** Run all of the single blockchain tests. */
public class SingleChainTradeTest extends AbstractExampleTest {

  @Test
  public void singlebcTrade() throws Exception {
    String tempPropsFile = createPropertiesFile(true, true, true);
    Main.main(new String[] {tempPropsFile});
  }
}
