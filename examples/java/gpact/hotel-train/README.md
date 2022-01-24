Hotel - Train Example
----------------
This example demonstrates how the hotel - train problem can be solved
using GPACT. The hotel - train problem is: a travel agency wants to 
book a hotel and a train. However, they don't want the hotel booking
if the train seat is not available, and vise-versa.


## Introduction
This sample demonstrates:
* A crosschain transaction that spans three blockchains. The travel
agency is on one blockchain. The hotel is on another blockchain. The
train is on a third blockchain.  
* The use of hotel and train contracts that lock individual 
  elements of the contract (single seats / hotel rooms). This 
  allows multiple simultaneous bookings.
* How to use the lockable ERC20 contract. This ERC 20 can be used
  in crosschain transactions, but can not be transferred to or from
  directly.
* Authentication EOA to a function in a contract.
* Authentication function in a contract on one blockchain calling a
  function in a contract on another blockchain.


## Running the Sample Code

This sample is to be used with three the Hyperledger Besu instances
each operating as a node on a separate blockchain. 

## Details

The diagram below shows the system architecture.

![Architecture Diagram](architecture.png)

The Hotel operates a blockchain to allow travel agencies and individuals 
to book and pay for rooms. The Hotel has a Hotel contract to allow booking 
rooms and a modified ERC 20 contract to allow for payment of the rooms.
The ERC 20 allows for value transfers to be part of crosschain transactions. 

Similarly, the Train operates a blockchain with contract representing 
the train, and a lockable ERC 20 contract. The contract representing
the train is in fact an instance of the Hotel.sol contract. This was done because the 
only difference between the Hotel contract and a possible Train contact would be variable 
names (seat vs room). 

To book a hotel and train, the travel agency creates a call execution tree. This is 
passed to the Crosschain Execution Engine, that then executes the entire transaction,
managing the parts of the transaction (Start on Travel Agency blockchain, Segments
on Hotel and Train blockchains, then Root on the Travel Agency blockchains, the Signals
on the Hotel and Train blockchains).

Note in the Travel Agency contract that it authenticates the call to book a hotel room
and a train seat, and the Hotel contract authenticates the crosschain call from the 
Travel Agency, ensuring it is in fact the Travel Agency contract that is doing the call. 