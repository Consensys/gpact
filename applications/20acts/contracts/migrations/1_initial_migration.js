const Migrations = artifacts.require("Migrations");
const TwentyActs = artifacts.require("./ContractA.sol");

module.exports = function (deployer) {
  deployer.deploy(Migrations);
  deployer.deploy(TwentyActs);
};

