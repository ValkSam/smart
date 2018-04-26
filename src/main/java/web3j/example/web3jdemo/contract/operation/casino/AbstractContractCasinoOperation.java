package web3j.example.web3jdemo.contract.operation.casino;

import org.web3j.crypto.CipherException;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import web3j.example.web3jdemo.contract.operation.AbstractContractOperation;
import web3j.example.web3jdemo.contract.operation.actiontype.ContractCasinoActionType;
import web3j.example.web3jdemo.contract.wrapper.DldContract;
import web3j.example.web3jdemo.domain.entity.Casino;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.function.Consumer;

public abstract class AbstractContractCasinoOperation extends AbstractContractOperation {

    protected final Casino casino;

    public AbstractContractCasinoOperation(ContractCasinoActionType contractActionType,
                                           Casino casino,
                                           String data,
                                           Consumer<TransactionReceipt> onReject,
                                           Consumer<Exception> onError) {
        super(contractActionType, data, onReject, onError);
        this.casino = casino;
    }

    public AbstractContractCasinoOperation(ContractCasinoActionType contractActionType,
                                           Casino casino,
                                           String data) {
        super(contractActionType, data);
        this.casino = casino;
    }

    @PostConstruct
    protected void init() throws IOException, CipherException {
        this.contract = contractFactory.defaultCasinoContractBuilder(casino).build();
    }

}
