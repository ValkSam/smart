package web3j.example.web3jdemo.contract.operation;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import web3j.example.web3jdemo.contract.builder.token.ContractFactory;
import web3j.example.web3jdemo.contract.operation.actiontype.ContractActionType;
import web3j.example.web3jdemo.contract.operation.exception.ContractException;
import web3j.example.web3jdemo.contract.operation.exception.ContractGasTooLowException;
import web3j.example.web3jdemo.contract.operation.exception.ContractOperationGeneralException;
import web3j.example.web3jdemo.contract.operation.exception.ContractUnderpricedException;
import web3j.example.web3jdemo.contract.operation.exception.ContractUnrecognizedException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

import static java.lang.Integer.decode;
import static java.util.Objects.isNull;
import static web3j.example.web3jdemo.contract.operation.AbstractContractTransactionalOperation.KnownWeb3jException.GAS_TOO_LOW;
import static web3j.example.web3jdemo.contract.operation.AbstractContractTransactionalOperation.KnownWeb3jException.UNDERPRICED;

public abstract class AbstractContractTransactionalOperation {

    protected final ContractActionType contractActionType;
    protected final String data;

    @Autowired
    protected ContractFactory contractFactory;

    private LocalDateTime startDate = LocalDateTime.now();

    public AbstractContractTransactionalOperation(ContractActionType contractActionType,
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
                System.out.println(contractActionType + " failed " + Thread.currentThread().getName());
                if (isNull(e.getMessage())) {
                    throw new ContractUnrecognizedException(e);
                }
                if (e.getMessage().contains(UNDERPRICED.getExceptionPhrase())) {
                    throw new ContractUnderpricedException(e);
                } else if (e.getMessage().contains(GAS_TOO_LOW.getExceptionPhrase())) {
                    throw new ContractGasTooLowException(e);
                } else {
                    throw new ContractUnrecognizedException(e);
                }
            }
        } catch (Exception e) {
            ContractException exception;
            if (e instanceof ContractException) {
                exception = (ContractException) e;
            } else {
                exception = new ContractOperationGeneralException(e);
            }
            callOnReject(exception);
            throw exception;
        }
    }

    public abstract void callOnSuccess(TransactionReceipt receipt);

    public abstract void callOnReject(TransactionReceipt receipt);

    public abstract void callOnReject(ContractException exception);

    public ContractActionType getContractActionType() {
        return contractActionType;
    }

    public String getData() {
        return data;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    @Getter
    protected enum KnownWeb3jException {
        UNDERPRICED("replacement transaction underpriced"),
        GAS_TOO_LOW("intrinsic gas too low");

        private final String exceptionPhrase;

        KnownWeb3jException(String exceptionPhrase) {
            this.exceptionPhrase = exceptionPhrase;
        }
    }

}
