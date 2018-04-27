package web3j.example.web3jdemo.contract.operation.user.register;

import org.web3j.protocol.core.methods.response.TransactionReceipt;
import web3j.example.web3jdemo.contract.operation.actiontype.ContractUserActionType;
import web3j.example.web3jdemo.contract.operation.exception.ContractException;
import web3j.example.web3jdemo.contract.operation.user.AbstractContractUserOperation;
import web3j.example.web3jdemo.contract.operation.wrapper.event.RegisterEvent;
import web3j.example.web3jdemo.contract.operation.wrapper.receipt.RegisterUserReceipt;
import web3j.example.web3jdemo.domain.entity.DldWallet;

import java.util.function.Consumer;

import static java.util.Objects.isNull;

public abstract class AbstractContractUserRegisterOperation extends AbstractContractUserOperation {

    private Consumer<RegisterEvent> onSuccess;
    private Consumer<RegisterUserReceipt> onReject;

    public AbstractContractUserRegisterOperation(ContractUserActionType contractUserActionType,
                                                 DldWallet dldWallet,
                                                 String data,
                                                 Consumer<RegisterEvent> onSuccess,
                                                 Consumer<RegisterUserReceipt> onReject) {
        super(contractUserActionType, dldWallet, data);
        this.onSuccess = onSuccess;
        this.onReject = onReject;
    }

    public AbstractContractUserRegisterOperation(ContractUserActionType contractUserActionType,
                                                 DldWallet dldWallet,
                                                 String data) {
        super(contractUserActionType, dldWallet, data);
    }

    @Override
    public void callOnSuccess(TransactionReceipt receipt) {
        if (!isNull(onSuccess)) {
            onSuccess.accept(new RegisterEvent(contract, receipt));
        }
    }

    @Override
    public void callOnReject(TransactionReceipt receipt) {
        if (!isNull(onReject)) {
            onReject.accept(new RegisterUserReceipt(receipt, this));
        }
    }

    @Override
    public void callOnReject(ContractException exception) {
        if (!isNull(onReject)) {
            onReject.accept(new RegisterUserReceipt(exception, this));
        }
    }

}
