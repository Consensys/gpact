package net.consensys.gpact.common;

public class CrosschainProtocolStackException extends Exception {

  public CrosschainProtocolStackException(String msg) {
    super(msg);
  }

  public CrosschainProtocolStackException(String msg, Throwable th) {
    super(msg, th);
  }
}
