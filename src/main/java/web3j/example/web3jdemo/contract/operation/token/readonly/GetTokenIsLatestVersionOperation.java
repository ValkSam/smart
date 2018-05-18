package web3j.example.web3jdemo.contract.operation.token.readonly;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.exceptions.TransactionException;
import web3j.example.web3jdemo.contract.operation.actiontype.TokenContractActionType;

import java.io.IOException;

import static web3j.example.web3jdemo.contract.operation.actiontype.TokenContractActionType.GET_IS_LATEST_VERSION;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GetTokenIsLatestVersionOperation extends AbstractTokenContractReadonlyOperation {

    private static final TokenContractActionType ACTION_TYPE = GET_IS_LATEST_VERSION;

    public GetTokenIsLatestVersionOperation(Credentials senderCredentials) {
        super(senderCredentials, ACTION_TYPE);
    }

    @Override
    public Boolean execute() throws IOException, TransactionException {
        return (Boolean) execute(contract.isLatestVersion());
    }

}
