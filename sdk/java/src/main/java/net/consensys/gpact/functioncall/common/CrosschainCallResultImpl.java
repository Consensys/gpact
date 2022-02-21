package net.consensys.gpact.functioncall.common;

import static net.consensys.gpact.functioncall.common.CallPath.callPathToMapKey;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import net.consensys.gpact.functioncall.CallExecutionTree;
import net.consensys.gpact.functioncall.CrosschainCallResult;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

public class CrosschainCallResultImpl implements CrosschainCallResult {
  private final CallExecutionTree callTree;
  private final boolean success;
  private final Map<BigInteger, TransactionReceipt> transactionReceipts;
  private String additionalErrorInfo;

  public CrosschainCallResultImpl(
      final CallExecutionTree callExecutionTree,
      final boolean success,
      final Map<BigInteger, TransactionReceipt> transactionReceipts) {
    this.callTree = callExecutionTree;
    this.success = success;
    this.transactionReceipts = transactionReceipts;
  }

  public CrosschainCallResultImpl(
      final CallExecutionTree callExecutionTree,
      final boolean success,
      final Map<BigInteger, TransactionReceipt> transactionReceipts,
      String additionalErrorInfo) {
    this(callExecutionTree, success, transactionReceipts);
    this.additionalErrorInfo = additionalErrorInfo;
  }

  //  public void setAdditionalErrorInfo(final String info) {
  //  }

  @Override
  public CallExecutionTree getCallTree() {
    return callTree;
  }

  @Override
  public boolean isSuccessful() {
    return success;
  }

  @Override
  public TransactionReceipt getTransactionReceipt(List<BigInteger> callPath) {
    return this.transactionReceipts.get(callPathToMapKey(callPath));
  }

  @Override
  public String getAdditionalErrorInfo() {
    return this.additionalErrorInfo;
  }
}
