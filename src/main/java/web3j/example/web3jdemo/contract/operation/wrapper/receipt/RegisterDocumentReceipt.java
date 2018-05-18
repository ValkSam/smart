package web3j.example.web3jdemo.contract.operation.wrapper.receipt;

import lombok.Getter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import web3j.example.web3jdemo.contract.operation.exception.ContractException;
import web3j.example.web3jdemo.contract.operation.owner.transactional.document.AbstractTokenContractUserDocumentOperation;

@Getter
public class RegisterDocumentReceipt extends AbstractContractReceipt {

    private final AbstractTokenContractUserDocumentOperation contractOperation;

    public RegisterDocumentReceipt(TransactionReceipt receipt, AbstractTokenContractUserDocumentOperation operation) {
        super(receipt);
        this.contractOperation = operation;
    }

    public RegisterDocumentReceipt(ContractException exception, AbstractTokenContractUserDocumentOperation operation) {
        super(exception);
        this.contractOperation = operation;
    }

}
