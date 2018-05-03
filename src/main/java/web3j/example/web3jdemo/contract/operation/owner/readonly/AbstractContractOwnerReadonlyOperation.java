package web3j.example.web3jdemo.contract.operation.owner.readonly;

import lombok.Getter;
import org.web3j.crypto.CipherException;
import web3j.example.web3jdemo.contract.operation.AbstractContractReadonlyOperation;
import web3j.example.web3jdemo.contract.operation.actiontype.ContractOwnerActionType;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Getter
public abstract class AbstractContractOwnerReadonlyOperation extends AbstractContractReadonlyOperation {

    public AbstractContractOwnerReadonlyOperation(ContractOwnerActionType contractUserActionType) {
        super(contractUserActionType);
    }

    @PostConstruct
    protected void init() throws IOException, CipherException {
        this.contract = contractFactory.defaultOwnerContractBuilder().build();
    }

}
