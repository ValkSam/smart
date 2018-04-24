package web3j.example.web3jdemo.contract.operation.user;

import org.web3j.crypto.CipherException;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import web3j.example.web3jdemo.contract.operation.ContractOperation;
import web3j.example.web3jdemo.domain.UserAddressType;
import web3j.example.web3jdemo.domain.entity.DldWallet;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.function.Consumer;

public abstract class ContractUserOperation extends ContractOperation {

    protected final DldWallet dldWallet;
    protected final UserAddressType senderUserAddressType;

    public ContractUserOperation(ContractActionType contractActionType,
                                 DldWallet dldWallet,
                                 UserAddressType senderUserAddressType,
                                 String data,
                                 Consumer<TransactionReceipt> onReject,
                                 Consumer<Exception> onError) {
        super(contractActionType, data, onReject, onError);
        this.dldWallet = dldWallet;
        this.senderUserAddressType = senderUserAddressType;
    }

    public ContractUserOperation(ContractActionType contractActionType,
                                 DldWallet dldWallet,
                                 UserAddressType senderUserAddressType,
                                 String data) {
        super(contractActionType, data);
        this.dldWallet = dldWallet;
        this.senderUserAddressType = senderUserAddressType;
    }

    @PostConstruct
    protected void init() throws IOException, CipherException {
        this.contract = contractFactory.defaultUserContractBuilder(dldWallet, senderUserAddressType).build();
    }

}
