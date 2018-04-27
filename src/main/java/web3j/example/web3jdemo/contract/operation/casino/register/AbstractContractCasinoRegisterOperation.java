package web3j.example.web3jdemo.contract.operation.casino.register;

import org.web3j.protocol.core.methods.response.TransactionReceipt;
import web3j.example.web3jdemo.contract.operation.actiontype.ContractCasinoActionType;
import web3j.example.web3jdemo.contract.operation.casino.AbstractContractCasinoOperation;
import web3j.example.web3jdemo.contract.wrapper.DldContract;
import web3j.example.web3jdemo.domain.entity.Casino;

import java.util.function.Consumer;

import static java.util.Objects.isNull;

public abstract class AbstractContractCasinoRegisterOperation extends AbstractContractCasinoOperation {

    protected final Casino casino;

    private Consumer<DldContract.RegisterEventResponse> onSuccess;

    public AbstractContractCasinoRegisterOperation(ContractCasinoActionType contractCasinoActionType,
                                                   Casino casino,
                                                   String data,
                                                   Consumer<DldContract.RegisterEventResponse> onSuccess,
                                                   Consumer<TransactionReceipt> onReject,
                                                   Consumer<Exception> onError) {
        super(contractCasinoActionType, casino, data, onReject, onError);
        this.casino = casino;
        this.onSuccess = onSuccess;
    }

    public AbstractContractCasinoRegisterOperation(ContractCasinoActionType contractActionType,
                                                   Casino casino,
                                                   String data) {
        super(contractActionType, casino, data);
        this.casino = casino;
    }

    @Override
    public void callOnSuccess(TransactionReceipt receipt) {
        if (!isNull(onSuccess)) {
            onSuccess.accept(contract.getRegisterEvents(receipt).get(0));
        }
    }

}
