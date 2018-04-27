package web3j.example.web3jdemo.contract.operation.wrapper.event;

import lombok.Getter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import web3j.example.web3jdemo.contract.wrapper.DldContract;

@Getter
public class MintEvent extends AbstractContractEvent {

    private final DldContract.MintEventResponse eventResponse;

    public MintEvent(DldContract contract, TransactionReceipt receipt) {
        super(receipt);
        eventResponse = contract.getMintEvents(receipt).get(0);
    }

}
