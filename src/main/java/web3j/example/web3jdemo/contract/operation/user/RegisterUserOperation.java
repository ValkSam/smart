package web3j.example.web3jdemo.contract.operation.user;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import web3j.example.web3jdemo.domain.entity.DldWallet;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import static java.math.BigInteger.ZERO;
import static web3j.example.web3jdemo.domain.AddressType.INDEX;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RegisterUserOperation extends ContractUserOperation {

    public RegisterUserOperation(DldWallet dldWallet,
                                 String documentUid,
                                 String data) {
        super(dldWallet, INDEX, ZERO, documentUid, data);
    }

    @Override
    public CompletableFuture<TransactionReceipt> execute() throws IOException, TransactionException {
//        return CompletableFuture.supplyAsync(TransactionReceipt::new);
        return contract.registerMeAsUser(
                addressIndex,
                dldWallet.getAddressActive(),
                dldWallet.getAddressPassive(),
                documentNumber,
                data).sendAsync();
    }

}
