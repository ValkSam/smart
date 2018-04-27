package web3j.example.web3jdemo.contract.operation;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import web3j.example.web3jdemo.contract.builder.defaultgas.DefaultContractFactory;
import web3j.example.web3jdemo.contract.operation.actiontype.ContractActionType;
import web3j.example.web3jdemo.contract.operation.exception.ContractException;
import web3j.example.web3jdemo.contract.operation.exception.ContractExecutionInterruptedException;
import web3j.example.web3jdemo.contract.operation.exception.ContractUnderpricedException;
import web3j.example.web3jdemo.contract.operation.exception.ContractUnrecognizedException;
import web3j.example.web3jdemo.contract.wrapper.DldContract;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static java.lang.Integer.decode;
import static java.util.Objects.isNull;
import static web3j.example.web3jdemo.contract.operation.AbstractContractOperation.KnownWeb3jException.UNDERPRICED;

public abstract class AbstractContractOperation {

    @Getter
    protected enum KnownWeb3jException {
        UNDERPRICED("replacement transaction underpriced");
        private final String exceptionPhrase;

        KnownWeb3jException(String exceptionPhrase) {
            this.exceptionPhrase = exceptionPhrase;
        }
    }

    private static final Integer MAX_ATTEMPTS_COUNT = 60;
    private static final Integer ATTEMPT_INTERVAL_MILLISECONDS = 1000;
    protected final ContractActionType contractActionType;
    protected final String data;
    @Autowired
    protected DefaultContractFactory contractFactory;
    protected int attemptCount = 1;
    protected DldContract contract;

    private LocalDateTime startDate = LocalDateTime.now();
    private Consumer<Exception> onError;

    public AbstractContractOperation(ContractActionType contractActionType,
                                     String data,
                                     Consumer<Exception> onError) {
        this(contractActionType, data);
        this.onError = onError;
    }

    public AbstractContractOperation(ContractActionType contractActionType,
                                     String data) {
        this.contractActionType = contractActionType;
        this.data = data;
    }

    @Async("contractOperationExecutor")
    public abstract CompletableFuture<TransactionReceipt> execute() throws IOException, TransactionException;

    protected final CompletableFuture<TransactionReceipt> execute(RemoteCall<TransactionReceipt> remoteCall) throws IOException, TransactionException {
        try {
            TransactionReceipt receipt;
            try {
                System.out.println(contractActionType + " >>>>>>>>>> start: " + Thread.currentThread().getName());
                receipt = remoteCall.send();
                if (decode(receipt.getStatus()) == 1) {
                    callOnSuccess(receipt);
                }
                if (decode(receipt.getStatus()) == 0) {
                    callOnReject(receipt);
                }
                System.out.println(contractActionType + " >>>>>>>>>> end: " + Thread.currentThread().getName());
                return CompletableFuture.completedFuture(receipt);
            } catch (Exception e) {
                if (!isNull(e.getMessage()) && e.getMessage().contains(UNDERPRICED.getExceptionPhrase())) {
                    System.out.println(contractActionType + " failed " + Thread.currentThread().getName());
                    try {
                        Thread.sleep(ATTEMPT_INTERVAL_MILLISECONDS);
                    } catch (InterruptedException e1) {
                        throw new ContractExecutionInterruptedException(contractActionType, e1);
                    }
                    if (attemptCount == MAX_ATTEMPTS_COUNT) {
                        throw new ContractUnderpricedException(contractActionType, e);
                    }
                    attemptCount++;
                } else {
                    throw new ContractUnrecognizedException(contractActionType, e);
                }
                return this.execute(remoteCall);
            }
        } catch (Exception e) {
            if ((!(e.getCause() instanceof ContractException))
                    && (!(e.getCause() instanceof ContractException))) {
                e.printStackTrace();
            }
            if (!isNull(onError)) {
                onError.accept(e);
            }
            throw e;
        }
    }

    public abstract void callOnSuccess(TransactionReceipt receipt);

    public abstract void callOnReject(TransactionReceipt receipt);

    public ContractActionType getContractActionType() {
        return contractActionType;
    }

    public String getData() {
        return data;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }
}
