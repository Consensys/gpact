package net.consensys.gpact.helpers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public abstract class AbstractExampleTest {

  protected String createPropertiesFile(
      boolean useDirectSigning, boolean serialExecution, boolean oneBlockchain) throws IOException {
    File file = File.createTempFile("temp", null);
    //    file.deleteOnExit();

    Properties props = new Properties();

    props.setProperty("RELAYER_URI", "http://127.0.0.1:9625");

    if (useDirectSigning) {
      props.setProperty("CONSENSUS_METHODOLOGY", "EVENT_SIGNING");
    } else {
      props.setProperty("CONSENSUS_METHODOLOGY", "TRANSACTION_RECEIPT_SIGNING");
    }

    if (serialExecution) {
      props.setProperty("EXECUTION_ENGINE", "SERIAL");
    } else {
      props.setProperty("EXECUTION_ENGINE", "PARALLEL");
    }

    if (oneBlockchain) {
      props.setProperty("ROOT_BC_ID", "1F");
      props.setProperty("ROOT_BC_RPC_URI", "http://127.0.0.1:8310/");
      props.setProperty("ROOT_BC_WS_URI", "ws://bc31node1:8546");
      props.setProperty("ROOT_GAS", "FREE");
      props.setProperty("ROOT_PERIOD", "1000");
      props.setProperty("ROOT_OBSERVER_URI", "http://127.0.0.1:9525");
      props.setProperty("ROOT_DISPATCHER_URI", "http://127.0.0.1:9725");
      props.setProperty("ROOT_MSG_STORE_FROM_DISPATCHER_URI", "msgstore:8080");
      props.setProperty("ROOT_MSG_STORE_FROM_USER_URI", "127.0.0.1:8080");

      props.setProperty("BC2_BC_ID", "1F");
      props.setProperty("BC2_BC_RPC_URI", "http://127.0.0.1:8310/");
      props.setProperty("BC2_BC_WS_URI", "ws://bc31node1:8546");
      props.setProperty("BC2_GAS", "FREE");
      props.setProperty("BC2_PERIOD", "1000");
      props.setProperty("BC2_OBSERVER_URI", "http://127.0.0.1:9525");
      props.setProperty("BC2_DISPATCHER_URI", "http://127.0.0.1:9725");
      props.setProperty("BC2_MSG_STORE_URI", "msgstore:8080");
      props.setProperty("BC2_MSG_STORE_FROM_DISPATCHER_URI", "msgstore:8080");
      props.setProperty("BC2_MSG_STORE_FROM_USER_URI", "127.0.0.1:8080");

      props.setProperty("BC3_BC_ID", "1F");
      props.setProperty("BC3_BC_RPC_URI", "http://127.0.0.1:8310/");
      props.setProperty("BC3_BC_WS_URI", "ws://bc31node1:8546");
      props.setProperty("BC3_GAS", "FREE");
      props.setProperty("BC3_PERIOD", "1000");
      props.setProperty("BC3_OBSERVER_URI", "http://127.0.0.1:9525");
      props.setProperty("BC3_DISPATCHER_URI", "http://127.0.0.1:9725");
      props.setProperty("BC3_MSG_STORE_FROM_DISPATCHER_URI", "msgstore:8080");
      props.setProperty("BC3_MSG_STORE_FROM_USER_URI", "127.0.0.1:8080");

      props.setProperty("BC4_BC_ID", "1F");
      props.setProperty("BC4_BC_RPC_URI", "http://127.0.0.1:8310/");
      props.setProperty("BC4_BC_WS_URI", "ws://bc31node1:8546");
      props.setProperty("BC4_GAS", "FREE");
      props.setProperty("BC4_PERIOD", "1000");
      props.setProperty("BC4_OBSERVER_URI", "http://127.0.0.1:9525");
      props.setProperty("BC4_DISPATCHER_URI", "http://127.0.0.1:9725");
      props.setProperty("BC4_MSG_STORE_FROM_DISPATCHER_URI", "msgstore:8080");
      props.setProperty("BC4_MSG_STORE_FROM_USER_URI", "127.0.0.1:8080");

      props.setProperty("BC5_BC_ID", "1F");
      props.setProperty("BC5_BC_RPC_URI", "http://127.0.0.1:8310/");
      props.setProperty("BC5_BC_WS_URI", "ws://bc31node1:8546");
      props.setProperty("BC5_GAS", "FREE");
      props.setProperty("BC5_PERIOD", "1000");
      props.setProperty("BC5_OBSERVER_URI", "http://127.0.0.1:9525");
      props.setProperty("BC5_DISPATCHER_URI", "http://127.0.0.1:9725");
      props.setProperty("BC5_MSG_STORE_FROM_DISPATCHER_URI", "msgstore:8080");
      props.setProperty("BC5_MSG_STORE_FROM_USER_URI", "127.0.0.1:8080");
    } else {
      props.setProperty("ROOT_BC_ID", "1F");
      props.setProperty("ROOT_BC_RPC_URI", "http://127.0.0.1:8310/");
      props.setProperty("ROOT_BC_WS_URI", "ws://bc31node1:8546");
      props.setProperty("ROOT_GAS", "FREE");
      props.setProperty("ROOT_PERIOD", "1000");
      props.setProperty("ROOT_OBSERVER_URI", "http://127.0.0.1:9525");
      props.setProperty("ROOT_DISPATCHER_URI", "http://127.0.0.1:9725");
      props.setProperty("ROOT_MSG_STORE_FROM_DISPATCHER_URI", "msgstore:8080");
      props.setProperty("ROOT_MSG_STORE_FROM_USER_URI", "127.0.0.1:8080");

      props.setProperty("BC2_BC_ID", "20");
      props.setProperty("BC2_BC_RPC_URI", "http://127.0.0.1:8320/");
      props.setProperty("BC2_BC_WS_URI", "ws://bc32node1:8546");
      props.setProperty("BC2_GAS", "FREE");
      props.setProperty("BC2_PERIOD", "1000");
      props.setProperty("BC2_OBSERVER_URI", "http://127.0.0.1:9526");
      props.setProperty("BC2_DISPATCHER_URI", "http://127.0.0.1:9725");
      props.setProperty("BC2_MSG_STORE_FROM_DISPATCHER_URI", "msgstore:8080");
      props.setProperty("BC2_MSG_STORE_FROM_USER_URI", "127.0.0.1:8080");

      props.setProperty("BC3_BC_ID", "21");
      props.setProperty("BC3_BC_RPC_URI", "http://127.0.0.1:8330/");
      props.setProperty("BC3_BC_WS_URI", "ws://bc33node1:8546");
      props.setProperty("BC3_GAS", "FREE");
      props.setProperty("BC3_PERIOD", "1000");
      props.setProperty("BC3_OBSERVER_URI", "http://127.0.0.1:9527");
      props.setProperty("BC3_DISPATCHER_URI", "http://127.0.0.1:9725");
      props.setProperty("BC3_MSG_STORE_FROM_DISPATCHER_URI", "msgstore:8080");
      props.setProperty("BC3_MSG_STORE_FROM_USER_URI", "127.0.0.1:8080");

      props.setProperty("BC4_BC_ID", "22");
      props.setProperty("BC4_BC_RPC_URI", "http://127.0.0.1:8340/");
      props.setProperty("BC4_BC_WS_URI", "ws://bc34node1:8546");
      props.setProperty("BC4_GAS", "FREE");
      props.setProperty("BC4_PERIOD", "1000");
      props.setProperty("BC4_OBSERVER_URI", "http://127.0.0.1:9528");
      props.setProperty("BC4_DISPATCHER_URI", "http://127.0.0.1:9725");
      props.setProperty("BC4_MSG_STORE_FROM_DISPATCHER_URI", "msgstore:8080");
      props.setProperty("BC4_MSG_STORE_FROM_USER_URI", "127.0.0.1:8080");

      props.setProperty("BC5_BC_ID", "23");
      props.setProperty("BC5_BC_RPC_URI", "http://127.0.0.1:8350/");
      props.setProperty("BC5_BC_WS_URI", "ws://bc35node1:8546");
      props.setProperty("BC5_GAS", "FREE");
      props.setProperty("BC5_PERIOD", "1000");
      props.setProperty("BC5_OBSERVER_URI", "http://127.0.0.1:9529");
      props.setProperty("BC5_DISPATCHER_URI", "http://127.0.0.1:9725");
      props.setProperty("BC5_MSG_STORE_FROM_DISPATCHER_URI", "msgstore:8080");
      props.setProperty("BC5_MSG_STORE_FROM_USER_URI", "127.0.0.1:8080");
    }

    FileOutputStream fos = new FileOutputStream(file);
    props.store(fos, "");
    fos.flush();
    fos.close();

    return file.getAbsolutePath();
  }
}
