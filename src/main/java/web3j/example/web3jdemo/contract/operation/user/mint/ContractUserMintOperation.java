package web3j.example.web3jdemo.contract.operation.user.mint;

import org.web3j.protocol.core.methods.response.TransactionReceipt;
import web3j.example.web3jdemo.contract.operation.user.ContractUserOperation;
import web3j.example.web3jdemo.contract.wrapper.DldContract;
import web3j.example.web3jdemo.domain.entity.DldWallet;

import java.math.BigInteger;
import java.util.function.Consumer;

import static java.util.Objects.isNull;
import static web3j.example.web3jdemo.domain.UserAddressType.INDEX;

public abstract class ContractUserMintOperation extends ContractUserOperation {

    protected final BigInteger amount;
    protected final String documentUID;

    private Consumer<DldContract.MintEventResponse> onSuccess;

    public ContractUserMintOperation(ContractActionType contractActionType,
                                     DldWallet dldWallet,
                                     BigInteger amount,
                                     String documentUid,
                                     String data,
                                     Consumer<DldContract.MintEventResponse> onSuccess,
                                     Consumer<TransactionReceipt> onReject,
                                     Consumer<Exception> onError) {
        super(contractActionType, dldWallet, INDEX, data, onReject, onError);
        this.amount = amount;
        this.documentUID = documentUid;
        this.onSuccess = onSuccess;
    }

    public ContractUserMintOperation(ContractActionType contractActionType,
                                     DldWallet dldWallet,
                                     BigInteger amount,
                                     String documentUid,
                                     String data) {
        super(contractActionType, dldWallet, INDEX, data);
        this.amount = amount;
        this.documentUID = documentUid;
    }

    @Override
    public void callOnSuccess(TransactionReceipt receipt) {
        if (!isNull(onSuccess)) {
            onSuccess.accept(contract.getMintEvents(receipt).get(0));
        }
    }

}
