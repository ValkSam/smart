package web3j.example.web3jdemo.contract.train;

import lombok.Getter;
import lombok.Setter;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import web3j.example.web3jdemo.blockchain.utils.CredentialsHelper;
import web3j.example.web3jdemo.contract.operation.AbstractContractTransactionalOperation;
import web3j.example.web3jdemo.contract.operation.admin.AdminContractOperationFactory;
import web3j.example.web3jdemo.contract.operation.admin.readonly.GetIsAdminOperation;
import web3j.example.web3jdemo.contract.operation.admin.transactional.register.AdminRegisterOperation;
import web3j.example.web3jdemo.contract.operation.wrapper.event.AdminRegisterEvent;
import web3j.example.web3jdemo.contract.operation.wrapper.receipt.AdminRegisterReceipt;
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
public class AdminManipulations {

    private final Map<Integer, Integer> blocksStatistics = new ConcurrentHashMap<>();
    private final Map<Integer, Integer> blocksStatisticsSuccess = new ConcurrentHashMap<>();
    private final Map<Integer, Integer> blocksStatisticsReject = new ConcurrentHashMap<>();
    private final Map<Integer, Integer> blocksStatisticsError = new ConcurrentHashMap<>();

    private final CredentialsHelper credentialsHelper;
    private final DldWalletService dldWalletService;
    private final AdminContractOperationFactory adminContractOperationFactory;

    private CountDownLatch latch;

    public AdminManipulations(CredentialsHelper credentialsHelper,
                              DldWalletService dldWalletService,
                              AdminContractOperationFactory adminContractOperationFactory) {
        this.credentialsHelper = credentialsHelper;
        this.dldWalletService = dldWalletService;
        this.adminContractOperationFactory = adminContractOperationFactory;
    }

    Consumer<AdminRegisterEvent> onRegisterAdminSuccess = (event) -> {
        synchronized (ConcurrentMap.class) {
            Integer block = event.getReceipt().getBlockNumber().intValue();
            Integer count = blocksStatistics.getOrDefault(block, 0);
            blocksStatistics.put(block, ++count);

            Integer countSuccess = blocksStatisticsSuccess.getOrDefault(block, 0);
            blocksStatisticsSuccess.put(block, ++countSuccess);
        }
        System.out.println(">> OK ! txType: " + event.getEventResponse().txType + " : " + event.getEventResponse().registeredAddress);
        if (!isNull(latch)) latch.countDown();
    };

    Consumer<AdminRegisterReceipt> onRegisterAdminReject = (receipt) -> {
        if (receipt.getException().isPresent()) {
            synchronized (ConcurrentMap.class) {
                Integer block = 0;
                Integer count = blocksStatistics.getOrDefault(block, 0);
                blocksStatistics.put(block, ++count);

                Integer countError = blocksStatisticsError.getOrDefault(block, 0);
                blocksStatisticsError.put(block, ++countError);
            }
            System.out.println(">> ERROR ! txType: " + receipt.getContractOperation().getContractActionType() + " : " + ((AdminRegisterOperation) receipt.getContractOperation()).getAddressToBeRegistered() + " : " + receipt.getException().get() + " : " + Thread.currentThread());
        } else {
            synchronized (ConcurrentMap.class) {
                Integer block = receipt.getTransactionReceipt().get().getBlockNumber().intValue();
                Integer count = blocksStatistics.getOrDefault(block, 0);
                blocksStatistics.put(block, ++count);

                Integer countReject = blocksStatisticsReject.getOrDefault(block, 0);
                blocksStatisticsReject.put(block, ++countReject);
            }
            System.out.println(">> REJECTED txType: ! " + receipt.getContractOperation().getContractActionType() + " : " + ((AdminRegisterOperation) receipt.getContractOperation()).getAddressToBeRegistered() + " : " + Thread.currentThread());
        }
        if (!isNull(latch)) latch.countDown();
    };

    public void registerAdmins(int startWalletId, int count) throws InterruptedException, ExecutionException, TransactionException, IOException, CipherException {
        System.out.println("registerAdmins ===================================");

        latch = new CountDownLatch(count);

        Credentials senderCredentials = credentialsHelper.getOwnerCredentials();

        for (int i = startWalletId; i <= startWalletId + count - 1; i++) {
            GetIsAdminOperation getIsAdminOperation = adminContractOperationFactory.getIsAdminOperation(
                    senderCredentials, dldWalletService.getWalletById(i));
            System.out.println(dldWalletService.getWalletById(i).getAddress() + " ->" + getIsAdminOperation.execute());
            registerAdmin(senderCredentials, i);
        }

        latch.await();

        System.out.println("===================================");

        for (int i = startWalletId; i <= startWalletId + count - 1; i++) {
            GetIsAdminOperation getIsAdminOperation = adminContractOperationFactory.getIsAdminOperation(
                    senderCredentials, dldWalletService.getWalletById(i));
            System.out.println(dldWalletService.getWalletById(i).getAddress() + " ->" + getIsAdminOperation.execute());
        }

        System.out.println("=================================");
        System.out.println("ALL: " + blocksStatistics);
        System.out.println("SUCCESS: " + blocksStatisticsSuccess);
        System.out.println("REJECT: " + blocksStatisticsReject);
        System.out.println("ERROR: " + blocksStatisticsError);
    }

    public void registerAdmin(Credentials senderCredentials, int walletId) throws ExecutionException, InterruptedException, IOException, TransactionException {

        AbstractContractTransactionalOperation adminRegisterOperation = adminContractOperationFactory.adminRegisterOperation(
                senderCredentials,
                dldWalletService.getWalletById(walletId).getAddress(),
                "{\"adminId\":" + (walletId * 10) + "}",
                onRegisterAdminSuccess,
                onRegisterAdminReject);

        CompletableFuture<TransactionReceipt> receipt = adminRegisterOperation.execute();

    }

}
