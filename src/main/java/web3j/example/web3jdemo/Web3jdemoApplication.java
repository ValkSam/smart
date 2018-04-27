package web3j.example.web3jdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.web3j.crypto.CipherException;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import rx.functions.Action1;
import web3j.example.web3jdemo.blockchain.utils.Web3jHelper;
import web3j.example.web3jdemo.contract.builder.defaultgas.DefaultContractFactory;
import web3j.example.web3jdemo.contract.operation.AbstractContractOperation;
import web3j.example.web3jdemo.contract.operation.ContractOperationFactory;
import web3j.example.web3jdemo.contract.operation.wrapper.event.RegisterDocumentEvent;
import web3j.example.web3jdemo.contract.operation.wrapper.receipt.RegisterDocumentReceipt;
import web3j.example.web3jdemo.contract.wrapper.DldContract;
import web3j.example.web3jdemo.service.CasinoService;
import web3j.example.web3jdemo.service.DldWalletService;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

import static java.lang.String.format;

@SpringBootApplication
@EnableAsync
@Slf4j
public class Web3jdemoApplication implements CommandLineRunner {

    private final String APP_MESSAGE_PATTERN = "\n\tAPP: %s";

    private final Web3jHelper web3jHelper;
    private final ContractOperationFactory contractOperationFactory;
    private final DefaultContractFactory defaultContractFactory;
    private final DldWalletService dldWalletService;
    private final CasinoService casinoService;

    @Autowired
    public Web3jdemoApplication(Web3jHelper web3jHelper,
                                ContractOperationFactory contractOperationFactory,
                                DefaultContractFactory defaultContractFactory,
                                DldWalletService dldWalletService,
                                CasinoService casinoService) {
        this.web3jHelper = web3jHelper;
        this.contractOperationFactory = contractOperationFactory;
        this.defaultContractFactory = defaultContractFactory;
        this.dldWalletService = dldWalletService;
        this.casinoService = casinoService;
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(Web3jdemoApplication.class)
//                .properties("spring.config.name=web3jdemo")
                .web(false)
                .build()
                .run(args)
                .close();
    }

    @Override
    public void run(String... args) throws IOException, InterruptedException, TransactionException, CipherException, ExecutionException {
        log.info(format(APP_MESSAGE_PATTERN, web3jHelper.getWeb3ClientVersion()));

        DldContract contract = defaultContractFactory.defaultSingletonOwnerContractBuilder().build();

//        listenToEvent(contract, BigInteger.valueOf(41500));

//        registerCasinoOne();
//        registerUserOne();

        Consumer<RegisterDocumentEvent> onRegisterDocumentSuccess = (event) -> {
            System.out.println(">> OK ! txType: " + event.getEventResponse().txType + " : " + event.getEventResponse().documentUID);
        };

        Consumer<RegisterDocumentReceipt> onRegisterDocumentReject = (receipt) -> {
            if (receipt.getException().isPresent()) {
                System.out.println(">> ERROR ! txType: " + receipt.getContractOperation().getContractActionType() + " : " + receipt.getContractOperation().getDocumentUID() + " : " + receipt.getException().get() + " : " + Thread.currentThread());
            } else
                System.out.println(">> REJECTED txType: ! " + receipt.getContractOperation().getContractActionType() + " : " + receipt.getContractOperation().getDocumentUID() + " : " + Thread.currentThread());
        };

        AbstractContractOperation enrollRequestOperation1 = contractOperationFactory.registerEnrollRequestDocument(
                dldWalletService.getWalletOne(),
                BigInteger.valueOf(11L),
                "doc_7" + '\u241F' + "5",
                "{\"invoiceAmount\":110}",
                onRegisterDocumentSuccess,
                onRegisterDocumentReject);

        CompletableFuture<TransactionReceipt> receipt1 = enrollRequestOperation1.execute();
//        receipt1.get();

        delay(100);

        CompletableFuture<TransactionReceipt> receipt2 = contractOperationFactory.registerEnrollRequestDocument(
                dldWalletService.getWalletOne(),
                BigInteger.valueOf(12L),
                "doc_8" + '\u241F' + "5",
                "{\"invoiceAmount\":120}",
                onRegisterDocumentSuccess,
                onRegisterDocumentReject)
                .execute();
//        receipt2.get();

        CompletableFuture<TransactionReceipt> receipt3 = contractOperationFactory.registerEnrollRequestDocument(
                dldWalletService.getWalletOne(),
                BigInteger.valueOf(13L),
                "doc_9" + '\u241F' + "5",
                "{\"invoiceAmount\":130}",
                onRegisterDocumentSuccess,
                onRegisterDocumentReject)
                .execute();
//        receipt3.get();

        System.out.println("*************************************");

//        delay(5000);
//        if (1 == 1) return;

        CompletableFuture<TransactionReceipt> receipt1_1 = contractOperationFactory.enrollOperation(
                dldWalletService.getWalletOne(),
                BigInteger.valueOf(11L),
                "doc_7" + '\u241F' + "5",
                "{\"invoiceResponseId\":1000}",
                null,
                onRegisterDocumentReject)
                .execute();
//        receipt1_2.get();

        CompletableFuture<TransactionReceipt> receipt2_2 = contractOperationFactory.enrollOperation(
                dldWalletService.getWalletOne(),
                BigInteger.valueOf(12L),
                "doc_8" + '\u241F' + "5",
                "{\"invoiceResponseId\":1001}",
                null,
                onRegisterDocumentReject)
                .execute();
//        receipt2_2.get();

        CompletableFuture<TransactionReceipt> receipt3_2 = contractOperationFactory.cancelEnrollRequestTransferOperation(
                dldWalletService.getWalletOne(),
                BigInteger.valueOf(13L),
                "doc_9" + '\u241F' + "5",
                "{\"comment\":\"It's my decision\"}",
                null,
                onRegisterDocumentReject)
                .execute();
        receipt3_2.get();

//        listenToBlocks();

//        EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST,
//                DefaultBlockParameterName.LATEST, "0x978A5DfD109fE59C6250549D3Dc5602B0B731839");

    }

    private void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    private void listenToEvent(DldContract contract, BigInteger blockNumber) {
        contract.transferEventObservable(
                DefaultBlockParameter.valueOf(blockNumber),
                DefaultBlockParameter.valueOf(DefaultBlockParameterName.LATEST.name()))
                .subscribe(event -> {
                    System.out.println("######");
//                    System.out.println(ConvertHelper.TransactionEventResponseToString(event));
                    System.out.println("######");
                });
    }

    private void registerCasinoOne() throws ExecutionException, InterruptedException, IOException, TransactionException {
        AbstractContractOperation enrollRequestOperation0 = contractOperationFactory.registerCasinoOperation(
                casinoService.getCasinoOne(),
                "{\"casinoId\":20}");

        CompletableFuture<TransactionReceipt> receipt0 = enrollRequestOperation0.execute();
        System.out.println(receipt0.get());
    }

    private void registerUserOne() throws ExecutionException, InterruptedException, IOException, TransactionException {
        AbstractContractOperation enrollRequestOperation0 = contractOperationFactory.registerUserOperation(
                dldWalletService.getWalletOne(),
                "{\"userId\":2000}");

        CompletableFuture<TransactionReceipt> receipt0 = enrollRequestOperation0.execute();
        System.out.println(receipt0.get());
    }

    private void listenToBlocks() {
        Action1<EthBlock> onNext = (block) -> {
            System.out.println(block.getBlock().getNumber());
            block.getBlock().getTransactions().forEach(tx -> {
                tx.get();
            });
        };
        web3jHelper.listenToBlocks(onNext, 2);
    }

    //        DldContract.TransactionEventResponse transactionEventResponse1 = contract.getTransactionEvents(receipt1.get()).get(0);
//        System.out.println(ConvertHelper.TransactionEventResponseToString(transactionEventResponse1));

}
