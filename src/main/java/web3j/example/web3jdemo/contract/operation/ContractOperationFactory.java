package web3j.example.web3jdemo.contract.operation;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import web3j.example.web3jdemo.contract.operation.casino.register.RegisterCasinoOperation;
import web3j.example.web3jdemo.contract.operation.user.register.RegisterUserOperation;
import web3j.example.web3jdemo.contract.operation.user.document.CancelEnrollRequestOperation;
import web3j.example.web3jdemo.contract.operation.user.document.EnrollRequestOperation;
import web3j.example.web3jdemo.contract.operation.user.mint.EnrollTransferOperation;
import web3j.example.web3jdemo.contract.wrapper.DldContract;
import web3j.example.web3jdemo.domain.entity.Casino;
import web3j.example.web3jdemo.domain.entity.DldWallet;

import java.math.BigInteger;
import java.util.function.Consumer;

@Component
public abstract class ContractOperationFactory {

    @Lookup
    public abstract EnrollRequestOperation registerEnrollRequestDocument(
            DldWallet dldWallet,
            BigInteger amount,
            String documentUid,
            String data);

    @Lookup
    public abstract EnrollRequestOperation registerEnrollRequestDocument(
            DldWallet dldWallet,
            BigInteger amount,
            String documentUid,
            String data,
            Consumer<DldContract.TransferEventResponse> onExecute,
            Consumer<TransactionReceipt> onReject,
            Consumer<Exception> onError);

    @Lookup
    public abstract CancelEnrollRequestOperation cancelEnrollRequestTransferOperation(
            DldWallet dldWallet,
            BigInteger amount,
            String documentUid,
            String data);

    @Lookup
    public abstract CancelEnrollRequestOperation cancelEnrollRequestTransferOperation(
            DldWallet dldWallet,
            BigInteger amount,
            String documentUid,
            String data,
            Consumer<DldContract.TransferEventResponse> onExecute,
            Consumer<TransactionReceipt> onReject,
            Consumer<Exception> onError);

    @Lookup
    public abstract EnrollTransferOperation enrollOperation(
            DldWallet dldWallet,
            BigInteger amount,
            String documentUid,
            String data);

    @Lookup
    public abstract EnrollTransferOperation enrollOperation(
            DldWallet dldWallet,
            BigInteger amount,
            String documentUid,
            String data,
            Consumer<DldContract.TransferEventResponse> onExecute,
            Consumer<TransactionReceipt> onReject,
            Consumer<Exception> onError);

    @Lookup
    public abstract RegisterCasinoOperation registerCasinoOperation(
            Casino casino,
            String data);

    @Lookup
    public abstract RegisterCasinoOperation registerCasinoOperation(
            Casino casino,
            String data,
            Consumer<DldContract.TransferEventResponse> onExecute,
            Consumer<TransactionReceipt> onReject,
            Consumer<Exception> onError);

    @Lookup
    public abstract RegisterUserOperation registerUserOperation(
            DldWallet dldWallet,
            String data);

    @Lookup
    public abstract RegisterUserOperation registerUserOperation(
            DldWallet dldWallet,
            String data,
            Consumer<DldContract.TransferEventResponse> onExecute,
            Consumer<TransactionReceipt> onReject,
            Consumer<Exception> onError);

}
