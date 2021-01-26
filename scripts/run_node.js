#!/usr/bin/env node
"use strict";

// Run one of the nodes created by the create_chain script

if (process.argv.length < 3) {
    console.log("Usage: run_node.js <chainId> [<nodeNumber>] ['extra args for Besu']");
    process.exit(1);
}

const cp = require('child_process');

const chainId = process.argv[2];
if (chainId>99) {
    console.log("Use a chainId < 99");
    process.exit(2)
}
const nodeNum = process.argv[3] || 0; // default to node 0

const executable = process.env.BESUPATH || `../besu/build/install/besu/bin/besu`
const resourcesPath = `scripts/resources`;
const destPath = process.env.HOME+`/cltacfc_data`;
const chainPath = `${destPath}/chain${chainId}`;
const nodePath = `${chainPath}/node${nodeNum}`;

let child = cp.execSync(`${executable} --revert-reason-enabled=true --config-file=${nodePath}/config.toml --data-path=${nodePath} --genesis-file=${chainPath}/genesis.json `+ (process.argv[4] || ""), { stdio: 'inherit' })//, { shell: false });
