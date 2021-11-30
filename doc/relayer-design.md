# Relayer High-level Architecture

Version: 0.1 (DRAFT)\
Date: 18/11/2021

## Overview
The Relayer is an off-chain system that is responsible for receiving a cross-chain communication message from a source network and delivering it to one or more destination networks. A Relayer might also perform additional duties such as attesting to the validity of a message being communicated. A Relayer will generally interface with two or more networks and support a wide range of message communication needs. A Relayer might operate independently or in coordination with other distinct Relayers that are owned and managed by other parties.

## Key Features
The Relayer offers the following set of core capabilities:
1. Listen to cross-chain communication messages originating from a source network
1. Attest to the validity of the given message, based on the finalised state on the source ledger
1. Coordinate with other Relayers as required to attest to messages
1. Deliver cross-chain communication messages to one or more destination networks

## Design Considerations
 The capabilities of the system can be seen in two parts, a) protocol-specific capabilities, such as reading and writing to specific network protocols b) protocol-agnostic capabilities, such as processing and signing messages and communicating with other relayers as required. The design of a Relayer has to afford a significant amount of extensibility for the former while enabling flexibility and variability in the operations of the latter. This is because a Relayer needs to integrate with a growing number of heterogenous networks while also addressing a wide array of use-cases with different message processing and attestation requirements.

From a process perspective, the operations of a Relayer occur in three distinct steps, a) receiving messages, b) processing messages and c) delivering messages. Each of these phases has different non-functional requirements and constraints (latency, scalability, security etc). The design of the Relay should offer flexibility to address the requirements of each phase independently.

## Architecture
The core aspects of the proposed architecture can be summarised as follows:
- Separate protocol-specific functionality from protocol-agnostic ones.
- Decouple the three phases of relaying cross-chain messages, i.e. a) receiving b) processing and c) delivering
- Enable variability and extensibility at the edges (a and c), and consistency with flexibility at the core (b).
- Decouple network Read from Write operations, as these might have different non-functional requirements (performance, scalability, security etc.)

The proposed architecture follows a microservice pattern involving three distinct service types: 1) *Message Observers*, 2) *Message Dispatchers* and 3) a *Relayer Core*. *Message Observers* and *Message Dispatchers* serve as adapters that enable a Relayer to read and write state to a specific network. A Relayer might have several such services, each providing an integration point to a different a network. The Relayer Core on the other hand, is a single service responsible for performing message processing and routing operations. A *Relayer Core* interacts with multiple protocol adapters representing different Networks, and message configurations, using a message queue.

![High Level Architecture](images/hla-overview.png)
<p align="center">Figure 1: Relayer High-level Architecture, consists of Message Observer service (left), Relayer Core service (middle) and Message Dispatcher service (right)</p>

### Platform Adapters
The platform adapters are Blockchain protocol specific components that interact with a given network. They act as clients on a network, able to read or write to the ledger according to the protocol specifications of the underlying network. They leverage existing tools and SDKs for a protocol and can be built using different languages or frameworks.

![High Level Architecture](images/hla-details.png)
<p align="center">Figure 2: Platform adapters offer extension points for different chains</p>

#### Message Observer
The Message Observer subscribes to state-change events on a designated Blockchain, annotates these events with additional metadata, wraps them onto a common message data model and places them onto a queue for processing by the Relayer core. An observer could be configured to listen to all state-change events or employ filtering based on the requirements of a use-case. The default implementations of Message Observer enable listening to all events (*Block Event Observer*) or filter based on simple criteria (*Filtered Event Observer*), however,  the Message Observer, interface can be extended to listen to other types of state-change events (see Figure 3 below for examples).

<p align="center">
<img src="images/protocol-adapter-observer.png" width="700"/>
</p>
<p align="center">Figure 3: Message Observer can be extended to observe different types of state-change events </p>

#### Message Dispatcher
The Message Dispatcher is responsible for submitting a cross-chain communication message to its intended destination chain. It receives messages from the Relayer core through a message queue and submits the message as a transaction on the destination blockchain. The Message Dispatcher maintains cryptographic credentials with which to transact, with the underlying network. The account used by the dispatcher incurs any costs associated with submitting the transaction on the destination chain.

### Relayer Core
The Relayer Core, is responsible for performing blockchain-agnostic operations on a message, such processing and routing. The service is responsible for receiving blockchain messages (in a protocol-agnostic format), identifying how the specific message should be processed, attesting to the message if required, and finally handing the message to the appropriate Message Dispatcher.

The Relayer Core maintains two message queues,  an *Ingress queue* and and *Egress queue*. The former is used by message observers to submit, messages to the Relayer Core, while the latter is used by the Relayer Core, to send message to dispatchers for submission. These queues can be configured with different topics for different observers and dispatchers to better manage security and SLAs.

<p align="center">
<img src="images/relayer-core.png" width="550"/>
</p>
<p align="center">Figure 4: Relayer Core </p>

#### Message Router
A Message Router maps messages to workflows. When a message is received by the Relayer Core, the router is responsible for determining what specific message handling workflow needs to be triggered, based on context information provided in the message (e.g. destination information). Conceptually, a Router is a map of keys to values, where keys can be as simple as a message destination identifier, and values are pointers to a workflow (see *Message Processor* for details). A router can be extended to offer more complex key types, enabling more sophisticated routing logic.

<p align="center">
<img src="images/message-router-example.png" width="720"/>
</p>
<p align="center">Figure 5: Message Router maps messages to any number of workflows, for processing and dispatching. </p>

#### Message Processor
A Message Processor is a component that performs a discrete operation on a message. Message processors would typically be chained together into a workflow, in a chain-of-responsibility pattern, to orchestrate more complex operations on a Message. Common Message Processor implementations include validating a message, persisting messages, aggregating messages, orchestrating the signing of messages and placing messages to a dispatch queue for delivery to a destination chain. A library of such message processor components can be developed over time and weaved together in different ways to cater for a wide array of use-cases.

#### Message Signer
The Message Signer is responsible for signing and attesting to the validity of messages on behalf of the Relay operator. The component can offer a range of cryptographic signature algorithms, depending on the requirements of the use-case. It might interact with external key-management components or services to perform this operation.

<p align="center">
<img src="images/message-signer.png" width="520"/>
</p>
<p align="center">Figure 6: Message Signer </p>

### Message Format
Messages communicated across the different components are encoded as JSON, and adhere to the data scheme specified [here](./relay-schema.json) and exemplified below:

**Message**
```jsonc
{
   "id":"", // Unique identifier for a message generated in a source blockchain
   "meta":{
      "timestamp": "2021-11-19T20:00:10Z",  // Timestamp for when message was generated
      "version": "0.0.1" // version number of message schema
    },
    "destination": [{ // Details of one or more destination contracts to relay message to
            "networkId":"",
            "bridgeAddress":"", // The bridge contract to deliver message to.
            "contractAddress":"" // The ultimate contract that will consume this message
    }],
    "source":{ // Details of the originator of this message
         "networkId":"",
         "bridgeAddress":"",   // The bridge contract that initiated the cross-chain message.
         "contractAddress":"" // The address of the contract that initiated the cross-chain message
    },
   "proofs":[{
         "type":"EcdsaSecp256k1Signature2019", // Type of cryptographic method used to prove authenticity or validity of message
         "created":"2021-11-19T20:00:10Z",  // Timestamp of when the proof was created
         "proof":"Base64EncodedString" // Base64 encoding of proof
    }],
   "payload":"Base64EncodedString" // Base64 encoded of message from source blockchain
}
```

#### TODOs
- [ ] Discuss topics in queues assigned to each observer and dispatcher. Access Control, SLAs etc.
- [ ] Interface definitions for Components
- [ ] Example flow
- [ ] Example sequence diagrams
- [ ] Deployment considerations
- [ ] Inter-relay communication
