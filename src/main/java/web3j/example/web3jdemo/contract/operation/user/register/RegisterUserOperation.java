package web3j.example.web3jdemo.contract.operation.user.register;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import web3j.example.web3jdemo.contract.wrapper.DldContract;
import web3j.example.web3jdemo.domain.entity.DldWallet;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static web3j.example.web3jdemo.contract.operation.ContractOperation.ContractActionType.REGISTER_USER;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RegisterUserOperation extends ContractUserRegisterOperation {

    private static final ContractActionType ACTION_TYPE = REGISTER_USER;

    public RegisterUserOperation(DldWallet dldWallet,
                                 String data) {
        super(ACTION_TYPE, dldWallet, data);
    }

    public RegisterUserOperation(DldWallet dldWallet,
                                 String data,
                                 Consumer<DldContract.RegisterEventResponse> onSuccess,
                                 Consumer<TransactionReceipt> onReject,
                                 Consumer<Exception> onError) {
        super(ACTION_TYPE, dldWallet, data, onSuccess, onReject, onError);
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
