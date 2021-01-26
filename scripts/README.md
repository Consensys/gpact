## General organization

Assumptions:
 - The repos besu and LTACFC must reside together under a same directory.
 - Node configuration and data files go into ~/cltacfc_data.

## 1. Build Besu 

Inside of the besu directory, build Besu with 

```
./gradlew installDist
```

This will skip tests (they take very long) and unpack the distribution file (which will leave the binary at  `besu/build/install/besu/bin/besu`).

### Prepare to run the helper scripts

They are written in JavaScript for coherence with Truffle and such.  
In macOS, it's currently recommended to use Node v10, not the latest v12. (Truffle suffers the same issue)
```bash
brew install node@10
``` 

Then install the libraries needed by the scripts: inside of the sidechains-samples directory, run:
```bash
npm install scripts
```

## 2. Configuration of blockchain nodes

### Quick start: 
To create 1 blockchain with chainIds 11 with one node:
```bash
scripts/create_chain.js 11 1 
```
 ----------------
#### Explanation:
 
Each node directory will contain the node's key and data directory.

Each node will listen for RPC at port `8000 + chainId*10 + nodeN`. For example, node 0 of chain 22 will listen at port 8220. The script will remind you of this when it finishes.



## 3. Run each node with the prepared config files

### Quick start: 

```bash
scripts/run_node.sh
```
--------------------

## FYI: creating a Genesis File

As stated, this is all done for you by the script `scripts/create_chain.js`, but as a FYI here is the manual process to create an IBFT2 crosschain-enabled genesis file with the account address of the validator nodes (as explained in the Besu docs).

The `genesis_template.json` file can be used as a template, but needs to be customized with the address of your node/s. To do so:
1. Ensure that the nodes intended to be validators have their keys somewhere where they will not be deleted randomly - else they will be regenerated and you'll have to reconfigure the genesis file. 
2. Obtain each node's account address and put them all into a valid JSON file
3. Make Besu RLP-encode the extraData structure, built containing the addresses in the JSON file
3. Copy the extradata text into the "extradata" field of the genesis file



