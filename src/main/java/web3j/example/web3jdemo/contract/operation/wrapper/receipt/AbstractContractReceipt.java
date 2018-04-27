package web3j.example.web3jdemo.contract.operation.wrapper.receipt;

import lombok.Getter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

@Getter
public abstract class AbstractContractReceipt {

    private final TransactionReceipt transactionReceipt;

    public AbstractContractReceipt(TransactionReceipt transactionReceipt) {
        this.transactionReceipt = transactionReceipt;
    }

}
