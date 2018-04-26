package web3j.example.web3jdemo.contract.operation.user.transfer;

import org.web3j.protocol.core.methods.response.TransactionReceipt;
import web3j.example.web3jdemo.contract.operation.actiontype.ContractUserActionType;
import web3j.example.web3jdemo.contract.operation.user.AbstractContractUserOperation;
import web3j.example.web3jdemo.contract.wrapper.DldContract;
import web3j.example.web3jdemo.domain.entity.DldWallet;

import java.math.BigInteger;
import java.util.function.Consumer;

import static java.util.Objects.isNull;

public abstract class AbstractContractUserTransferOperation extends AbstractContractUserOperation {

    protected final BigInteger amount;
    protected final String receiverIndexAddress;

    private Consumer<DldContract.TransferEventResponse> onSuccess;

    public AbstractContractUserTransferOperation(ContractUserActionType contractUserActionType,
                                                 DldWallet dldWallet,
                                                 String receiverIndexAddress,
                                                 BigInteger amount,
                                                 String data,
                                                 Consumer<DldContract.TransferEventResponse> onSuccess,
                                                 Consumer<TransactionReceipt> onReject,
                                                 Consumer<Exception> onError) {
        super(contractUserActionType, dldWallet, data, onReject, onError);
        this.amount = amount;
        this.receiverIndexAddress = receiverIndexAddress;
        this.onSuccess = onSuccess;
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
            onSuccess.accept(contract.getTransferEvents(receipt).get(0));
        }
    }

}
