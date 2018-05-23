package web3j.example.web3jdemo.contract.builder.token;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;

@Component
public abstract class ContractFactory {

    @Lookup
    public abstract TokenContractBuilder tokenContractBuilder(Credentials credentials);

    @Lookup
    public abstract AdminContractBuilder adminContractBuilder(Credentials credentials);

}
