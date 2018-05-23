package web3j.example.web3jdemo.contract.operation.admin.transactional.register;

import lombok.Getter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import web3j.example.web3jdemo.contract.operation.actiontype.AdminContractActionType;
import web3j.example.web3jdemo.contract.operation.wrapper.event.AdminRegisterEvent;
import web3j.example.web3jdemo.contract.operation.wrapper.receipt.AdminRegisterReceipt;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static web3j.example.web3jdemo.contract.operation.actiontype.AdminContractActionType.REGISTER_ADMIN;

@Component
@Getter
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AdminRegisterOperation extends AbstractAdminContractRegisterOperation {

    private static final AdminContractActionType ACTION_TYPE = REGISTER_ADMIN;

    private final String addressToBeRegistered;

    public AdminRegisterOperation(Credentials senderCredentials,
                                  String addressToBeRegistered,
                                  String data) {
        super(senderCredentials, ACTION_TYPE, data);
        this.addressToBeRegistered = addressToBeRegistered;
    }

    public AdminRegisterOperation(Credentials senderCredentials,
                                  String addressToBeRegistered,
                                  String data,
                                  Consumer<AdminRegisterEvent> onSuccess,
                                  Consumer<AdminRegisterReceipt> onReject) {
        super(senderCredentials, ACTION_TYPE, data, onSuccess, onReject);
        this.addressToBeRegistered = addressToBeRegistered;
    }

    @Override
    public CompletableFuture<TransactionReceipt> execute() throws IOException, TransactionException {
        return execute(contract.register(
                addressToBeRegistered,
                data));
    }

}
