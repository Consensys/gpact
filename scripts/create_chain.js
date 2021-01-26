#!/usr/bin/env node
"use strict";

// Creates a chain directory, with multiple node subdirectories, with the appropriate configuration files to make
//    the whole chain work together

if (process.argv.length < 4) {
    console.log("Missing arguments: create_chain.js <chainID> <nodeCount>");
    process.exit(1);
}

const chainId = process.argv[2];
if (chainId>99) {
    console.log("Use a chainId < 99");
    process.exit(2);
}

const nodeCount = process.argv[3];
if (nodeCount>=10 || nodeCount<1) {
    console.log("nodeCount must be at least 1 and less than 10");
    process.exit(3);
}

if (nodeCount>=10) {
    console.log("Use a nodeCount < 10");
    process.exit(3);
}

console.log("Creating a blockchain with chainID " + chainId + " and " + nodeCount + " validators in the genesis file...");

const besuPath = process.env.BESUPATH || `../besu/build/install/besu/bin/besu`
const resourcesPath = `scripts/resources`;
const destPath = process.env.HOME+`/cltacfc_data`;
const basePort = 8000;
const baseP2PPort = 30000;
const chainPath = `${destPath}/chain${chainId}`;

const { execFileSync } = require('child_process');
const fs = require('fs-extra');
const toml = require('toml-j0.4')
const tomlEncoder = require('tomlify-j0.4')


var staticNode = "[\n";
var node_acct_list = [];

// For each node, create its data directory and configuration files, and capture data that will be needed at the chain level
for (var i = 0; i < nodeCount; i++) {
    const nodeNum = i.toString();
    const nodePath = `${chainPath}/node${nodeNum}`;

    if (i == 0) {
        fs.emptyDirSync(chainPath)
    }
    fs.emptyDirSync(nodePath)

    let config = toml.parse(fs.readFileSync(resourcesPath + "/config_template.toml").toString());
    let port = basePort + chainId*10 + nodeNum*1
    let p2pPort = baseP2PPort + chainId*10 + nodeNum*1
    config['p2p-enabled'] = true
    config['p2p-port'] = p2pPort
    config['rpc-http-port'] = port

    // miner-extra-data is just informative. Let's record who in the chain mined the block.
    config['miner-extra-data'] = "0x000000000000000000000000000000000000000000000000000000000000"+
                                    chainId.padStart(2, '0') + nodeNum.padStart(2,'0');
    fs.writeFileSync(`${nodePath}/config.toml`,tomlEncoder.toToml(config,{ replace:
        // JavaScript has no integers, only floats. Here we format all integer-like values to have 0 decimals.
        function (key, value) {
            if (Number.isInteger(value)){
                return value.toFixed(0)
            }
            // for anything non-integer-looking, return false, which cancels the custom encoding
            return false
        }
    }))


    let child = execFileSync(besuPath, [`--data-path=${nodePath}`,'public-key', 'export-address',`--to=${nodePath}/node-acct`]);

    let node_acct = fs.readFileSync(`${nodePath}/node-acct`).toString().trim();
    node_acct_list.push(node_acct);

    // Besu might show warnings, so make it clear that we finished successfully.
    console.log(`Created config files for node ${nodeNum} as a validator for chainId ${chainId} at RPC port ${port}.`);

    child = execFileSync(besuPath, [`--data-path=${nodePath}`,'public-key', 'export',`--to=${nodePath}/enode`]);
    var enode = fs.readFileSync(`${nodePath}/enode`).toString().substring(2,);
    staticNode += "\"enode://" + enode + "@127.0.0.1:" + p2pPort.toString() + "\"";
    if (i != nodeCount - 1) {
        staticNode += ","
    }
    staticNode += "\n"
}
staticNode += "]\n"

// Write a static nodes file for each node
for (var i = 0; i < nodeCount; i++) {
    const nodeNum = i.toString();
    const nodePath = `${chainPath}/node${nodeNum}`;
    fs.writeFileSync(`${nodePath}/static-nodes.json`, staticNode);
}

// Create the chain's genesis file
fs.writeFileSync(`${chainPath}/toEncode.json`, JSON.stringify(node_acct_list.sort()));

let child = execFileSync(besuPath, ['rlp','encode', `--from=${chainPath}/toEncode.json`,`--to=${chainPath}/extradata`], { stdio: 'ignore' });
let extradata = fs.readFileSync(`${chainPath}/extradata`).toString().trim();

fs.unlinkSync(`${chainPath}/toEncode.json`);
fs.unlinkSync(`${chainPath}/extradata`);

let genesis = JSON.parse(fs.readFileSync(`${resourcesPath}/genesis_template.json`).toString().trim());
genesis["extraData"] = extradata;

genesis["config"]["chainId"] = parseInt(chainId);
fs.writeFileSync(`${chainPath}/genesis.json`, JSON.stringify(genesis, null, 4));
