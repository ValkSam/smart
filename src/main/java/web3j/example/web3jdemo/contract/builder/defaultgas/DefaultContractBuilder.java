package web3j.example.web3jdemo.contract.builder.defaultgas;

import org.springframework.stereotype.Component;
import org.web3j.crypto.CipherException;
import web3j.example.web3jdemo.contract.builder.ContractBuilder;
import web3j.example.web3jdemo.contract.wrapper.DldContract;

import java.io.IOException;

@Component
public abstract class DefaultContractBuilder extends ContractBuilder {

    public DldContract build() throws IOException, CipherException {
        this.setGasPrice(ethereumConfig.getDefaultGasPrice());
        this.setGasLimit(ethereumConfig.getDefaultGasLimit());
        return super.build();
    }

}
