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

@Component
public class TransactionManagerFactory extends ContractFactory {

    @Autowired
    protected EthereumConfig ethereumConfig;

    public TransactionManager transactionManager(Web3j web3j, Credentials credentials) {
        return ethereumConfig.getUseCustomTransactionManager()
                ? new DldTransactionManager(web3j, credentials)
                : (ethereumConfig.getUseFastRawTransactionManager()
                ? new FastRawTransactionManager(web3j, credentials)
                : new RawTransactionManager(web3j, credentials));
    }

}
