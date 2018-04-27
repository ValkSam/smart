package web3j.example.web3jdemo.contract.operation.user.mint;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import web3j.example.web3jdemo.contract.operation.actiontype.ContractUserActionType;
import web3j.example.web3jdemo.contract.operation.wrapper.event.MintEvent;
import web3j.example.web3jdemo.contract.operation.wrapper.receipt.MintReceipt;
import web3j.example.web3jdemo.domain.entity.DldWallet;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static web3j.example.web3jdemo.contract.operation.actiontype.ContractUserActionType.ENROLL;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class EnrollTransferOperation extends AbstractContractUserMintOperation {

    private static final ContractUserActionType ACTION_TYPE = ENROLL;

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
                                   Consumer<MintEvent> onSuccess,
                                   Consumer<MintReceipt> onReject) {
        super(ACTION_TYPE, dldWallet, amount, documentUid, data, onSuccess, onReject);
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
