package web3j.example.web3jdemo.contract.operation;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import web3j.example.web3jdemo.contract.operation.user.EnrollOperation;
import web3j.example.web3jdemo.contract.operation.user.EnrollRequestOperation;
import web3j.example.web3jdemo.contract.operation.user.RegisterUserOperation;
import web3j.example.web3jdemo.contract.wrapper.DldContract;
import web3j.example.web3jdemo.domain.entity.DldWallet;

import java.math.BigInteger;
import java.util.function.Consumer;

@Component
public abstract class ContractOperationFactory {

    @Lookup
    public abstract EnrollRequestOperation enrollRequestOperation(
            DldWallet dldWallet,
            BigInteger amount,
            String documentUid,
            String data);

    @Lookup
    public abstract EnrollRequestOperation enrollRequestOperation(
            DldWallet dldWallet,
            BigInteger amount,
            String documentUid,
            String data,
            Consumer<DldContract.TransactionEventResponse> onExecute,
            Consumer<TransactionReceipt> onReject,
            Consumer<Exception> onError);

    @Lookup
    public abstract EnrollOperation enrollOperation(
            DldWallet dldWallet,
            BigInteger amount,
            String documentUid,
            String data);

    @Lookup
    public abstract EnrollOperation enrollOperation(
            DldWallet dldWallet,
            BigInteger amount,
            String documentUid,
            String data,
            Consumer<DldContract.TransactionEventResponse> onExecute,
            Consumer<TransactionReceipt> onReject,
            Consumer<Exception> onError);

    @Lookup
    public abstract RegisterUserOperation registerUserOperation(
            DldWallet dldWallet,
            String documentUid,
            String data);

    @Lookup
    public abstract RegisterUserOperation registerUserOperation(
            DldWallet dldWallet,
            String documentUid,
            String data,
            Consumer<DldContract.TransactionEventResponse> onExecute,
            Consumer<TransactionReceipt> onReject,
            Consumer<Exception> onError);

}
