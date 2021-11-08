package net.consensys.gpact.txroot;

import java.math.BigInteger;
import java.util.ArrayList;

public class Signatures {
  ArrayList<String> theSigners;
  ArrayList<byte[]> sigR;
  ArrayList<byte[]> sigS;
  ArrayList<BigInteger> sigV;

  public Signatures(
      ArrayList<String> theSigners,
      ArrayList<byte[]> sigR,
      ArrayList<byte[]> sigS,
      ArrayList<BigInteger> sigV) {
    this.theSigners = theSigners;
    this.sigR = sigR;
    this.sigS = sigS;
    this.sigV = sigV;
  }
}
