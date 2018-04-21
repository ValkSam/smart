package web3j.example.web3jdemo.contract.operation.owner;

import org.springframework.beans.factory.annotation.Autowired;
import org.web3j.crypto.CipherException;
import web3j.example.web3jdemo.blockchain.utils.CredentialsHelper;
import web3j.example.web3jdemo.contract.operation.ContractOperation;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigInteger;

public abstract class ContractOwnerOperation extends ContractOperation {

    public ContractOwnerOperation(String userName,
                                  BigInteger amount,
                                  String documentUid,
                                  BigInteger documentAmount) {
        super(userName, amount, documentUid, documentAmount);
    }

    @PostConstruct
    protected void init() throws IOException, CipherException {
        this.contract = contractFactory.defaultOwnerContractBuilder().build();
    }

}
