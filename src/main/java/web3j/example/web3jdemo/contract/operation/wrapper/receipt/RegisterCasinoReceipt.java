package web3j.example.web3jdemo.contract.operation.wrapper.receipt;

import lombok.Getter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import web3j.example.web3jdemo.contract.operation.casino.register.AbstractContractCasinoRegisterOperation;
import web3j.example.web3jdemo.contract.operation.exception.ContractException;

@Getter
public class RegisterCasinoReceipt extends AbstractContractReceipt {

    private final AbstractContractCasinoRegisterOperation contractOperation;

    public RegisterCasinoReceipt(TransactionReceipt receipt, AbstractContractCasinoRegisterOperation operation) {
        super(receipt);
        this.contractOperation = operation;
    }

    public RegisterCasinoReceipt(ContractException exception, AbstractContractCasinoRegisterOperation operation) {
        super(exception);
        this.contractOperation = operation;
    }

}
