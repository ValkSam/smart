package web3j.example.web3jdemo.blockchain.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.utils.Numeric;
import web3j.example.web3jdemo.blockchain.config.EthereumConfig;

import java.io.IOException;
import java.math.BigInteger;

@Component
public class CredentialsHelper {

    private final EthereumConfig ethereumConfig;

    @Autowired
    public CredentialsHelper(EthereumConfig ethereumConfig) {
        this.ethereumConfig = ethereumConfig;
    }

    public Credentials getOwnerCredentialsFromWalletFile() throws IOException, CipherException {
        return getFromWalletFile(ethereumConfig.getContractOwnerPrivateKey(), ethereumConfig.getContractOwnerWalletPassword());
    }

    public Credentials getFromWalletFile(String password, String walletFile) throws IOException, CipherException {
        return WalletUtils.loadCredentials(password, walletFile);
    }

    public Credentials getOwnerCredentials() throws IOException, CipherException {
        return getCredentials(ethereumConfig.getContractOwnerPrivateKey());
    }

    public Credentials getCredentials(String privateKey) {
        return Credentials.create(Numeric.toHexStringNoPrefix(new BigInteger(privateKey)));
    }

}


