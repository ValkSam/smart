package web3j.example.web3jdemo.contract.operation.user.document;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import web3j.example.web3jdemo.contract.operation.actiontype.ContractUserActionType;
import web3j.example.web3jdemo.contract.operation.wrapper.event.RegisterDocumentEvent;
import web3j.example.web3jdemo.contract.operation.wrapper.receipt.RegisterDocumentReceipt;
import web3j.example.web3jdemo.domain.entity.DldWallet;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static web3j.example.web3jdemo.contract.operation.actiontype.ContractUserActionType.WITHDRAW_REQUEST;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WithdrawRequestOperation extends AbstractContractUserDocumentOperation {

    //TODO try to bind ACTION_TYPE with *EventResponse
    private static final ContractUserActionType ACTION_TYPE = WITHDRAW_REQUEST;

    public WithdrawRequestOperation(DldWallet dldWallet,
                                    BigInteger amount,
                                    String documentUid,
                                    String data) {
        super(ACTION_TYPE, dldWallet, amount, documentUid, data);
    }

    public WithdrawRequestOperation(DldWallet dldWallet,
                                    BigInteger amount,
                                    String documentUid,
                                    String data,
                                    Consumer<RegisterDocumentEvent> onSuccess,
                                    Consumer<RegisterDocumentReceipt> onReject,
                                    Consumer<Exception> onError) {
        super(ACTION_TYPE, dldWallet, amount, documentUid, data, onSuccess, onReject, onError);
    }

    @Override
    public CompletableFuture<TransactionReceipt> execute() throws IOException, TransactionException {
        return execute(contract.registerWithdrawRequestDocument(
                dldWallet.getAddressIndex(),
                amount,
                documentUID,
                data));
    }

}
