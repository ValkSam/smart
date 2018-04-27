package web3j.example.web3jdemo.contract.operation.wrapper.receipt;

import lombok.Getter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import web3j.example.web3jdemo.contract.operation.exception.ContractException;
import web3j.example.web3jdemo.contract.operation.user.document.AbstractContractUserDocumentOperation;

@Getter
public class RegisterDocumentReceipt extends AbstractContractReceipt {

    private final AbstractContractUserDocumentOperation contractOperation;

    public RegisterDocumentReceipt(TransactionReceipt receipt, AbstractContractUserDocumentOperation operation) {
        super(receipt);
        this.contractOperation = operation;
    }

    public RegisterDocumentReceipt(ContractException exception, AbstractContractUserDocumentOperation operation) {
        super(exception);
        this.contractOperation = operation;
    }

}
