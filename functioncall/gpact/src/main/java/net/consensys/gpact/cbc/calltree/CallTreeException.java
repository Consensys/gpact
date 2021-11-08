package net.consensys.gpact.cbc.calltree;

public class CallTreeException extends Exception {
  public CallTreeException(String msg) {
    super(msg);
  }

  public CallTreeException(String msg, StringBuilder out) {
    super(msg + ((out == null) ? "" : "\nCall Execution Tree prior to parsing error:\n" + out));
  }
}
