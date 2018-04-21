package web3j.example.web3jdemo.contract.builder.defaultgas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.web3j.crypto.CipherException;
import web3j.example.web3jdemo.blockchain.utils.CredentialsHelper;
import web3j.example.web3jdemo.contract.wrapper.DldContract;

import java.io.IOException;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DefaultOwnerContractBuilder extends DefaultContractBuilder {

    @Autowired
    private CredentialsHelper credentialsHelper;

    public DldContract build() throws IOException, CipherException {
        setCredentials(credentialsHelper.getOwnerCredentials());
        return super.build();
    }

}
