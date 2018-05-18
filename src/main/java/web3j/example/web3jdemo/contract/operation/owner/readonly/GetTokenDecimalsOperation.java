package web3j.example.web3jdemo.contract.operation.owner.readonly;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.exceptions.TransactionException;
import web3j.example.web3jdemo.contract.operation.actiontype.ContractOwnerActionType;

import java.io.IOException;
import java.math.BigInteger;

import static web3j.example.web3jdemo.contract.operation.actiontype.ContractOwnerActionType.GET_DECIMALS;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GetTokenDecimalsOperation extends AbstractTokenContractReadonlyOperation {

    private static final ContractOwnerActionType ACTION_TYPE = GET_DECIMALS;

    public GetTokenDecimalsOperation(Credentials senderCredentials) {
        super(senderCredentials, ACTION_TYPE);
    }

    @Override
    public BigInteger execute() throws IOException, TransactionException {
        return (BigInteger) execute(contract.decimals());
    }

}
