/*
 * Copyright ConsenSys AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package tech.pegasys.poc.witnesscodeanalysis.trie.ethereum.trie;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.apache.logging.log4j.Logger;
import org.apache.tuweni.bytes.Bytes;
import org.apache.tuweni.bytes.Bytes32;
import org.hyperledger.besu.ethereum.rlp.BytesValueRLPOutput;
import org.hyperledger.besu.ethereum.rlp.RLP;

import static org.apache.logging.log4j.LogManager.getLogger;
import static org.hyperledger.besu.crypto.Hash.keccak256;

class LeafNode<V> implements Node<V> {
  private static final Logger LOG = getLogger();
  private final Bytes path;
  private final V value;
  private final NodeFactory<V> nodeFactory;
  private final Function<V, Bytes> valueSerializer;
  private WeakReference<Bytes> rlp;
  private SoftReference<Bytes32> hash;
  private boolean dirty = false;

  LeafNode(
      final Bytes path,
      final V value,
      final NodeFactory<V> nodeFactory,
      final Function<V, Bytes> valueSerializer) {
    this.path = path;
    this.value = value;
    this.nodeFactory = nodeFactory;
    this.valueSerializer = valueSerializer;
  }

  @Override
  public Node<V> accept(final PathNodeVisitor<V> visitor, final Bytes path) {
    return visitor.visit(this, path);
  }

  @Override
  public Node<V> constructMultiproof(final List<Bytes> keys, final NodeFactory<V> nodeFactory) {
    if(keys.size() != 1) {
      LOG.error("Construct LeafNode should be called with only one key. It is called with {} keys:", keys.size());
      for(int i =0; i < keys.size(); i++)
      {
        LOG.info(keys.get(i).toHexString());
      }
      return NullNode.instance();
    }

    if(!(keys.get(0).slice(0, path.size()-1).equals(path.slice(0, path.size()-1)))) {
      LOG.error("Construct LeafNode is called with an unmatched key. Key = {}, Path inside leaf = {}",
        keys.get(0).toHexString(), path.toHexString());
      return NullNode.instance();
    }

    return nodeFactory.createLeaf(this.path, this.value);
  }

  @Override
  public Bytes constructSimpleProof(final Bytes key, final List<Bytes> proof) {
    return getRlp();
  }

  @Override
  public void accept(final NodeVisitor<V> visitor) {
    visitor.visit(this);
  }

  @Override
  public Bytes getPath() {
    return path;
  }

  @Override
  public Optional<V> getValue() {
    return Optional.of(value);
  }

  @Override
  public List<Node<V>> getChildren() {
    return Collections.emptyList();
  }

  @Override
  public Bytes getRlp() {
    if (rlp != null) {
      final Bytes encoded = rlp.get();
      if (encoded != null) {
        return encoded;
      }
    }

    final BytesValueRLPOutput out = new BytesValueRLPOutput();
    out.startList();
    out.writeBytes(CompactEncoding.encode(path));
    out.writeBytes(valueSerializer.apply(value));
    out.endList();
    final Bytes encoded = out.encoded();
    rlp = new WeakReference<>(encoded);
    return encoded;
  }

  @Override
  public Bytes getRlpRef() {
    if (isReferencedByHash()) {
      return RLP.encodeOne(getHash());
    } else {
      return getRlp();
    }
  }

  @Override
  public Bytes32 getHash() {
    if (hash != null) {
      final Bytes32 hashed = hash.get();
      if (hashed != null) {
        return hashed;
      }
    }
    final Bytes32 hashed = keccak256(getRlp());
    hash = new SoftReference<>(hashed);
    return hashed;
  }

  @Override
  public Node<V> replacePath(final Bytes path) {
    return nodeFactory.createLeaf(path, value);
  }

  @Override
  public String print() {
    return "Leaf:"
        + "\n\tRef: "
        + getRlpRef()
        + "\n\tPath: "
        + CompactEncoding.encode(path)
        + "\n\tValue: "
        + getValue().map(Object::toString).orElse("empty");
  }

  @Override
  public boolean isDirty() {
    return dirty;
  }

  @Override
  public void markDirty() {
    dirty = true;
  }
}
