import { default as Web3ABI } from "web3-eth-abi";
import { TreeNode } from "./treenode.js";

export class CrosschainCall {
  constructor(chainID, contractType, contractAddr, method, ...params) {
    this.chainID = chainID;
    this.contractType = contractType;
    this.contractAddr = contractAddr;
    this.method = method;
    this.params = params;
  }
}

export class Caller {
  constructor(root, parent, current, cmgr, abis, links) {
    this.root = root;
    this.parent = parent;
    this.current = current;
    this.cmgr = cmgr;
    this.abis = abis;
    this.links = links;
  }

  decodeAtomicAuthParameters() {
    return [this.root.chainID, this.parent.chainID, this.parent.contractAddr];
  }

  async makeCall(call) {
    if (!this.abis.has(call.contractType)) {
      throw new Error("unsupported contract type");
    }
    const contractABI = this.abis.get(call.contractType);
    const methodABI = searchMethod(contractABI, call.method);
    if (methodABI == null) {
      throw new Error("method not found");
    }
    if (!this.links.has(call.contractType)) {
      throw new Error("link not found for given contract type");
    }
    if (!this.links.get(call.contractType).has(call.method)) {
      throw new Error("link not found for given method");
    }
    const link = this.links.get(call.contractType).get(call.method);
    const callData = Web3ABI.encodeFunctionCall(methodABI, call.params);
    const node = new TreeNode(call.chainID, call.contractAddr, callData);
    // Create caller
    const caller = new Caller(
      this.root,
      this.current,
      node,
      this.cmgr,
      this.abis,
      this.links
    );
    const rets = await link(this.cmgr, caller, call);
    if (rets == null) {
      if (methodABI.outputs.length != 0) {
        throw new Error("method output number mismatch 1");
      }
    } else if (rets.length != methodABI.outputs.length) {
      throw new Error("method output number mismatch 2");
    }
    this.current.addChild(node);
    return rets;
  }
}

export class Simulator {
  constructor(cmgr) {
    this.cmgr = cmgr;
    this.abis = new Map();
    this.links = new Map();
  }

  registerABI(contractType, contractABI) {
    this.abis.set(contractType, contractABI);
  }

  registerCallLink(contractType, method, link) {
    if (!this.links.has(contractType)) {
      this.links.set(contractType, new Map());
    }
    this.links.get(contractType).set(method, link);
  }

  async simulate(call) {
    if (!this.abis.has(call.contractType)) {
      throw new Error("unsupported contract type");
    }
    const contractABI = this.abis.get(call.contractType);
    const methodABI = searchMethod(contractABI, call.method);
    if (methodABI == null) {
      throw new Error("method not found");
    }
    if (!this.links.has(call.contractType)) {
      throw new Error("link not found for given contract type");
    }
    if (!this.links.get(call.contractType).has(call.method)) {
      throw new Error("link not found for given method");
    }
    const link = this.links.get(call.contractType).get(call.method);
    const callData = Web3ABI.encodeFunctionCall(methodABI, call.params);
    const root = new TreeNode(call.chainID, call.contractAddr, callData);
    // Create caller
    const caller = new Caller(
      root,
      null,
      root,
      this.cmgr,
      this.abis,
      this.links
    );
    const rets = await link(this.cmgr, caller, call);
    if (rets == null) {
      if (methodABI.outputs.length != 0) {
        throw new Error("method output number mismatch 1");
      }
    } else if (rets.length != methodABI.outputs.length) {
      throw new Error("method output number mismatch 2");
    }
    return [root, rets];
  }
}

function searchMethod(abi, name) {
  for (const method of abi) {
    if (method.name == name) {
      return method;
    }
  }
  return null;
}
