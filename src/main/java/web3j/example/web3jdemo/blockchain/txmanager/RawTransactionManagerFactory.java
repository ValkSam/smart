package web3j.example.web3jdemo.blockchain.txmanager;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;

@Component
@ConditionalOnExpression("${ethereum.contract.transactionManager.custom}==false " +
        "&&${ethereum.contract.transactionManager.manageNonce}==false")
public class RawTransactionManagerFactory implements TransactionManagerFactory {

    public TransactionManager transactionManager(Web3j web3j, Credentials credentials) {
        return new RawTransactionManager(web3j, credentials);
    }

}
