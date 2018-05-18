package web3j.example.web3jdemo.contract.operation.token.transactional.document;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import web3j.example.web3jdemo.contract.operation.actiontype.TokenContractActionType;
import web3j.example.web3jdemo.contract.operation.wrapper.event.RegisterDocumentEvent;
import web3j.example.web3jdemo.contract.operation.wrapper.receipt.RegisterDocumentReceipt;
import web3j.example.web3jdemo.domain.entity.DldWallet;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static web3j.example.web3jdemo.contract.operation.actiontype.TokenContractActionType.REGISTER_ENROLL_DOCUMENT;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RegisterEnrollRequestDocumentOperation extends AbstractTokenContractUserDocumentOperation {

    private static final TokenContractActionType ACTION_TYPE = REGISTER_ENROLL_DOCUMENT;

    public RegisterEnrollRequestDocumentOperation(Credentials senderCredentials,
                                                  DldWallet userWallet,
                                                  BigInteger amount,
                                                  String documentUid,
                                                  String data) {
        super(senderCredentials, ACTION_TYPE, userWallet, amount, documentUid, data);
    }

    public RegisterEnrollRequestDocumentOperation(Credentials senderCredentials,
                                                  DldWallet userWallet,
                                                  BigInteger amount,
                                                  String documentUid,
                                                  String data,
                                                  Consumer<RegisterDocumentEvent> onSuccess,
                                                  Consumer<RegisterDocumentReceipt> onReject) {
        super(senderCredentials, ACTION_TYPE, userWallet, amount, documentUid, data, onSuccess, onReject);
    }

    @Override
    public CompletableFuture<TransactionReceipt> execute() throws IOException, TransactionException {
        return execute(contract.registerEnrollRequestDocument(
                userWallet.getAddress(),
                amount,
                documentUID,
                data));
    }

}
