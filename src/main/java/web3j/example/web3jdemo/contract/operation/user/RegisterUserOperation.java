package web3j.example.web3jdemo.contract.operation.user;

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

import static java.math.BigInteger.ZERO;
import static web3j.example.web3jdemo.domain.AddressType.INDEX;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RegisterUserOperation extends ContractUserOperation {

    private static final String FUNC_NAME = "REGISTER_USER";

    public RegisterUserOperation(DldWallet dldWallet,
                                 String documentUid,
                                 String data) {
        super(FUNC_NAME, dldWallet, INDEX, ZERO, documentUid, data);
    }

    public RegisterUserOperation(DldWallet dldWallet,
                                 String documentUid,
                                 String data,
                                 Consumer<DldContract.TransactionEventResponse> onSuccess,
                                 Consumer<TransactionReceipt> onReject,
                                 Consumer<Exception> onError) {
        super(FUNC_NAME, dldWallet, INDEX, ZERO, documentUid, data, onSuccess, onReject, onError);
    }

    @Override
    public CompletableFuture<TransactionReceipt> execute() throws IOException, TransactionException {
        return execute(contract.registerMeAsUser(
                addressIndex,
                dldWallet.getAddressActive(),
                dldWallet.getAddressPassive(),
                documentUID,
                data));
    }

}
