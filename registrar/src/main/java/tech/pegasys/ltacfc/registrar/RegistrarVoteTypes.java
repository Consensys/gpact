package tech.pegasys.ltacfc.registrar;

import java.math.BigInteger;

public enum RegistrarVoteTypes {
  VOTE_NONE(0),                            // 0: MUST be the first value so it is the zero / deleted value.
  VOTE_ADD_ADMIN(1),                       // 1
  VOTE_REMOVE_ADMIN(2),                    // 2
  VOTE_CHANGE_VOTING(3),                   // 3
  VOTE_ADD_BLOCKCHAIN(4),                  // 4
  VOTE_SET_SIGNING_THRESHOLD(5),            // 5
  VOTE_ADD_SIGNER(6),
  VOTE_REMOVE_SIGNER(7);

  private int val;

  private RegistrarVoteTypes(int value) {
    this.val = value;
  }

  public BigInteger asBigInt() {
    return BigInteger.valueOf(this.val);
  }
}
