package web3j.example.web3jdemo.contract.operation.user;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import web3j.example.web3jdemo.domain.entity.DldWallet;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;

import static web3j.example.web3jdemo.domain.AddressType.INDEX;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class EnrollRequestOperation extends ContractUserOperation {

    public EnrollRequestOperation(DldWallet dldWallet,
                                  BigInteger amount,
                                  String documentUid,
                                  String data) {
        super(dldWallet, INDEX, amount, documentUid, data);
    }

    @Override
    public CompletableFuture<TransactionReceipt> execute() throws IOException, TransactionException {
//        return CompletableFuture.supplyAsync(TransactionReceipt::new);
        return contract.enrollRequest(
                addressIndex,
                amount,
                documentNumber,
                data).sendAsync();
    }

}
