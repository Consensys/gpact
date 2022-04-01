const bridgeABI = [
  {
    "inputs": [
      {
        "internalType": "uint256",
        "name": "_chainId",
        "type": "uint256"
      },
      {
        "internalType": "address",
        "name": "_cbcContract",
        "type": "address"
      }
    ],
    "stateMutability": "nonpayable",
    "type": "constructor"
  },
  {
    "anonymous": false,
    "inputs": [
      {
        "indexed": false,
        "internalType": "address",
        "name": "_tokenContract",
        "type": "address"
      },
      {
        "indexed": false,
        "internalType": "uint256",
        "name": "_amount",
        "type": "uint256"
      },
      {
        "indexed": false,
        "internalType": "address",
        "name": "_tokenRecipient",
        "type": "address"
      },
      {
        "indexed": false,
        "internalType": "uint256",
        "name": "_otherBcId",
        "type": "uint256"
      },
      {
        "indexed": false,
        "internalType": "address",
        "name": "_otherNftContract",
        "type": "address"
      },
      {
        "indexed": false,
        "internalType": "uint256",
        "name": "_otherNftId",
        "type": "uint256"
      },
      {
        "indexed": false,
        "internalType": "address",
        "name": "_otherNftRecipient",
        "type": "address"
      }
    ],
    "name": "Purchase",
    "type": "event"
  },
  {
    "anonymous": false,
    "inputs": [
      {
        "indexed": false,
        "internalType": "address",
        "name": "_nftContract",
        "type": "address"
      },
      {
        "indexed": false,
        "internalType": "uint256",
        "name": "_nftId",
        "type": "uint256"
      },
      {
        "indexed": false,
        "internalType": "uint256",
        "name": "_otherBcId",
        "type": "uint256"
      },
      {
        "indexed": false,
        "internalType": "address",
        "name": "_otherTokenContract",
        "type": "address"
      }
    ],
    "name": "RemoveAsking",
    "type": "event"
  },
  {
    "anonymous": false,
    "inputs": [
      {
        "indexed": true,
        "internalType": "bytes32",
        "name": "role",
        "type": "bytes32"
      },
      {
        "indexed": true,
        "internalType": "bytes32",
        "name": "previousAdminRole",
        "type": "bytes32"
      },
      {
        "indexed": true,
        "internalType": "bytes32",
        "name": "newAdminRole",
        "type": "bytes32"
      }
    ],
    "name": "RoleAdminChanged",
    "type": "event"
  },
  {
    "anonymous": false,
    "inputs": [
      {
        "indexed": true,
        "internalType": "bytes32",
        "name": "role",
        "type": "bytes32"
      },
      {
        "indexed": true,
        "internalType": "address",
        "name": "account",
        "type": "address"
      },
      {
        "indexed": true,
        "internalType": "address",
        "name": "sender",
        "type": "address"
      }
    ],
    "name": "RoleGranted",
    "type": "event"
  },
  {
    "anonymous": false,
    "inputs": [
      {
        "indexed": true,
        "internalType": "bytes32",
        "name": "role",
        "type": "bytes32"
      },
      {
        "indexed": true,
        "internalType": "address",
        "name": "account",
        "type": "address"
      },
      {
        "indexed": true,
        "internalType": "address",
        "name": "sender",
        "type": "address"
      }
    ],
    "name": "RoleRevoked",
    "type": "event"
  },
  {
    "anonymous": false,
    "inputs": [
      {
        "indexed": false,
        "internalType": "address",
        "name": "_nftContract",
        "type": "address"
      },
      {
        "indexed": false,
        "internalType": "uint256",
        "name": "_nftId",
        "type": "uint256"
      }
    ],
    "name": "StartListing",
    "type": "event"
  },
  {
    "anonymous": false,
    "inputs": [
      {
        "indexed": false,
        "internalType": "address",
        "name": "_nftContract",
        "type": "address"
      },
      {
        "indexed": false,
        "internalType": "uint256",
        "name": "_nftId",
        "type": "uint256"
      }
    ],
    "name": "StopListing",
    "type": "event"
  },
  {
    "anonymous": false,
    "inputs": [
      {
        "indexed": false,
        "internalType": "address",
        "name": "_nftContract",
        "type": "address"
      },
      {
        "indexed": false,
        "internalType": "uint256",
        "name": "_nftId",
        "type": "uint256"
      },
      {
        "indexed": false,
        "internalType": "address",
        "name": "_nftRecipient",
        "type": "address"
      },
      {
        "indexed": false,
        "internalType": "uint256",
        "name": "_otherBcId",
        "type": "uint256"
      },
      {
        "indexed": false,
        "internalType": "address",
        "name": "_otherTokenContract",
        "type": "address"
      },
      {
        "indexed": false,
        "internalType": "uint256",
        "name": "_amount",
        "type": "uint256"
      },
      {
        "indexed": false,
        "internalType": "address",
        "name": "_otherTokenRecipient",
        "type": "address"
      }
    ],
    "name": "SuccessfulPurchase",
    "type": "event"
  },
  {
    "anonymous": false,
    "inputs": [
      {
        "indexed": false,
        "internalType": "address",
        "name": "_nftContract",
        "type": "address"
      },
      {
        "indexed": false,
        "internalType": "uint256",
        "name": "_nftId",
        "type": "uint256"
      },
      {
        "indexed": false,
        "internalType": "uint256",
        "name": "_otherBcId",
        "type": "uint256"
      },
      {
        "indexed": false,
        "internalType": "address",
        "name": "_otherTokenContract",
        "type": "address"
      },
      {
        "indexed": false,
        "internalType": "uint256",
        "name": "_amount",
        "type": "uint256"
      },
      {
        "indexed": false,
        "internalType": "address",
        "name": "_otherTokenRecipient",
        "type": "address"
      }
    ],
    "name": "UpsertAsking",
    "type": "event"
  },
  {
    "inputs": [],
    "name": "DEFAULT_ADMIN_ROLE",
    "outputs": [
      {
        "internalType": "bytes32",
        "name": "",
        "type": "bytes32"
      }
    ],
    "stateMutability": "view",
    "type": "function",
    "constant": true
  },
  {
    "inputs": [],
    "name": "chainId",
    "outputs": [
      {
        "internalType": "uint256",
        "name": "",
        "type": "uint256"
      }
    ],
    "stateMutability": "view",
    "type": "function",
    "constant": true
  },
  {
    "inputs": [
      {
        "internalType": "bytes32",
        "name": "role",
        "type": "bytes32"
      }
    ],
    "name": "getRoleAdmin",
    "outputs": [
      {
        "internalType": "bytes32",
        "name": "",
        "type": "bytes32"
      }
    ],
    "stateMutability": "view",
    "type": "function",
    "constant": true
  },
  {
    "inputs": [
      {
        "internalType": "bytes32",
        "name": "role",
        "type": "bytes32"
      },
      {
        "internalType": "address",
        "name": "account",
        "type": "address"
      }
    ],
    "name": "grantRole",
    "outputs": [],
    "stateMutability": "nonpayable",
    "type": "function"
  },
  {
    "inputs": [
      {
        "internalType": "bytes32",
        "name": "role",
        "type": "bytes32"
      },
      {
        "internalType": "address",
        "name": "account",
        "type": "address"
      }
    ],
    "name": "hasRole",
    "outputs": [
      {
        "internalType": "bool",
        "name": "",
        "type": "bool"
      }
    ],
    "stateMutability": "view",
    "type": "function",
    "constant": true
  },
  {
    "inputs": [
      {
        "internalType": "bytes",
        "name": "",
        "type": "bytes"
      }
    ],
    "name": "listings",
    "outputs": [
      {
        "internalType": "uint256",
        "name": "index",
        "type": "uint256"
      },
      {
        "internalType": "address",
        "name": "nftContract",
        "type": "address"
      },
      {
        "internalType": "uint256",
        "name": "nftId",
        "type": "uint256"
      },
      {
        "internalType": "address",
        "name": "owner",
        "type": "address"
      }
    ],
    "stateMutability": "view",
    "type": "function",
    "constant": true
  },
  {
    "inputs": [
      {
        "internalType": "uint256",
        "name": "",
        "type": "uint256"
      }
    ],
    "name": "listingsKeys",
    "outputs": [
      {
        "internalType": "bytes",
        "name": "",
        "type": "bytes"
      }
    ],
    "stateMutability": "view",
    "type": "function",
    "constant": true
  },
  {
    "inputs": [
      {
        "internalType": "uint256",
        "name": "",
        "type": "uint256"
      }
    ],
    "name": "remoteBridges",
    "outputs": [
      {
        "internalType": "address",
        "name": "",
        "type": "address"
      }
    ],
    "stateMutability": "view",
    "type": "function",
    "constant": true
  },
  {
    "inputs": [
      {
        "internalType": "bytes32",
        "name": "role",
        "type": "bytes32"
      },
      {
        "internalType": "address",
        "name": "account",
        "type": "address"
      }
    ],
    "name": "renounceRole",
    "outputs": [],
    "stateMutability": "nonpayable",
    "type": "function"
  },
  {
    "inputs": [
      {
        "internalType": "bytes32",
        "name": "role",
        "type": "bytes32"
      },
      {
        "internalType": "address",
        "name": "account",
        "type": "address"
      }
    ],
    "name": "revokeRole",
    "outputs": [],
    "stateMutability": "nonpayable",
    "type": "function"
  },
  {
    "inputs": [
      {
        "internalType": "bytes4",
        "name": "interfaceId",
        "type": "bytes4"
      }
    ],
    "name": "supportsInterface",
    "outputs": [
      {
        "internalType": "bool",
        "name": "",
        "type": "bool"
      }
    ],
    "stateMutability": "view",
    "type": "function",
    "constant": true
  },
  {
    "inputs": [
      {
        "internalType": "uint256[]",
        "name": "_otherBcIds",
        "type": "uint256[]"
      },
      {
        "internalType": "address[]",
        "name": "_otherBridgeAddrs",
        "type": "address[]"
      }
    ],
    "name": "registerRemoteBridges",
    "outputs": [],
    "stateMutability": "nonpayable",
    "type": "function"
  },
  {
    "inputs": [
      {
        "internalType": "uint256[]",
        "name": "_otherBcIds",
        "type": "uint256[]"
      }
    ],
    "name": "removeRemoteBridges",
    "outputs": [],
    "stateMutability": "nonpayable",
    "type": "function"
  },
  {
    "inputs": [
      {
        "internalType": "address",
        "name": "_nftContract",
        "type": "address"
      },
      {
        "internalType": "uint256",
        "name": "_nftId",
        "type": "uint256"
      }
    ],
    "name": "startListingNFT",
    "outputs": [],
    "stateMutability": "nonpayable",
    "type": "function"
  },
  {
    "inputs": [
      {
        "internalType": "address",
        "name": "_nftContract",
        "type": "address"
      },
      {
        "internalType": "uint256",
        "name": "_nftId",
        "type": "uint256"
      }
    ],
    "name": "stopListingNFT",
    "outputs": [],
    "stateMutability": "nonpayable",
    "type": "function"
  },
  {
    "inputs": [
      {
        "internalType": "address",
        "name": "_nftContract",
        "type": "address"
      },
      {
        "internalType": "uint256",
        "name": "_nftId",
        "type": "uint256"
      },
      {
        "internalType": "uint256",
        "name": "_otherBcId",
        "type": "uint256"
      },
      {
        "internalType": "address",
        "name": "_otherTokenContract",
        "type": "address"
      },
      {
        "internalType": "uint256",
        "name": "_amount",
        "type": "uint256"
      },
      {
        "internalType": "address",
        "name": "_otherTokenRecipient",
        "type": "address"
      }
    ],
    "name": "upsertAsking",
    "outputs": [],
    "stateMutability": "nonpayable",
    "type": "function"
  },
  {
    "inputs": [
      {
        "internalType": "address",
        "name": "_nftContract",
        "type": "address"
      },
      {
        "internalType": "uint256",
        "name": "_nftId",
        "type": "uint256"
      },
      {
        "internalType": "uint256",
        "name": "_otherBcId",
        "type": "uint256"
      },
      {
        "internalType": "address",
        "name": "_otherTokenContract",
        "type": "address"
      }
    ],
    "name": "removeAsking",
    "outputs": [],
    "stateMutability": "nonpayable",
    "type": "function"
  },
  {
    "inputs": [
      {
        "internalType": "address",
        "name": "_tokenContract",
        "type": "address"
      },
      {
        "internalType": "uint256",
        "name": "_amount",
        "type": "uint256"
      },
      {
        "internalType": "address",
        "name": "_tokenRecipient",
        "type": "address"
      },
      {
        "internalType": "uint256",
        "name": "_otherBcId",
        "type": "uint256"
      },
      {
        "internalType": "address",
        "name": "_otherNftContract",
        "type": "address"
      },
      {
        "internalType": "uint256",
        "name": "_otherNftId",
        "type": "uint256"
      },
      {
        "internalType": "address",
        "name": "_otherNftRecipient",
        "type": "address"
      }
    ],
    "name": "buyNFTOnOtherBlockchain",
    "outputs": [],
    "stateMutability": "nonpayable",
    "type": "function"
  },
  {
    "inputs": [
      {
        "internalType": "address",
        "name": "_nftContract",
        "type": "address"
      },
      {
        "internalType": "uint256",
        "name": "_nftId",
        "type": "uint256"
      },
      {
        "internalType": "address",
        "name": "_nftRecipient",
        "type": "address"
      },
      {
        "internalType": "uint256",
        "name": "_otherBcId",
        "type": "uint256"
      },
      {
        "internalType": "address",
        "name": "_otherTokenContract",
        "type": "address"
      },
      {
        "internalType": "uint256",
        "name": "_amount",
        "type": "uint256"
      },
      {
        "internalType": "address",
        "name": "_otherTokenRecipient",
        "type": "address"
      }
    ],
    "name": "processPurchaseFromOtherBlockchain",
    "outputs": [],
    "stateMutability": "nonpayable",
    "type": "function"
  }
];
const erc20ABI = [
  {
    "anonymous": false,
    "inputs": [
      {
        "indexed": true,
        "internalType": "address",
        "name": "owner",
        "type": "address"
      },
      {
        "indexed": true,
        "internalType": "address",
        "name": "spender",
        "type": "address"
      },
      {
        "indexed": false,
        "internalType": "uint256",
        "name": "value",
        "type": "uint256"
      }
    ],
    "name": "Approval",
    "type": "event"
  },
  {
    "anonymous": false,
    "inputs": [
      {
        "indexed": true,
        "internalType": "address",
        "name": "from",
        "type": "address"
      },
      {
        "indexed": true,
        "internalType": "address",
        "name": "to",
        "type": "address"
      },
      {
        "indexed": false,
        "internalType": "uint256",
        "name": "value",
        "type": "uint256"
      }
    ],
    "name": "Transfer",
    "type": "event"
  },
  {
    "inputs": [],
    "name": "totalSupply",
    "outputs": [
      {
        "internalType": "uint256",
        "name": "",
        "type": "uint256"
      }
    ],
    "stateMutability": "view",
    "type": "function"
  },
  {
    "inputs": [
      {
        "internalType": "address",
        "name": "account",
        "type": "address"
      }
    ],
    "name": "balanceOf",
    "outputs": [
      {
        "internalType": "uint256",
        "name": "",
        "type": "uint256"
      }
    ],
    "stateMutability": "view",
    "type": "function"
  },
  {
    "inputs": [
      {
        "internalType": "address",
        "name": "to",
        "type": "address"
      },
      {
        "internalType": "uint256",
        "name": "amount",
        "type": "uint256"
      }
    ],
    "name": "transfer",
    "outputs": [
      {
        "internalType": "bool",
        "name": "",
        "type": "bool"
      }
    ],
    "stateMutability": "nonpayable",
    "type": "function"
  },
  {
    "inputs": [
      {
        "internalType": "address",
        "name": "owner",
        "type": "address"
      },
      {
        "internalType": "address",
        "name": "spender",
        "type": "address"
      }
    ],
    "name": "allowance",
    "outputs": [
      {
        "internalType": "uint256",
        "name": "",
        "type": "uint256"
      }
    ],
    "stateMutability": "view",
    "type": "function"
  },
  {
    "inputs": [
      {
        "internalType": "address",
        "name": "spender",
        "type": "address"
      },
      {
        "internalType": "uint256",
        "name": "amount",
        "type": "uint256"
      }
    ],
    "name": "approve",
    "outputs": [
      {
        "internalType": "bool",
        "name": "",
        "type": "bool"
      }
    ],
    "stateMutability": "nonpayable",
    "type": "function"
  },
  {
    "inputs": [
      {
        "internalType": "address",
        "name": "from",
        "type": "address"
      },
      {
        "internalType": "address",
        "name": "to",
        "type": "address"
      },
      {
        "internalType": "uint256",
        "name": "amount",
        "type": "uint256"
      }
    ],
    "name": "transferFrom",
    "outputs": [
      {
        "internalType": "bool",
        "name": "",
        "type": "bool"
      }
    ],
    "stateMutability": "nonpayable",
    "type": "function"
  }
];

const bridgeRinkebyAddr = "0x04Bcb7968C9338122fD38446E13f8Ba8a5169833";
const bridgeGoerliAddr = "0xE5855252E4A365F12187daec095D1571A3027368";

const forwarderOrigin = 'http://localhost:9010';

const initialize = () => {
  const onboardButton = document.getElementById('connectButton');
  const checkAccountButton = document.getElementById('checkAccountButton');
  const approveButton = document.getElementById('approveButton');
  const purchaseButton = document.getElementById('purchaseButton');
  const nftChain = document.getElementById('otherBcId');
  const nftContractAddr = document.getElementById('nftContractAddress');
  const nftId = document.getElementById('nftId');
  const tokenAddr = document.getElementById('tokenContractAddress');
  const tokenAmount = document.getElementById('amount');
  const tokenRecipient = document.getElementById('tokenRecipientAddress');
  const infos1 = document.getElementById('infos1');
  const infos2 = document.getElementById('infos2');
  const infos3 = document.getElementById('infos3');
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
      onboardButton.innerText = 'Connected';
      onboardButton.onclick = onClickConnect;
      onboardButton.disabled = true;
      checkAccountButton.disabled = false;
      myWeb3 = new Web3(window.ethereum);
    }
  };
  MetaMaskClientCheck();

  checkAccountButton.addEventListener('click', async () => {
    //we use eth_accounts because it returns a list of addresses owned by us.
    const accounts = await ethereum.request({ method: 'eth_accounts' });
    const chainId = await ethereum.request({ method: 'eth_chainId' });
    //We take the first address in the array of addresses and display it
    if (accounts[0]) {
      infos1.innerHTML = '<br>Account selected is ' + (accounts[0]);
      if (chainId == 4) {
        infos1.innerHTML += '<br>You are on Rinkeby.';
      } else if (chainId == 5) {
        infos1.innerHTML += '<br>You are on Goerli.';
      } else {
        infos1.innerHTML += '<br>You are on unsupported chain: ' + chainId + ".";
        return;
      }
      approveButton.disabled = false;
      purchaseButton.disabled = false;
    } else {
      infos1.innerHTML = '<br>Not able to get accounts';
    }
  });

  approveButton.addEventListener('click', async() => {
    const accounts = await ethereum.request({ method: 'eth_accounts' });
    const chainId = await ethereum.request({ method: 'eth_chainId' });
    var ierc20 = new myWeb3.eth.Contract(erc20ABI, tokenAddr.value);
    var bridgeAddr;
    if (chainId == 4) {
      bridgeAddr = bridgeRinkebyAddr;
    } else if (chainId == 5) {
      bridgeAddr = bridgeGoerliAddr;
    } else {
      infos2.innerHTML = '<br>You are on unsupported chain: ' + chainId + ".";
      return;
    }
    const val = Math.round(tokenAmount.value * 1e18)
    if (val <= 0) {
      infos2.innerHTML = '<br>Amount must be positive';
      return;
    }
    ierc20.methods.approve(bridgeAddr, BigInt(val)).send({
      from: accounts[0],
      gas: 50000,
      gasPrice: await myWeb3.eth.getGasPrice()
    }, async (err, res) => {
      if (err) {
        infos2.innerHTML = '<br>Transaction failed: ' + err + ".";
        return
      } else {
        infos2.innerHTML = "<br>Please wait for transaction " + res + " to confirm before proceeding.";
      }
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
        infos2.innerHTML += "<br>Transaction Succeed."
      } else {
        infos2.innerHTML += "<br>Transaction " + res + " fail to confirm.";
      }
    })
  });

  purchaseButton.addEventListener('click', async() => {
    const accounts = await ethereum.request({ method: 'eth_accounts' });
    const chainId = await ethereum.request({ method: 'eth_chainId' });
    var bridge;
    if (chainId == 4) {
      bridge = new myWeb3.eth.Contract(bridgeABI, bridgeRinkebyAddr);
    } else if (chainId == 5) {
      bridge = new myWeb3.eth.Contract(bridgeABI, bridgeGoerliAddr);
    } else {
      infos3.innerHTML = '<br>You are on unsupported chain: ' + chainId + ".";
      return;
    }
    const val = Math.round(tokenAmount.value * 1e18)
    if (val <= 0) {
      infos3.innerHTML = '<br>Amount must be positive';
      return;
    }
    bridge.methods.buyNFTOnOtherBlockchain(tokenAddr.value, BigInt(val), tokenRecipient.value, nftChain.value, nftContractAddr.value, nftId.value, accounts[0]).send({
      from: accounts[0],
      gas: 1000000,
      gasPrice: await myWeb3.eth.getGasPrice()
    }, async (err, res) => {
      if (err) {
        infos3.innerHTML = '<br>Transaction failed: ' + err + ".";
        return
      } else {
        infos3.innerHTML = "<br>Please wait for transaction " + res + " to confirm.";
      }
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
        infos3.innerHTML += "<br>Transaction Succeed."
      } else {
        infos3.innerHTML += "<br>Transaction " + res + " fail to confirm.";
      }
    });
  });

  function handleAccountChanged(_acct) {
    approveButton.disabled = true;
    purchaseButton.disabled = true;
    infos1.innerHTML = '<br>Chain/Account changed, please re-check again';
  }
  ethereum.on('chainChanged', handleAccountChanged);
  ethereum.on('accountsChanged', handleAccountChanged);
};
window.addEventListener('DOMContentLoaded', initialize);
