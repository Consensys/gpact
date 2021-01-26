package tech.pegasys.ltacfc.rlp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hyperledger.besu.ethereum.rlp.RLPInput;

public class RlpDumper {
  private static final Logger LOG = LogManager.getLogger(RlpDumper.class);

  public static void dump(RLPInput rlpInput) {
    int depth = 0;

    while (!rlpInput.isDone()) {
      if (rlpInput.nextIsList()) {
        rlpInput.enterList();
        LOG.info("{}Enter List", getBlanks(depth));
        depth++;
      }
      else if (rlpInput.nextIsNull()) {
        LOG.info("{}Null: {}", getBlanks(depth), rlpInput.readBytes());
      }
      else if (rlpInput.isEndOfCurrentList()) {
        rlpInput.leaveList();
        depth--;
        LOG.info("{}End List", getBlanks(depth));
      }
      else {
        LOG.info("{}Bytes: {}", getBlanks(depth), rlpInput.readBytes());
      }
    }
  }

  private static String getBlanks(int depth) {
    switch (depth) {
      case 0:
        return " ";
      case 1:
        return "  ";
      case 2:
        return "   ";
      case 3:
        return "    ";
      case 4:
        return "     ";
      case 5:
        return "      ";
      case 6:
        return "       ";
      case 7:
        return "        ";
      case 8:
        return "         ";
      case 9:
        return "          ";
      case 10:
        return "           ";
      default:
        throw new Error("Not implemented yet");
    }
  }

}
