package web3j.example.web3jdemo.contract.operation.token;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import web3j.example.web3jdemo.contract.operation.token.readonly.GetTokenBalanceOfOperation;
import web3j.example.web3jdemo.contract.operation.token.readonly.GetTokenCheckContractTypeOperation;
import web3j.example.web3jdemo.contract.operation.token.readonly.GetTokenDecimalsOperation;
import web3j.example.web3jdemo.contract.operation.token.readonly.GetTokenIsLatestVersionOperation;
import web3j.example.web3jdemo.contract.operation.token.readonly.GetTokenMaxSupplyOperation;
import web3j.example.web3jdemo.contract.operation.token.readonly.GetTokenNameOperation;
import web3j.example.web3jdemo.contract.operation.token.readonly.GetTokenSymbolOperation;
import web3j.example.web3jdemo.contract.operation.token.readonly.GetTokenTotalSupplyOperation;
import web3j.example.web3jdemo.contract.operation.token.transactional.document.CancelEnrollRequestDocumentOperation;
import web3j.example.web3jdemo.contract.operation.token.transactional.document.RegisterEnrollRequestDocumentOperation;
import web3j.example.web3jdemo.contract.operation.wrapper.event.RegisterDocumentEvent;
import web3j.example.web3jdemo.contract.operation.wrapper.receipt.RegisterDocumentReceipt;
import web3j.example.web3jdemo.domain.entity.DldWallet;

import java.math.BigInteger;
import java.util.function.Consumer;

@Component
public abstract class TokenContractOperationFactory {

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

    @Lookup
    public abstract CancelEnrollRequestDocumentOperation cancelEnrollRequestDocumentOperation(
            Credentials senderCredentials,
            DldWallet dldWallet,
            BigInteger amount,
            String documentUid,
            String data);

    @Lookup
    public abstract CancelEnrollRequestDocumentOperation cancelEnrollRequestDocumentOperation(
            Credentials senderCredentials,
            DldWallet dldWallet,
            BigInteger amount,
            String documentUid,
            String data,
            Consumer<RegisterDocumentEvent> onExecute,
            Consumer<RegisterDocumentReceipt> onReject);

}
