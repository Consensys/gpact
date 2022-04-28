/*
 * Copyright 2022 ConsenSys Software Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package net.consensys.gpact.applications.twentyacts;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import net.consensys.gpact.applications.twentyacts.crosscontrol.TwentyActsManager;
import net.consensys.gpact.applications.twentyacts.helpers.ERC20Manager;
import net.consensys.gpact.applications.twentyacts.helpers.TwentyActsExampleSystemManager;
import net.consensys.gpact.common.BlockchainConfig;
import net.consensys.gpact.common.Tuple;
import net.consensys.gpact.functioncall.CrossControlManagerGroup;
import net.consensys.gpact.helpers.AbstractExampleTest;
import net.consensys.gpact.helpers.CredentialsCreator;
import org.web3j.crypto.Credentials;

public abstract class Abstract20ActsTest extends AbstractExampleTest {
  protected Credentials deployerCredsA;
  protected Credentials deployerCredsB;
  protected Credentials infCreds;
  protected Credentials chainAErc20Owner;
  protected Credentials chainBErc20Owner;
  protected Credentials user1;
  protected Credentials receipient1;
  protected Credentials lp1;

  protected String user1Account;
  protected String recipient1Account;
  protected String lp1Account;
  protected String infAccount;

  protected BlockchainConfig chainA;
  protected BlockchainConfig chainB;

  protected CrossControlManagerGroup managerGroup;

  public static final int DEFAULT_WITHDRAWAL_WAIT_TIME = 100;
  protected BigInteger withdrawalWaitTime;

  public static final int CHAIN_A_INITIAL_SUPPLY = 1000;
  protected BigInteger chainAInitialSupply = BigInteger.valueOf(CHAIN_A_INITIAL_SUPPLY);
  public static final int CHAIN_B_INITIAL_SUPPLY = 1000;
  protected BigInteger chainBInitialSupply = BigInteger.valueOf(CHAIN_B_INITIAL_SUPPLY);

  protected ERC20Manager depChainAErc20;
  protected TwentyActsManager depChainA20Acts;

  protected ERC20Manager depChainBErc20;
  protected TwentyActsManager depChainB20Acts;

  public static final int USER1START = 100;
  public static final int LP1START = 500;

  protected ERC20Manager erc20OwnerChainA;
  protected ERC20Manager erc20OwnerChainB;

  protected ERC20Manager lp1ChainAErc20;
  protected ERC20Manager lp1ChainBErc20;
  protected TwentyActsManager lp1ChainB20Acts;
  protected TwentyActsManager lp1ChainA20Acts;

  protected ERC20Manager user1ChainAErc20;
  protected TwentyActsManager user1ChainA20Acts;
  protected ERC20Manager user1ChainBErc20;

  protected String erc20A;
  protected String erc20B;

  protected int lp1DepA = 0;
  protected int lp1DepB = LP1START;
  protected int infDepA = 0;
  protected int user1BalA = USER1START;
  protected int recipient1BalB = 0;

  protected void deployAndSetup() throws Exception {
    String tempPropsFile = createPropertiesFile(true, false, false);

    this.deployerCredsA = CredentialsCreator.createCredentials();
    this.deployerCredsB = CredentialsCreator.createCredentials();
    this.infCreds = CredentialsCreator.createCredentials();
    this.chainAErc20Owner = CredentialsCreator.createCredentials();
    this.chainBErc20Owner = CredentialsCreator.createCredentials();
    this.user1 = CredentialsCreator.createCredentials();
    this.receipient1 = CredentialsCreator.createCredentials();
    this.lp1 = CredentialsCreator.createCredentials();
    this.user1Account = user1.getAddress();
    this.recipient1Account = receipient1.getAddress();
    this.lp1Account = lp1.getAddress();
    this.infAccount = infCreds.getAddress();

    TwentyActsExampleSystemManager exampleManager =
        new TwentyActsExampleSystemManager(tempPropsFile);
    // Deploy twenty acts contracts and verifier contracts on blockchain.
    exampleManager.standardExampleConfig(new Credentials[] {deployerCredsA, deployerCredsB});
    this.managerGroup = exampleManager.getCrossControlManagerGroup();

    this.chainA = exampleManager.getRootBcInfo();
    this.chainB = exampleManager.getBc2Info();

    this.withdrawalWaitTime = BigInteger.valueOf(DEFAULT_WITHDRAWAL_WAIT_TIME);

    // Set-up Chain A
    this.depChainAErc20 =
        new ERC20Manager(
            deployerCredsA,
            chainA.bcId,
            chainA.blockchainNodeRpcUri,
            chainA.gasPriceStrategy,
            chainA.period);
    this.depChainA20Acts = (TwentyActsManager) managerGroup.getCbcManager(chainA.bcId);
    this.depChainA20Acts.setWithdrawalTime(withdrawalWaitTime);
    this.depChainA20Acts.setInfBenficiary(infCreds.getAddress());

    String chainAName = "DAI";
    String chainASymbol = "DAI";
    this.depChainAErc20.deployErc20Contract(
        chainAName, chainASymbol, chainAInitialSupply, chainAErc20Owner.getAddress());

    // Set-up Chain B
    this.depChainBErc20 =
        new ERC20Manager(
            deployerCredsB,
            chainB.bcId,
            chainB.blockchainNodeRpcUri,
            chainB.gasPriceStrategy,
            chainB.period);
    this.depChainB20Acts = (TwentyActsManager) managerGroup.getCbcManager(chainB.bcId);
    this.depChainB20Acts.setWithdrawalTime(withdrawalWaitTime);
    this.depChainB20Acts.setInfBenficiary(infCreds.getAddress());

    String chainBName = "DAI";
    String chainBSymbol = "DAI";
    this.depChainBErc20.deployErc20Contract(
        chainBName, chainBSymbol, chainBInitialSupply, chainBErc20Owner.getAddress());

    // Connect the chain A and chain B contracts together so there is a bridge.
    this.depChainA20Acts.addRemoteErc20(
        depChainAErc20.getErc20ContractAddress(),
        chainB.bcId,
        depChainBErc20.getErc20ContractAddress());
    this.depChainB20Acts.addRemoteErc20(
        depChainBErc20.getErc20ContractAddress(),
        chainA.bcId,
        depChainAErc20.getErc20ContractAddress());

    this.erc20OwnerChainA = depChainAErc20.forUser(chainAErc20Owner);
    this.erc20OwnerChainB = depChainBErc20.forUser(chainBErc20Owner);
    this.lp1ChainAErc20 = depChainAErc20.forUser(lp1);
    this.lp1ChainBErc20 = depChainBErc20.forUser(lp1);
    this.lp1ChainB20Acts = depChainB20Acts.forUser(lp1);
    this.lp1ChainA20Acts = depChainA20Acts.forUser(lp1);
    this.user1ChainAErc20 = depChainAErc20.forUser(user1);
    this.user1ChainA20Acts = depChainA20Acts.forUser(user1);
    this.user1ChainBErc20 = depChainBErc20.forUser(user1);

    this.erc20A = user1ChainAErc20.getErc20ContractAddress();
    this.erc20B = user1ChainBErc20.getErc20ContractAddress();
  }

  protected void setupBalances() throws Exception {
    // Give some coins to the user on Chain A and the liquidity provider on Chain B.
    this.erc20OwnerChainA.erc20Faucet(user1.getAddress(), BigInteger.valueOf(USER1START));
    this.erc20OwnerChainB.erc20Faucet(lp1.getAddress(), BigInteger.valueOf(LP1START));

    // Liquidity provider deposits funds into 20ACTS contract.
    this.lp1ChainBErc20.erc20Approve(
        lp1ChainB20Acts.getCbcContractAddress(), BigInteger.valueOf(LP1START));
    this.lp1ChainB20Acts.lpDeposit(
        lp1ChainBErc20.getErc20ContractAddress(), BigInteger.valueOf(LP1START));

    this.lp1DepA = 0;
    this.lp1DepB = LP1START;
    this.infDepA = 0;
    this.user1BalA = USER1START;
    this.recipient1BalB = 0;

    checkHoldings(lp1ChainB20Acts, lp1Account, erc20B, lp1DepB, 0, 0);
    checkBalance(user1ChainAErc20, user1Account, USER1START);
  }

  protected void checkHoldings(
      TwentyActsManager twentyActsManager,
      String account,
      String erc20,
      int deposits,
      int allocation,
      int withdrawals)
      throws Exception {
    Tuple<BigInteger, BigInteger, BigInteger> holdings =
        twentyActsManager.getHoldings(account, erc20);
    assertEquals(deposits, holdings.getFirst().intValue());
    assertEquals(allocation, holdings.getSecond().intValue());
    assertEquals(withdrawals, holdings.getThird().intValue());
  }

  protected void checkBalance(ERC20Manager erc20Manager, String account, int balance)
      throws Exception {
    BigInteger actualBalance = erc20Manager.balanceOf(account);
    assertEquals(balance, actualBalance.intValue());
  }
}
