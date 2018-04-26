package web3j.example.web3jdemo.contract.operation.user;

import org.web3j.crypto.CipherException;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import web3j.example.web3jdemo.contract.operation.AbstractContractOperation;
import web3j.example.web3jdemo.contract.operation.actiontype.ContractUserActionType;
import web3j.example.web3jdemo.domain.UserAddressType;
import web3j.example.web3jdemo.domain.entity.DldWallet;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.function.Consumer;

public abstract class AbstractContractUserOperation extends AbstractContractOperation {

    protected final DldWallet dldWallet;
    protected final UserAddressType senderUserAddressType;

    public AbstractContractUserOperation(ContractUserActionType contractUserActionType,
                                         DldWallet dldWallet,
                                         String data,
                                         Consumer<TransactionReceipt> onReject,
                                         Consumer<Exception> onError) {
        super(contractUserActionType, data, onReject, onError);
        this.dldWallet = dldWallet;
        this.senderUserAddressType = contractUserActionType.getUserAddressType();
    }

    public AbstractContractUserOperation(ContractUserActionType contractUserActionType,
                                         DldWallet dldWallet,
                                         String data) {
        super(contractUserActionType, data);
        this.dldWallet = dldWallet;
        this.senderUserAddressType = contractUserActionType.getUserAddressType();
    }

    @PostConstruct
    protected void init() throws IOException, CipherException {
        this.contract = contractFactory.defaultUserContractBuilder(dldWallet, senderUserAddressType).build();
    }

}
