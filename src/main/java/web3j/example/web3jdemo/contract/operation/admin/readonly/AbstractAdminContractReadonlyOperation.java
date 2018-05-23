package web3j.example.web3jdemo.contract.operation.admin.readonly;

import lombok.Getter;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import web3j.example.web3jdemo.blockchain.DltAdminContract;
import web3j.example.web3jdemo.contract.operation.AbstractContractReadonlyOperation;
import web3j.example.web3jdemo.contract.operation.actiontype.AdminContractActionType;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Getter
public abstract class AbstractAdminContractReadonlyOperation extends AbstractContractReadonlyOperation {

    protected DltAdminContract contract;

    protected Credentials senderCredentials;

    public AbstractAdminContractReadonlyOperation(
            Credentials senderCredentials,
            AdminContractActionType tokenContractActionType) {
        super(tokenContractActionType);
        this.senderCredentials = senderCredentials;
    }

    @PostConstruct
    protected void init() throws IOException, CipherException {
        this.contract = contractFactory.adminContractBuilder(senderCredentials).build();
    }

}
