package web3j.example.web3jdemo.contract.operation.admin.readonly;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.exceptions.TransactionException;
import web3j.example.web3jdemo.contract.operation.actiontype.AdminContractActionType;
import web3j.example.web3jdemo.domain.entity.DldWallet;

import java.io.IOException;

import static web3j.example.web3jdemo.contract.operation.actiontype.AdminContractActionType.GET_IS_ADMIN;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GetIsAdminOperation extends AbstractAdminContractReadonlyOperation {

    private static final AdminContractActionType ACTION_TYPE = GET_IS_ADMIN;

    private final String addressToCheck;

    public GetIsAdminOperation(Credentials senderCredentials, DldWallet walletToCheck) {
        this(senderCredentials, walletToCheck.getAddress());
    }

    public GetIsAdminOperation(Credentials senderCredentials, String addressToCheck) {
        super(senderCredentials, ACTION_TYPE);
        this.addressToCheck = addressToCheck;
    }

    @Override
    public Boolean execute() throws IOException, TransactionException {
        return (Boolean) execute(contract.isAdmin(addressToCheck));
    }

}
