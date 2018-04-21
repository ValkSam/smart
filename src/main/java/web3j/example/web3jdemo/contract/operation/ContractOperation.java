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

    protected final String userName;
    protected final BigInteger amount;
    protected final String documentNumber;
    protected final BigInteger documentAmount;

    public ContractOperation(String userName,
                             BigInteger amount,
                             String documentUid,
                             BigInteger documentAmount) {
        this.userName = userName;
        this.amount = amount;
        this.documentNumber = documentUid;
        this.documentAmount = documentAmount;
    }

    @Async
    public abstract CompletableFuture<TransactionReceipt> execute() throws IOException, TransactionException;

}
