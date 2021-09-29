Transaction Receipt Root Transfer
------------------------------
The code in this directory uses the Transaction Receipt Root Transfer model.

The process is:
* Transaction Receipt Root signers register their `address` (think public key) with destination blockchain's 
  [Registrar contract](https://github.com/ConsenSys/gpact/blob/main/messaging/attestor-sign/src/main/solidity/AttestorSignRegistrar.sol).
* A part of a Crosschain Transaction occurs on a blockchain. 
* The Crosschain Control contract emits an event.
* Transaction Receipt Relay nodes (not yet implemented) observe blocks of transactions.
  For blocks that are deemed final, they determine if there are any events emitted by the 
  Crosschain Control contract. If there are, the relayers cooperate to 
  threshold sign the transaction receipt root. One of the relayers transfers
  the signed transaction receipt root to the destination chains that will
  need to use the transaction receipt root. That is, blockchains that are 
  destination chains. If it is not possible to infer which destination 
  blockchain will use the signed transaction receipt root, then the 
  signed transaction receipt root will need to be transferred to all chains.
  Note that the relayer nodes are currently simulated.
* The user has the transaction receipt for the transaction. They calculate
  a proof to prove the transaction receipt is part of the modified Patricia 
  Merkle trie containing all transaction receipts for the block.  
* The user then uses the proof with the transaction receipt with the
  Crosschain Function Call protocol they are using.
* When the user submits the transaction receipt and proof to a 
  Crosschain Control contract on 
  a target blockchain, the Crosschain Control contract will check with 
  [CrosschainVerifierTxRoot contracts](https://github.com/ConsenSys/gpact/blob/main/messaging/txroot-transfer/src/main/solidity/CrosschainVerifierTxRoot.sol)
  to check that the transaction receipt and the signed event within it is valid. 
  
Advantages of this technique: 
* Relay nodes only need to observe block headers and act on them.
  
Disadvantages of this technique:  
* Higher latency. The transaction receipt root needs to be transferred first
  in one transaction. The user is likely to want this to become final. 
  Then the transaction receipt itself can be used as part of a crosschain
  transaction. 
* Relayers nodes do not need to observe each transaction on the source 
  blockchain.
* Relayers do not need to respond to requests from users. 

  