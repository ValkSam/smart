package web3j.example.web3jdemo.contract.operation.wrapper.receipt;

import lombok.Getter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import web3j.example.web3jdemo.contract.operation.exception.ContractException;
import web3j.example.web3jdemo.contract.operation.user.mint.AbstractContractUserMintTransactionalOperation;

@Getter
public class MintReceipt extends AbstractContractReceipt {

    private final AbstractContractUserMintTransactionalOperation contractOperation;

    public MintReceipt(TransactionReceipt receipt, AbstractContractUserMintTransactionalOperation operation) {
        super(receipt);
        this.contractOperation = operation;
    }

    public MintReceipt(ContractException exception, AbstractContractUserMintTransactionalOperation operation) {
        super(exception);
        this.contractOperation = operation;
    }

}
