package web3j.example.web3jdemo.contract.operation.token.transactional;

import lombok.Getter;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import web3j.example.web3jdemo.contract.operation.AbstractContractTransactionalOperation;
import web3j.example.web3jdemo.contract.operation.actiontype.ContractUserActionType;
import web3j.example.web3jdemo.contract.operation.actiontype.TokenContractActionType;
import web3j.example.web3jdemo.contract.wrapper.token.DltTokenContract;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Getter
public abstract class AbstractTokenContractTransactionalOperation extends AbstractContractTransactionalOperation {

    protected DltTokenContract contract;

    protected final Credentials senderCredentials;

    public AbstractTokenContractTransactionalOperation(
            Credentials senderCredentials,
            TokenContractActionType tokenContractActionType,
            String data) {
        super(tokenContractActionType, data);
        this.senderCredentials = senderCredentials;
    }

    @PostConstruct
    protected void init() throws IOException, CipherException {
        this.contract = contractFactory.tokenContractBuilder(senderCredentials).build();
    }

}
