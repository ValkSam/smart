package web3j.example.web3jdemo.contract.operation.user.register;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import web3j.example.web3jdemo.contract.operation.actiontype.ContractUserActionType;
import web3j.example.web3jdemo.contract.operation.wrapper.event.RegisterEvent;
import web3j.example.web3jdemo.contract.operation.wrapper.receipt.RegisterUserReceipt;
import web3j.example.web3jdemo.domain.entity.DldWallet;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static web3j.example.web3jdemo.contract.operation.actiontype.ContractUserActionType.REGISTER_USER;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RegisterUserOperation extends AbstractContractUserRegisterOperation {

    private static final ContractUserActionType ACTION_TYPE = REGISTER_USER;

    public RegisterUserOperation(DldWallet dldWallet,
                                 String data) {
        super(ACTION_TYPE, dldWallet, data);
    }

    public RegisterUserOperation(DldWallet dldWallet,
                                 String data,
                                 Consumer<RegisterEvent> onSuccess,
                                 Consumer<RegisterUserReceipt> onReject) {
        super(ACTION_TYPE, dldWallet, data, onSuccess, onReject);
    }

    @Override
    public CompletableFuture<TransactionReceipt> execute() throws IOException, TransactionException {
        return execute(contract.registerMeAsUser(
                dldWallet.getAddressIndex(),
                dldWallet.getAddressActive(),
                dldWallet.getAddressPassive(),
                data));
    }

}
