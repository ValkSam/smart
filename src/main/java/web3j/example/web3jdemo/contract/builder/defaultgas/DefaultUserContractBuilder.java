package web3j.example.web3jdemo.contract.builder.defaultgas;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import web3j.example.web3jdemo.contract.wrapper.DldContract;

import java.io.IOException;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DefaultUserContractBuilder extends DefaultContractBuilder {

    public DefaultUserContractBuilder(Credentials credentials) {
        this.setCredentials(credentials);
    }

    public DldContract build() throws IOException, CipherException {
        return super.build();
    }

}
