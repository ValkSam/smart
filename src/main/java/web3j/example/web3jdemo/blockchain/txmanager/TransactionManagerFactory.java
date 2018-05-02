package web3j.example.web3jdemo.blockchain.txmanager;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.TransactionManager;

public interface TransactionManagerFactory {
    TransactionManager transactionManager(Web3j web3j, Credentials credentials);
}
