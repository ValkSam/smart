package web3j.example.web3jdemo.contract.operation.casino;

import lombok.Getter;
import org.web3j.crypto.CipherException;
import web3j.example.web3jdemo.contract.operation.AbstractContractTransactionalOperation;
import web3j.example.web3jdemo.contract.operation.actiontype.ContractCasinoActionType;
import web3j.example.web3jdemo.domain.entity.Casino;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Getter
public abstract class AbstractContractCasinoTransactionalOperation extends AbstractContractTransactionalOperation {

    protected final Casino casino;

    public AbstractContractCasinoTransactionalOperation(ContractCasinoActionType contractActionType,
                                                        Casino casino,
                                                        String data) {
        super(contractActionType, data);
        this.casino = casino;
    }

    @PostConstruct
    protected void init() throws IOException, CipherException {
//        this.contract = contractFactory.defaultCasinoContractBuilder(casino).build();
    }

}
