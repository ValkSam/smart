package web3j.example.web3jdemo.contract.operation.wrapper.receipt;

import lombok.Getter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import web3j.example.web3jdemo.contract.operation.exception.ContractException;
import web3j.example.web3jdemo.contract.operation.user.register.AbstractContractUserRegisterTransactionalOperation;

@Getter
public class RegisterUserReceipt extends AbstractContractReceipt {

    private final AbstractContractUserRegisterTransactionalOperation contractOperation;

    public RegisterUserReceipt(TransactionReceipt receipt, AbstractContractUserRegisterTransactionalOperation operation) {
        super(receipt);
        this.contractOperation = operation;
    }

    public RegisterUserReceipt(ContractException exception, AbstractContractUserRegisterTransactionalOperation operation) {
        super(exception);
        this.contractOperation = operation;
    }

}
