package web3j.example.web3jdemo.contract.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import web3j.example.web3jdemo.contract.builder.defaultgas.DefaultContractFactory;
import web3j.example.web3jdemo.contract.wrapper.DldContract;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;

public abstract class ContractOperation {

    private static final Integer MAX_ATTEMPTS_COUNT = 10;
    private static final Integer ATTEMPT_INTERVAL_MILLISECONDS = 300;
    protected final String functionName;
    protected final String addressIndex;
    protected final BigInteger amount;
    protected final String documentUID;
    protected final String data;
    @Autowired
    protected DefaultContractFactory contractFactory;
    protected int attemptCount = 0;
    protected DldContract contract;

    private Exception lastException;

    public ContractOperation(String functionName,
                             String addressIndex,
                             BigInteger amount,
                             String documentUid,
                             String data) {
        this.functionName = functionName;
        this.addressIndex = addressIndex;
        this.amount = amount;
        this.documentUID = documentUid;
        this.data = data;
    }

    @Async("contractOperationExecutor")
    public abstract CompletableFuture<TransactionReceipt> execute() throws IOException, TransactionException;

    protected final CompletableFuture<TransactionReceipt> execute(RemoteCall<TransactionReceipt> remoteCall) throws IOException, TransactionException {
        if (attemptCount > MAX_ATTEMPTS_COUNT) {
            String message = functionName + " >>>>>>>>> I have not been able to execute !" + Thread.currentThread().getName() + " : " + lastException.getMessage();
            System.out.println(message);
            throw new RuntimeException(message);
        }
        TransactionReceipt receipt;
        try {
            System.out.println(functionName + " >>>>>>>>>> start: " + Thread.currentThread().getName());
            receipt = remoteCall.send();
            System.out.println(functionName + " >>>>>>>>>> end: " + Thread.currentThread().getName());
            return CompletableFuture.completedFuture(receipt);
        } catch (Exception e) {
            System.out.println(functionName + " failed " + Thread.currentThread().getName());
            lastException = e;
            try {
                Thread.sleep(ATTEMPT_INTERVAL_MILLISECONDS);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            attemptCount++;
            return this.execute(remoteCall);
        }
    }

}
