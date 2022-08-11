package net.consensys.gpact.functioncall.common;

import static net.consensys.gpact.functioncall.CrosschainCallResult.FIRST_CALL;
import static net.consensys.gpact.functioncall.CrosschainCallResult.ROOT_CALL;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class CallPath {
  // The maximum number of calls that can be done from any one function. The value
  // has been set to an aritrarily largish number. If people write complicated
  // functions that have a 1000 calls, or write functions that have loops and
  // do many cross-blockchain function calls, then this number might need to be made larger.
  private static final BigInteger MAX_CALLS_FROM_ONE_FUNCTION = BigInteger.valueOf(1000);

  /**
   * Determine a key that can be used for a map that uniquely identifies the call path. A message
   * digest of the call path could be used, but a simpler multiplication method will work just as
   * well.
   *
   * @param callPath The call path to determine a map key for.
   * @return The map key representing the call path.
   */
  public static BigInteger callPathToMapKey(List<BigInteger> callPath) {
    if (callPath.size() == 0) {
      throw new RuntimeException("Invalid call path length: " + 0);
    } else {
      BigInteger key = BigInteger.ONE;
      for (BigInteger call : callPath) {
        if (call.compareTo(MAX_CALLS_FROM_ONE_FUNCTION) >= 0) {
          throw new RuntimeException(
              "Maximum calls from one function is: " + MAX_CALLS_FROM_ONE_FUNCTION);
        }

        key = key.multiply(MAX_CALLS_FROM_ONE_FUNCTION);
        key = key.add(call.add(BigInteger.ONE));
      }
      return key;
    }
  }

  public static BigInteger calculateRootCallMapKey() {
    return callPathToMapKey(ROOT_CALL);
  }

  public static BigInteger calculateFirstCallMapKey() {
    return callPathToMapKey(FIRST_CALL);
  }

  // Don't call from root function
  public static List<BigInteger> parentCallPath(List<BigInteger> callPath) {
    List<BigInteger> callPathOfParent = new ArrayList<>(callPath);
    int lastIndex = callPath.size() - 1;
    if (callPath.get(lastIndex).compareTo(BigInteger.ZERO) != 0) {
      callPathOfParent.set(lastIndex, BigInteger.ZERO);
    } else {
      callPathOfParent.remove(lastIndex);
      callPathOfParent.set(lastIndex - 1, BigInteger.ZERO);
    }
    return callPathOfParent;
  }
}
