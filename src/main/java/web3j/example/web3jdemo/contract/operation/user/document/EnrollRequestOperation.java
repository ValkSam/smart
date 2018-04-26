package web3j.example.web3jdemo.contract.operation.user.document;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import web3j.example.web3jdemo.contract.operation.actiontype.ContractUserActionType;
import web3j.example.web3jdemo.contract.wrapper.DldContract;
import web3j.example.web3jdemo.domain.entity.DldWallet;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static web3j.example.web3jdemo.contract.operation.actiontype.ContractUserActionType.ENROLL_REQUEST;
import static web3j.example.web3jdemo.domain.UserAddressType.INDEX;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class EnrollRequestOperation extends AbstractContractUserDocumentOperation {

    private static final ContractUserActionType ACTION_TYPE = ENROLL_REQUEST;

    public EnrollRequestOperation(DldWallet dldWallet,
                                  BigInteger amount,
                                  String documentUid,
                                  String data) {
        super(ACTION_TYPE, dldWallet, amount, documentUid, data);
    }

    public EnrollRequestOperation(DldWallet dldWallet,
                                  BigInteger amount,
                                  String documentUid,
                                  String data,
                                  Consumer<DldContract.RegisterDocumentEventResponse> onSuccess,
                                  Consumer<TransactionReceipt> onReject,
                                  Consumer<Exception> onError) {
        super(ACTION_TYPE, dldWallet, amount, documentUid, data, onSuccess, onReject, onError);
    }

    @Override
    public CompletableFuture<TransactionReceipt> execute() throws IOException, TransactionException {
        return execute(contract.registerEnrollRequestDocument(
                dldWallet.getAddressIndex(),
                amount,
                documentUID,
                data));
    }

}
