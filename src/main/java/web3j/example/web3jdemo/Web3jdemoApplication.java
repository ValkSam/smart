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
import org.web3j.protocol.exceptions.TransactionException;
import rx.functions.Action1;
import web3j.example.web3jdemo.blockchain.utils.CredentialsHelper;
import web3j.example.web3jdemo.blockchain.utils.Web3jHelper;
import web3j.example.web3jdemo.contract.builder.token.ContractFactory;
import web3j.example.web3jdemo.contract.operation.admin.AdminContractOperationFactory;
import web3j.example.web3jdemo.contract.operation.token.TokenContractOperationFactory;
import web3j.example.web3jdemo.contract.train.AdminManipulations;
import web3j.example.web3jdemo.contract.train.EnrollManipulations;
import web3j.example.web3jdemo.contract.train.TokenContractRequestsByOwner;
import web3j.example.web3jdemo.contract.wrapper.token.DltTokenContract;
import web3j.example.web3jdemo.service.CasinoService;
import web3j.example.web3jdemo.service.DldWalletService;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

import static java.lang.String.format;

@SpringBootApplication
@EnableAsync
@Slf4j
public class Web3jdemoApplication implements CommandLineRunner {

    private final String APP_MESSAGE_PATTERN = "\n\tAPP: %s";

    private final Web3jHelper web3jHelper;
    private final TokenContractOperationFactory tokenContractOperationFactory;
    private final AdminContractOperationFactory adminContractOperationFactory;
    private final ContractFactory tokenContractFactory;
    private final DldWalletService dldWalletService;
    private final CasinoService casinoService;
    private final CredentialsHelper credentialsHelper;


    @Autowired
    public Web3jdemoApplication(Web3jHelper web3jHelper,
                                TokenContractOperationFactory tokenContractOperationFactory,
                                AdminContractOperationFactory adminContractOperationFactory,
                                ContractFactory tokenContractFactory, DldWalletService dldWalletService, CasinoService casinoService, CredentialsHelper credentialsHelper) {
        this.web3jHelper = web3jHelper;
        this.tokenContractOperationFactory = tokenContractOperationFactory;
        this.adminContractOperationFactory = adminContractOperationFactory;
        this.tokenContractFactory = tokenContractFactory;
        this.dldWalletService = dldWalletService;
        this.casinoService = casinoService;
        this.credentialsHelper = credentialsHelper;
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

        TokenContractRequestsByOwner tokenContractRequestsByOwner = new TokenContractRequestsByOwner(
                credentialsHelper, tokenContractOperationFactory);
        tokenContractRequestsByOwner.performReadOnlyRequests();


//        listenToEvent(contract, BigInteger.valueOf(64000));

//        registerCasinoOne();
//        userManipulations.registerUserOne();

        /*DldContract contract = defaultContractFactory.defaultSingletonOwnerContractBuilder().build();
        contract.registerEventObservable(
                DefaultBlockParameter.valueOf(BigInteger.valueOf(64809)), DefaultBlockParameter.valueOf(BigInteger.valueOf(64979)))
                .subscribe(event -> {
                    System.out.println(event);
                });*/

        web3jHelper.listenPendingTransaction(tx -> {
            System.out.println("------------------------------------------------------------------------------------" + tx.getBlockNumber());
        });

        AdminManipulations adminManipulations = new AdminManipulations(credentialsHelper, dldWalletService, adminContractOperationFactory);
        int adminWalletId = 10 + 1;
        adminManipulations.registerAdmins(adminWalletId, 2);

        EnrollManipulations enrollManipulations = new EnrollManipulations(credentialsHelper, dldWalletService, tokenContractOperationFactory);
//        enrollManipulations.registerDocuments(200);
        int walletId = 997;
//        enrollManipulations.registerDocumentsForOneUser(walletId, 10);
//        enrollManipulations.cancelDocumentsForOneUser(walletId, 10);
        /*enrollManipulations.printDocs(walletId);*/

        System.out.println("===================================");



        /*char[] ch = new char[30000];
        Arrays.fill(ch, 'a');
        String str = new String(ch);*/


        System.out.println("*************************************");
/*
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
//        receipt3_2.get();

        delay(50000);*/

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

    private void listenToEvent(DltTokenContract contract, BigInteger blockNumber) {
        contract.transferEventObservable(
                DefaultBlockParameter.valueOf(blockNumber),
                DefaultBlockParameter.valueOf(DefaultBlockParameterName.LATEST.name()))
                .subscribe(event -> {
                    System.out.println("######");
//                    System.out.println(ConvertHelper.TransactionEventResponseToString(event));
                    System.out.println("######");
                });
    }

    /*private void registerCasinoOne() throws ExecutionException, InterruptedException, IOException, TransactionException {
        AbstractContractOperation enrollRequestOperation = contractOperationFactory.registerCasinoOperation(
                casinoService.getCasinoOne(),
                "{\"casinoId\":20}");

        CompletableFuture<TransactionReceipt> receipt = enrollRequestOperation.execute();
        System.out.println(receipt.get());
    }
*/

    private void listenToBlocks() {
        Action1<EthBlock> onNext = (block) -> {
            System.out.println(block.getBlock().getNumber());
            block.getBlock().getTransactions().forEach(tx -> {
                tx.get();
            });
        };
        web3jHelper.listenToBlocks(onNext, 2);
    }

  /*  private void makeStorage() {
        for (int i = 0; i < 1000; i++) {
            DldWallet wallet = web3jHelper.createDldWallet();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("wallets.add(DldWallet.builder()");
            stringBuilder.append(".id(" + (i + 1) + "L)");
            stringBuilder.append(".addressIndex(\"" + wallet.getAddressIndex() + "\")");
            stringBuilder.append(".addressIndexKey(\"" + wallet.getAddressIndexKey() + "\")");
            stringBuilder.append(".addressActive(\"" + wallet.getAddressActive() + "\")");
            stringBuilder.append(".addressActiveKey(\"" + wallet.getAddressActiveKey() + "\")");
            stringBuilder.append(".addressPassive(\"" + wallet.getAddressPassive() + "\")");
            stringBuilder.append(".addressPassiveKey(\"" + wallet.getAddressPassiveKey() + "\")");
            stringBuilder.append(".build());");
            System.out.println(stringBuilder.toString());
        }
    }
*/
    //        DldContract.TransactionEventResponse transactionEventResponse1 = contract.getTransactionEvents(receipt1.get()).get(0);
//        System.out.println(ConvertHelper.TransactionEventResponseToString(transactionEventResponse1));

}
