package tech.pegasys.ltacfc.examples.twochain.sim;

import java.math.BigInteger;

public class SimOtherContract {
  private BigInteger val;

  public void setVal(BigInteger _val) {
    this.val = _val;
  }

  public void setValues(BigInteger _val1, BigInteger _val2) {
    this.val = _val1.add(_val2);
  }

  public void incrementVal() {
    this.val = this.val.add(BigInteger.ONE);
  }

  public BigInteger getVal() {
    return this.val;
  }
}
