package web3j.example.web3jdemo.contract.operation.wrapper.receipt;

import lombok.Getter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import web3j.example.web3jdemo.contract.operation.exception.ContractException;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

@Getter
public abstract class AbstractContractReceipt {

    private final Optional<TransactionReceipt> transactionReceipt;
    private Optional<ContractException> exception;

    public AbstractContractReceipt(TransactionReceipt transactionReceipt) {
        this.transactionReceipt = of(transactionReceipt);
        this.exception = empty();
    }

    public AbstractContractReceipt(ContractException exception) {
        this.transactionReceipt = empty();
        this.exception = of(exception);
    }
}
