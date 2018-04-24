package web3j.example.web3jdemo.contract.operation.casino.register;

import org.web3j.crypto.CipherException;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import web3j.example.web3jdemo.contract.operation.ContractOperation;
import web3j.example.web3jdemo.contract.wrapper.DldContract;
import web3j.example.web3jdemo.domain.entity.Casino;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.function.Consumer;

import static java.util.Objects.isNull;

public abstract class ContractCasinoRegisterOperation extends ContractOperation {

    protected final Casino casino;

    private Consumer<DldContract.RegisterEventResponse> onSuccess;

    public ContractCasinoRegisterOperation(ContractActionType contractActionType,
                                           Casino casino,
                                           String data,
                                           Consumer<DldContract.RegisterEventResponse> onSuccess,
                                           Consumer<TransactionReceipt> onReject,
                                           Consumer<Exception> onError) {
        super(contractActionType, data, onReject, onError);
        this.casino = casino;
        this.onSuccess = onSuccess;
    }

    public ContractCasinoRegisterOperation(ContractActionType contractActionType,
                                           Casino casino,
                                           String data) {
        super(contractActionType, data);
        this.casino = casino;
    }

    @Override
    public void callOnSuccess(TransactionReceipt receipt) {
        if (!isNull(onSuccess)) {
            onSuccess.accept(contract.getRegisterEvents(receipt).get(0));
        }
    }

    @PostConstruct
    protected void init() throws IOException, CipherException {
        this.contract = contractFactory.defaultCasinoContractBuilder(casino).build();
    }

}
