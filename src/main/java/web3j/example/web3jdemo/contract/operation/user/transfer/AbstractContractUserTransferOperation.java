package web3j.example.web3jdemo.contract.operation.user.transfer;

import org.web3j.protocol.core.methods.response.TransactionReceipt;
import web3j.example.web3jdemo.contract.operation.actiontype.ContractUserActionType;
import web3j.example.web3jdemo.contract.operation.exception.ContractException;
import web3j.example.web3jdemo.contract.operation.user.AbstractContractUserOperation;
import web3j.example.web3jdemo.contract.operation.wrapper.event.TransferEvent;
import web3j.example.web3jdemo.contract.operation.wrapper.receipt.TransferReceipt;
import web3j.example.web3jdemo.domain.entity.DldWallet;

import java.math.BigInteger;
import java.util.function.Consumer;

import static java.util.Objects.isNull;

public abstract class AbstractContractUserTransferOperation extends AbstractContractUserOperation {

    protected final BigInteger amount;
    protected final String receiverIndexAddress;

    private Consumer<TransferEvent> onSuccess;
    private Consumer<TransferReceipt> onReject;

    public AbstractContractUserTransferOperation(ContractUserActionType contractUserActionType,
                                                 DldWallet dldWallet,
                                                 String receiverIndexAddress,
                                                 BigInteger amount,
                                                 String data,
                                                 Consumer<TransferEvent> onSuccess,
                                                 Consumer<TransferReceipt> onReject) {
        super(contractUserActionType, dldWallet, data);
        this.amount = amount;
        this.receiverIndexAddress = receiverIndexAddress;
        this.onSuccess = onSuccess;
        this.onReject = onReject;
    }

    public AbstractContractUserTransferOperation(ContractUserActionType contractUserActionType,
                                                 DldWallet dldWallet,
                                                 String receiverIndexAddress,
                                                 BigInteger amount,
                                                 String data) {
        super(contractUserActionType, dldWallet, data);
        this.amount = amount;
        this.receiverIndexAddress = receiverIndexAddress;
    }

    @Override
    public void callOnSuccess(TransactionReceipt receipt) {
        if (!isNull(onSuccess)) {
            onSuccess.accept(new TransferEvent(contract, receipt));
        }
    }

    @Override
    public void callOnReject(TransactionReceipt receipt) {
        if (!isNull(onReject)) {
            onReject.accept(new TransferReceipt(receipt, this));
        }
    }

    @Override
    public void callOnReject(ContractException exception) {
        if (!isNull(onReject)) {
            onReject.accept(new TransferReceipt(exception, this));
        }
    }

}
