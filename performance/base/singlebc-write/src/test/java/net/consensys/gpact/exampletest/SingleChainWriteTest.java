package net.consensys.gpact.exampletest;

import org.junit.Test;

/** Run all of the single blockchain tests. */
public class SingleChainWriteTest extends AbstractExampleTest {
  @Test
  public void singlebcWrite() throws Exception {
    String tempPropsFile = createPropertiesFile(true, true, true);
    net.consensys.gpact.examples.singlebc.write.Main.main(new String[] {tempPropsFile});
  }
}
