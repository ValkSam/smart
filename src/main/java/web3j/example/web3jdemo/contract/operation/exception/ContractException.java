package web3j.example.web3jdemo.contract.operation.exception;

public abstract class ContractException extends RuntimeException {

    public ContractException(Throwable cause) {
        super(cause);
    }
}
