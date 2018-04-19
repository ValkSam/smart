package web3j.example.web3jdemo.contract.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import web3j.example.web3jdemo.contract.builder.ContractFactory;
import web3j.example.web3jdemo.contract.wrapper.DldContract;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;

import static java.util.Objects.isNull;

public abstract class ContractOperation {

    @Autowired
    ContractFactory contractFactory;

    protected DldContract contract;

    protected Credentials credentials;
    protected final String userName;
    protected final BigInteger amount;
    protected final String documentNumber;
    protected final BigInteger documentAmount;

    public ContractOperation(Credentials credentials,
                             String userName,
                             BigInteger amount,
                             String documentUid,
                             BigInteger documentAmount) {
        this.credentials = credentials;
        this.userName = userName;
        this.amount = amount;
        this.documentNumber = documentUid;
        this.documentAmount = documentAmount;
    }

    @PostConstruct
    protected void init() throws IOException, CipherException {
        if (isNull(credentials)) return;
        this.contract = contractFactory.defaultContractBuilder(credentials).build();
    }

    @Async
    public abstract CompletableFuture<TransactionReceipt> execute() throws IOException, TransactionException;

}
