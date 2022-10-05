package net.consensys.gpact.messaging.eventattest;

import net.consensys.gpact.common.test.AbstractWeb3Test;
import net.consensys.gpact.messaging.common.MessagingRegistrar;
import org.junit.jupiter.api.Test;

public class EventAttestTest extends AbstractWeb3Test {
  private MessagingRegistrar registrarContract;
  private EventAttestationVerifier verifier;

  private void deployContracts() throws Exception {
    this.registrarContract =
        MessagingRegistrar.deploy(this.web3j, this.tm, this.freeGasProvider).send();
    this.verifier =
        EventAttestationVerifier.deploy(
                this.web3j,
                this.tm,
                this.freeGasProvider,
                this.registrarContract.getContractAddress())
            .send();
  }

  @Test
  public void verifyUnknownSigType() throws Exception {
    deployContracts();
  }
}
