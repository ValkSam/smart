package web3j.example.web3jdemo.blockchain.txmanager;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.TransactionManager;

@Component
@ConditionalOnProperty(name = "ethereum.contract.transactionManager.custom", havingValue = "true")
public class CustomTransactionManagerFactory implements TransactionManagerFactory {

    public TransactionManager transactionManager(Web3j web3j, Credentials credentials) {
        return new DldTransactionManager(web3j, credentials);
    }

}
