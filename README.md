# General Purpose Atomic Crosschain Transactions Protocol

This repo contains the General Purpose Atomic Crosschain Transactions (GPACT) 
protocol implementation and associated protocols. It contains Solidity contracts, Java library
code, test code, and example code. It contains Docker files to run multiple blockchains using 
Hyperledger Besu so that the entire system can be run on a laptop computer. 
The GPACT Protocol is described in this paper: https://arxiv.org/abs/2011.12783.

## Crosschain Protocol Layers
GPACT forms part of an overall crosschain protocol stack as shown in the diagram below.
The links in the table below will take you to implementations of those parts 
of the protocol stack.

<table>
<thead>
<tr>
  <th>Crosschain Protocol Layer</th>
  <th>Atomic Updates</th>
  <th>Not Atomic Updates</th>
</tr>
</thead>
<tbody>
<tr>
  <td rowspan=2>Crosschain Applications</td>
  <td>Examples: <br>
    <a href="https://github.com/ConsenSys/gpact/tree/main/application/gpact-examples/conditional">Conditional Execution</a><br>
    <a href="https://github.com/ConsenSys/gpact/tree/main/application/gpact-examples/hotel-train">Hotel Train problem (3 blockchains)</a><br>
    <a href="https://github.com/ConsenSys/gpact/tree/main/application/gpact-examples/read">Read across chains</a><br>
    <a href="https://github.com/ConsenSys/gpact/tree/main/application/gpact-examples/tokenbridge">ERC20 Token Bridge</a><br>
    <a href="https://github.com/ConsenSys/gpact/tree/main/application/gpact-examples/trade">Trade-Finance (5 blockchains)</a><br>
    <a href="https://github.com/ConsenSys/gpact/tree/main/application/gpact-examples/write">Write across chains</a><br>
  </td>
  <td>Examples:
    <a href="https://github.com/ConsenSys/gpact/tree/main/application/sfc-examples/erc20tokenbridge">ERC 20 Token Bridge</a><br>
    <a href="https://github.com/ConsenSys/gpact/tree/main/application/sfc-examples/erc721tokenbridge">ERC 721 Token Bridge</a><br>
    <a href="https://github.com/ConsenSys/gpact/tree/main/application/sfc-examples/write">Write across chains</a><br>
  </td>
</tr>
<tr>
  <td>Helper contracts:<br>
    <a href="https://github.com/ConsenSys/gpact/tree/main/application/atomic-appcontracts/erc20">Crosschain ERC20</a><br>
    <a href="https://github.com/ConsenSys/gpact/tree/main/application/atomic-appcontracts/erc20">Lockable storage</a><br>
  </td>
  <td>Helper contracts:<br>
    <a href="https://github.com/ConsenSys/gpact/tree/main/application/nonatomic-appcontracts/erc20bridge">ERC20 Token Bridge</a><br>
    <a href="https://github.com/ConsenSys/gpact/tree/main/application/nonatomic-appcontracts/erc721bridge">ERC721 Token Bridge</a><br>
  </td>
</tr>
<tr>
  <td rowspan="2">Crosschain Function Calls</td>
  <td colspan=2>
    <a href="https://github.com/ConsenSys/gpact/tree/main/functioncall/interface">Interfaces</a><br>
  </td>
</tr>
<tr>
  <td>
    <a href="https://github.com/ConsenSys/gpact/tree/main/functioncall/gpact">General Purpose Atomic Crosschain Transaction (GPACT)</a><br>
  </td>
  <td>
    <a href="https://github.com/ConsenSys/gpact/tree/main/functioncall/sfc">Simple Function Call (SFC)</a><br>
  </td>
</tr>
<tr>
  <td rowspan="2">Crosschain Messaging</td>
  <td colspan=2>
    <a href="https://github.com/ConsenSys/gpact/tree/main/messaging/interface">Interfaces</a><br>
  </td>
</tr>
<tr>
  <td colspan=2>
    Messaging implementations:<br>
    <a href="https://github.com/ConsenSys/gpact/tree/main/messaging/attestor-sign">Attestor Signing</a><br>
    <a href="https://github.com/ConsenSys/gpact/tree/main/messaging/txroot-transfer">Transaction Receipt Root Transfer</a><br>
  </td>
</tr>
</tbody>
</table>


Applications use the Crosschain Function Call code to execute function calls across blockchains.
Crosschain Function Call code uses Crosschain Message Verification to ensure information from
one blockchain is trusted on another blockchain. The layers of the protocol stack are separated
by interfaces. Using common interfaces allows applications to use a variety of crosschain 
function call implementations, and for crosschain function call implementations to use a variety
of crosschain messaging implementations. Importantly, this allows for different
crosschain messaging systems to be used for different blockchains and rollups. It
allows applications to choose use lighter weight non-atomic function call approaches 
(which may be less costly and have lower latency) for low cost and less important transactions
and fully atomic protocols such as GPACT for more important business critical transactions.

Applications that are written for atomic crosschain function protocols will 
be different to non-atomic function call protocols, because the non-atomic
implementations need to handle failures where an execution occurs on a source
blockchain by not a destination blockchain. 

It is expected that more Crosschain Messaging and Crosschain Function Call
implementations will be written. Additionally, more example 
application code will be written. Please get in contact if you are interested
in writing an implementation or an example.

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






