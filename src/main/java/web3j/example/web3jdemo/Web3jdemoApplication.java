package web3j.example.web3jdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.web3j.crypto.CipherException;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import rx.functions.Action1;
import web3j.example.web3jdemo.blockchain.utils.ConvertHelper;
import web3j.example.web3jdemo.blockchain.utils.Web3jHelper;
import web3j.example.web3jdemo.contract.builder.defaultgas.DefaultContractFactory;
import web3j.example.web3jdemo.contract.operation.ContractOperation;
import web3j.example.web3jdemo.contract.operation.ContractOperationFactory;
import web3j.example.web3jdemo.contract.wrapper.DldContract;
import web3j.example.web3jdemo.service.DldWalletService;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.lang.String.format;

@SpringBootApplication
@Slf4j
public class Web3jdemoApplication implements CommandLineRunner {

    private final String APP_MESSAGE_PATTERN = "\n\tAPP: %s";

    private final Web3jHelper web3jHelper;
    private final ContractOperationFactory contractOperationFactory;
    private final DefaultContractFactory defaultContractFactory;
    private final DldWalletService dldWalletService;

    @Autowired
    public Web3jdemoApplication(Web3jHelper web3jHelper, ContractOperationFactory contractOperationFactory, DefaultContractFactory defaultContractFactory, DldWalletService dldWalletService) {
        this.web3jHelper = web3jHelper;
        this.contractOperationFactory = contractOperationFactory;
        this.defaultContractFactory = defaultContractFactory;
        this.dldWalletService = dldWalletService;
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


        Action1<EthBlock> onNext = (block) -> {
            System.out.println(block.getBlock().getNumber());
            block.getBlock().getTransactions().forEach(tx -> {
                tx.get();
            });
        };

        DldContract contract = defaultContractFactory.defaultSingletonOwnerContractBuilder().build();

//        if (1 == 1) return;

        contract.transactionEventObservable(
                DefaultBlockParameter.valueOf(BigInteger.valueOf(28096)),
//                DefaultBlockParameter.valueOf(BigInteger.ZERO),
                DefaultBlockParameter.valueOf(BigInteger.valueOf(Integer.MAX_VALUE)))
                .subscribe(event -> {
                    System.out.println("######");
                    System.out.println(ConvertHelper.TransactionEventResponseToString(event));
                    System.out.println("######");
                });

        ContractOperation enrollRequestOperation0 = contractOperationFactory.registerUserOperation(
                dldWalletService.getWalletOne(),
                "2000",
                "{\"userId\":2000}");

        CompletableFuture<TransactionReceipt> receipt0 = enrollRequestOperation0.execute();
        System.out.println(receipt0.get());

        ContractOperation enrollRequestOperation1 = contractOperationFactory.enrollRequestOperation(
                dldWalletService.getWalletOne(),
                BigInteger.valueOf(11L),
                "doc_7" + '\u241F' + "5",
                "{\"invoiceAmount\":110}");

        CompletableFuture<TransactionReceipt> receipt1 = enrollRequestOperation1.execute();
        receipt1.get();
//        DldContract.TransactionEventResponse transactionEventResponse1 = contract.getTransactionEvents(receipt1.get()).get(0);
//        System.out.println(ConvertHelper.TransactionEventResponseToString(transactionEventResponse1));

        CompletableFuture<TransactionReceipt> receipt2 = contractOperationFactory.enrollRequestOperation(
                dldWalletService.getWalletOne(),
                BigInteger.valueOf(12L),
                "doc_8" + '\u241F' + "5",
                "{\"invoiceAmount\":120}")
                .execute();
        receipt2.get();

        System.out.println("*************************************");

        CompletableFuture<TransactionReceipt> receipt3 = contractOperationFactory.enrollOperation(
                dldWalletService.getWalletOne(),
                BigInteger.valueOf(11L),
                "doc_7" + '\u241F' + "5",
                "{\"invoiceResponseId\":1000}")
                .execute();
        System.out.println(receipt3.get());

        CompletableFuture<TransactionReceipt> receipt4 = contractOperationFactory.enrollOperation(
                dldWalletService.getWalletOne(),
                BigInteger.valueOf(12L),
                "doc_8" + '\u241F' + "5",
                "{\"invoiceResponseId\":1001}")
                .execute();
        receipt4.get();

        web3jHelper.listenToBlocks(onNext, 2);

        EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST,
                DefaultBlockParameterName.LATEST, "0x978A5DfD109fE59C6250549D3Dc5602B0B731839");



        /*BigInteger lastBlockNumber = null;
        while (true) {
            BigInteger blockNumber = web3jHelper.getLatestBlockNumber();
            if (!blockNumber.equals(lastBlockNumber)) {
                lastBlockNumber = blockNumber;
                log.info(format(APP_MESSAGE_PATTERN, blockNumber));
            }
        }*/
    }

}
