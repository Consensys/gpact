package net.consensys.gpact.performance.singlebc.read;

import net.consensys.gpact.common.AbstractExampleTest;
import org.junit.jupiter.api.Test;

/** Run all of the single blockchain tests. */
public class SingleChainReadTest extends AbstractExampleTest {

  @Test
  public void singlebcRead() throws Exception {
    String tempPropsFile = createPropertiesFile(true, true, true);
    Main.main(new String[] {tempPropsFile});
  }
}
