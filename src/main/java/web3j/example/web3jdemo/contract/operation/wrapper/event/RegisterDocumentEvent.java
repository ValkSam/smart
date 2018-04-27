package web3j.example.web3jdemo.contract.operation.wrapper.event;

import lombok.Getter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import web3j.example.web3jdemo.contract.wrapper.DldContract;

@Getter
public class RegisterDocumentEvent extends AbstractContractEvent {

    private final DldContract.RegisterDocumentEventResponse eventResponse;

    public RegisterDocumentEvent(DldContract contract, TransactionReceipt receipt) {
        super(receipt);
        eventResponse = contract.getRegisterDocumentEvents(receipt).get(0);
    }

}
