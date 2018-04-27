package web3j.example.web3jdemo.contract.operation.wrapper.event;

import lombok.Getter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

@Getter
public abstract class AbstractContractEvent {
    private final TransactionReceipt receipt;

    public AbstractContractEvent(TransactionReceipt receipt) {
        this.receipt = receipt;
    }

}
