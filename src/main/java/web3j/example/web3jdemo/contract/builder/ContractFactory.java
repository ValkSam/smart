package web3j.example.web3jdemo.contract.builder;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.ens.Contracts;

@Component
public abstract class ContractFactory {

    @Lookup
    public abstract DefaultContractBuilder defaultContractBuilder(Credentials credentials);

}
