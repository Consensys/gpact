package net.consensys.gpact.helpers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public abstract class AbstractExampleTest {
  public static final String ROOT_BC_ID = "1F";
  public static final String ROOT_BC_RPC_URI = "http://127.0.0.1:8310/";
  public static final String ROOT_BC_WS_URI = "ws://bc31node1:8546";
  public static final String GAS_STRATEGY_FREE = "FREE";
  public static final String POLL_PERIOD_MS = "1000";
  public static final String OBSERVER_URI_BC1 = "http://127.0.0.1:9525";
  public static final String DISPATCHER_URI = "http://127.0.0.1:9725";
  public static final String MSG_STORE_FROM_DISPATCHER = "msgstore:8080";
  public static final String MSG_STORE_FROM_USER = "127.0.0.1:8080";

  public enum MessagingType {
    EVENT_SIGNING,
    TRANSACTION_RECEIPT_SIGNING,
    EVENT_RELAY
  }

  protected String createPropertiesFile(
          boolean useDirectSigning, boolean serialExecution, boolean oneBlockchain) throws IOException {
    MessagingType msgType = useDirectSigning ? MessagingType.EVENT_SIGNING : MessagingType.TRANSACTION_RECEIPT_SIGNING;
    return createPropertiesFile(msgType, serialExecution, oneBlockchain);
  }


  protected String createPropertiesFile(
            MessagingType msgType, boolean serialExecution, boolean oneBlockchain) throws IOException {
    File file = File.createTempFile("temp", null);
    //    file.deleteOnExit();

    Properties props = new Properties();

    props.setProperty("RELAYER_URI", "http://127.0.0.1:9625");
    props.setProperty("CONSENSUS_METHODOLOGY", msgType.name());

    if (serialExecution) {
      props.setProperty("EXECUTION_ENGINE", "SERIAL");
    } else {
      props.setProperty("EXECUTION_ENGINE", "PARALLEL");
    }

    if (oneBlockchain) {
      setProperties(
          props,
          "ROOT",
          ROOT_BC_ID,
          ROOT_BC_RPC_URI,
          ROOT_BC_WS_URI,
          GAS_STRATEGY_FREE,
          POLL_PERIOD_MS,
          OBSERVER_URI_BC1,
          DISPATCHER_URI,
          MSG_STORE_FROM_DISPATCHER,
          MSG_STORE_FROM_USER);

      setProperties(
          props,
          "BC2",
          ROOT_BC_ID,
          ROOT_BC_RPC_URI,
          ROOT_BC_WS_URI,
          GAS_STRATEGY_FREE,
          POLL_PERIOD_MS,
          OBSERVER_URI_BC1,
          DISPATCHER_URI,
          MSG_STORE_FROM_DISPATCHER,
          MSG_STORE_FROM_USER);

      setProperties(
          props,
          "BC3",
          ROOT_BC_ID,
          ROOT_BC_RPC_URI,
          ROOT_BC_WS_URI,
          GAS_STRATEGY_FREE,
          POLL_PERIOD_MS,
          OBSERVER_URI_BC1,
          DISPATCHER_URI,
          MSG_STORE_FROM_DISPATCHER,
          MSG_STORE_FROM_USER);

      setProperties(
          props,
          "BC4",
          ROOT_BC_ID,
          ROOT_BC_RPC_URI,
          ROOT_BC_WS_URI,
          GAS_STRATEGY_FREE,
          POLL_PERIOD_MS,
          OBSERVER_URI_BC1,
          DISPATCHER_URI,
          MSG_STORE_FROM_DISPATCHER,
          MSG_STORE_FROM_USER);

      setProperties(
          props,
          "BC5",
          ROOT_BC_ID,
          ROOT_BC_RPC_URI,
          ROOT_BC_WS_URI,
          GAS_STRATEGY_FREE,
          POLL_PERIOD_MS,
          OBSERVER_URI_BC1,
          DISPATCHER_URI,
          MSG_STORE_FROM_DISPATCHER,
          MSG_STORE_FROM_USER);
    } else {
      setProperties(
          props,
          "ROOT",
          ROOT_BC_ID,
          ROOT_BC_RPC_URI,
          ROOT_BC_WS_URI,
          GAS_STRATEGY_FREE,
          POLL_PERIOD_MS,
          OBSERVER_URI_BC1,
          DISPATCHER_URI,
          MSG_STORE_FROM_DISPATCHER,
          MSG_STORE_FROM_USER);

      setProperties(
          props,
          "BC2",
          "20",
          "http://127.0.0.1:8320/",
          "ws://bc32node1:8546",
          GAS_STRATEGY_FREE,
          POLL_PERIOD_MS,
          "http://127.0.0.1:9526",
          DISPATCHER_URI,
          MSG_STORE_FROM_DISPATCHER,
          MSG_STORE_FROM_USER);

      setProperties(
          props,
          "BC3",
          "21",
          "http://127.0.0.1:8330/",
          "ws://bc33node1:8546",
          GAS_STRATEGY_FREE,
          POLL_PERIOD_MS,
          "http://127.0.0.1:9527",
          DISPATCHER_URI,
          MSG_STORE_FROM_DISPATCHER,
          MSG_STORE_FROM_USER);

      setProperties(
          props,
          "BC4",
          "22",
          "http://127.0.0.1:8340/",
          "ws://bc34node1:8546",
          GAS_STRATEGY_FREE,
          POLL_PERIOD_MS,
          "http://127.0.0.1:9528",
          DISPATCHER_URI,
          MSG_STORE_FROM_DISPATCHER,
          MSG_STORE_FROM_USER);

      setProperties(
          props,
          "BC5",
          "23",
          "http://127.0.0.1:8350/",
          "ws://bc35node1:8546",
          GAS_STRATEGY_FREE,
          POLL_PERIOD_MS,
          "http://127.0.0.1:9529",
          DISPATCHER_URI,
          MSG_STORE_FROM_DISPATCHER,
          MSG_STORE_FROM_USER);
    }

    FileOutputStream fos = new FileOutputStream(file);
    props.store(fos, "");
    fos.flush();
    fos.close();

    return file.getAbsolutePath();
  }

  private void setProperties(
      Properties props,
      final String name,
      final String bcId,
      final String rpcUri,
      final String wsUri,
      final String gasStrategy,
      final String pollPeriodMs,
      final String observerUri,
      final String dispatcherUri,
      final String msgStoreFromDispatcher,
      final String msgStoreFromUser) {
    props.setProperty(name + "_BC_ID", bcId);
    props.setProperty(name + "_BC_RPC_URI", rpcUri);
    props.setProperty(name + "_BC_WS_URI", wsUri);
    props.setProperty(name + "_GAS", gasStrategy);
    props.setProperty(name + "_PERIOD", pollPeriodMs);
    props.setProperty(name + "_OBSERVER_URI", observerUri);
    props.setProperty(name + "_DISPATCHER_URI", dispatcherUri);
    props.setProperty(name + "_MSG_STORE_FROM_DISPATCHER_URI", msgStoreFromDispatcher);
    props.setProperty(name + "_MSG_STORE_FROM_USER_URI", msgStoreFromUser);
  }
}
