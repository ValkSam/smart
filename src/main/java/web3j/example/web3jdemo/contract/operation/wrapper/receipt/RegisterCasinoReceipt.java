package web3j.example.web3jdemo.contract.operation.wrapper.receipt;

import lombok.Getter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import web3j.example.web3jdemo.contract.operation.casino.register.AbstractContractCasinoRegisterTransactionalOperation;
import web3j.example.web3jdemo.contract.operation.exception.ContractException;

@Getter
public class RegisterCasinoReceipt extends AbstractContractReceipt {

    private final AbstractContractCasinoRegisterTransactionalOperation contractOperation;

    public RegisterCasinoReceipt(TransactionReceipt receipt, AbstractContractCasinoRegisterTransactionalOperation operation) {
        super(receipt);
        this.contractOperation = operation;
    }

    public RegisterCasinoReceipt(ContractException exception, AbstractContractCasinoRegisterTransactionalOperation operation) {
        super(exception);
        this.contractOperation = operation;
    }

}
