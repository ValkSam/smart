package web3j.example.web3jdemo.contract.operation.wrapper.receipt;

import lombok.Getter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import web3j.example.web3jdemo.contract.operation.admin.transactional.register.AbstractAdminContractRegisterOperation;
import web3j.example.web3jdemo.contract.operation.exception.ContractException;

@Getter
public class AdminRegisterReceipt extends AbstractContractReceipt {

    private final AbstractAdminContractRegisterOperation contractOperation;

    public AdminRegisterReceipt(TransactionReceipt receipt, AbstractAdminContractRegisterOperation operation) {
        super(receipt);
        this.contractOperation = operation;
    }

    public AdminRegisterReceipt(ContractException exception, AbstractAdminContractRegisterOperation operation) {
        super(exception);
        this.contractOperation = operation;
    }

}
