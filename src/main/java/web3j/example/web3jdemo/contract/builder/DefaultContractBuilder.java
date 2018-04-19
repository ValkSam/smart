package web3j.example.web3jdemo.contract.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import web3j.example.web3jdemo.Web3jProvider;
import web3j.example.web3jdemo.blockchain.EthereumConfig;
import web3j.example.web3jdemo.contract.wrapper.DldContract;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DefaultContractBuilder extends ContractBuilder {

    @Autowired
    private EthereumConfig ethereumConfig;

    @Autowired
    private Web3jProvider web3jProvider;

    public DefaultContractBuilder(Credentials credentials) {
        this.setCredentials(credentials);
    }

    public DldContract build() {
        this.setContractAddress(ethereumConfig.getContractAddress());
        this.setWeb3j(web3jProvider.get());
        this.setGasPrice(ethereumConfig.getDefaultGasPrice());
        this.setGasLimit(ethereumConfig.getDefaultGasLimit());
        return super.build();
    }

}
