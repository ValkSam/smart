package web3j.example.web3jdemo.contract.operation.wrapper.receipt;

import lombok.Getter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import web3j.example.web3jdemo.contract.operation.user.mint.AbstractContractUserMintOperation;

@Getter
public class MintReceipt extends AbstractContractReceipt {

    private final AbstractContractUserMintOperation contractOperation;

    public MintReceipt(TransactionReceipt receipt, AbstractContractUserMintOperation operation) {
        super(receipt);
        this.contractOperation = operation;
    }

}
