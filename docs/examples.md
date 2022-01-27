# Examples

## Set-up Ethereum nodes
The samples can be used with Ethereum MainNet, one of the test networks,
or blockchain nodes that run locally on your computer. To run blockchain 
nodes on your computer, see [Preparing Test Blockchains](./blockchains.md)

## Private Key
The example code needs a private key. Please use the [PKey Utility](../utils/pkey/README.md) to 
generate a private key. ./example.props contains a private key that could 
be used to test purposes.

## Example properties file

```$xslt
# Properties file for example code.

# This private key is used for the account submitting transactions on each blockchain.
PRIVATE_KEY=2af253c5d00cb3fb4c6215601410a6693f82024de6b79d3c72af54966d5043ec

# Consensus methodologies currently supported are Event Signing and Transaction Receipt Signing.
CONSENSUS_METHODOLOGY=EVENT_SIGNING
#CONSENSUS_METHODOLOGY=TRANSACTION_RECEIPT_SIGNING

# Execution engine can be serial or parallel. Serial will always work. Parallel currently fails
# when multiple transactions are issued for the same block. This will result in the transactions
# having the same nonce, which will fail.
EXECUTION_ENGINE=SERIAL
#EXECUTION_ENGINE=PARALLEL


# For each blockchain:
# XXXX_BC_ID: Blockchain ID is in hex, and must not be prefixed by 0x. The Blockchain ID must match the chain id that the blockchain
# configured with
# XXXX_URI: The IP address and port of the Ethereum node's JSON RPC.
# XXXX_GAS: The gas pricing strategy to use.
#   ETH_GAS_PRICE: The value returned by the Ethereum node Get Gas Price RPC call.
#   LOWEST: Use the lowest gas price used in a transaction that was accepted in the previous block.
#   HIGHEST: Use the highest gas price used in a transaction that was accepted in the previous block.
#   AVERAGE: Use the average gas price used in a transaction that was accepted in the previous block.
#   MEDIAN: Use the median gas price used in a transaction that was accepted in the previous block.
#   FREE: Use a gas price of 0 Eth.
# XXXX_PERIOD: The typical block period of the blockchain. This is used to determine how often the blockchain should be
# polled to see if a transaction has been added to a block. The value is in milli-seconds.


# Root blockchain
ROOT_BC_ID=1F
ROOT_URI=http://127.0.0.1:8310/
ROOT_GAS=FREE
ROOT_PERIOD=2000

BC2_BC_ID=20
BC2_URI=http://127.0.0.1:8320/
BC2_GAS=FREE
BC2_PERIOD=2000

BC3_BC_ID=21
BC3_URI=http://127.0.0.1:8330/
BC3_GAS=FREE
BC3_PERIOD=2000

BC4_BC_ID=22
BC4_URI=http://127.0.0.1:8340/
BC4_GAS=FREE
BC4_PERIOD=2000

BC5_BC_ID=23
BC5_URI=http://127.0.0.1:8350/
BC5_GAS=FREE
BC5_PERIOD=2000
```