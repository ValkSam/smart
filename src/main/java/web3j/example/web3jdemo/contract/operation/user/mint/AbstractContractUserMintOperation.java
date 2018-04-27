package web3j.example.web3jdemo.contract.operation.user.mint;

import org.web3j.protocol.core.methods.response.TransactionReceipt;
import web3j.example.web3jdemo.contract.operation.actiontype.ContractUserActionType;
import web3j.example.web3jdemo.contract.operation.wrapper.event.MintEvent;
import web3j.example.web3jdemo.contract.operation.wrapper.receipt.MintReceipt;
import web3j.example.web3jdemo.contract.operation.user.AbstractContractUserOperation;
import web3j.example.web3jdemo.domain.entity.DldWallet;

import java.math.BigInteger;
import java.util.function.Consumer;

import static java.util.Objects.isNull;

public abstract class AbstractContractUserMintOperation extends AbstractContractUserOperation {

    protected final BigInteger amount;
    protected final String documentUID;

    private Consumer<MintEvent> onSuccess;
    private Consumer<MintReceipt> onReject;

    public AbstractContractUserMintOperation(ContractUserActionType contractUserActionType,
                                             DldWallet dldWallet,
                                             BigInteger amount,
                                             String documentUid,
                                             String data,
                                             Consumer<MintEvent> onSuccess,
                                             Consumer<MintReceipt> onReject,
                                             Consumer<Exception> onError) {
        super(contractUserActionType, dldWallet, data, onError);
        this.amount = amount;
        this.documentUID = documentUid;
        this.onSuccess = onSuccess;
        this.onReject = onReject;
    }

    public AbstractContractUserMintOperation(ContractUserActionType contractUserActionType,
                                             DldWallet dldWallet,
                                             BigInteger amount,
                                             String documentUid,
                                             String data) {
        super(contractUserActionType, dldWallet, data);
        this.amount = amount;
        this.documentUID = documentUid;
    }

    @Override
    public void callOnSuccess(TransactionReceipt receipt) {
        if (!isNull(onSuccess)) {
            onSuccess.accept(new MintEvent(contract, receipt));
        }
    }

    @Override
    public void callOnReject(TransactionReceipt receipt) {
        if (!isNull(onReject)) {
            onReject.accept(new MintReceipt(receipt, this));
        }
    }

}
