package web3j.example.web3jdemo.contract.operation.owner.readonly;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.exceptions.TransactionException;
import web3j.example.web3jdemo.contract.operation.actiontype.ContractOwnerActionType;
import web3j.example.web3jdemo.domain.entity.DldWallet;

import java.io.IOException;
import java.math.BigInteger;

import static web3j.example.web3jdemo.contract.operation.actiontype.ContractOwnerActionType.GET_BALANCE_OF;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GetTokenBalanceOfOperation extends AbstractTokenContractReadonlyOperation {

    private static final ContractOwnerActionType ACTION_TYPE = GET_BALANCE_OF;

    private final String userAddress;

    public GetTokenBalanceOfOperation(Credentials senderCredentials, DldWallet userWallet) {
        this(senderCredentials, userWallet.getAddress());
    }

    public GetTokenBalanceOfOperation(Credentials senderCredentials, String userAddress) {
        super(senderCredentials, ACTION_TYPE);
        this.userAddress = userAddress;
    }

    @Override
    public BigInteger execute() throws IOException, TransactionException {
        return (BigInteger) execute(contract.balanceOf(userAddress));
    }

}
