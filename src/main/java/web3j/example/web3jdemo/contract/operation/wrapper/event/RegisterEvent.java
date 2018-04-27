package web3j.example.web3jdemo.contract.operation.wrapper.event;

import lombok.Getter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import web3j.example.web3jdemo.contract.wrapper.DldContract;

@Getter
public class RegisterEvent extends AbstractContractEvent {

    private final DldContract.RegisterEventResponse eventResponse;

    public RegisterEvent(DldContract contract, TransactionReceipt receipt) {
        super(receipt);
        eventResponse = contract.getRegisterEvents(receipt).get(0);
    }

}
