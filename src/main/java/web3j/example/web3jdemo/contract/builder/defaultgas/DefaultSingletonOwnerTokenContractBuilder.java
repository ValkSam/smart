package web3j.example.web3jdemo.contract.builder.defaultgas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.crypto.CipherException;
import web3j.example.web3jdemo.blockchain.utils.CredentialsHelper;
import web3j.example.web3jdemo.contract.wrapper.token.DltTokenContract;

import java.io.IOException;

import static java.util.Objects.isNull;

@Component
public class DefaultSingletonOwnerTokenContractBuilder  {

    private static volatile DltTokenContract contract;

    @Autowired
    private CredentialsHelper credentialsHelper;

    public DltTokenContract build() throws IOException, CipherException {
        DltTokenContract instance = contract;
        if (isNull(instance)) {
            synchronized (DefaultSingletonOwnerTokenContractBuilder.class) {
                instance = contract;
                if (isNull(instance)) {
//                    setCredentials(credentialsHelper.getOwnerCredentials());
//                    contract = instance = super.build();
                }
            }
        }
        return instance;
    }

}
