package web3j.example.web3jdemo.contract.operation.admin.transactional.register;

import lombok.Getter;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import web3j.example.web3jdemo.contract.operation.actiontype.AdminContractActionType;
import web3j.example.web3jdemo.contract.operation.admin.transactional.AbstractAdminContractTransactionalOperation;
import web3j.example.web3jdemo.contract.operation.exception.ContractException;
import web3j.example.web3jdemo.contract.operation.wrapper.event.AdminRegisterEvent;
import web3j.example.web3jdemo.contract.operation.wrapper.receipt.AdminRegisterReceipt;

import java.util.function.Consumer;

import static java.util.Objects.isNull;

@Getter
public abstract class AbstractAdminContractRegisterOperation extends AbstractAdminContractTransactionalOperation {

    private Consumer<AdminRegisterEvent> onSuccess;
    private Consumer<AdminRegisterReceipt> onReject;

    public AbstractAdminContractRegisterOperation(Credentials senderCredentials,
                                                  AdminContractActionType adminContractActionType,
                                                  String data,
                                                  Consumer<AdminRegisterEvent> onSuccess,
                                                  Consumer<AdminRegisterReceipt> onReject) {
        super(senderCredentials, adminContractActionType, data);
        this.onSuccess = onSuccess;
        this.onReject = onReject;
    }

    public AbstractAdminContractRegisterOperation(Credentials senderCredentials,
                                                  AdminContractActionType adminContractActionType,
                                                  String data) {
        super(senderCredentials, adminContractActionType, data);
    }

    @Override
    public void callOnSuccess(TransactionReceipt receipt) {
        if (!isNull(onSuccess)) {
            onSuccess.accept(new AdminRegisterEvent(contract, receipt));
        }
    }

    @Override
    public void callOnReject(TransactionReceipt receipt) {
        if (!isNull(onReject)) {
            onReject.accept(new AdminRegisterReceipt(receipt, this));
        }
    }

    @Override
    public void callOnReject(ContractException exception) {
        if (!isNull(onReject)) {
            onReject.accept(new AdminRegisterReceipt(exception, this));
        }
    }

}
