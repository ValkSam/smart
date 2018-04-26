package web3j.example.web3jdemo.contract.operation.user.document;

import org.web3j.protocol.core.methods.response.TransactionReceipt;
import web3j.example.web3jdemo.contract.operation.actiontype.ContractUserActionType;
import web3j.example.web3jdemo.contract.operation.user.AbstractContractUserOperation;
import web3j.example.web3jdemo.contract.wrapper.DldContract;
import web3j.example.web3jdemo.domain.entity.DldWallet;

import java.math.BigInteger;
import java.util.function.Consumer;

import static java.util.Objects.isNull;

public abstract class AbstractContractUserDocumentOperation extends AbstractContractUserOperation {

    protected final BigInteger amount;
    protected final String documentUID;

    private Consumer<DldContract.RegisterDocumentEventResponse> onSuccess;

    public AbstractContractUserDocumentOperation(ContractUserActionType contractUserActionType,
                                                 DldWallet dldWallet,
                                                 BigInteger amount,
                                                 String documentUid,
                                                 String data,
                                                 Consumer<DldContract.RegisterDocumentEventResponse> onSuccess,
                                                 Consumer<TransactionReceipt> onReject,
                                                 Consumer<Exception> onError) {
        super(contractUserActionType, dldWallet, data, onReject, onError);
        this.amount = amount;
        this.documentUID = documentUid;
        this.onSuccess = onSuccess;
    }

    public AbstractContractUserDocumentOperation(ContractUserActionType contractUserActionType,
                                                 DldWallet dldWallet,
                                                 BigInteger amount,
                                                 String documentUid,
                                                 String data) {
        super(contractUserActionType, dldWallet, data);
        this.amount = amount;
        this.documentUID = documentUid;
    }

    @Override
    public void callOnSuccess(TransactionReceipt receipt) {
        if (!isNull(onSuccess)) {
            onSuccess.accept(contract.getRegisterDocumentEvents(receipt).get(0));
        }
    }

}
