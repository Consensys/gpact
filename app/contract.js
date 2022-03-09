import { GPACTManager } from "./gpactmgr.js";

const ercABI = [{"inputs":[{"internalType":"string","name":"_name","type":"string"},{"internalType":"string","name":"_symbol","type":"string"},{"internalType":"address","name":"_cbc","type":"address"},{"internalType":"uint256","name":"_initialSupply","type":"uint256"},{"internalType":"address","name":"_owner","type":"address"}],"stateMutability":"nonpayable","type":"constructor"},{"anonymous":false,"inputs":[{"indexed":true,"internalType":"address","name":"owner","type":"address"},{"indexed":true,"internalType":"address","name":"spender","type":"address"},{"indexed":false,"internalType":"uint256","name":"value","type":"uint256"}],"name":"Approval","type":"event"},{"anonymous":false,"inputs":[{"indexed":true,"internalType":"address","name":"owner","type":"address"},{"indexed":true,"internalType":"address","name":"spender","type":"address"},{"indexed":false,"internalType":"uint256","name":"value","type":"uint256"}],"name":"ApprovalDecrease","type":"event"},{"anonymous":false,"inputs":[{"indexed":true,"internalType":"address","name":"owner","type":"address"},{"indexed":true,"internalType":"address","name":"spender","type":"address"},{"indexed":false,"internalType":"uint256","name":"value","type":"uint256"}],"name":"ApprovalIncrease","type":"event"},{"anonymous":false,"inputs":[{"indexed":true,"internalType":"address","name":"previousOwner","type":"address"},{"indexed":true,"internalType":"address","name":"newOwner","type":"address"}],"name":"OwnershipTransferred","type":"event"},{"anonymous":false,"inputs":[{"indexed":true,"internalType":"address","name":"from","type":"address"},{"indexed":true,"internalType":"address","name":"to","type":"address"},{"indexed":false,"internalType":"uint256","name":"value","type":"uint256"}],"name":"Transfer","type":"event"},{"anonymous":false,"inputs":[{"indexed":false,"internalType":"address","name":"bridge","type":"address"},{"indexed":false,"internalType":"bool","name":"added","type":"bool"}],"name":"TrustedBridge","type":"event"},{"inputs":[],"name":"accountPallelizationFactor","outputs":[{"internalType":"uint256","name":"","type":"uint256"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"address","name":"bridge","type":"address"}],"name":"addTrustedBridge","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"address","name":"owner","type":"address"},{"internalType":"address","name":"spender","type":"address"}],"name":"allowance","outputs":[{"internalType":"uint256","name":"","type":"uint256"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"address","name":"owner","type":"address"},{"internalType":"address","name":"spender","type":"address"}],"name":"allowanceMax","outputs":[{"internalType":"uint256","name":"","type":"uint256"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"address","name":"owner","type":"address"},{"internalType":"address","name":"spender","type":"address"}],"name":"allowanceMin","outputs":[{"internalType":"uint256","name":"","type":"uint256"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"address","name":"owner","type":"address"},{"internalType":"address","name":"spender","type":"address"}],"name":"allowanceProvisional","outputs":[{"internalType":"uint256","name":"","type":"uint256"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"address","name":"spender","type":"address"},{"internalType":"uint256","name":"amount","type":"uint256"}],"name":"approve","outputs":[{"internalType":"bool","name":"","type":"bool"}],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"address","name":"account","type":"address"}],"name":"balanceOf","outputs":[{"internalType":"uint256","name":"","type":"uint256"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"address","name":"account","type":"address"}],"name":"balanceOfMin","outputs":[{"internalType":"uint256","name":"","type":"uint256"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"address","name":"account","type":"address"}],"name":"balanceOfProvisional","outputs":[{"internalType":"uint256","name":"","type":"uint256"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"uint256","name":"amount","type":"uint256"}],"name":"burn","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"address","name":"account","type":"address"},{"internalType":"uint256","name":"amount","type":"uint256"}],"name":"burnFrom","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"address","name":"spender","type":"address"},{"internalType":"address","name":"sender","type":"address"},{"internalType":"uint256","name":"amount","type":"uint256"}],"name":"burnFromAccount","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"address","name":"burner","type":"address"},{"internalType":"address","name":"account","type":"address"},{"internalType":"uint256","name":"amount","type":"uint256"}],"name":"burnFromInternal","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[],"name":"decimals","outputs":[{"internalType":"uint8","name":"","type":"uint8"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"address","name":"spender","type":"address"},{"internalType":"uint256","name":"subtractedValue","type":"uint256"}],"name":"decreaseAllowance","outputs":[{"internalType":"bool","name":"","type":"bool"}],"stateMutability":"nonpayable","type":"function"},{"inputs":[],"name":"erc20PallelizationFactor","outputs":[{"internalType":"uint256","name":"","type":"uint256"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"bool","name":"_commit","type":"bool"},{"internalType":"bytes32","name":"_crossRootTxId","type":"bytes32"}],"name":"finalise","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"uint256","name":"amount","type":"uint256"}],"name":"increaseAccountParallelizartionFactor","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"address","name":"spender","type":"address"},{"internalType":"uint256","name":"addedValue","type":"uint256"}],"name":"increaseAllowance","outputs":[{"internalType":"bool","name":"","type":"bool"}],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"uint256","name":"amount","type":"uint256"}],"name":"increaseERC20ParallelizartionFactor","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"uint256","name":"_key","type":"uint256"}],"name":"isLocked","outputs":[{"internalType":"bool","name":"","type":"bool"}],"stateMutability":"view","type":"function"},{"inputs":[],"name":"name","outputs":[{"internalType":"string","name":"","type":"string"}],"stateMutability":"view","type":"function"},{"inputs":[],"name":"owner","outputs":[{"internalType":"address","name":"","type":"address"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"address","name":"bridge","type":"address"}],"name":"removeTrustedBridge","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[],"name":"renounceOwnership","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[],"name":"symbol","outputs":[{"internalType":"string","name":"","type":"string"}],"stateMutability":"view","type":"function"},{"inputs":[],"name":"totalSupply","outputs":[{"internalType":"uint256","name":"","type":"uint256"}],"stateMutability":"view","type":"function"},{"inputs":[],"name":"totalSupplyMax","outputs":[{"internalType":"uint256","name":"","type":"uint256"}],"stateMutability":"view","type":"function"},{"inputs":[],"name":"totalSupplyMin","outputs":[{"internalType":"uint256","name":"","type":"uint256"}],"stateMutability":"view","type":"function"},{"inputs":[],"name":"totalSupplyProvisional","outputs":[{"internalType":"uint256","name":"","type":"uint256"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"address","name":"recipient","type":"address"},{"internalType":"uint256","name":"amount","type":"uint256"}],"name":"transfer","outputs":[{"internalType":"bool","name":"","type":"bool"}],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"address","name":"sender","type":"address"},{"internalType":"address","name":"recipient","type":"address"},{"internalType":"uint256","name":"amount","type":"uint256"}],"name":"transferFrom","outputs":[{"internalType":"bool","name":"","type":"bool"}],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"address","name":"spender","type":"address"},{"internalType":"address","name":"sender","type":"address"},{"internalType":"address","name":"recipient","type":"address"},{"internalType":"uint256","name":"amount","type":"uint256"}],"name":"transferFromAccount","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"address","name":"newOwner","type":"address"}],"name":"transferOwnership","outputs":[],"stateMutability":"nonpayable","type":"function"}]

const forwarderOrigin = 'http://localhost:9010'

const initialize = () => {
  const onboardButton = document.getElementById('connectButton');
  const getAccountsButton = document.getElementById('getAccounts');
  const getAccountsResult = document.getElementById('getAccountsResult');
  const transferButton = document.getElementById('transferButton');
  const transferAmount = document.getElementById('transferAmount');
  const transferAccount = document.getElementById('transferAccount');
  const infos = document.getElementById('infos');
  var myWeb3;
  //Created check function to see if the MetaMask extension is installed
  const isMetaMaskInstalled = () => {
    //Have to check the ethereum binding on the window object to see if it's installed
    const { ethereum } = window;
    return Boolean(ethereum && ethereum.isMetaMask);
  };
  const onboarding = new MetaMaskOnboarding({ forwarderOrigin });
  //This will start the onboarding proccess
  const onClickInstall = () => {
    onboardButton.innerText = 'Onboarding in progress';
    onboardButton.disabled = true;
    onboarding.startOnboarding();
  };
  const onClickConnect = async () => {
    try {
      // Will open the MetaMask UI
      // You should disable this button while the request is pending!
      await ethereum.request({ method: 'eth_requestAccounts' });
    } catch (error) {
      console.error(error);
    }
  };

  const MetaMaskClientCheck = async () => {
    //Now we check to see if MetaMask is installed
    if (!isMetaMaskInstalled()) {
      onboardButton.innerText = 'Click here to install MetaMask!';
      onboardButton.onclick = onClickInstall;
      onboardButton.disabled = false;
    } else {
      //If it is installed we change our button text
      onboardButton.innerText = 'Connect';
      onboardButton.onclick = onClickConnect;
      onboardButton.disabled = false;
      myWeb3 = new Web3(window.ethereum);
    }
  };
  MetaMaskClientCheck();

  getAccountsButton.addEventListener('click', async () => {
    //we use eth_accounts because it returns a list of addresses owned by us.
    const accounts = await ethereum.request({ method: 'eth_accounts' });
    const chainId = await ethereum.request({ method: 'eth_chainId' });
    //We take the first address in the array of addresses and display it
    if (accounts[0]) {
      getAccountsResult.innerHTML = (accounts[0] || 'Not able to get accounts')
      if (chainId == 4) {
        getAccountsResult.innerHTML += " on Rinkeby. You can transfer your DAI to cDAI on Goerli";
        transferButton.disabled = false
      } else if (chainId == 5) {
        getAccountsResult.innerHTML += " on Goerli. You can transfer your cDAI to DAI on Rinkeby";
        transferButton.disabled = false
      } else {
        getAccountsResult.innerHTML += " on " + chainId + ". Please use Rinkeby or Goerli for this demo";
      }
    } else {
      getAccountsResult.innerHTML = ('Not able to get accounts')
    }
  });

  transferButton.addEventListener('click', async () => {
    const val = Math.round(transferAmount.value * 1e18)
    var recipient = transferAccount.value
    if (val > 0) {
      const accounts = await ethereum.request({ method: 'eth_accounts' });
      var gpactMgr = await new GPACTManager(window.ethereum, accounts[0])
      if (recipient == "") {
        recipient = accounts[0]
      } else if (!myWeb3.utils.isAddress(recipient)) {
        infos.innerHTML = "not valid recipient address"
        return;
      }
      const chainId = await ethereum.request({ method: 'eth_chainId' });
      var ercAddress;
      var otherChainId;
      var bridgeAddress;
      if (chainId == 4) {
        // rinkeby
        ercAddress = "0x02711f1a33bafa7cfD323Cba56012c1E2721c09F";
        bridgeAddress = "0x2576CE4BD89Af5D379cA7B5670f25191e3Bb8c6B"
        infos.innerHTML = "Transfer to " + recipient + " on Goerli: "
        otherChainId = 5
      } else if (chainId == 5) {
        // goerli
        ercAddress = "0x4bf8A1CeCd8F747A41A99B30a514F06B7F1E36bc";
        bridgeAddress = "0xe2AB1D514363338E322AE14c4571A48a4020c822"
        infos.innerHTML = "Transfer to " + recipient + " on Rinkeby: "
        otherChainId = 4
      } else {
        // this should never happen.
        return;
      }
      var erc20 = new myWeb3.eth.Contract(ercABI, ercAddress);
      const balance = await erc20.methods.balanceOf(accounts[0]).call()
      if (val <= balance) {
        infos.innerHTML += val/1e18 + " tokens.";   
        // Start transfer. First, let's do approve.
        erc20.methods.approve(bridgeAddress, BigInt(val)).send({
          from: accounts[0],
          gas: 200000,
          gasPrice: await myWeb3.eth.getGasPrice()
        }, async (err, res) => {
          if (err) {
            infos.innerHTML += "Approve transaction failed."; 
          } else {
            infos.innerHTML += "Waiting for transaction " + res + " to confirm...";
            var success = false;
            for (let i = 0; i < 60; i++) {
              var receipt = await myWeb3.eth.getTransactionReceipt(res)
               if (receipt != null) {
                  success = true;
                   break;
               }
               await new Promise(r => setTimeout(r, 2000));
            }
            if (success) {
              infos.innerHTML += "Confirmed. Start crosschain transfer: "
              await gpactMgr.tokenTransfer(chainId, otherChainId, ercAddress, recipient, val)
            } else {
              infos.innerHTML += "Transaction " + res + " fail to confirm.";
            }
          }
        })
      } else {
        infos.innerHTML += "Not enough balance - want " + val/1e18 + " but balance is " + balance/1e18; 
      }
    } else {
      infos.innerHTML = "Value to transfer must be positive and need to be at least 1e-18???"
    }
  });
  
  function handleAccountChanged(_acct) {
    window.location.reload();
  }
  ethereum.on('accountsChanged', handleAccountChanged)
}
window.addEventListener('DOMContentLoaded', initialize)