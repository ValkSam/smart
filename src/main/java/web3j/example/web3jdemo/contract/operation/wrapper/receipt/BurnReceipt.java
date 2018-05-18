package web3j.example.web3jdemo.contract.operation.wrapper.receipt;

import lombok.Getter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import web3j.example.web3jdemo.contract.operation.exception.ContractException;
import web3j.example.web3jdemo.contract.operation.user.burn.AbstractContractUserBurnTransactionalOperation;

@Getter
public class BurnReceipt extends AbstractContractReceipt {

    private final AbstractContractUserBurnTransactionalOperation contractOperation;

    public BurnReceipt(TransactionReceipt receipt, AbstractContractUserBurnTransactionalOperation operation) {
        super(receipt);
        this.contractOperation = operation;
    }

    public BurnReceipt(ContractException exception, AbstractContractUserBurnTransactionalOperation operation) {
        super(exception);
        this.contractOperation = operation;
    }
}
