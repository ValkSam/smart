package web3j.example.web3jdemo.contract.operation.user.mint;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import web3j.example.web3jdemo.contract.wrapper.DldContract;
import web3j.example.web3jdemo.domain.entity.DldWallet;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static web3j.example.web3jdemo.contract.operation.ContractOperation.ContractActionType.ENROLL;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class EnrollTransferOperation extends ContractUserMintOperation {

    private static final ContractActionType ACTION_TYPE = ENROLL;

    public EnrollTransferOperation(DldWallet dldWallet,
                                   BigInteger amount,
                                   String documentUid,
                                   String data) {
        super(ACTION_TYPE, dldWallet, amount, documentUid, data);
    }

    public EnrollTransferOperation(DldWallet dldWallet,
                                   BigInteger amount,
                                   String documentUid,
                                   String data,
                                   Consumer<DldContract.MintEventResponse> onSuccess,
                                   Consumer<TransactionReceipt> onReject,
                                   Consumer<Exception> onError) {
        super(ACTION_TYPE, dldWallet, amount, documentUid, data, onSuccess, onReject, onError);
    }

    @Override
    public CompletableFuture<TransactionReceipt> execute() throws IOException, TransactionException {
        return execute(contract.enroll(
                dldWallet.getAddressIndex(),
                amount,
                documentUID,
                data));
    }

}
