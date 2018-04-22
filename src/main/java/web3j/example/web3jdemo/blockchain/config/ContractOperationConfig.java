package web3j.example.web3jdemo.blockchain.config;

import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Configuration
@Slf4j
public class ContractOperationConfig {

    @Autowired
    private EthereumConfig ethereumConfig;

    @Bean
    ScheduledExecutorService web3jScheduledExecutorService() {
        return Executors.newScheduledThreadPool(2);
    }

    @Bean("contractOperationExecutor")
    public Executor contractOperationExecutor(
            @Value("${contractOperation.threadpool.coreSize}") int coreSize,
            @Value("${contractOperation.threadpool.maxSize}") int maxSize,
            @Value("${contractOperation.threadpool.queueCapacity}") int queueCapacity
    ) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(coreSize);
        executor.setMaxPoolSize(maxSize);
        executor.setQueueCapacity(queueCapacity);
        executor.initialize();
        return executor;
    }

    @Bean
    @Scope("${web3j.scope}")
    public Web3j web3j(
            @Autowired OkHttpClient gethHttpClient,
            @Autowired ScheduledExecutorService web3jScheduledExecutorService) {
        String gethUrl = ethereumConfig.getGethUrl();
        log.info("Building web3j service for endpoint: {}", gethUrl);
        return Web3j
                .build(new HttpService(gethUrl, gethHttpClient, false),
                        ethereumConfig.getWeb3jPollingInterval(),
                        web3jScheduledExecutorService
                );
    }

    @Bean
    public OkHttpClient gethHttpClient() {
        log.info("Building new OkHttpClient");
        ConnectionPool connectionPool = new ConnectionPool(
                ethereumConfig.getMaxConnIdle(),
                ethereumConfig.getMaxIdleTimeSeconds(),
                TimeUnit.SECONDS);
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequestsPerHost(ethereumConfig.getMaxConnPerRoute());
        dispatcher.setMaxRequests(ethereumConfig.getMaxConnTotal());
        return new OkHttpClient.Builder()
                .connectionPool(connectionPool)
                .dispatcher(dispatcher)
                .readTimeout(ethereumConfig.getReadTimeoutSeconds(), TimeUnit.SECONDS)
                .connectTimeout(ethereumConfig.getConnectionTimeoutSeconds(), TimeUnit.SECONDS)
                .build();
    }
}


