package tech.pegasys.ltacfc.exampletest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public abstract class AbstractExampleTest {

  protected String createPropertiesFile(boolean useDirectSigning, boolean serialExecution, boolean oneBlockchain) throws IOException {
    File file = File.createTempFile("temp", null);
//    file.deleteOnExit();

    Properties props = new Properties();

    props.setProperty("PRIVATE_KEY", "40000123456789012345678903961456349a242b3a4b8a211d85ea4d89b1");
    if (useDirectSigning) {
      props.setProperty("CONSENSUS_METHODOLOGY", "EVENT_SIGNING");
    }
    else {
      props.setProperty("CONSENSUS_METHODOLOGY", "TRANSACTION_RECEIPT_SIGNING");
    }

    if (serialExecution) {
      props.setProperty("EXECUTION_ENGINE", "SERIAL");
    }
    else {
      props.setProperty("EXECUTION_ENGINE", "PARALLEL");
    }

    if (oneBlockchain) {
      props.setProperty("OTHER_BC_ID", "1F");
      props.setProperty("OTHER_URI", "http://127.0.0.1:8310/");
      props.setProperty("OTHER_GAS", "FREE");
      props.setProperty("OTHER_PERIOD", "2000");

      props.setProperty("ROOT_BC_ID", "1F");
      props.setProperty("ROOT_URI", "http://127.0.0.1:8310/");
      props.setProperty("ROOT_GAS", "FREE");
      props.setProperty("ROOT_PERIOD", "2000");

      props.setProperty("BC2_BC_ID", "1F");
      props.setProperty("BC2_URI", "http://127.0.0.1:8310/");
      props.setProperty("BC2_GAS", "FREE");
      props.setProperty("BC2_PERIOD", "2000");

      props.setProperty("BC3_BC_ID", "1F");
      props.setProperty("BC3_URI", "http://127.0.0.1:8310/");
      props.setProperty("BC3_GAS", "FREE");
      props.setProperty("BC3_PERIOD", "2000");

      props.setProperty("BC4_BC_ID", "1F");
      props.setProperty("BC4_URI", "http://127.0.0.1:8310/");
      props.setProperty("BC4_GAS", "FREE");
      props.setProperty("BC4_PERIOD", "2000");

      props.setProperty("BC5_BC_ID", "1F");
      props.setProperty("BC5_URI", "http://127.0.0.1:8310/");
      props.setProperty("BC5_GAS", "FREE");
      props.setProperty("BC5_PERIOD", "2000");
    }
    else {
      props.setProperty("OTHER_BC_ID", "20");
      props.setProperty("OTHER_URI", "http://127.0.0.1:8320/");
      props.setProperty("OTHER_GAS", "FREE");
      props.setProperty("OTHER_PERIOD", "2000");

      props.setProperty("ROOT_BC_ID", "1F");
      props.setProperty("ROOT_URI", "http://127.0.0.1:8310/");
      props.setProperty("ROOT_GAS", "FREE");
      props.setProperty("ROOT_PERIOD", "2000");

      props.setProperty("BC2_BC_ID", "20");
      props.setProperty("BC2_URI", "http://127.0.0.1:8320/");
      props.setProperty("BC2_GAS", "FREE");
      props.setProperty("BC2_PERIOD", "2000");

      props.setProperty("BC3_BC_ID", "21");
      props.setProperty("BC3_URI", "http://127.0.0.1:8330/");
      props.setProperty("BC3_GAS", "FREE");
      props.setProperty("BC3_PERIOD", "2000");

      props.setProperty("BC4_BC_ID", "22");
      props.setProperty("BC4_URI", "http://127.0.0.1:8340/");
      props.setProperty("BC4_GAS", "FREE");
      props.setProperty("BC4_PERIOD", "2000");

      props.setProperty("BC5_BC_ID", "23");
      props.setProperty("BC5_URI", "http://127.0.0.1:8350/");
      props.setProperty("BC5_GAS", "FREE");
      props.setProperty("BC5_PERIOD", "2000");
    }


    FileOutputStream fos = new FileOutputStream(file);
    props.store(fos, "");
    fos.flush();
    fos.close();

    return file.getAbsolutePath();
  }

}
