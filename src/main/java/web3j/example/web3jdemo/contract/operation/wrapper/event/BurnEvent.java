package web3j.example.web3jdemo.contract.operation.wrapper.event;

import lombok.Getter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import web3j.example.web3jdemo.contract.wrapper.token.DltTokenContract;

@Getter
public class BurnEvent extends AbstractContractEvent {

    private final DltTokenContract.BurnEventResponse eventResponse;

    public BurnEvent(DltTokenContract contract, TransactionReceipt receipt) {
        super(receipt);
        eventResponse = contract.getBurnEvents(receipt).get(0);
    }

}
