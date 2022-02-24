package net.consensys.gpact.applications.twentyacts;

import net.consensys.gpact.helpers.AbstractExampleTest;
import org.junit.jupiter.api.Test;

public class HappyPathTest extends AbstractExampleTest {

  @Test
  public void happyPathDirectSignMultiBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(true, false, false);
    HappyPath.main(new String[] {tempPropsFile});
  }

}
