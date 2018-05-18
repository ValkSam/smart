package web3j.example.web3jdemo.contract.operation.owner.transactional.document;

import lombok.Getter;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import web3j.example.web3jdemo.contract.operation.actiontype.ContractUserActionType;
import web3j.example.web3jdemo.contract.operation.exception.ContractException;
import web3j.example.web3jdemo.contract.operation.owner.transactional.AbstractTokenContractTransactionalOperation;
import web3j.example.web3jdemo.contract.operation.wrapper.event.RegisterDocumentEvent;
import web3j.example.web3jdemo.contract.operation.wrapper.receipt.RegisterDocumentReceipt;
import web3j.example.web3jdemo.domain.entity.DldWallet;

import java.math.BigInteger;
import java.util.function.Consumer;

import static java.util.Objects.isNull;

@Getter
public abstract class AbstractTokenContractUserDocumentOperation extends AbstractTokenContractTransactionalOperation {

    protected final DldWallet userWallet;
    protected final BigInteger amount;
    protected final String documentUID;

    private Consumer<RegisterDocumentEvent> onSuccess;
    private Consumer<RegisterDocumentReceipt> onReject;

    public AbstractTokenContractUserDocumentOperation(Credentials senderCredentials,
                                                      ContractUserActionType contractUserActionType,
                                                      DldWallet userWallet,
                                                      BigInteger amount,
                                                      String documentUid,
                                                      String data,
                                                      Consumer<RegisterDocumentEvent> onSuccess,
                                                      Consumer<RegisterDocumentReceipt> onReject) {
        super(senderCredentials, contractUserActionType, data);
        this.userWallet = userWallet;
        this.amount = amount;
        this.documentUID = documentUid;
        this.onSuccess = onSuccess;
        this.onReject = onReject;
    }

    public AbstractTokenContractUserDocumentOperation(Credentials senderCredentials,
                                                      ContractUserActionType contractUserActionType,
                                                      DldWallet userWallet,
                                                      BigInteger amount,
                                                      String documentUid,
                                                      String data) {
        super(senderCredentials, contractUserActionType, data);
        this.userWallet = userWallet;
        this.amount = amount;
        this.documentUID = documentUid;
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
