package web3j.example.web3jdemo.blockchain;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.tx.TransactionManager;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;

public class DldTransactionManager extends TransactionManager {

    private static volatile BigInteger n = BigInteger.ZERO;

    private final Credentials credentials;
    private final Web3j web3j;

    public DldTransactionManager(Web3j web3j, Credentials credentials) {
        super(web3j, credentials.getAddress());
        this.credentials = credentials;
        this.web3j = web3j;
    }

    @Override
    public EthSendTransaction sendTransaction(
            BigInteger gasPrice,
            BigInteger gasLimit,
            String to,
            String data,
            BigInteger value) throws IOException {
        BigInteger nonce = getNonce(web3j, credentials);
        System.out.println(">>>>>" + nonce.toString());
        RawTransaction rawTransaction = RawTransaction.createTransaction(
                nonce, gasPrice, gasLimit, to, value, data);
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String hexValue = Numeric.toHexString(signedMessage);
        return web3j.ethSendRawTransaction(hexValue).send();
    }

    private BigInteger getNonce(Web3j web3j, Credentials credentials) throws IOException {
        synchronized (DldTransactionManager.class) {
            if (n.equals(BigInteger.ZERO)) {
                EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
                        credentials.getAddress(), DefaultBlockParameterName.LATEST).send();

                BigInteger nonce = ethGetTransactionCount.getTransactionCount();

                n = nonce;
            } else {
                n = n.add(BigInteger.ONE);
            }
            return n;
        }
    }
}
