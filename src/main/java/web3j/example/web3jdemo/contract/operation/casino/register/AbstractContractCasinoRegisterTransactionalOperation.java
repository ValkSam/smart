package web3j.example.web3jdemo.contract.operation.casino.register;

import org.web3j.protocol.core.methods.response.TransactionReceipt;
import web3j.example.web3jdemo.contract.operation.actiontype.ContractCasinoActionType;
import web3j.example.web3jdemo.contract.operation.casino.AbstractContractCasinoTransactionalOperation;
import web3j.example.web3jdemo.contract.operation.exception.ContractException;
import web3j.example.web3jdemo.contract.operation.wrapper.event.RegisterEvent;
import web3j.example.web3jdemo.contract.operation.wrapper.receipt.RegisterCasinoReceipt;
import web3j.example.web3jdemo.domain.entity.Casino;

import java.util.function.Consumer;

import static java.util.Objects.isNull;

public abstract class AbstractContractCasinoRegisterTransactionalOperation extends AbstractContractCasinoTransactionalOperation {

    protected final Casino casino;

    private Consumer<RegisterEvent> onSuccess;
    private Consumer<RegisterCasinoReceipt> onReject;

    public AbstractContractCasinoRegisterTransactionalOperation(ContractCasinoActionType contractCasinoActionType,
                                                                Casino casino,
                                                                String data,
                                                                Consumer<RegisterEvent> onSuccess,
                                                                Consumer<RegisterCasinoReceipt> onReject) {
        super(contractCasinoActionType, casino, data);
        this.casino = casino;
        this.onSuccess = onSuccess;
        this.onReject = onReject;
    }

    public AbstractContractCasinoRegisterTransactionalOperation(ContractCasinoActionType contractActionType,
                                                                Casino casino,
                                                                String data) {
        super(contractActionType, casino, data);
        this.casino = casino;
    }

    @Override
    public void callOnSuccess(TransactionReceipt receipt) {
        if (!isNull(onSuccess)) {
//            onSuccess.accept(new RegisterEvent(contract, receipt));
        }
    }

    @Override
    public void callOnReject(TransactionReceipt receipt) {
        if (!isNull(onReject)) {
            onReject.accept(new RegisterCasinoReceipt(receipt, this));
        }
    }

    @Override
    public void callOnReject(ContractException exception) {
        if (!isNull(onReject)) {
            onReject.accept(new RegisterCasinoReceipt(exception, this));
        }
    }

}
