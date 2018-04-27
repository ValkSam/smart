package web3j.example.web3jdemo.contract.operation.wrapper.event;

import lombok.Getter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import web3j.example.web3jdemo.contract.wrapper.DldContract;

@Getter
public class BurnEvent extends AbstractContractEvent {

    private final DldContract.BurnEventResponse eventResponse;

    public BurnEvent(DldContract contract, TransactionReceipt receipt) {
        super(receipt);
        eventResponse = contract.getBurnEvents(receipt).get(0);
    }

}
