package net.consensys.gpact.functioncall.gpact;

import net.consensys.gpact.functioncall.CallExecutionTree;
import net.consensys.gpact.functioncall.CrosschainCallResult;

public class GpactCrosschainCallResult implements CrosschainCallResult {
  private CallExecutionTree callTree;
  private boolean success;

  public GpactCrosschainCallResult(
      final CallExecutionTree callExecutionTree, final boolean success) {
    this.callTree = callExecutionTree;
    this.success = success;
  }

  @Override
  public CallExecutionTree getCallTree() {
    return callTree;
  }

  @Override
  public boolean successful() {
    return success;
  }
}
