package net.consensys.gpact.common;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.response.TransactionReceiptProcessor;

import java.util.HashMap;
import java.util.Map;

public class TxManagerCache {

    private static Map<String, FastTxManager> txManagers = new HashMap();

    public static FastTxManager getOrCreate(Web3j web3j, Credentials credentials, long chainId, TransactionReceiptProcessor transactionReceiptProcessor) {
        synchronized(TxManagerCache.class) {
            String key = credentials.getAddress()  + chainId;
            FastTxManager txManager = txManagers.get(key);
            if (txManager == null) {
                txManager = new FastTxManager(web3j, credentials, chainId, transactionReceiptProcessor);
                txManagers.put(key, txManager);
            }
            return txManager;
        }
    }
}
