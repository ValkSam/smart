package web3j.example.web3jdemo.contract.operation.admin.transactional;

import lombok.Getter;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import web3j.example.web3jdemo.blockchain.DltAdminContract;
import web3j.example.web3jdemo.contract.operation.AbstractContractTransactionalOperation;
import web3j.example.web3jdemo.contract.operation.actiontype.AdminContractActionType;
import web3j.example.web3jdemo.contract.operation.actiontype.TokenContractActionType;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Getter
public abstract class AbstractAdminContractTransactionalOperation extends AbstractContractTransactionalOperation {

    protected DltAdminContract contract;

    protected final Credentials senderCredentials;

    public AbstractAdminContractTransactionalOperation(
            Credentials senderCredentials,
            AdminContractActionType adminContractActionType,
            String data) {
        super(adminContractActionType, data);
        this.senderCredentials = senderCredentials;
    }

    @PostConstruct
    protected void init() throws IOException, CipherException {
        this.contract = contractFactory.adminContractBuilder(senderCredentials).build();
    }

}
