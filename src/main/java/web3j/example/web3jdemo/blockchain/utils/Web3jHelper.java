package web3j.example.web3jdemo.blockchain.utils;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Actions;
import rx.internal.util.ActionSubscriber;
import web3j.example.web3jdemo.Web3jProvider;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.CountDownLatch;

@Slf4j
@Component
public class Web3jHelper {

    private final Web3jProvider web3jProvider;
    private final Web3j web3j;

    @Autowired
    public Web3jHelper(Web3jProvider web3jProvider) throws IOException {
        this.web3jProvider = web3jProvider;
        this.web3j = web3jProvider.get();
    }

    public String getWeb3ClientVersion() throws IOException {
        Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
        return web3ClientVersion.getWeb3ClientVersion();
    }

    public BigInteger getLatestBlockNumber() {
        return getLatestBlockNumber(web3j);
    }

    public BigInteger getLatestBlockNumber(Web3j web3j) {
        return getBlockNumber(web3j, DefaultBlockParameterName.LATEST);
    }

    public BigInteger getBlockNumber(DefaultBlockParameter defaultBlockParameter) {
        return getBlockNumber(web3j, defaultBlockParameter);
    }

    public BigInteger getBlockNumber(Web3j web3j, DefaultBlockParameter defaultBlockParameter) {
        if (defaultBlockParameter instanceof DefaultBlockParameterNumber) {
            return ((DefaultBlockParameterNumber) defaultBlockParameter).getBlockNumber();
        } else {
            return getBlock(web3j, defaultBlockParameter).getBlock().getNumber();
        }
    }

    public EthBlock getBlock(Web3j web3j, DefaultBlockParameter defaultBlockParameter) {
        EthBlock latestEthBlock;
        try {
            latestEthBlock = web3j.ethGetBlockByNumber(defaultBlockParameter, false).send();
        } catch (IOException e) {
            throw new RuntimeException("Can't get block by number", e);
        }
        return latestEthBlock;
    }

    public void listenToBlocks(Action1<EthBlock> onNext) throws InterruptedException {
        listenToBlocks(onNext, Integer.MAX_VALUE);
    }

    public void listenToBlocks(Action1<EthBlock> onNext, Integer blocks) {
        CountDownLatch latch = new CountDownLatch(1);
        Action0 onCompleted = () -> {
            System.out.println("onCompleted");
            latch.countDown();
        };
        Subscriber<EthBlock> subscriber = new ActionSubscriber<>(onNext, Actions.errorNotImplemented(), onCompleted);
        Subscription subscription = web3jProvider.get()
                .blockObservable(true)
                .take(blocks)
                .subscribe(subscriber);
        try {
            latch.await();
        } catch (InterruptedException e) {
            subscription.unsubscribe();
            System.out.println("listenToBlocks interrupted");
            return;
        }
        subscription.unsubscribe();
        System.out.println("listenToBlocks unsubscribed");
    }

}
