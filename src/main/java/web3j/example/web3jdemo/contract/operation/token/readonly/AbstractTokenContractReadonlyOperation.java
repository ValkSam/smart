package web3j.example.web3jdemo.contract.operation.token.readonly;

import lombok.Getter;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import web3j.example.web3jdemo.contract.operation.AbstractContractReadonlyOperation;
import web3j.example.web3jdemo.contract.operation.actiontype.TokenContractActionType;
import web3j.example.web3jdemo.contract.wrapper.token.DltTokenContract;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Getter
public abstract class AbstractTokenContractReadonlyOperation extends AbstractContractReadonlyOperation {

    protected DltTokenContract contract;

    protected Credentials senderCredentials;

    public AbstractTokenContractReadonlyOperation(
            Credentials senderCredentials,
            TokenContractActionType tokenContractActionType) {
        super(tokenContractActionType);
        this.senderCredentials = senderCredentials;
    }

    @PostConstruct
    protected void init() throws IOException, CipherException {
        this.contract = contractFactory.tokenContractBuilder(senderCredentials).build();
    }

}
