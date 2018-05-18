package web3j.example.web3jdemo.contract.operation.owner.readonly;

import lombok.Getter;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import web3j.example.web3jdemo.contract.operation.AbstractContractReadonlyOperation;
import web3j.example.web3jdemo.contract.operation.actiontype.ContractOwnerActionType;
import web3j.example.web3jdemo.contract.wrapper.token.DltTokenContract;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Getter
public abstract class AbstractTokenContractReadonlyOperation extends AbstractContractReadonlyOperation {

    protected DltTokenContract contract;

    protected Credentials senderCredentials;

    public AbstractTokenContractReadonlyOperation(
            Credentials senderCredentials,
            ContractOwnerActionType contractOwnerActionType) {
        super(contractOwnerActionType);
        this.senderCredentials = senderCredentials;
    }

    @PostConstruct
    protected void init() throws IOException, CipherException {
        this.contract = contractFactory.tokenContractBuilder(senderCredentials).build();
    }

}
