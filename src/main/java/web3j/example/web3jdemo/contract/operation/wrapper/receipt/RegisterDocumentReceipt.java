package web3j.example.web3jdemo.contract.operation.wrapper.receipt;

import lombok.Getter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import web3j.example.web3jdemo.contract.operation.exception.ContractException;
import web3j.example.web3jdemo.contract.operation.token.transactional.document.AbstractTokenContractRegisterDocumentOperation;

@Getter
public class RegisterDocumentReceipt extends AbstractContractReceipt {

    private final AbstractTokenContractRegisterDocumentOperation contractOperation;

    public RegisterDocumentReceipt(TransactionReceipt receipt, AbstractTokenContractRegisterDocumentOperation operation) {
        super(receipt);
        this.contractOperation = operation;
    }

    public RegisterDocumentReceipt(ContractException exception, AbstractTokenContractRegisterDocumentOperation operation) {
        super(exception);
        this.contractOperation = operation;
    }

}
