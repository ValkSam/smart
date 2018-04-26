package web3j.example.web3jdemo.contract.operation.casino.register;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import web3j.example.web3jdemo.contract.operation.actiontype.ContractCasinoActionType;
import web3j.example.web3jdemo.contract.wrapper.DldContract;
import web3j.example.web3jdemo.domain.entity.Casino;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static web3j.example.web3jdemo.contract.operation.actiontype.ContractCasinoActionType.REGISTER_CASINO;


@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RegisterCasinoOperation extends AbstractContractCasinoRegisterOperation {

    private static final ContractCasinoActionType ACTION_TYPE = REGISTER_CASINO;

    public RegisterCasinoOperation(Casino casino,
                                   String data) {
        super(ACTION_TYPE, casino, data);
    }

    public RegisterCasinoOperation(Casino casino,
                                   String data,
                                   Consumer<DldContract.RegisterEventResponse> onSuccess,
                                   Consumer<TransactionReceipt> onReject,
                                   Consumer<Exception> onError) {
        super(ACTION_TYPE, casino, data, onSuccess, onReject, onError);
    }

    @Override
    public CompletableFuture<TransactionReceipt> execute() throws IOException, TransactionException {
        return execute(contract.registerMeAsCasino(
                casino.getAddress(),
                data));
    }

}
