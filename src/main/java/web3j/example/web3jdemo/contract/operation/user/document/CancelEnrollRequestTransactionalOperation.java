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

import static web3j.example.web3jdemo.contract.operation.actiontype.ContractUserActionType.CANCEL_ENROLL_REQUEST;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CancelEnrollRequestTransactionalOperation extends AbstractContractUserDocumentTransactionalOperation {

    private static final ContractUserActionType ACTION_TYPE = CANCEL_ENROLL_REQUEST;

    public CancelEnrollRequestTransactionalOperation(DldWallet dldWallet,
                                                     BigInteger amount,
                                                     String documentUid,
                                                     String data) {
        super(ACTION_TYPE, dldWallet, amount, documentUid, data);
    }

    public CancelEnrollRequestTransactionalOperation(DldWallet dldWallet,
                                                     BigInteger amount,
                                                     String documentUid,
                                                     String data,
                                                     Consumer<RegisterDocumentEvent> onSuccess,
                                                     Consumer<RegisterDocumentReceipt> onReject) {
        super(ACTION_TYPE, dldWallet, amount, documentUid, data, onSuccess, onReject);
    }

    @Override
    public CompletableFuture<TransactionReceipt> execute() throws IOException, TransactionException {
        /*return execute(contract.cancelEnrollRequestDocument(
                dldWallet.getAddress(),
                amount,
                documentUID,
                data));*/

        return null;
    }

}
