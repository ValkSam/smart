package web3j.example.web3jdemo.contract.operation.wrapper.event;

import lombok.Getter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import web3j.example.web3jdemo.blockchain.DltAdminContract;

@Getter
public class AdminRegisterEvent extends AbstractContractEvent {

    private final DltAdminContract.RegisterEventResponse eventResponse;

    public AdminRegisterEvent(DltAdminContract contract, TransactionReceipt receipt) {
        super(receipt);
        eventResponse = contract.getRegisterEvents(receipt).get(0);
    }

}
