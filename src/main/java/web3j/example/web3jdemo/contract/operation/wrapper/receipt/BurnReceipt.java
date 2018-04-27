package web3j.example.web3jdemo.contract.operation.wrapper.receipt;

import lombok.Getter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import web3j.example.web3jdemo.contract.operation.exception.ContractException;
import web3j.example.web3jdemo.contract.operation.user.burn.AbstractContractUserBurnOperation;

@Getter
public class BurnReceipt extends AbstractContractReceipt {

    private final AbstractContractUserBurnOperation contractOperation;

    public BurnReceipt(TransactionReceipt receipt, AbstractContractUserBurnOperation operation) {
        super(receipt);
        this.contractOperation = operation;
    }

    public BurnReceipt(ContractException exception, AbstractContractUserBurnOperation operation) {
        super(exception);
        this.contractOperation = operation;
    }
}
