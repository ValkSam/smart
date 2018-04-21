package web3j.example.web3jdemo.contract.builder.defaultgas;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import web3j.example.web3jdemo.contract.builder.ContractFactory;

import java.io.IOException;

@Component
public abstract class DefaultContractFactory extends ContractFactory {

    @Lookup
    public abstract DefaultOwnerContractBuilder defaultOwnerContractBuilder();

    @Lookup
    public abstract DefaultSingletonOwnerContractBuilder defaultSingletonOwnerContractBuilder();

    @Lookup
    public abstract DefaultUserContractBuilder defaultUserContractBuilder(Credentials credentials);


}
