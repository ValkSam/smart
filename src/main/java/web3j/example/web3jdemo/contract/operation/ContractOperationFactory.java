package web3j.example.web3jdemo.contract.operation;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;
import web3j.example.web3jdemo.contract.operation.casino.register.RegisterCasinoOperation;
import web3j.example.web3jdemo.contract.operation.owner.readonly.addressinfo.document.GetUserDocumentBlockOperation;
import web3j.example.web3jdemo.contract.operation.owner.readonly.addressinfo.document.GetUserDocumentListOperation;
import web3j.example.web3jdemo.contract.operation.user.document.CancelEnrollRequestOperation;
import web3j.example.web3jdemo.contract.operation.user.document.EnrollRequestOperation;
import web3j.example.web3jdemo.contract.operation.user.mint.EnrollTransferOperation;
import web3j.example.web3jdemo.contract.operation.user.register.RegisterUserOperation;
import web3j.example.web3jdemo.contract.operation.wrapper.event.RegisterDocumentEvent;
import web3j.example.web3jdemo.contract.operation.wrapper.event.RegisterEvent;
import web3j.example.web3jdemo.contract.operation.wrapper.receipt.RegisterCasinoReceipt;
import web3j.example.web3jdemo.contract.operation.wrapper.receipt.RegisterDocumentReceipt;
import web3j.example.web3jdemo.contract.operation.wrapper.receipt.RegisterUserReceipt;
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
            Consumer<RegisterDocumentEvent> onExecute,
            Consumer<RegisterDocumentReceipt> onReject);

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
            Consumer<RegisterDocumentEvent> onExecute,
            Consumer<RegisterDocumentReceipt> onReject);

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
            Consumer<RegisterDocumentEvent> onExecute,
            Consumer<RegisterDocumentReceipt> onReject);

    @Lookup
    public abstract RegisterCasinoOperation registerCasinoOperation(
            Casino casino,
            String data);

    @Lookup
    public abstract RegisterCasinoOperation registerCasinoOperation(
            Casino casino,
            String data,
            Consumer<RegisterEvent> onExecute,
            Consumer<RegisterCasinoReceipt> onReject);

    @Lookup
    public abstract RegisterUserOperation registerUserOperation(
            DldWallet dldWallet,
            String data);

    @Lookup
    public abstract RegisterUserOperation registerUserOperation(
            DldWallet dldWallet,
            String data,
            Consumer<RegisterEvent> onExecute,
            Consumer<RegisterUserReceipt> onReject);

    @Lookup
    public abstract GetUserDocumentListOperation getUserDocumentListOperation(
            String address);

    @Lookup
    public abstract GetUserDocumentListOperation getUserDocumentListOperation(
            DldWallet wallet);

    @Lookup
    public abstract GetUserDocumentBlockOperation getUserDocumentBlockOperation(
            String address, String documentUID);

    @Lookup
    public abstract GetUserDocumentBlockOperation getUserDocumentBlockOperation(
            DldWallet wallet, String documentUID);

}
