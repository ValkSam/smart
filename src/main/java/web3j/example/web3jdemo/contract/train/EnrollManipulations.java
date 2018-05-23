package web3j.example.web3jdemo.contract.train;

import lombok.Getter;
import lombok.Setter;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import web3j.example.web3jdemo.blockchain.utils.CredentialsHelper;
import web3j.example.web3jdemo.contract.operation.AbstractContractTransactionalOperation;
import web3j.example.web3jdemo.contract.operation.token.TokenContractOperationFactory;
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

    private final Map<Integer, Integer> blocksStatistics = new ConcurrentHashMap<>();
    private final Map<Integer, Integer> blocksStatisticsSuccess = new ConcurrentHashMap<>();
    private final Map<Integer, Integer> blocksStatisticsReject = new ConcurrentHashMap<>();
    private final Map<Integer, Integer> blocksStatisticsError = new ConcurrentHashMap<>();

    private final CredentialsHelper credentialsHelper;
    private final DldWalletService dldWalletService;
    private final TokenContractOperationFactory tokenContractOperationFactory;

    private CountDownLatch latch;

    public EnrollManipulations(CredentialsHelper credentialsHelper,
                               DldWalletService dldWalletService,
                               TokenContractOperationFactory tokenContractOperationFactory) {
        this.credentialsHelper = credentialsHelper;
        this.dldWalletService = dldWalletService;
        this.tokenContractOperationFactory = tokenContractOperationFactory;
    }

    Consumer<RegisterDocumentEvent> onRegisterDocumentSuccess = (event) -> {
        synchronized (ConcurrentMap.class) {
            Integer block = event.getReceipt().getBlockNumber().intValue();
            Integer count = blocksStatistics.getOrDefault(block, 0);
            blocksStatistics.put(block, ++count);

            Integer countSuccess = blocksStatisticsSuccess.getOrDefault(block, 0);
            blocksStatisticsSuccess.put(block, ++countSuccess);
        }
        System.out.println(">> OK ! txType: " + event.getEventResponse().txType + " : " + event.getEventResponse().documentUID);
        if (!isNull(latch)) latch.countDown();
    };

    Consumer<RegisterDocumentReceipt> onRegisterDocumentReject = (receipt) -> {
        if (receipt.getException().isPresent()) {
            synchronized (ConcurrentMap.class) {
                Integer block = 0;
                Integer count = blocksStatistics.getOrDefault(block, 0);
                blocksStatistics.put(block, ++count);

                Integer countError = blocksStatisticsError.getOrDefault(block, 0);
                blocksStatisticsError.put(block, ++countError);
            }
            System.out.println(">> ERROR ! txType: " + receipt.getContractOperation().getContractActionType() + " : " + receipt.getContractOperation().getDocumentUID() + " : " + receipt.getException().get() + " : " + Thread.currentThread());
        } else {
            synchronized (ConcurrentMap.class) {
                Integer block = receipt.getTransactionReceipt().get().getBlockNumber().intValue();
                Integer count = blocksStatistics.getOrDefault(block, 0);
                blocksStatistics.put(block, ++count);

                Integer countReject = blocksStatisticsReject.getOrDefault(block, 0);
                blocksStatisticsReject.put(block, ++countReject);
            }
            System.out.println(">> REJECTED txType: ! " + receipt.getContractOperation().getContractActionType() + " : " + receipt.getContractOperation().getDocumentUID() + " : " + Thread.currentThread());
        }
        if (!isNull(latch)) latch.countDown();
    };

    public void registerDocuments(int docCount) throws InterruptedException, ExecutionException, TransactionException, IOException {
        registerDocuments(1, docCount);
    }

    public void registerDocuments(int startWalletId, int docCount) throws InterruptedException, ExecutionException, TransactionException, IOException {
        latch = new CountDownLatch(docCount);

        for (int i = startWalletId; i <= startWalletId + docCount - 1; i++) {
//            registerEnroll(i);
        }

        latch.await();

        System.out.println("=================================");
        System.out.println("ALL: " + blocksStatistics);
        System.out.println("SUCCESS: " + blocksStatisticsSuccess);
        System.out.println("REJECT: " + blocksStatisticsReject);
        System.out.println("ERROR: " + blocksStatisticsError);
    }

    public void registerDocumentsForOneUser(int walletId, int docCount) throws InterruptedException, ExecutionException, TransactionException, IOException, CipherException {
        latch = new CountDownLatch(docCount);

        Credentials senderCredentials = credentialsHelper.getOwnerCredentials();

        for (int i = 1; i <= docCount; i++) {
            registerEnroll(senderCredentials, walletId, "doc___" + i);
        }

        latch.await();

        System.out.println("=================================");
        System.out.println("ALL: " + blocksStatistics);
        System.out.println("SUCCESS: " + blocksStatisticsSuccess);
        System.out.println("REJECT: " + blocksStatisticsReject);
        System.out.println("ERROR: " + blocksStatisticsError);
    }

    public void cancelDocumentsForOneUser(int walletId, int docCount) throws InterruptedException, ExecutionException, TransactionException, IOException, CipherException {
        latch = new CountDownLatch(docCount);

        Credentials senderCredentials = credentialsHelper.getOwnerCredentials();

        for (int i = 1; i <= docCount; i++) {
            cancelEnroll(senderCredentials, walletId, "doc___" + i);
        }

        latch.await();

        System.out.println("=================================");
        System.out.println("ALL: " + blocksStatistics);
        System.out.println("SUCCESS: " + blocksStatisticsSuccess);
        System.out.println("REJECT: " + blocksStatisticsReject);
        System.out.println("ERROR: " + blocksStatisticsError);
    }

    public void registerEnroll(Credentials senderCredentials, int walletId) throws ExecutionException, InterruptedException, IOException, TransactionException {

        AbstractContractTransactionalOperation enrollRequestOperation = tokenContractOperationFactory.registerEnrollRequestDocumentOperation(
                senderCredentials,
                dldWalletService.getWalletById(walletId),
                BigInteger.valueOf(walletId),
                "doc_" + walletId + '\u241F' + "X",
                "{\"invoiceAmount\":" + (walletId * 10) + "}",
                onRegisterDocumentSuccess,
                onRegisterDocumentReject);

        CompletableFuture<TransactionReceipt> receipt = enrollRequestOperation.execute();

    }

    public void registerEnroll(Credentials senderCredentials, int walletId, String doc) throws ExecutionException, InterruptedException, IOException, TransactionException {

        AbstractContractTransactionalOperation enrollRequestOperation = tokenContractOperationFactory.registerEnrollRequestDocumentOperation(
                senderCredentials,
                dldWalletService.getWalletById(walletId),
                BigInteger.valueOf(walletId),
                doc + '\u241F' + "Y",
                "{\"invoiceAmount\":" + (walletId * 10) + "}",
                onRegisterDocumentSuccess,
                onRegisterDocumentReject);

        CompletableFuture<TransactionReceipt> receipt = enrollRequestOperation.execute();

    }

    public void cancelEnroll(Credentials senderCredentials, int walletId, String doc) throws ExecutionException, InterruptedException, IOException, TransactionException {

        AbstractContractTransactionalOperation enrollRequestOperation = tokenContractOperationFactory.cancelEnrollRequestDocumentOperation(
                senderCredentials,
                dldWalletService.getWalletById(walletId),
                BigInteger.valueOf(walletId),
                doc + '\u241F' + "Y",
                "{\"invoiceAmount\":" + (walletId * 10) + "}",
                onRegisterDocumentSuccess,
                onRegisterDocumentReject);

        CompletableFuture<TransactionReceipt> receipt = enrollRequestOperation.execute();

    }

   /* public void printDocs(int walletId) throws IOException, TransactionException {
        DldWallet dldWallet = dldWalletService
                .getWalletById(walletId);
        List<String> userDocs = contractOperationFactory.getUserDocumentListOperation(dldWallet)
                .execute();
        System.out.println((userDocs).size());
        long startTime = System.nanoTime();
        for (String documentUID : userDocs) {
            System.out.println(documentUID + " : " + contractOperationFactory.getUserDocumentBlockOperation(dldWallet, documentUID)
                    .execute());
        }
        System.out.println((System.nanoTime() - startTime) / 1000000);
    }*/

}
