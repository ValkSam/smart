package web3j.example.web3jdemo.contract.operation.exception;

import web3j.example.web3jdemo.contract.operation.actiontype.ContractActionType;

public class ContractUnrecognizedException extends ContractException {

    public ContractUnrecognizedException(ContractActionType contractActionType, Throwable cause) {
        super(contractActionType, cause);
    }
}
