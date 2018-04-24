package web3j.example.web3jdemo.contract.operation.user.transfer;

import org.web3j.protocol.core.methods.response.TransactionReceipt;
import web3j.example.web3jdemo.contract.operation.user.ContractUserOperation;
import web3j.example.web3jdemo.contract.wrapper.DldContract;
import web3j.example.web3jdemo.domain.UserAddressType;
import web3j.example.web3jdemo.domain.entity.DldWallet;

import java.math.BigInteger;
import java.util.function.Consumer;

import static java.util.Objects.isNull;

public abstract class ContractUserTransferOperation extends ContractUserOperation {

    protected final BigInteger amount;
    protected final String documentUID;

    private Consumer<DldContract.TransferEventResponse> onSuccess;

    public ContractUserTransferOperation(ContractActionType contractActionType,
                                         DldWallet dldWallet,
                                         UserAddressType senderUserAddressType,
                                         BigInteger amount,
                                         String documentUid,
                                         String data,
                                         Consumer<DldContract.TransferEventResponse> onSuccess,
                                         Consumer<TransactionReceipt> onReject,
                                         Consumer<Exception> onError) {
        super(contractActionType, dldWallet, senderUserAddressType, data, onReject, onError);
        this.amount = amount;
        this.documentUID = documentUid;
        this.onSuccess = onSuccess;
    }

    public ContractUserTransferOperation(ContractActionType contractActionType,
                                         DldWallet dldWallet,
                                         UserAddressType senderUserAddressType,
                                         BigInteger amount,
                                         String documentUid,
                                         String data) {
        super(contractActionType, dldWallet, senderUserAddressType, data);
        this.amount = amount;
        this.documentUID = documentUid;
    }

    @Override
    public void callOnSuccess(TransactionReceipt receipt) {
        if (!isNull(onSuccess)) {
            onSuccess.accept(contract.getTransferEvents(receipt).get(0));
        }
    }

}
