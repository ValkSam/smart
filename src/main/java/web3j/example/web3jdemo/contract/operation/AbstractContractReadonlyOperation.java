package web3j.example.web3jdemo.contract.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.exceptions.TransactionException;
import web3j.example.web3jdemo.contract.builder.defaultgas.DefaultContractFactory;
import web3j.example.web3jdemo.contract.operation.actiontype.ContractActionType;
import web3j.example.web3jdemo.contract.operation.exception.ContractOperationGeneralException;
import web3j.example.web3jdemo.contract.wrapper.DldContract;

import java.io.IOException;

public abstract class AbstractContractReadonlyOperation {

    protected final ContractActionType contractActionType;

    @Autowired
    protected DefaultContractFactory contractFactory;

    protected DldContract contract;

    public AbstractContractReadonlyOperation(ContractActionType contractActionType) {
        this.contractActionType = contractActionType;
    }

    public abstract Object execute() throws IOException, TransactionException;

    protected final Object execute(RemoteCall remoteCall) throws IOException, TransactionException {
        try {
            Object result;
            result = remoteCall.send();
            return result;
        } catch (Exception e) {
            throw new ContractOperationGeneralException(e);
        }
    }

    public ContractActionType getContractActionType() {
        return contractActionType;
    }

}
