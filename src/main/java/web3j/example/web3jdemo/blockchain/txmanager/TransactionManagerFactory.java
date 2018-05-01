package web3j.example.web3jdemo.blockchain.txmanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.FastRawTransactionManager;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import web3j.example.web3jdemo.blockchain.config.EthereumConfig;
import web3j.example.web3jdemo.contract.builder.ContractFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TransactionManagerFactory extends ContractFactory {

    private volatile Map<String, TransactionManager> fastRawTransactionManagerMap = new ConcurrentHashMap<>();

    @Autowired
    protected EthereumConfig ethereumConfig;

    public TransactionManager transactionManager(Web3j web3j, Credentials credentials) {

        TransactionManager fast = fastRawTransactionManagerMap.get(credentials.getAddress());
        if (fast == null) {
            synchronized (TransactionManagerFactory.class) {
                fast = fastRawTransactionManagerMap.getOrDefault(credentials.getAddress(), new FastRawTransactionManager(web3j, credentials));
                fastRawTransactionManagerMap.putIfAbsent(credentials.getAddress(), fast);
            }
        }

        return ethereumConfig.getUseCustomTransactionManager()
                ? new DldTransactionManager(web3j, credentials)
                : (ethereumConfig.getUseFastRawTransactionManager()
                ? fast
                : new RawTransactionManager(web3j, credentials));
    }

}
