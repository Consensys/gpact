package tech.pegasys.ltacfc.registrar;

import java.math.BigInteger;

public enum SigAlgorithmTypes {
  ALG_NONE(0),                            // 0: MUST be the first value so it is the zero / deleted value.
  ALG_ECDSA_KECCAK256_SECP256K1 (1);      // 1

  private int val;

  private SigAlgorithmTypes(int value) {
    this.val = value;
  }

  public BigInteger asBigInt() {
    return BigInteger.valueOf(this.val);
  }
}
