package web3j.example.web3jdemo.contract.operation.wrapper.event;

import lombok.Getter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import web3j.example.web3jdemo.contract.wrapper.token.DltTokenContract;

@Getter
public class TransferEvent extends AbstractContractEvent {

    private final DltTokenContract.TransferEventResponse eventResponse;

    public TransferEvent(DltTokenContract contract, TransactionReceipt receipt) {
        super(receipt);
        eventResponse = contract.getTransferEvents(receipt).get(0);
    }

}
