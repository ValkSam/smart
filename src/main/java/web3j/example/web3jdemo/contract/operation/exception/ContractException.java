package web3j.example.web3jdemo.contract.operation.exception;

import web3j.example.web3jdemo.contract.operation.actiontype.ContractActionType;

public abstract class ContractException extends RuntimeException {

    public ContractException(ContractActionType contractActionType, Throwable cause) {
        super(contractActionType.getActionTypeName(), cause);
    }
}
