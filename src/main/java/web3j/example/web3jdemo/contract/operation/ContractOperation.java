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
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static java.lang.Integer.decode;
import static java.util.Objects.isNull;

public abstract class ContractOperation {

    private static final Integer MAX_ATTEMPTS_COUNT = 1;
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
    Consumer<Exception> onError;
    private Exception lastException;
    private LocalDateTime startDate = LocalDateTime.now();
    private Consumer<DldContract.TransactionEventResponse> onSuccess;
    private Consumer<TransactionReceipt> onReject;

    public ContractOperation(String functionName,
                             String addressIndex,
                             BigInteger amount,
                             String documentUid,
                             String data,
                             Consumer<DldContract.TransactionEventResponse> onSuccess,
                             Consumer<TransactionReceipt> onReject,
                             Consumer<Exception> onError) {
        this(functionName, addressIndex, amount, documentUid, data);
        this.onSuccess = onSuccess;
        this.onReject = onReject;
        this.onError = onError;
    }

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
            if (!isNull(onError)) {
                onError.accept(lastException);
            }
            throw new RuntimeException("");
        }
        TransactionReceipt receipt;
        try {
            System.out.println(functionName + " >>>>>>>>>> start: " + Thread.currentThread().getName());
            receipt = remoteCall.send();
            System.out.println(functionName + " >>>>>>>>>> end: " + Thread.currentThread().getName());
            if (decode(receipt.getStatus()) == 1 && !isNull(onSuccess)) {
                onSuccess.accept(contract.getTransactionEvents(receipt).get(0));
            }
            if (decode(receipt.getStatus()) == 0 && !isNull(onReject)) {
                onReject.accept(receipt);
            }
            return CompletableFuture.completedFuture(receipt);
        } catch (Exception e) {
            lastException = e;
            if (!isNull(e.getMessage()) && e.getMessage().contains("replacement transaction underpriced")) {
                System.out.println(functionName + " failed " + Thread.currentThread().getName());
                try {
                    Thread.sleep(ATTEMPT_INTERVAL_MILLISECONDS);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                attemptCount++;
            } else {
                attemptCount = MAX_ATTEMPTS_COUNT + 1;
            }
            return this.execute(remoteCall);

        }
    }

}
