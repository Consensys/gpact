# General Purpose Atomic Crosschain Transactions Protocol

This repo contains the General Purpose Atomic Crosschain Transactions (GPACT) 
protocol reference implementation. It contains Solidity contracts, Java library
code, test code, and example code. This is an implementation 
of this paper: https://arxiv.org/abs/2005.09790.

## Introduction

The General Purpose Atomic Crosschain Transaction protocol is a blockchain technology
that allows function calls across blockchains that either updates state on all 
blockchains or discards state updates on all blockchains. The function calls can 
update state on each blockchain and return values across blockchains. The protocol 
enables applications to access information and utilise functionality that resides 
on one blockchain from other blockchains. Unlike previous atomic crosschain protocols 
that only offer atomic asset swaps, this protocol allows for general purpose application logic.


![Trade Finance using GPACT protocol](https://raw.githubusercontent.com/ConsenSys/LTACFC/master/doc/images/trade.png "Trade Finance using GPACT protocol")

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
[Protocol Description](https://arxiv.org/abs/2005.09790)

[How to build](https://github.com/ConsenSys/LTACFC/blob/master/doc/build.md)

[Reproducing Performance Results](https://github.com/ConsenSys/LTACFC/blob/master/doc/perf.md)

[Directories in this Repo](https://github.com/ConsenSys/LTACFC/blob/master/doc/directories.md)






