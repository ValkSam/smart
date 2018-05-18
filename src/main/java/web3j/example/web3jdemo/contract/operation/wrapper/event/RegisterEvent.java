package web3j.example.web3jdemo.contract.operation.wrapper.event;

import lombok.Getter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import web3j.example.web3jdemo.contract.wrapper.token.DltTokenContract;

@Getter
public class RegisterEvent extends AbstractContractEvent {

    private final DltTokenContract.RegisterEventResponse eventResponse;

    public RegisterEvent(DltTokenContract contract, TransactionReceipt receipt) {
        super(receipt);
        eventResponse = contract.getRegisterEvents(receipt).get(0);
    }

}
