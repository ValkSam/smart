package web3j.example.web3jdemo.contract.operation;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import web3j.example.web3jdemo.contract.operation.owner.readonly.GetTokenBalanceOfOperation;
import web3j.example.web3jdemo.contract.operation.owner.readonly.GetTokenCheckContractTypeOperation;
import web3j.example.web3jdemo.contract.operation.owner.readonly.GetTokenDecimalsOperation;
import web3j.example.web3jdemo.contract.operation.owner.readonly.GetTokenIsLatestVersionOperation;
import web3j.example.web3jdemo.contract.operation.owner.readonly.GetTokenMaxSupplyOperation;
import web3j.example.web3jdemo.contract.operation.owner.readonly.GetTokenNameOperation;
import web3j.example.web3jdemo.contract.operation.owner.readonly.GetTokenSymbolOperation;
import web3j.example.web3jdemo.contract.operation.owner.readonly.GetTokenTotalSupplyOperation;
import web3j.example.web3jdemo.contract.operation.owner.transactional.document.RegisterEnrollRequestDocumentOperation;
import web3j.example.web3jdemo.contract.operation.wrapper.event.RegisterDocumentEvent;
import web3j.example.web3jdemo.contract.operation.wrapper.receipt.RegisterDocumentReceipt;
import web3j.example.web3jdemo.domain.entity.DldWallet;

import java.math.BigInteger;
import java.util.function.Consumer;

@Component
public abstract class ContractOperationFactory {

    /*

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
            DldWallet wallet, String documentUID);*/

    @Lookup
    public abstract GetTokenSymbolOperation getTokenSymbolOperation(Credentials senderCredentials);

    @Lookup
    public abstract GetTokenNameOperation getTokenNameOperation(Credentials senderCredentials);

    @Lookup
    public abstract GetTokenDecimalsOperation getTokenDecimalsOperation(Credentials senderCredentials);

    @Lookup
    public abstract GetTokenMaxSupplyOperation getTokenMaxSupplyOperation(Credentials senderCredentials);

    @Lookup
    public abstract GetTokenTotalSupplyOperation getTokenTotalSupplyOperation(Credentials senderCredentials);

    @Lookup
    public abstract GetTokenIsLatestVersionOperation getTokenIsLatestVersionOperation(Credentials senderCredentials);

    @Lookup
    public abstract GetTokenCheckContractTypeOperation getTokenCheckContractTypeOperation(Credentials senderCredentials);

    @Lookup
    public abstract GetTokenBalanceOfOperation getTokenBalanceOfOperation(Credentials senderCredentials, DldWallet userWallet);

    @Lookup
    public abstract GetTokenBalanceOfOperation getTokenBalanceOfOperation(Credentials senderCredentials, String address);

    @Lookup
    public abstract RegisterEnrollRequestDocumentOperation registerEnrollRequestDocumentOperation(
            Credentials senderCredentials,
            DldWallet dldWallet,
            BigInteger amount,
            String documentUid,
            String data);

    @Lookup
    public abstract RegisterEnrollRequestDocumentOperation registerEnrollRequestDocumentOperation(
            Credentials senderCredentials,
            DldWallet dldWallet,
            BigInteger amount,
            String documentUid,
            String data,
            Consumer<RegisterDocumentEvent> onExecute,
            Consumer<RegisterDocumentReceipt> onReject);

}
