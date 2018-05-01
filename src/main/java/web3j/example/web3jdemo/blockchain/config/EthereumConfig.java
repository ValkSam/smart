package web3j.example.web3jdemo.blockchain.config;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.math.BigInteger;

@Configuration
@Getter
@NoArgsConstructor
@Slf4j
public class EthereumConfig {

    @Value("${ethereum.geth.url}")
    private String gethUrl;

    @Value("${ethereum.geth.connPool.maxConnTotal}")
    private Integer maxConnTotal;

    @Value("${ethereum.geth.connPool.maxConnPerRoute}")
    private Integer maxConnPerRoute;

    @Value("${ethereum.geth.connPool.maxConnIdle}")
    private Integer maxConnIdle;

    @Value("${ethereum.geth.connPool.maxIdleTimeSeconds}")
    private Integer maxIdleTimeSeconds;

    @Value("${ethereum.geth.connPool.readTimeoutSeconds}")
    private Integer readTimeoutSeconds;

    @Value("${ethereum.geth.connPool.connectionTimeoutSeconds}")
    private Integer connectionTimeoutSeconds;

    @Value("${ethereum.admin.wallet}")
    private String adminWalletAddress;

    @Value("${ethereum.cryptocurrency.address}")
    private String cryptocurrencyContractAddress;

    @Value("${ethereum.cryptocurrency.gas-price}")
    private Long cryptocurrencyContractGasPrice;

    @Value("${ethereum.cryptocurrency.gas-limit}")
    private Long cryptocurrencyContractGasLimit;

    @Value("${ethereum.initial.ether}")
    private Long initialEther;

    @Value("${ethereum.cryptocurrency.initial-supply}")
    private BigInteger cryptocurrencyContractInitialSupply;

    @Value("${ethereum.mint-coins.default-comment}")
    private String mintCoinsDefaultComment;

    @Value("${ethereum.initial-transfer.comment}")
    private String initialTransferComment;

    @Value("${web3j.pollingIntervalMillisec}")
    private Integer web3jPollingInterval;

    @Value("${ethereum.contract.address}")
    private String contractAddress;

    @Value("${ethereum.contract.owner.privateKey}")
    private String contractOwnerPrivateKey;

    @Value("${ethereum.contract.owner.wallet.file}")
    private String contractOwnerWalletFile;

    @Value("${ethereum.contract.owner.wallet.password}")
    private String contractOwnerWalletPassword;

    @Value("${ethereum.contract.transactionManager.custom}")
    private Boolean useCustomTransactionManager;

    @Value("${ethereum.contract.transactionManager.fast}")
    private Boolean useFastRawTransactionManager;

    public BigInteger getDefaultGasLimit() {
        return BigInteger.valueOf(cryptocurrencyContractGasLimit);
    }

    public BigInteger getDefaultGasPrice() {
        return BigInteger.valueOf(cryptocurrencyContractGasPrice);
    }

}