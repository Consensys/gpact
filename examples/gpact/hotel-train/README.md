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

![Architecture Diagram](./architecture.png)

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

[Click on this link to see the sequence diagram of the Hotel - Train example](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIHEAUCCBhAKtAEge2Jc0BaaVAJwEMQA7aAUQA8yBbABymgFcBnKgc1oDdIlYNETBcnYGVBZKALmgBlEC3AgAZiEgATAFAAjHMCwsyJUAGMQzMsM67dN8yCs3hPElnbNoAYjU8ABbAAJ544FgA7tAAVDGIzKwu0iCy0MhY2pBxjmaW1rYiCUkWKbIAdABWZPxkuc6uhUoAIgDSukJ6DvX5bsAeXj6+zFQA1rExpDV4ojxCFiHQAEIRFqMWgRSUOU69TQBEAEp4ZGEk0AD0ouKQklgk+9BknKLH4KeQJGISxiQ9LgV3J5vH4AsEPJAhBMALK3ThkHi8aAAGQ+JB2eQBfWg+2+t1+yh4lGOiMk5BI5U4WHAj2eojxdxIhOJkFJwHJlOp-0awhxyE8nE4Gy2ADVPhotEyQETOTSni8UAKhZsqGKSBLPszZR1KNpoNzAf1gUMwcB9OB2DA4gAxdiUCwyajIMjgfCos4YhqGvlK4VUDLCTzgWW0hX8rCCv2UAPs6nazoGvoDEH+aXgkiQRZxYpqUqOlFoz17Xn7KaCcCIOb2kIh+XEcjlyvzGtU8A6vQJxOFZNDCFZmLYXD4FZYNZRotYg5vNGXa4-e6h5bTs4M35doGDUFpo2Q6hxWGChFI92fCc8kS4m6M5kkkBksy1ulLVf3G+su-sh+t9cX8ORlWUGqGpSjKraLksf7KqK4qaJq0qUPGuo-j2W5BGaFpWjEtr2vmzqugWHoxD+PoRlB-qyLGwZgXWEG+gBMZBohei7JOG4pqafYTDmyT5ie6JESx544oOeCPi8IlUVygnevsyKjqMZDmpANCHMgABMAAM8AZpwkDANaIB0DoijeKwLbUuBclrIpUAqepWk6XpBlGdoJmJOA5ltgmnbSUmxp+JxcRTFQyyrOsAFnjJy6fLOL4PHWyDRV8V5rsh-mpmhEJQvucJHpQfB8ZF2KXvOIEsmyHLUXSyBxW+FVflJmJCfskFRkBsFlWJ6StQB7WSlq36dmlm4ZcE5qWhM2EOqkToum6hYCU1Mk9VsDFxlVLzICt5GButXI+UtfkjRxGb9txeYzQRp6LV6xWwDYDp2ZpSzqtocxdcFCHfr5BxWQpSlPQ5txOYZxmmR5XXIH9NnKapmnacD+mg654Oee26MOAAvJj0AI04MDqPc6R0Vs9a2PC01pNjDiUDgMBYII5znWUCHVLUAA0LStAoyiMOw7y4NAFgkyFn6UBT+YADrCFg0BZLgJCMFQBN2pT1BOEwemfJw5Q03T0AMzFzOOlUNRkJzihtAo-KQNIMClPhkBGRY7D5uykK61jOPbdQUzi2QavQNTxszabtQEAAfJb3O0M7rv23N0BO5ALtuxmkBe3ASBoDzUjmEHmO6NHkeKqRUZrZJ4AKJIeTQAAFBYJhKyIxhC4nyep5d7uQAAlHrguG0zPsV7Kcg0M3AhCMAud5A4xcR4gSVxQosB6dAfPgKAZnS9wRI6EngjCJnCAoKgPOsowU-14YWDjJ4JhPCIgR0+APcF0XbSR7RZf0RRjGttXC+U8HDf3-KtP+e1wBfx9n1OCoFqRyCyI3LIiBdTtRCDQQ+wBdCgLIoBGC-V4Kyi-rVeCt57wUgAdARmGgQioO0MgQIKdRioECDpZ+4Aui4PLhAyukcJKj2gDfUYhwsAmF0AI1sX9oYAzhkDXSSMXJuTMoIsWnB1CfGtJ4Rg0sywzCbNWaArcJL93pozZYw9eGj3HmASewhz48EvkfD+rQv5LxSvcFea8N5bw8tAXelB96QCwd0bGWdT4OKcSIOuwj-G2xEGkT8VA37U3nltEW0YrFUN0o44Buh0k-3AbtPhEcClgNVAQuBX0EFIMyJAeh6DMHALKXg2BnUpGlNIUSchn5KEIOoTBOhupGHMNYew6kXQWk8OKcQiOn1BHCMUPE3Q8yOlQ3kjDQGCMFHOTBu5TyCg1EaJIFokwuiGz6KrAsIxstPqmINuYqZv8ZkAJsSIJp9ilBAOcWk9xpUvEiB8dYPxASgkhOPtnM+0BRE4HfvPUu5TMkvP6ffbBugEV4JHh0jFbVKntJqSnOpDTBkfOwTi3qeKBrUhLl08qH5Kr9JoeoIZDCmFrDGbcDhXRyVFMorMvRFYrkHKEWI0YEl6F3N0LTAe5ieU7T5a8iepKFAwrRfCv5+JPFwG8fzXxiwd7wTBXk3QYST45yUPBOaSJUmfwjtw55Cr+kBKtflEBljkVQLtTAylRCAG1JQWgklIT7XQXVB1KlnrnweLKj0hlVcBlhpZSM9lbDOUTLdRkrF1K7UyNsnI7ZIMlGo0EZoSgLo7wwDrhkRgE8ibNDvKUEgepmCeH4HeGaLoODMG0HbTgfcTU4zNVC5kLq+A2tcaU91jr43OtdLwBwTzeX-2zYuipYbCHwKrv6+pgbE2kvyd69dVTZk1WjXVelDV41MqTWylhqbOBcoXVO5dnr1nWVkfZAtii9kqKoaW8tul67VtrecetQozDNtbe22QnbvA9okH3IAA)