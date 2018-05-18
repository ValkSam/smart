package web3j.example.web3jdemo.contract.operation.user.burn;

import org.web3j.protocol.core.methods.response.TransactionReceipt;
import web3j.example.web3jdemo.contract.operation.actiontype.ContractUserActionType;
import web3j.example.web3jdemo.contract.operation.exception.ContractException;
import web3j.example.web3jdemo.contract.operation.user.AbstractContractUserTransactionalOperation;
import web3j.example.web3jdemo.contract.operation.wrapper.event.BurnEvent;
import web3j.example.web3jdemo.contract.operation.wrapper.receipt.BurnReceipt;
import web3j.example.web3jdemo.domain.entity.DldWallet;

import java.math.BigInteger;
import java.util.function.Consumer;

import static java.util.Objects.isNull;

public abstract class AbstractContractUserBurnTransactionalOperation extends AbstractContractUserTransactionalOperation {

    protected final BigInteger amount;
    protected final String documentUID;

    private Consumer<BurnEvent> onSuccess;
    private Consumer<BurnReceipt> onReject;

    public AbstractContractUserBurnTransactionalOperation(ContractUserActionType contractUserActionType,
                                                          DldWallet dldWallet,
                                                          BigInteger amount,
                                                          String documentUid,
                                                          String data,
                                                          Consumer<BurnEvent> onSuccess,
                                                          Consumer<BurnReceipt> onReject) {
        super(contractUserActionType, dldWallet, data);
        this.amount = amount;
        this.documentUID = documentUid;
        this.onSuccess = onSuccess;
        this.onReject = onReject;
    }

    public AbstractContractUserBurnTransactionalOperation(ContractUserActionType contractUserActionType,
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
//            onSuccess.accept(new BurnEvent(contract, receipt));
        }
    }

    @Override
    public void callOnReject(TransactionReceipt receipt) {
        if (!isNull(onReject)) {
            onReject.accept(new BurnReceipt(receipt, this));
        }
    }

    @Override
    public void callOnReject(ContractException exception) {
        if (!isNull(onReject)) {
            onReject.accept(new BurnReceipt(exception, this));
        }
    }

}
