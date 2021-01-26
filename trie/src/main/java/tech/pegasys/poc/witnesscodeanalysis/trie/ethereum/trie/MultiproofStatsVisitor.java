package tech.pegasys.poc.witnesscodeanalysis.trie.ethereum.trie;

import org.apache.logging.log4j.Logger;

import static org.apache.logging.log4j.LogManager.getLogger;

public class MultiproofStatsVisitor<V> implements NodeVisitor<V> {
  private int numHashes = 0;
  private int numExtensions = 0;
  private int numBranches = 0;

  public int getNumHashes() {
    return numHashes;
  }

  public int getNumExtensions() {
    return numExtensions;
  }

  public int getNumBranches() {
    return numBranches;
  }

  public void visit(final ExtensionNode<V> extensionNode) {
    numExtensions ++;
    extensionNode.getChild().accept(this);
  }

  public void visit(final BranchNode<V> branchNode) {
    numBranches ++;
    for(Node<V> child: branchNode.getChildren()) {
      child.accept(this);
    }
  }

  public void visit(final BinaryBranchNode<V> branchNode) {
    numBranches ++;
    for(Node<V> child: branchNode.getChildren()) {
      child.accept(this);
    }
  }

  public void visit(final LeafNode<V> leafNode) {

  }

  public void visit(final NullNode<V> nullNode) {

  }

  public void visit(final MerkleProofHashNode<V> proofNode) {
    numHashes ++;
  }
}
