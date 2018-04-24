package web3j.example.web3jdemo.contract.operation.owner;

import org.web3j.crypto.CipherException;
import web3j.example.web3jdemo.contract.operation.ContractOperation;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigInteger;

public abstract class ContractOwnerOperation extends ContractOperation {

    public ContractOwnerOperation(ContractActionType contractActionType,
                                  String addressIndex,
                                  BigInteger amount,
                                  String documentUid,
                                  String data) {
        super(contractActionType, data);
    }

    @PostConstruct
    protected void init() throws IOException, CipherException {
        this.contract = contractFactory.defaultOwnerContractBuilder().build();
    }

}
