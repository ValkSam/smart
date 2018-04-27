package web3j.example.web3jdemo.contract.operation.wrapper.receipt;

import lombok.Getter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import web3j.example.web3jdemo.contract.operation.exception.ContractException;
import web3j.example.web3jdemo.contract.operation.user.transfer.AbstractContractUserTransferOperation;

@Getter
public class TransferReceipt extends AbstractContractReceipt {

    private final AbstractContractUserTransferOperation contractOperation;

    public TransferReceipt(TransactionReceipt receipt, AbstractContractUserTransferOperation operation) {
        super(receipt);
        this.contractOperation = operation;
    }

    public TransferReceipt(ContractException exception, AbstractContractUserTransferOperation operation) {
        super(exception);
        this.contractOperation = operation;
    }

}
