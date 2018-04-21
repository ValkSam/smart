package web3j.example.web3jdemo.contract.operation;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;
import web3j.example.web3jdemo.contract.operation.user.EnrollOperation;
import web3j.example.web3jdemo.contract.operation.user.EnrollRequestOperation;
import web3j.example.web3jdemo.contract.operation.user.RegisterUserOperation;
import web3j.example.web3jdemo.domain.entity.DldWallet;

import java.math.BigInteger;

@Component
public abstract class ContractOperationFactory {

    @Lookup
    public abstract EnrollRequestOperation enrollRequestOperation(
            DldWallet dldWallet,
            BigInteger amount,
            String documentUid,
            String data);

    @Lookup
    public abstract EnrollOperation enrollOperation(
            DldWallet dldWallet,
            BigInteger amount,
            String documentUid,
            String data);

    @Lookup
    public abstract RegisterUserOperation registerUserOperation(
            DldWallet dldWallet,
            String documentUid,
            String data);

}
