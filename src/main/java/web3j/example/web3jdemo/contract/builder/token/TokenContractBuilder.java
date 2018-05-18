package web3j.example.web3jdemo.contract.builder.token;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import web3j.example.web3jdemo.Web3jProvider;
import web3j.example.web3jdemo.blockchain.config.EthereumConfig;
import web3j.example.web3jdemo.blockchain.txmanager.TransactionManagerFactory;
import web3j.example.web3jdemo.contract.wrapper.token.DltTokenContract;

import java.io.IOException;
import java.math.BigInteger;

@Component
@Setter
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TokenContractBuilder {

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

    public TokenContractBuilder(Credentials credentials) {
        this.credentials = credentials;
    }

    public DltTokenContract build() throws IOException, CipherException {
        this.setContractAddress(ethereumConfig.getContractAddress());
        this.setWeb3j(web3jProvider.get());
        this.setGasPrice(ethereumConfig.getDefaultGasPrice());
        this.setGasLimit(ethereumConfig.getDefaultGasLimit());
        return DltTokenContract.load(contractAddress,
                web3j,
                transactionManagerFactory.transactionManager(web3j, credentials),
                gasPrice,
                gasLimit);
    }

}
