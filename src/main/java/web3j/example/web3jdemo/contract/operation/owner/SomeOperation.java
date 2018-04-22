package web3j.example.web3jdemo.contract.operation.owner;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SomeOperation extends ContractOwnerOperation {

    private static final String FUNC_NAME = "Some";

    public SomeOperation(String userName,
                         BigInteger amount,
                         String documentUid,
                         String data) {
        super(FUNC_NAME, userName, amount, documentUid, data);
    }

    @Override
    public CompletableFuture<TransactionReceipt> execute() throws IOException, TransactionException {
        return CompletableFuture.supplyAsync(TransactionReceipt::new);
    }

}
