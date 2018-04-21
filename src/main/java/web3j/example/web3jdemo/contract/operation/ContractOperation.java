package web3j.example.web3jdemo.contract.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import web3j.example.web3jdemo.contract.builder.defaultgas.DefaultContractFactory;
import web3j.example.web3jdemo.contract.wrapper.DldContract;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;

public abstract class ContractOperation {

    @Autowired
    protected DefaultContractFactory contractFactory;

    protected DldContract contract;

    protected final String addressIndex;
    protected final BigInteger amount;
    protected final String documentNumber;
    protected final String data;

    public ContractOperation(String addressIndex,
                             BigInteger amount,
                             String documentUid,
                             String data) {
        this.addressIndex = addressIndex;
        this.amount = amount;
        this.documentNumber = documentUid;
        this.data = data;
    }

    @Async
    public abstract CompletableFuture<TransactionReceipt> execute() throws IOException, TransactionException;

}
