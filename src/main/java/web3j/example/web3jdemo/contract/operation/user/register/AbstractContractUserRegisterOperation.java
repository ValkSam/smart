package web3j.example.web3jdemo.contract.operation.user.register;

import org.web3j.protocol.core.methods.response.TransactionReceipt;
import web3j.example.web3jdemo.contract.operation.actiontype.ContractUserActionType;
import web3j.example.web3jdemo.contract.operation.user.AbstractContractUserOperation;
import web3j.example.web3jdemo.contract.wrapper.DldContract;
import web3j.example.web3jdemo.domain.entity.DldWallet;

import java.util.function.Consumer;

import static java.util.Objects.isNull;
import static web3j.example.web3jdemo.domain.UserAddressType.INDEX;

public abstract class AbstractContractUserRegisterOperation extends AbstractContractUserOperation {

    private Consumer<DldContract.RegisterEventResponse> onSuccess;

    public AbstractContractUserRegisterOperation(ContractUserActionType contractUserActionType,
                                                 DldWallet dldWallet,
                                                 String data,
                                                 Consumer<DldContract.RegisterEventResponse> onSuccess,
                                                 Consumer<TransactionReceipt> onReject,
                                                 Consumer<Exception> onError) {
        super(contractUserActionType, dldWallet, data, onReject, onError);
        this.onSuccess = onSuccess;
    }

    public AbstractContractUserRegisterOperation(ContractUserActionType contractUserActionType,
                                                 DldWallet dldWallet,
                                                 String data) {
        super(contractUserActionType, dldWallet, data);
    }

    @Override
    public void callOnSuccess(TransactionReceipt receipt) {
        if (!isNull(onSuccess)) {
            onSuccess.accept(contract.getRegisterEvents(receipt).get(0));
        }
    }

}
