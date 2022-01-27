Trade Finance Example
-----------------------
The code in this directory is the trade-finance example described in the 
GPACT paper.


![Trade Finance using GPACT protocol](../../../doc/images/trade.png "Trade Finance using GPACT protocol")

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

