package web3j.example.web3jdemo.contract.operation;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;
import web3j.example.web3jdemo.contract.operation.owner.EnrollOperation;
import web3j.example.web3jdemo.contract.operation.owner.EnrollRequestOperation;

import java.math.BigInteger;

@Component
public abstract class ContractOperationFactory {

    @Lookup
    public abstract EnrollRequestOperation enrollRequestOperation(
            String userName,
            BigInteger amount,
            String documentUid,
            BigInteger documentAmount);

    @Lookup
    public abstract EnrollOperation enrollOperation(
            String userName,
            BigInteger amount,
            String documentUid,
            BigInteger documentAmount);

}
