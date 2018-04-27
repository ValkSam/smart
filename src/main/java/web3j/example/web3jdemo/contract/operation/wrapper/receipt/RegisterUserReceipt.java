package web3j.example.web3jdemo.contract.operation.wrapper.receipt;

import lombok.Getter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import web3j.example.web3jdemo.contract.operation.user.register.AbstractContractUserRegisterOperation;

@Getter
public class RegisterUserReceipt extends AbstractContractReceipt {

    private final AbstractContractUserRegisterOperation contractOperation;

    public RegisterUserReceipt(TransactionReceipt receipt, AbstractContractUserRegisterOperation operation) {
        super(receipt);
        this.contractOperation = operation;
    }

}
