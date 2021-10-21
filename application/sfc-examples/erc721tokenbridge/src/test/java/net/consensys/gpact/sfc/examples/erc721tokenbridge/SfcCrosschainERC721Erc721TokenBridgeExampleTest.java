package net.consensys.gpact.sfc.examples.erc721tokenbridge;

import net.consensys.gpact.exampletest.AbstractExampleTest;
import org.junit.Test;

public class SfcCrosschainERC721Erc721TokenBridgeExampleTest extends AbstractExampleTest {

  @Test
  public void directSignMultipleBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(true, false, false);
    Erc721TokenBridgeExample.main(new String[]{tempPropsFile});
  }

}
