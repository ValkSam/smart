package web3j.example.web3jdemo.contract.builder;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.TransactionManager;
import web3j.example.web3jdemo.Web3jProvider;
import web3j.example.web3jdemo.blockchain.DldTransactionManager;
import web3j.example.web3jdemo.blockchain.EthereumConfig;
import web3j.example.web3jdemo.contract.wrapper.DldContract;

import java.io.IOException;
import java.math.BigInteger;

@Setter
public abstract class ContractBuilder {

    @Autowired
    protected EthereumConfig ethereumConfig;

    @Autowired
    private Web3jProvider web3jProvider;

    private String contractAddress;
    private Web3j web3j;
    private Credentials credentials;
    private BigInteger gasPrice;
    private BigInteger gasLimit;

    public DldContract build() throws IOException, CipherException {
        this.setContractAddress(ethereumConfig.getContractAddress());
        this.setWeb3j(web3jProvider.get());
        TransactionManager transactionManager = new DldTransactionManager(web3j, credentials);
        return DldContract.load(contractAddress,
                web3j,
                transactionManager,
                gasPrice,
                gasLimit);
    }

}
