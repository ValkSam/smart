package web3j.example.web3jdemo.contract.builder.defaultgas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.crypto.CipherException;
import web3j.example.web3jdemo.blockchain.utils.CredentialsHelper;
import web3j.example.web3jdemo.contract.wrapper.DldContract;

import java.io.IOException;

import static java.util.Objects.isNull;

@Component
public class DefaultSingletonOwnerContractBuilder extends DefaultContractBuilder {

    private static volatile DldContract contract;

    @Autowired
    private CredentialsHelper credentialsHelper;

    public DldContract build() throws IOException, CipherException {
        DldContract instance = contract;
        if (isNull(instance)) {
            synchronized (DefaultSingletonOwnerContractBuilder.class) {
                instance = contract;
                if (isNull(instance)) {
                    setCredentials(credentialsHelper.getOwnerCredentials());
                    contract = instance = super.build();
                }
            }
        }
        return instance;
    }

}
