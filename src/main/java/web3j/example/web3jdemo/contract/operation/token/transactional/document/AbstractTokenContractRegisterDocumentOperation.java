package web3j.example.web3jdemo.contract.operation.token.transactional.document;

import lombok.Getter;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import web3j.example.web3jdemo.contract.operation.actiontype.TokenContractActionType;
import web3j.example.web3jdemo.contract.operation.exception.ContractException;
import web3j.example.web3jdemo.contract.operation.token.transactional.AbstractTokenContractTransactionalOperation;
import web3j.example.web3jdemo.contract.operation.wrapper.event.RegisterDocumentEvent;
import web3j.example.web3jdemo.contract.operation.wrapper.receipt.RegisterDocumentReceipt;
import web3j.example.web3jdemo.domain.entity.DldWallet;

import java.util.function.Consumer;

import static java.util.Objects.isNull;

@Getter
public abstract class AbstractTokenContractRegisterDocumentOperation extends AbstractTokenContractTransactionalOperation {

    protected final DldWallet userWallet;
    protected final String documentUID;

    private Consumer<RegisterDocumentEvent> onSuccess;
    private Consumer<RegisterDocumentReceipt> onReject;

    public AbstractTokenContractRegisterDocumentOperation(Credentials senderCredentials,
                                                          TokenContractActionType tokenContractActionType,
                                                          DldWallet userWallet,
                                                          String documentUID,
                                                          String data,
                                                          Consumer<RegisterDocumentEvent> onSuccess,
                                                          Consumer<RegisterDocumentReceipt> onReject) {
        super(senderCredentials, tokenContractActionType, data);
        this.userWallet = userWallet;
        this.documentUID = documentUID;
        this.onSuccess = onSuccess;
        this.onReject = onReject;
    }

    public AbstractTokenContractRegisterDocumentOperation(Credentials senderCredentials,
                                                          TokenContractActionType tokenContractActionType,
                                                          DldWallet userWallet,
                                                          String documentUID,
                                                          String data) {
        super(senderCredentials, tokenContractActionType, data);
        this.userWallet = userWallet;
        this.documentUID = documentUID;
    }

    @Override
    public void callOnSuccess(TransactionReceipt receipt) {
        if (!isNull(onSuccess)) {
            onSuccess.accept(new RegisterDocumentEvent(contract, receipt));
        }
    }

    @Override
    public void callOnReject(TransactionReceipt receipt) {
        if (!isNull(onReject)) {
            onReject.accept(new RegisterDocumentReceipt(receipt, this));
        }
    }

    @Override
    public void callOnReject(ContractException exception) {
        if (!isNull(onReject)) {
            onReject.accept(new RegisterDocumentReceipt(exception, this));
        }
    }

}
