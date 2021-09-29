Attestor Sign
------------------------------
The code in this directory uses the Attestor - Sign model.

The process is:
* Attestors register their `address` (think public key) with destination blockchain's 
  [Registrar contract](https://github.com/ConsenSys/gpact/blob/main/messaging/attestor-sign/src/main/solidity/AttestorSignRegistrar.sol).
* A part of a Crosschain Transaction occurs on a blockchain. 
* The Crosschain Control contract emits an event.
* Attestor nodes (not yet implemented) observe blocks of transactions.
  For blocks that are deemed final, they gather events emitted by the 
  Crosschain Control contract.
  They cooperate to 
  threshold sign the event. Note that the Attestor nodes are currently simulated.
* The user has the transaction receipt for the transaction and fetches the 
  transaction receipt and the signed event. They ask an Attestor to give them
  the threshold signed event.
* The user then uses the threshold signed event with the Crosschain Function 
  Call protocol they are using.
* When the user submits the signed event to a Crosschain Control contract on 
  a target blockchain, the Crosschain Control contract will check with 
  [CrosschainVerifySign contracts](https://github.com/ConsenSys/gpact/blob/main/messaging/attestor-sign/src/main/solidity/CrosschainVerifierSign.sol)
  to check that the signed event is valid. 
  
Advantages of this technique: 
* The 
  latency will be lower than for Merkle Root / Transaction Receipt Root / 
  Block Header transfer mechanisms. 

Disadvantages of this technique:  
* Attestor nodes will need to observe each transaction on the source 
  blockchain.
* Attestors will need to respond to event signing requests from users.
  It could be imagined that some sort of DDOS counter-measures would 
  need to be in place. 
  