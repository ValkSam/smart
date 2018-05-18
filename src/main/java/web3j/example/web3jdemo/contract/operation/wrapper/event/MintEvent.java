package web3j.example.web3jdemo.contract.operation.wrapper.event;

import lombok.Getter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import web3j.example.web3jdemo.contract.wrapper.token.DltTokenContract;

@Getter
public class MintEvent extends AbstractContractEvent {

    private final DltTokenContract.MintEventResponse eventResponse;

    public MintEvent(DltTokenContract contract, TransactionReceipt receipt) {
        super(receipt);
        eventResponse = contract.getMintEvents(receipt).get(0);
    }

}
