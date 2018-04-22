package web3j.example.web3jdemo.contract.builder;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import web3j.example.web3jdemo.Web3jProvider;
import web3j.example.web3jdemo.blockchain.config.EthereumConfig;
import web3j.example.web3jdemo.blockchain.txmanager.TransactionManagerFactory;
import web3j.example.web3jdemo.contract.wrapper.DldContract;

import java.io.IOException;
import java.math.BigInteger;

@Setter
public abstract class ContractBuilder {

    @Autowired
    protected EthereumConfig ethereumConfig;

    @Autowired
    private Web3jProvider web3jProvider;

    @Autowired
    private TransactionManagerFactory transactionManagerFactory;

    private String contractAddress;
    private Web3j web3j;
    private Credentials credentials;
    private BigInteger gasPrice;
    private BigInteger gasLimit;

    public DldContract build() throws IOException, CipherException {
        this.setContractAddress(ethereumConfig.getContractAddress());
        this.setWeb3j(web3jProvider.get());
        return DldContract.load(contractAddress,
                web3j,
                transactionManagerFactory.transactionManager(web3j, credentials),
                gasPrice,
                gasLimit);
    }

}
