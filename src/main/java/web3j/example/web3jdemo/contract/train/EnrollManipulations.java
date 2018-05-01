package web3j.example.web3jdemo.contract.train;

import lombok.Getter;
import lombok.Setter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import web3j.example.web3jdemo.contract.operation.AbstractContractOperation;
import web3j.example.web3jdemo.contract.operation.ContractOperationFactory;
import web3j.example.web3jdemo.contract.operation.wrapper.event.RegisterDocumentEvent;
import web3j.example.web3jdemo.contract.operation.wrapper.receipt.RegisterDocumentReceipt;
import web3j.example.web3jdemo.service.DldWalletService;

import java.io.IOException;
import java.math.BigInteger;
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
public class EnrollManipulations {

    private CountDownLatch latch;

    private final Map<Integer, Integer> blocksStatistics = new ConcurrentHashMap<>();

    Consumer<RegisterDocumentEvent> onRegisterDocumentSuccess = (event) -> {
        synchronized (ConcurrentMap.class) {
            Integer block = event.getReceipt().getBlockNumber().intValue();
            Integer count = blocksStatistics.getOrDefault(block, 0);
            blocksStatistics.put(block, ++count);
        }
        System.out.println(">> OK ! txType: " + event.getEventResponse().txType + " : " + event.getEventResponse().documentUID);
        if (!isNull(latch)) latch.countDown();
    };

    Consumer<RegisterDocumentReceipt> onRegisterDocumentReject = (receipt) -> {
        if (receipt.getException().isPresent()) {
            System.out.println(">> ERROR ! txType: " + receipt.getContractOperation().getContractActionType() + " : " + receipt.getContractOperation().getDocumentUID() + " : " + receipt.getException().get() + " : " + Thread.currentThread());
        } else {
            synchronized (ConcurrentMap.class) {
                Integer block = receipt.getTransactionReceipt().get().getBlockNumber().intValue();
                Integer count = blocksStatistics.getOrDefault(block, 0);
                blocksStatistics.put(block, ++count);
            }
            System.out.println(">> REJECTED txType: ! " + receipt.getContractOperation().getContractActionType() + " : " + receipt.getContractOperation().getDocumentUID() + " : " + Thread.currentThread());
        }
        if (!isNull(latch)) latch.countDown();
    };

    private final DldWalletService dldWalletService;
    private final ContractOperationFactory contractOperationFactory;

    public EnrollManipulations(DldWalletService dldWalletService, ContractOperationFactory contractOperationFactory) {
        this.dldWalletService = dldWalletService;
        this.contractOperationFactory = contractOperationFactory;
    }

    public void registerDocuments(int docCount) throws InterruptedException, ExecutionException, TransactionException, IOException {
        registerDocuments(1, docCount);
    }

    public void registerDocuments(int startWalletId, int docCount) throws InterruptedException, ExecutionException, TransactionException, IOException {
        latch = new CountDownLatch(docCount);

        for (int i = startWalletId; i <= startWalletId + docCount - 1; i++) {
            registerEnroll(i);
        }

        latch.await();

        System.out.println("=================================");
        System.out.println(blocksStatistics);
    }

    public void registerOneDocument(int walletId, int docCount) throws InterruptedException, ExecutionException, TransactionException, IOException {
        latch = new CountDownLatch(docCount);

        for (int i = 1; i <= docCount; i++) {
            registerEnroll(walletId, "doc___" + i);
        }

        latch.await();

        System.out.println("=================================");
        System.out.println(blocksStatistics);
    }

    public void registerEnroll(int walletId) throws ExecutionException, InterruptedException, IOException, TransactionException {

        AbstractContractOperation enrollRequestOperation = contractOperationFactory.registerEnrollRequestDocument(
                dldWalletService.getWalletById(walletId),
                BigInteger.valueOf(walletId),
                "doc_" + walletId + '\u241F' + "X",
                "{\"invoiceAmount\":" + (walletId * 10) + "}",
                onRegisterDocumentSuccess,
                onRegisterDocumentReject);

        CompletableFuture<TransactionReceipt> receipt = enrollRequestOperation.execute();

    }

    public void registerEnroll(int walletId, String doc) throws ExecutionException, InterruptedException, IOException, TransactionException {

        AbstractContractOperation enrollRequestOperation = contractOperationFactory.registerEnrollRequestDocument(
                dldWalletService.getWalletById(walletId),
                BigInteger.valueOf(walletId),
                doc + '\u241F' + "Y",
                "{\"invoiceAmount\":" + (walletId * 10) + "}",
                onRegisterDocumentSuccess,
                onRegisterDocumentReject);

        CompletableFuture<TransactionReceipt> receipt = enrollRequestOperation.execute();

    }

}
