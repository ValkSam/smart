package web3j.example.web3jdemo.contract.operation.user;

import org.web3j.crypto.CipherException;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import web3j.example.web3jdemo.contract.operation.ContractOperation;
import web3j.example.web3jdemo.contract.wrapper.DldContract;
import web3j.example.web3jdemo.domain.AddressType;
import web3j.example.web3jdemo.domain.entity.DldWallet;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigInteger;
import java.util.function.Consumer;

public abstract class ContractUserOperation extends ContractOperation {

    protected final DldWallet dldWallet;
    private final AddressType senderAddressType;

    public ContractUserOperation(String functionName,
                                 DldWallet dldWallet,
                                 AddressType senderAddressType,
                                 BigInteger amount,
                                 String documentUid,
                                 String data,
                                 Consumer<DldContract.TransactionEventResponse> onSuccess,
                                 Consumer<TransactionReceipt> onReject,
                                 Consumer<Exception> onError) {
        super(functionName, dldWallet.getAddressIndex(), amount, documentUid, data, onSuccess, onReject, onError);
        this.dldWallet = dldWallet;
        this.senderAddressType = senderAddressType;
    }

    public ContractUserOperation(String functionName,
                                 DldWallet dldWallet,
                                 AddressType senderAddressType,
                                 BigInteger amount,
                                 String documentUid,
                                 String data) {
        super(functionName, dldWallet.getAddressIndex(), amount, documentUid, data);
        this.dldWallet = dldWallet;
        this.senderAddressType = senderAddressType;
    }

    @PostConstruct
    protected void init() throws IOException, CipherException {
        this.contract = contractFactory.defaultUserContractBuilder(dldWallet, senderAddressType).build();
    }

}
