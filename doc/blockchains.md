## Preparing Test Blockchains

Assumptions:
 - The repos besu and gpact must reside together under a same directory.
 - Ethereum node configuration and data files go into ~/cgpact_data.

## 1. Get and build Hyperledger Besu 
Look at https://wiki.hyperledger.org/display/BESU/Building+from+source for the 
latest build information. At time of writing it was (assuming Mac OS build):
```bash
brew install libsodium
git clone --recursive https://github.com/hyperledger/besu
cd besu
./gradlew installDist
```

This will skip tests (they take very long) and unpack the distribution file (which will leave the binary at  `besu/build/install/besu/bin/besu`).

### Prepare to run the helper scripts
In macOS, it's currently recommended to use Node v10, not the latest v12. 
```bash
brew install node@10
``` 

Then install the libraries needed by the scripts: inside of the ./gpact directory, run:
```bash
npm install fs-extra
npm install toml-j0.4 
npm install tomlify-j0.4 
```

## 2. Configuration of blockchain nodes

### Quick start: 
To create a blockchain with chain ID 11 (0xb) with one node:
```bash
scripts/create_chain.js 11 1 
```
 ----------------
#### Explanation:
 
Each node directory will contain the node's key and data directory.

Each node will listen for RPC at port `8000 + chainId*10 + nodeN`. For example, node 0 of chain 22 will listen at port 8220. The script will remind you of this when it finishes.

### Create nodes used by example code
The following will create five blockchains with one node per blockchain:
```$xslt
scripts/create_chain.js 31 1
scripts/create_chain.js 32 1
scripts/create_chain.js 33 1
scripts/create_chain.js 34 1
scripts/create_chain.js 35 1
```


## 3. Run each node with the prepared config files

### Quick start: 
```bash
scripts/run_node.sh 11
```

### Run nodes used by example code
To start the blockchain nodes, in separate windows run:
```$xslt
./scripts/run_node.js 31
./scripts/run_node.js 32
./scripts/run_node.js 33
./scripts/run_node.js 34
./scripts/run_node.js 35
```


--------------------

## FYI: creating a Genesis File

As stated, this is all done for you by the script `scripts/create_chain.js`, but as a FYI here is the manual process to create an IBFT2 crosschain-enabled genesis file with the account address of the validator nodes (as explained in the Besu docs).

The `genesis_template.json` file can be used as a template, but needs to be customized with the address of your node/s. To do so:
1. Ensure that the nodes intended to be validators have their keys somewhere where they will not be deleted randomly - else they will be regenerated and you'll have to reconfigure the genesis file. 
2. Obtain each node's account address and put them all into a valid JSON file
3. Make Besu RLP-encode the extraData structure, built containing the addresses in the JSON file
3. Copy the extradata text into the "extradata" field of the genesis file



