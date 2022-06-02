import * as fs from 'fs';
import Web3 from 'web3';
import execSync from 'child_process';
import { MsgStore } from "./msgstore.js"
import { Executor } from "./executor.js"
import { ChainAPManager } from "./chainmgr.js";
import { Simulator, CrosschainCall } from "./simulator.js"

// Pre-set key and addr
const signerKey = "c96e489ac9cc2211144d4e428d6cfbe29aa7822e38ea7721ad9f6ed920b203bb";
const signerAddr = "0xfBdfD9c515c4b696D5133bE894A674b61f84229B";
const sellerKey = "98f2a9ea4797679231a1771fe8475e947b80e31fb4dd0289e6475a8e4ca9fbc3";
const sellerAddr = "0xBB7C00f1dd65bCE0e10a6B6228Dff430C9c1C871";
const buyerKey = "c4e8789cac69d6d962ce279812fdf06a194801beef6d35ab062d1b3972c0d384";
const buyerAddr = "0x9F2758f2e0e9A5c4Ba389035C4c2895232d84b46";
let chainA;
let chainB;
let gpactAddrA;
let gpactAddrB;
let bridgeAddrA;
let bridgeAddrB;
let nftAddrA;
let tokenAddrB;

async function config() {
    // Get ABI & Bin for register, verifier, gpact and application.
    let regABI = JSON.parse(fs.readFileSync("../java/build/MessagingRegistrar.abi", "utf8"));
    let regBIN = fs.readFileSync("../java/build/MessagingRegistrar.bin", "utf8");
    let vefABI = JSON.parse(fs.readFileSync("../java/build/EventAttestationVerifier.abi", "utf8"));
    let vefBIN = fs.readFileSync("../java/build/EventAttestationVerifier.bin", "utf8");
    let gpactABI = JSON.parse(fs.readFileSync("../java/build/CrosschainControl.abi", "utf8"));
    let gpactBIN = fs.readFileSync("../java/build/CrosschainControl.bin", "utf8");
    let bridgeABI = JSON.parse(fs.readFileSync("./resources/AtomicBridge.abi", "utf8"));
    let bridgeBIN = fs.readFileSync("./resources/AtomicBridge.bin", "utf8");
    let tokenABI = JSON.parse(fs.readFileSync("./resources/CoinToken.abi", "utf8"));
    let tokenBIN = fs.readFileSync("./resources/CoinToken.bin", "utf8");
    let nftABI = JSON.parse(fs.readFileSync("./resources/GameItem.abi", "utf8"));
    let nftBIN = fs.readFileSync("./resources/GameItem.bin", "utf8");

    // Get web3 & create accounts.
    let web3A = new Web3('http://127.0.0.1:8111');
    let web3B = new Web3('http://127.0.0.1:8222');
    web3A.eth.accounts.wallet.create(1, web3A.utils.randomHex(32));
    web3B.eth.accounts.wallet.create(1, web3B.utils.randomHex(32));
    let accountA = web3A.eth.accounts.wallet[0].address;
    let accountB = web3B.eth.accounts.wallet[0].address;
    chainA = await web3A.eth.getChainId();
    chainB = await web3B.eth.getChainId();
    
    // Setup
    console.log("Testing environment setup...");
    
    // Deploy registrars
    let regA = new web3A.eth.Contract(regABI);
    let regB = new web3B.eth.Contract(regABI);
    let res;
    res = await regA.deploy({
        data: regBIN
    }).send({
        from: accountA,
        gas: 10000000
    });
    let regAddrA = res.options.address;
    regA.options.address = regAddrA;
    console.log("Registrar A:", regAddrA);
    res = await regB.deploy({
        data: regBIN
    }).send({
        from: accountB,
        gas: 10000000
    });
    let regAddrB = res.options.address;
    regB.options.address = regAddrB;
    console.log("Registrar B:", regAddrB);

    // Add signer
    res = await regA.methods.addSignerSetThreshold(chainA, signerAddr, 1).send({
        from: accountA,
        gas: 10000000
    });
    console.log(res.status)
    await regA.methods.addSignerSetThreshold(chainB, signerAddr, 1).send({
        from: accountA,
        gas: 10000000
    });
    console.log(res.status)
    await regB.methods.addSignerSetThreshold(chainA, signerAddr, 1).send({
        from: accountB,
        gas: 10000000
    });
    console.log(res.status)
    await regB.methods.addSignerSetThreshold(chainB, signerAddr, 1).send({
        from: accountB,
        gas: 10000000
    });
    console.log(res.status)

    // Deploy verifiers
    let vefA = new web3A.eth.Contract(vefABI)
    let vefB = new web3B.eth.Contract(vefABI)
    res = await vefA.deploy({
        data: vefBIN,
        arguments: [regA.options.address]
    }).send({
        from: accountA,
        gas: 10000000
    });
    let vefAddrA = res.options.address;
    vefA.options.address = vefAddrA;
    console.log("Verifier A:", vefAddrA);
    res = await vefB.deploy({
        data: vefBIN,
        arguments: [regB.options.address]
    }).send({
        from: accountB,
        gas: 10000000
    });
    let vefAddrB = res.options.address;
    vefB.options.address = vefAddrB;
    console.log("Verifier B:", vefAddrB);

    // Deploy gpacts
    let gpactA = new web3A.eth.Contract(gpactABI)
    let gpactB = new web3B.eth.Contract(gpactABI)
    res = await gpactA.deploy({
        data: gpactBIN,
        arguments: [chainA]
    }).send({
        from: accountA,           
        gas: 10000000
    });
    gpactAddrA = res.options.address;
    gpactA.options.address = gpactAddrA;
    console.log("Gpact A:", gpactAddrA);
    res = await gpactB.deploy({
        data: gpactBIN,
        arguments: [chainB]
    }).send({
        from: accountB,           
        gas: 10000000
    });
    gpactAddrB = res.options.address;
    gpactB.options.address = gpactAddrB;
    console.log("Gpact B:", gpactAddrB);

    // Add verifiers
    res = await gpactA.methods.addVerifier(chainA, vefA.options.address).send({
        from: accountA,
        gas: 10000000
    });
    console.log(res.status);
    res = await gpactA.methods.addVerifier(chainB, vefA.options.address).send({
        from: accountA,
        gas: 10000000
    });
    console.log(res.status);
    res = await gpactB.methods.addVerifier(chainA, vefB.options.address).send({
        from: accountB,
        gas: 10000000
    });
    console.log(res.status);
    res = await gpactB.methods.addVerifier(chainB, vefB.options.address).send({
        from: accountB,
        gas: 10000000
    });
    console.log(res.status);

    // Add gpact mappings
    res = await gpactA.methods.addRemoteCrosschainControl(chainA, gpactA.options.address).send({
        from: accountA,
        gas: 10000000
    });
    console.log(res.status);
    res = await gpactA.methods.addRemoteCrosschainControl(chainB, gpactB.options.address).send({
        from: accountA,
        gas: 10000000
    });
    console.log(res.status);
    res = await gpactB.methods.addRemoteCrosschainControl(chainA, gpactA.options.address).send({
        from: accountB,
        gas: 10000000
    });
    console.log(res.status);
    res = await gpactB.methods.addRemoteCrosschainControl(chainB, gpactB.options.address).send({
        from: accountB,
        gas: 10000000
    });
    console.log(res.status);

    // Deploy bridges.
    let bridgeA = new web3A.eth.Contract(bridgeABI);
    let bridgeB = new web3B.eth.Contract(bridgeABI);
    res = await bridgeA.deploy({
        data: bridgeBIN,
        arguments: [gpactAddrA]
    }).send({
        from: accountA,
        gas: 10000000
    });
    bridgeAddrA = res.options.address;
    bridgeA.options.address = bridgeAddrA;
    console.log("Bridge A:", bridgeAddrA);
    res = await bridgeB.deploy({
        data: bridgeBIN,
        arguments: [gpactAddrB]
    }).send({
        from: accountB,
        gas: 10000000
    });
    bridgeAddrB = res.options.address;
    bridgeB.options.address = bridgeAddrB;
    console.log("Bridge B:", bridgeAddrB);

    // Add bridge mapping
    res = await bridgeA.methods.registerRemoteBridges([chainB], [bridgeAddrB]).send({
        from: accountA,
        gas: 10000000
    });
    console.log(res.status);
    res = await bridgeB.methods.registerRemoteBridges([chainA], [bridgeAddrA]).send({
        from: accountB,
        gas: 10000000
    });
    console.log(res.status);

    // Deploy NFT on chain A and token on chain B
    let nftA = new web3A.eth.Contract(nftABI);
    let tokenB = new web3B.eth.Contract(tokenABI);
    res = await nftA.deploy({
        data: nftBIN
    }).send({
        from: accountA,
        gas: 10000000
    });
    nftAddrA = res.options.address;
    nftA.options.address = nftAddrA;
    console.log("NFT A:", nftAddrA);
    res = await tokenB.deploy({
        data: tokenBIN,
        arguments: ["1000000000000000000000"]
    }).send({
        from: accountB,
        gas: 10000000
    });
    tokenAddrB = res.options.address;
    tokenB.options.address = tokenAddrB;
    console.log("Token B:", tokenAddrB);

    // Send NFT and tokens to test accounts
    res = await nftA.methods.awardItem(sellerAddr).send({
        from: accountA,
        gas: 10000000
    });
    console.log(res.status);
    res = await nftA.methods.awardItem(sellerAddr).send({
        from: accountA,
        gas: 10000000
    });
    console.log(res.status);
    res = await nftA.methods.awardItem(sellerAddr).send({
        from: accountA,
        gas: 10000000
    });
    console.log(res.status);
    res = await tokenB.methods.transfer(buyerAddr, "1000000000000000000000").send({
        from: accountB,
        gas: 10000000
    });
    console.log(res.status);

    // Configure relayer components
    let output;
    output = execSync.execSync('docker exec observer1 /app/build/admin observer start localhost:9425 ' + chainA + " ws://bc31node1:8546 GPACT " + gpactA.options.address);
    console.log(output.toString());
    output = execSync.execSync('docker exec observer2 /app/build/admin observer start localhost:9425 ' + chainB + " ws://bc32node1:8546 GPACT " + gpactB.options.address);
    console.log(output.toString());
    output = execSync.execSync('docker exec relayer /app/build/admin relayer set-key localhost:9425 0 0x0000000000000000000000000000000000000000 ' + signerKey);
    console.log(output.toString());
    output = execSync.execSync('docker exec dispatcher /app/build/admin dispatcher set-msgstore localhost:9425 msgstore:8080');
    console.log(output.toString());
}
async function test() {
    // Get ABIs
    let bridgeABI = JSON.parse(fs.readFileSync("./resources/AtomicBridge.abi", "utf8"));
    let tokenABI = JSON.parse(fs.readFileSync("./resources/CoinToken.abi", "utf8"));
    let nftABI = JSON.parse(fs.readFileSync("./resources/GameItem.abi", "utf8"));
    let listingABI = JSON.parse(fs.readFileSync("./resources/ListingStorage.abi", "utf8"));

    // Get web3 & add accounts.
    let web3A_seller = new Web3('http://127.0.0.1:8111');
    let web3B_seller = new Web3('http://127.0.0.1:8222');
    let web3A_buyer = new Web3('http://127.0.0.1:8111');
    let web3B_buyer = new Web3('http://127.0.0.1:8222');
    web3A_seller.eth.accounts.wallet.add(sellerKey);
    web3B_seller.eth.accounts.wallet.add(sellerKey);
    web3A_buyer.eth.accounts.wallet.add(buyerKey);
    web3B_buyer.eth.accounts.wallet.add(buyerKey);

    // Initialise SDK
    // Create chain manager.
    let chainMgr = new ChainAPManager();
    chainMgr.registerChainAP(chainA, web3A_buyer);
    chainMgr.registerChainAP(chainB, web3B_buyer);
    // Create message store manager.
    let ms = new MsgStore("localhost:8080");
    // Create simulator.
    let simulator = new Simulator(chainMgr);
    simulator.registerABI("bridge", bridgeABI);
    simulator.registerCallLink("bridge", "buyNFTUsingRemoteFunds", async function (cmgr, caller, call) {
        // Parse arguments.
        if (call.params.length != 5) {
            throw new Error("invalid parameters");
        }
        let buyer = call.params[0];
        let _nftContract = call.params[1];
        let nftId = call.params[2];
        let otherBcId = call.params[3];
        let otherTokenContract = call.params[4];
        // Load bridge contract.
        let chain = await cmgr.chainAP(call.chainID);
        let bridge = new chain.eth.Contract(bridgeABI, call.contractAddr);
        // ====== START CONTRACT LOGIC ======
        let destBridge = await bridge.methods.remoteBridges(otherBcId).call();
        if (destBridge == "0x0000000000000000000000000000000000000000") {
            throw new Error("Bridge: dest chain not supported");
        }
        // Find listing.
        let storageContract = new chain.eth.Contract(listingABI, await bridge.methods.listingStorage().call());
        await storageContract.methods.findListing(_nftContract, nftId).call();
        // Find asking.
        let asking = await storageContract.methods.findAsking(_nftContract, nftId, otherBcId, otherTokenContract).call();
        // Perform cross call.
        let subCall = new CrosschainCall(otherBcId, "bridge", destBridge, "processTokenTransfer", otherTokenContract, asking.amount, buyer, asking.recipient);
        await caller.makeCall(subCall);
        return null;
    });
    simulator.registerCallLink("bridge", "processTokenTransfer", async function (cmgr, caller, call) {
        // Parse arguments.
        if (call.params.length != 4) {
            throw new Error("invalid parameters");
        }
        let _tokenContract = call.params[0];
        let amt = call.params[1];
        let from = call.params[2];
        // let to = call.params[3] // Not used.
        // Load bridge contract.
        let chain  = await cmgr.chainAP(call.chainID);
        let bridge = new chain.eth.Contract(bridgeABI, call.contractAddr);
        // ====== START CONTRACT LOGIC ======
        let auth = caller.decodeAtomicAuthParameters();
        let sourceBcId = auth[1];
        let sourceContract = auth[2];
        if (sourceContract != await bridge.methods.remoteBridges(sourceBcId).call()) {
            throw new Error("Bridge: Contract does not match");
        }
        let erc20 = new chain.eth.Contract(tokenABI, _tokenContract);
        if (await erc20.methods.allowance(from, call.contractAddr).call() < amt) {
            throw new Error("ERC20 allowance not enough");
        }
        return null;
    });
    // Create executor
    let executor = new Executor(chainMgr, buyerAddr, ms);
    executor.registerGPACT(chainA, gpactAddrA);
    executor.registerGPACT(chainB, gpactAddrB);

    // Test happy case
    let bridgeContract = new web3A_seller.eth.Contract(bridgeABI, bridgeAddrA);
    let nftContract = new web3A_seller.eth.Contract(nftABI, nftAddrA);
    let tokenContract = new web3B_buyer.eth.Contract(tokenABI, tokenAddrB);

    let res;
    res = await nftContract.methods.approve(bridgeAddrA, 1).send({
        from: sellerAddr,
        gas: 10000000,
    });
    console.log(res.status);

    res = await bridgeContract.methods.startListingNFTWithAsking(nftAddrA, 1, chainB, tokenAddrB, 100, sellerAddr).send({
        from: sellerAddr,
        gas: 10000000,
    });
    console.log(res.status);

    // Get balance before
    let nftOwner = await nftContract.methods.ownerOf(1).call();
    let tokenBalanceBuyer = await tokenContract.methods.balanceOf(buyerAddr).call();
    let tokenBalanceSeller = await tokenContract.methods.balanceOf(sellerAddr).call();
    if (nftOwner != sellerAddr) {
        throw new Error("nft 1 should belong to seller before purchase");
    }
    if (tokenBalanceBuyer != BigInt("1000000000000000000000")) {
        throw new Error("buyer should have 1000000000000000000000 tokens before purchase");
    }
    if (tokenBalanceSeller != BigInt(0)) {
        throw new Error("seller should have 0 tokens before purchase");
    }

    // Approve & do the purchase
    res = await tokenContract.methods.approve(bridgeAddrB, 100).send({
        from: buyerAddr,
        gas: 10000000,
    });
    console.log(res.status);

    let temp = await simulator.simulate(new CrosschainCall(chainA, "bridge", bridgeAddrA, "buyNFTUsingRemoteFunds", buyerAddr, nftAddrA, 1, chainB, tokenAddrB));
    let root = temp[0];
    await executor.crosschainCall(root);

    // Get balance after
    nftOwner = await nftContract.methods.ownerOf(1).call();
    tokenBalanceBuyer = await tokenContract.methods.balanceOf(buyerAddr).call();
    tokenBalanceSeller = await tokenContract.methods.balanceOf(sellerAddr).call();
    if (nftOwner != buyerAddr) {
        throw new Error("nft 1 should belong to buyer after purchase");
    }
    if (tokenBalanceBuyer != BigInt("999999999999999999900")) {
        throw new Error("buyer should have 999999999999999999900 tokens after purchase");
    }
    if (tokenBalanceSeller != BigInt(100)) {
        throw new Error("seller should have 100 tokens after purchase");
    }
    console.log("Testing happy case succeed")

    // Start testing failure cases.
    res = await nftContract.methods.approve(bridgeAddrA, 2).send({
        from: sellerAddr,
        gas: 10000000,
    });
    console.log(res.status);

    res = await bridgeContract.methods.startListingNFTWithAsking(nftAddrA, 2, chainB, tokenAddrB, 100, sellerAddr).send({
        from: sellerAddr,
        gas: 10000000,
    });
    console.log(res.status);

    // Test failure case #1, change approved token amount after simulation.
    res = await tokenContract.methods.approve(bridgeAddrB, 100).send({
        from: buyerAddr,
        gas: 10000000,
    });
    console.log(res.status);

    temp = await simulator.simulate(new CrosschainCall(chainA, "bridge", bridgeAddrA, "buyNFTUsingRemoteFunds", buyerAddr, nftAddrA, 2, chainB, tokenAddrB));
    root = temp[0];
    // Change approved amount.
    res = await tokenContract.methods.approve(bridgeAddrB, 90).send({
        from: buyerAddr,
        gas: 10000000,
    });
    console.log(res.status);
    console.log(res.status);
    await executor.crosschainCall(root);

    // Get balance after, should not change
    nftOwner = await nftContract.methods.ownerOf(2).call();
    tokenBalanceBuyer = await tokenContract.methods.balanceOf(buyerAddr).call();
    tokenBalanceSeller = await tokenContract.methods.balanceOf(sellerAddr).call();
    if (nftOwner != sellerAddr) {
        throw new Error("nft 1 should belong to seller after failed purchase");
    }
    if (tokenBalanceBuyer != BigInt("999999999999999999900")) {
        throw new Error("buyer should still have 999999999999999999900 tokens after failed purchase");
    }
    if (tokenBalanceSeller != BigInt(100)) {
        throw new Error("seller should have still 100 tokens after failed purchase");
    }
    console.log("Testing failure case #1 succeed")

    // Test failure case #2, update asking with higher price
    res = await tokenContract.methods.approve(bridgeAddrB, 100).send({
        from: buyerAddr,
        gas: 10000000,
    });
    console.log(res.status);

    temp = await simulator.simulate(new CrosschainCall(chainA, "bridge", bridgeAddrA, "buyNFTUsingRemoteFunds", buyerAddr, nftAddrA, 2, chainB, tokenAddrB));
    root = temp[0];
    // Update asking with higher price.
    res = await bridgeContract.methods.upsertAsking(nftAddrA, 2, chainB, tokenAddrB, 110, sellerAddr).send({
        from: sellerAddr,
        gas: 10000000,
    });
    console.log(res.status);
    await executor.crosschainCall(root);

    // Get balance after, should not change
    nftOwner = await nftContract.methods.ownerOf(2).call();
    tokenBalanceBuyer = await tokenContract.methods.balanceOf(buyerAddr).call();
    tokenBalanceSeller = await tokenContract.methods.balanceOf(sellerAddr).call();
    if (nftOwner != sellerAddr) {
        throw new Error("nft 1 should belong to seller after failed purchase");
    }
    if (tokenBalanceBuyer != BigInt("999999999999999999900")) {
        throw new Error("buyer should still have 999999999999999999900 tokens after failed purchase");
    }
    if (tokenBalanceSeller != BigInt(100)) {
        throw new Error("seller should have still 100 tokens after failed purchase");
    }
    console.log("Testing failure case #2 succeed")

    // Test failure case #3, stop listing after simulation.
    res = await tokenContract.methods.approve(bridgeAddrB, 110).send({
        from: buyerAddr,
        gas: 10000000,
    });
    console.log(res.status);

    temp = await simulator.simulate(new CrosschainCall(chainA, "bridge", bridgeAddrA, "buyNFTUsingRemoteFunds", buyerAddr, nftAddrA, 2, chainB, tokenAddrB));
    root = temp[0];
    // Stop the listing after a simulation is done.
    res = await bridgeContract.methods.stopListingNFT(nftAddrA, 2).send({
        from: sellerAddr,
        gas: 10000000,
    });
    console.log(res.status);
    await executor.crosschainCall(root);

    // Get balance after, should not change
    nftOwner = await nftContract.methods.ownerOf(2).call();
    tokenBalanceBuyer = await tokenContract.methods.balanceOf(buyerAddr).call();
    tokenBalanceSeller = await tokenContract.methods.balanceOf(sellerAddr).call();
    if (nftOwner != sellerAddr) {
        throw new Error("nft 1 should belong to seller after failed purchase");
    }
    if (tokenBalanceBuyer != BigInt("999999999999999999900")) {
        throw new Error("buyer should still have 999999999999999999900 tokens after failed purchase");
    }
    if (tokenBalanceSeller != BigInt(100)) {
        throw new Error("seller should have still 100 tokens after failed purchase");
    }
    console.log("Testing failure case #3 succeed")
}
await config();
await test();