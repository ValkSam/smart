package web3j.example.web3jdemo.blockchain.txmanager;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.FastRawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ConditionalOnExpression("${ethereum.contract.transactionManager.custom}==false " +
        "&&${ethereum.contract.transactionManager.manageNonce}==true")
public class FastRawTransactionManagerFactory implements TransactionManagerFactory {

    private static volatile Map<Credentials, TransactionManager> fastRawTransactionManagerMap = new ConcurrentHashMap<>();

    public TransactionManager transactionManager(Web3j web3j, Credentials credentials) {

        TransactionManager fast = fastRawTransactionManagerMap.get(credentials);
        if (fast == null) {
            synchronized (credentials) {
                fast = fastRawTransactionManagerMap.getOrDefault(
                        credentials,
                        new FastRawTransactionManager(
                                web3j,
                                credentials,
                                new PollingTransactionReceiptProcessor(
                                        web3j, 1000, TransactionManager.DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH)));
                fastRawTransactionManagerMap.putIfAbsent(credentials, fast);
            }
        }

        return fast;
    }

}
