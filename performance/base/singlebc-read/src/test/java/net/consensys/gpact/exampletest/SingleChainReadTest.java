package net.consensys.gpact.exampletest;

import org.junit.Test;

/**
 * Run all of the single blockchain tests.
 */
public class SingleChainReadTest extends AbstractExampleTest {

  @Test
  public void singlebcRead() throws Exception {
    String tempPropsFile = createPropertiesFile(true, true, true);
    net.consensys.gpact.examples.singlebc.read.Main.main(new String[]{tempPropsFile});
  }
}
