package web3j.example.web3jdemo.contract.operation.token.readonly;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.exceptions.TransactionException;
import web3j.example.web3jdemo.contract.operation.actiontype.TokenContractActionType;

import java.io.IOException;
import java.math.BigInteger;

import static web3j.example.web3jdemo.contract.operation.actiontype.TokenContractActionType.GET_CHECK_CONTRACT_TYPE;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GetTokenCheckContractTypeOperation extends AbstractTokenContractReadonlyOperation {

    private static final TokenContractActionType ACTION_TYPE = GET_CHECK_CONTRACT_TYPE;

    private static final BigInteger TOKEN_CONTRACT_TYPE_CODE = BigInteger.valueOf(4);

    public GetTokenCheckContractTypeOperation(Credentials senderCredentials) {
        super(senderCredentials, ACTION_TYPE);
    }

    @Override
    public Boolean execute() throws IOException, TransactionException {
        return (Boolean) execute(contract.checkContractType(TOKEN_CONTRACT_TYPE_CODE));
    }

}
