package web3j.example.web3jdemo.contract.builder;

import lombok.Setter;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import web3j.example.web3jdemo.contract.wrapper.DldContract;

import java.math.BigInteger;

@Setter
public abstract class ContractBuilder {

    private String contractAddress;
    private Web3j web3j;
    private Credentials credentials;
    private BigInteger gasPrice;
    private BigInteger gasLimit;

    public DldContract build() {
        return DldContract.load(contractAddress,
                web3j,
                credentials,
                gasPrice,
                gasLimit);
    }

}
