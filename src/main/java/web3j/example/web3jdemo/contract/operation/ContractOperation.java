package web3j.example.web3jdemo.contract.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import web3j.example.web3jdemo.contract.builder.defaultgas.DefaultContractFactory;
import web3j.example.web3jdemo.contract.wrapper.DldContract;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static java.lang.Integer.decode;
import static java.util.Objects.isNull;

public abstract class ContractOperation {

    private static final Integer MAX_ATTEMPTS_COUNT = 10;
    private static final Integer ATTEMPT_INTERVAL_MILLISECONDS = 1000;
    protected final ContractActionType contractActionType;
    protected final String data;
    @Autowired
    protected DefaultContractFactory contractFactory;
    protected int attemptCount = 1;
    protected DldContract contract;

    private Exception lastException;
    private LocalDateTime startDate = LocalDateTime.now();
    private Consumer<TransactionReceipt> onReject;
    private Consumer<Exception> onError;

    public ContractOperation(ContractActionType contractActionType,
                             String data,
                             Consumer<TransactionReceipt> onReject,
                             Consumer<Exception> onError) {
        this(contractActionType, data);
        this.onReject = onReject;
        this.onError = onError;
    }

    public ContractOperation(ContractActionType contractActionType,
                             String data) {
        this.contractActionType = contractActionType;
        this.data = data;
    }

    @Async("contractOperationExecutor")
    public abstract CompletableFuture<TransactionReceipt> execute() throws IOException, TransactionException;

    protected final CompletableFuture<TransactionReceipt> execute(RemoteCall<TransactionReceipt> remoteCall) throws IOException, TransactionException {
        if (attemptCount > MAX_ATTEMPTS_COUNT) {
            if (!isNull(onError)) {
                onError.accept(lastException);
            }
            throw new RuntimeException(lastException.getMessage());
        }
        TransactionReceipt receipt;
        try {
            System.out.println(contractActionType + " >>>>>>>>>> start: " + Thread.currentThread().getName());
            receipt = remoteCall.send();
            System.out.println(contractActionType + " >>>>>>>>>> end: " + Thread.currentThread().getName());
            if (decode(receipt.getStatus()) == 1) {
                callOnSuccess(receipt);
            }
            if (decode(receipt.getStatus()) == 0 && !isNull(onReject)) {
                onReject.accept(receipt);
            }
            return CompletableFuture.completedFuture(receipt);
        } catch (Exception e) {
            lastException = e;
            if (!isNull(e.getMessage()) && e.getMessage().contains("replacement transaction underpriced")) {
                System.out.println(contractActionType + " failed " + Thread.currentThread().getName());
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

    public abstract void callOnSuccess(TransactionReceipt receipt);

    public static enum ContractActionType {
        REGISTER_CASINO, REGISTER_USER, TRANSFER, ENROLL, BUY, SELL, ENROLL_REQUEST, CANCEL_ENROLL_REQUEST;
    }

}
