package tech.pegasys.ltacfc.examples.twochain.sim;

import java.math.BigInteger;

// Note: only need to simulate enough to know what the parameters for function calls should be.
public class SimRootContract {
  BigInteger val1;
  BigInteger val2;

  SimOtherContract simOtherContract;

  public boolean someComplexBusinessLogicIfTrue = false;
  public BigInteger someComplexBusinessLogicSetValParameter = BigInteger.ZERO;
  public BigInteger someComplexBusinessLogicSetValuesParameter1 = BigInteger.ZERO;
  public BigInteger someComplexBusinessLogicSetValuesParameter2 = BigInteger.ZERO;


  public SimRootContract(SimOtherContract otherContract) {
    this.simOtherContract = otherContract;
  }

  public void someComplexBusinessLogic(BigInteger _val) {
    // Use the value on the other blockchain as a threshold
    BigInteger valueFromOtherBlockchain = this.simOtherContract.getVal();
    this.val2 = valueFromOtherBlockchain;

    // if (_val > currentThreshold)
    if (_val.compareTo(valueFromOtherBlockchain) > 0) {
      this.someComplexBusinessLogicIfTrue = true;
      this.someComplexBusinessLogicSetValuesParameter1 = _val;
      this.someComplexBusinessLogicSetValuesParameter2 = valueFromOtherBlockchain;
      this.simOtherContract.setValues(this.someComplexBusinessLogicSetValuesParameter1, this.someComplexBusinessLogicSetValuesParameter2);
      this.val1 = valueFromOtherBlockchain;
    }
    else {
      this.someComplexBusinessLogicIfTrue = false;
      this.someComplexBusinessLogicSetValParameter = _val.add(BigInteger.valueOf(13));
      this.simOtherContract.setVal(this.someComplexBusinessLogicSetValParameter);
      this.val1 = _val;
    }
  }

  public void setVal1(BigInteger _val) {
    this.val1 = _val;
  }

  public void setVal2(BigInteger _val) {
    this.val2 = _val;
  }

  public BigInteger getVal1() {
    return this.val1;
  }
  public BigInteger getVal2() {
    return this.val2;
  }
}
