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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.apache.logging.log4j.Logger;
import org.apache.tuweni.bytes.Bytes;
import org.apache.tuweni.bytes.Bytes32;
import org.apache.tuweni.bytes.MutableBytes;
import org.hyperledger.besu.ethereum.rlp.BytesValueRLPOutput;
import org.hyperledger.besu.ethereum.rlp.RLP;

import static org.apache.logging.log4j.LogManager.getLogger;
import static org.hyperledger.besu.crypto.Hash.keccak256;

class BinaryBranchNode<V> implements Node<V> {
  public static final byte RADIX = 2;
  private static final Logger LOG = getLogger();

  @SuppressWarnings("rawtypes")
  private static final Node NULL_NODE = NullNode.instance();

  private final ArrayList<Node<V>> children;
  private final Optional<V> value;
  private final NodeFactory<V> nodeFactory;
  private final Function<V, Bytes> valueSerializer;
  private WeakReference<Bytes> rlp;
  private SoftReference<Bytes32> hash;
  private boolean dirty = false;

  BinaryBranchNode(
    final ArrayList<Node<V>> children,
    final Optional<V> value,
    final NodeFactory<V> nodeFactory,
    final Function<V, Bytes> valueSerializer) {
    assert (children.size() == RADIX);
    this.children = children;
    this.value = value;
    this.nodeFactory = nodeFactory;
    this.valueSerializer = valueSerializer;
  }

  @Override
  public Node<V> accept(final PathNodeVisitor<V> visitor, final Bytes path) {
    return visitor.visit(this, path);
  }

  public Node<V> constructMultiproof(final List<Bytes> keys, final NodeFactory<V> nodeFactory) {
    ArrayList<Node<V>> proofChildren = new ArrayList<>();

    for(byte i = 0; i < RADIX; i ++) {
      if (children.get(i) instanceof NullNode) {
        proofChildren.add(i, NullNode.instance());
        continue;
      }

      boolean match = false;
      List<Bytes> newkeys = new ArrayList<>();
      for(Bytes key : keys) {
        // Matching the first nibble of the key with the branch label
        if(key.get(0) == i) {
          newkeys.add(key.slice(1, key.size()-1));
          match = true;
        }
      }

      if(match) {
        Node<V> proofChild = children.get(i).constructMultiproof(newkeys, nodeFactory);
        proofChildren.add(i, proofChild);
      } else {
        proofChildren.add(i, nodeFactory.createProofHash(children.get(i).getRlp()));
      }
    }

    return nodeFactory.createBranch(proofChildren, Optional.empty());
  }

  @Override
  public Bytes constructSimpleProof(final Bytes key, final List<Bytes> proof) {
    proof.add(getRlp());
    return children.get(key.get(0)).constructSimpleProof(key.slice(1, key.size()-1), proof);
  }

  @Override
  public void accept(final NodeVisitor<V> visitor) {
    visitor.visit(this);
  }

  @Override
  public Bytes getPath() {
    return Bytes.EMPTY;
  }

  @Override
  public Optional<V> getValue() {
    return value;
  }

  @Override
  public List<Node<V>> getChildren() {
    return Collections.unmodifiableList(children);
  }

  public Node<V> child(final byte index) {
    return children.get(index);
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
    for (int i = 0; i < RADIX; ++i) {
      out.writeRLPUnsafe(children.get(i).getRlpRef());
    }
    if (value.isPresent()) {
      out.writeBytes(valueSerializer.apply(value.get()));
    } else {
      out.writeNull();
    }
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
  public Node<V> replacePath(final Bytes newPath) {
    return nodeFactory.createExtension(newPath, this);
  }

  public Node<V> replaceChild(final byte index, final Node<V> updatedChild) {
    final ArrayList<Node<V>> newChildren = new ArrayList<>(children);
    newChildren.set(index, updatedChild);

    if (updatedChild == NULL_NODE) {
      if (value.isPresent() && !hasChildren()) {
        return nodeFactory.createLeaf(Bytes.of(index), value.get());
      } else if (!value.isPresent()) {
        final Optional<Node<V>> flattened = maybeFlatten(newChildren);
        if (flattened.isPresent()) {
          return flattened.get();
        }
      }
    }

    return nodeFactory.createBranch(newChildren, value);
  }

  public Node<V> replaceValue(final V value) {
    return nodeFactory.createBranch(children, Optional.of(value));
  }

  public Node<V> removeValue() {
    return maybeFlatten(children).orElse(nodeFactory.createBranch(children, Optional.empty()));
  }

  private boolean hasChildren() {
    for (final Node<V> child : children) {
      if (child != NULL_NODE) {
        return true;
      }
    }
    return false;
  }

  private static <V> Optional<Node<V>> maybeFlatten(final ArrayList<Node<V>> children) {
    final int onlyChildIndex = findOnlyChild(children);
    if (onlyChildIndex >= 0) {
      // replace the path of the only child and return it
      final Node<V> onlyChild = children.get(onlyChildIndex);
      final Bytes onlyChildPath = onlyChild.getPath();
      final MutableBytes completePath = MutableBytes.create(1 + onlyChildPath.size());
      completePath.set(0, (byte) onlyChildIndex);
      onlyChildPath.copyTo(completePath, 1);
      return Optional.of(onlyChild.replacePath(completePath));
    }
    return Optional.empty();
  }

  private static <V> int findOnlyChild(final ArrayList<Node<V>> children) {
    int onlyChildIndex = -1;
    assert (children.size() == RADIX);
    for (int i = 0; i < RADIX; ++i) {
      if (children.get(i) != NULL_NODE) {
        if (onlyChildIndex >= 0) {
          return -1;
        }
        onlyChildIndex = i;
      }
    }
    return onlyChildIndex;
  }

  @Override
  public String print() {
    final StringBuilder builder = new StringBuilder();
    builder.append("Branch:");
    builder.append("\n\tRef: ").append(getRlpRef());
    for (int i = 0; i < RADIX; i++) {
      final Node<V> child = child((byte) i);
      if (!Objects.equals(child, NullNode.instance())) {
        final String branchLabel = "[" + Integer.toHexString(i) + "] ";
        final String childRep = child.print().replaceAll("\n\t", "\n\t\t");
        builder.append("\n\t").append(branchLabel).append(childRep);
      }
    }
    builder.append("\n\tValue: ").append(getValue().map(Object::toString).orElse("empty"));
    return builder.toString();
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
