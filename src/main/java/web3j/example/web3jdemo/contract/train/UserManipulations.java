package web3j.example.web3jdemo.contract.train;

import lombok.Getter;
import lombok.Setter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import web3j.example.web3jdemo.contract.operation.AbstractContractOperation;
import web3j.example.web3jdemo.contract.operation.ContractOperationFactory;
import web3j.example.web3jdemo.contract.operation.wrapper.event.RegisterEvent;
import web3j.example.web3jdemo.contract.operation.wrapper.receipt.RegisterUserReceipt;
import web3j.example.web3jdemo.service.DldWalletService;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

import static java.util.Objects.isNull;

@Getter
@Setter
public class UserManipulations {

    private final Map<Integer, Integer> blocksStatistics = new ConcurrentHashMap<>();
    private final DldWalletService dldWalletService;
    private final ContractOperationFactory contractOperationFactory;
    private CountDownLatch latch;
    private Consumer<RegisterEvent> onRegisterUserSuccess = (event) -> {
        synchronized (ConcurrentMap.class) {
            Integer block = event.getReceipt().getBlockNumber().intValue();
            Integer count = blocksStatistics.getOrDefault(block, 0);
            blocksStatistics.put(block, ++count);
        }
        System.out.println(">> OK ! txType: " + event.getEventResponse().txType + " : " + event.getEventResponse().details + " : " + event.getReceipt().getBlockNumber());
        if (!isNull(latch)) latch.countDown();
    };
    private Consumer<RegisterUserReceipt> onRegisterUserReject = (receipt) -> {
        if (receipt.getException().isPresent()) {
            System.out.println(">> ERROR ! txType: " + receipt.getContractOperation().getContractActionType() + " : " + receipt.getContractOperation().getData() + " : " + receipt.getException().get() + " : " + Thread.currentThread());
        } else {
            synchronized (ConcurrentMap.class) {
                Integer block = receipt.getTransactionReceipt().get().getBlockNumber().intValue();
                Integer count = blocksStatistics.getOrDefault(block, 0);
                blocksStatistics.put(block, ++count);
            }
            System.out.println(">> REJECTED txType: ! " + receipt.getContractOperation().getContractActionType() + " : " + receipt.getContractOperation().getData() + " : " + Thread.currentThread() + " : " + receipt.getTransactionReceipt().get().getBlockNumber());
        }
        if (!isNull(latch)) latch.countDown();
    };

    public UserManipulations(DldWalletService dldWalletService, ContractOperationFactory contractOperationFactory) {
        this.dldWalletService = dldWalletService;
        this.contractOperationFactory = contractOperationFactory;
    }

    public void registerAllUsersAndExit(int userCount) throws InterruptedException, ExecutionException, TransactionException, IOException {
        latch = new CountDownLatch(userCount);

        for (int i = 1; i <= userCount; i++) {
            registerUser(i);
        }

        latch.await();

        System.out.println("=================================");
        System.out.println(blocksStatistics);
        System.exit(0);
    }

    public void registerUser(int id) throws ExecutionException, InterruptedException, IOException, TransactionException {
        AbstractContractOperation registerUserOperation = contractOperationFactory.registerUserOperation(
                dldWalletService.getWalletById(id),
                "{\"userId\":" + id + "}",
                onRegisterUserSuccess,
                onRegisterUserReject);

        CompletableFuture<TransactionReceipt> receipt = registerUserOperation.execute();
    }

    public void registerUserOne() throws ExecutionException, InterruptedException, IOException, TransactionException {
        AbstractContractOperation registerUserOperation = contractOperationFactory.registerUserOperation(
                dldWalletService.getWalletOne(),
                "{\"userId\":One}");

        CompletableFuture<TransactionReceipt> receipt = registerUserOperation.execute();
        System.out.println(receipt.get());
    }

}
