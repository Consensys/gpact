package net.consensys.gpact.applications.sfc.erc721bridge;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import net.consensys.gpact.common.test.AbstractWeb3Test;
import net.consensys.gpact.common.test.DummyAddressGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ERC721AutoURIRemoteBlockchainTest extends AbstractWeb3Test {

  private ERC721AutoURIRemoteBlockchain fixERC721RemoteContract;
  private final String fixTokenSymbol = "abc";
  private final String fixBaseURI = String.format("ipfs://%s/", fixTokenSymbol);

  @BeforeEach
  public void setup() throws Exception {
    setupWeb3();
    String fixTokenName = "abc";
    fixERC721RemoteContract =
        ERC721AutoURIRemoteBlockchain.deploy(
                this.web3j, this.tm, this.freeGasProvider, fixTokenName, fixTokenSymbol, fixBaseURI)
            .send();
  }

  @Test
  public void mintingShouldAutogenerateTokenURI() throws Exception {
    BigInteger fixID = BigInteger.ONE;
    String expectedURI = fixBaseURI + fixID;
    fixERC721RemoteContract
        .mint(DummyAddressGenerator.gen(), fixID, "random-payload".getBytes())
        .send();

    String tokenURI = fixERC721RemoteContract.tokenURI(fixID).send();
    assertEquals(expectedURI, tokenURI);
  }
}
