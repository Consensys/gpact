package net.consensys.gpact.performance.singlebc.write;

import net.consensys.gpact.helpers.AbstractExampleTest;
import org.junit.jupiter.api.Test;

/** Run all of the single blockchain tests. */
public class SingleChainWriteTest extends AbstractExampleTest {
  @Test
  public void singlebcWrite() throws Exception {
    String tempPropsFile = createPropertiesFile(true, true, true);
    Main.main(new String[] {tempPropsFile});
  }
}
