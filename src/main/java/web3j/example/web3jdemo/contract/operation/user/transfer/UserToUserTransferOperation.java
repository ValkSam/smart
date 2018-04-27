package web3j.example.web3jdemo.contract.operation.user.transfer;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import web3j.example.web3jdemo.contract.operation.actiontype.ContractUserActionType;
import web3j.example.web3jdemo.contract.operation.wrapper.event.TransferEvent;
import web3j.example.web3jdemo.contract.operation.wrapper.receipt.TransferReceipt;
import web3j.example.web3jdemo.domain.entity.DldWallet;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static web3j.example.web3jdemo.contract.operation.actiontype.ContractUserActionType.TRANSFER;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserToUserTransferOperation extends AbstractContractUserTransferOperation {

    private static final ContractUserActionType ACTION_TYPE = TRANSFER;

    public UserToUserTransferOperation(DldWallet dldWallet,
                                       String receiverIndexAddress,
                                       BigInteger amount,
                                       String data) {
        super(ACTION_TYPE, dldWallet, receiverIndexAddress, amount, data);
    }

    public UserToUserTransferOperation(DldWallet dldWallet,
                                       String receiverIndexAddress,
                                       BigInteger amount,
                                       String data,
                                       Consumer<TransferEvent> onSuccess,
                                       Consumer<TransferReceipt> onReject,
                                       Consumer<Exception> onError) {
        super(ACTION_TYPE, dldWallet, receiverIndexAddress, amount, data, onSuccess, onReject, onError);
    }

    @Override
    public CompletableFuture<TransactionReceipt> execute() throws IOException, TransactionException {
        return execute(contract.transferCoin(
                dldWallet.getAddressIndex(),
                receiverIndexAddress,
                amount,
                data));
    }

}
