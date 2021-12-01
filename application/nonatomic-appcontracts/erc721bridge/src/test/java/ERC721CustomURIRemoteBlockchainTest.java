import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import net.consensys.gpact.appcontracts.nonatomic.erc721bridge.soliditywrappers.ERC721CustomURIRemoteBlockchain;
import net.consensys.gpact.common.test.AbstractWeb3Test;
import net.consensys.gpact.common.test.DummyAddressGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ERC721CustomURIRemoteBlockchainTest extends AbstractWeb3Test {

  private ERC721CustomURIRemoteBlockchain fixERC721RemoteContract;

  @BeforeEach
  public void setup() throws Exception {
    setupWeb3();
    String fixTokenName = "abc";
    String fixTokenSymbol = "abc";
    fixERC721RemoteContract =
        ERC721CustomURIRemoteBlockchain.deploy(
                this.web3j, this.tm, this.freeGasProvider, fixTokenName, fixTokenSymbol, "")
            .send();
  }

  @Test
  public void mintingShouldAssignTokenURIProvided() throws Exception {
    BigInteger fixID = BigInteger.ONE;
    String customURI = "ipfs://custom-URI-path/" + fixID;
    fixERC721RemoteContract.mint(DummyAddressGenerator.gen(), fixID, customURI.getBytes()).send();
    String tokenURI = fixERC721RemoteContract.tokenURI(fixID).send();
    assertEquals(customURI, tokenURI);
  }
}
