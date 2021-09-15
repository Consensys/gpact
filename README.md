# General Purpose Atomic Crosschain Transactions Protocol

This repo contains the General Purpose Atomic Crosschain Transactions (GPACT) 
protocol implementation and associated protocols. It contains Solidity contracts, Java library
code, test code, and example code. It contains Docker files to run multiple blockchains using 
Hyperledger Besu so that the entire system can be run on a laptop computer. 
The GPACT Protocol is described in this paper: https://arxiv.org/abs/2011.12783.

## Crosschain Protocol Layers
GPACT forms part of an overall crosschain protocol stack as shown in the diagram below.

![Crosschain Protocol Layers](https://raw.githubusercontent.com/ConsenSys/gpact/master/doc/images/layers.png "Crosschain Protocol Layers")

Applications use the Crosschain Function Call code to execute function calls across blockchains.
Crosschain Function Call code uses Crosschain Message Verification to ensure information from
one blockchain is trusted on another blockchain. The layers of the protocol stack are separated
by interfaces. Using common interfaces allows applications to use a variety of crosschain 
function call implementations, and for crosschain function call implementations to use a variety
of crosschain message verificaiton implementations. Importantly, this allows for different
crosschain message verification systems to be used for different blockchains and rollups. It
allows applications to choose use lighter weight non-atomic function call approaches 
(which may be less costly and have lower latency) for low cost and less important transactions
and fully atomic protocols such as GPACT for more important business critical transactions.

The code is arranged in the following main directories:
* application: Example code plus "appcontracts" that provide example
  implementations for lockable storage and crosschain ERC20.
* functioncall: GPACT, other function call protocols, and interfaces. 
* messaging: Crosschain message verification protocols and interfaces.


## GPACT

The General Purpose Atomic Crosschain Transaction protocol is a blockchain technology
that allows function calls across blockchains that either updates state on all 
blockchains or discards state updates on all blockchains. The function calls can 
update state on each blockchain and return values across blockchains. The protocol 
enables applications to access information and utilise functionality that resides 
on one blockchain from other blockchains. Unlike previous atomic crosschain protocols 
that only offer atomic asset swaps, this protocol allows for general purpose application logic.


![Trade Finance using GPACT protocol](https://raw.githubusercontent.com/ConsenSys/gpact/master/doc/images/trade.png "Trade Finance using GPACT protocol")

The figure above shows a logical representation of a crosschain call graph using the protocol. 
A trade finance application creates a crosschain function 
call that goes across five contracts on five blockchains to execute a trade for a shipment of goods. 
The Root Transaction executes the entry point function, the `executeTrade` function in the 
`Trade Wallet` contract on the `Wallet` blockchain. The `Trade Wallet` contract could be a 
multi-signatory wallet that parties to a shipment have to submit a transaction to, indicating that they agree 
a shipment for a certain quantity of goods has been made and should be paid for. The 
`executeTrade` function calls the shipment function in the `Logic` contract on the `Terms` blockchain 
to determine the price that should be paid and to affect the transfer of stock and payment. The 
`shipment` function calls the `getPrice` function on the `Oracle` contract on the `Price Oracle` 
blockchain to determine the price that should be paid for the goods, then calls the `transfer` 
function on the `Balances` contract on the `Finance` blockchain to affect the payment, and finally 
calls the `delivery` function on the `Stock` contract on the `Logistics` blockchain to register the 
changed ownership of the goods.

It could be argued that some of the contracts could exist on the one blockchain, thus reducing 
the need for crosschain transactions. However, the `Finance` blockchain and the `Logistics` 
blockchain in particular could be consortium blockchains involving different participants. 
The `Price Oracle` blockchain could be operated by a consortium that charged for access to the 
information. Government regulators could require the logic on the `Terms` blockchain visible to
them, but the participants in the trade wallet on the `Wallet` blockchain may wish to remain 
anonymous. A crosschain transaction capability is needed to meet these requirements.


## More information
[How to build](https://github.com/ConsenSys/gpact/blob/master/doc/build.md)

[Reproducing Performance Results](https://github.com/ConsenSys/gpact/blob/master/doc/perf.md)

[Directories in this Repo](https://github.com/ConsenSys/gpact/blob/master/doc/directories.md)






