package web3j.example.web3jdemo.contract.train;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.exceptions.TransactionException;
import web3j.example.web3jdemo.blockchain.utils.CredentialsHelper;
import web3j.example.web3jdemo.contract.operation.token.TokenContractOperationFactory;
import web3j.example.web3jdemo.contract.operation.token.readonly.GetTokenBalanceOfOperation;
import web3j.example.web3jdemo.contract.operation.token.readonly.GetTokenCheckContractTypeOperation;
import web3j.example.web3jdemo.contract.operation.token.readonly.GetTokenDecimalsOperation;
import web3j.example.web3jdemo.contract.operation.token.readonly.GetTokenIsLatestVersionOperation;
import web3j.example.web3jdemo.contract.operation.token.readonly.GetTokenMaxSupplyOperation;
import web3j.example.web3jdemo.contract.operation.token.readonly.GetTokenNameOperation;
import web3j.example.web3jdemo.contract.operation.token.readonly.GetTokenSymbolOperation;
import web3j.example.web3jdemo.contract.operation.token.readonly.GetTokenTotalSupplyOperation;

import java.io.IOException;

public class TokenContractRequestsByOwner {

    private final CredentialsHelper credentialsHelper;

    private final TokenContractOperationFactory tokenContractOperationFactory;

    public TokenContractRequestsByOwner(
            CredentialsHelper credentialsHelper,
            TokenContractOperationFactory tokenContractOperationFactory) {
        this.credentialsHelper = credentialsHelper;
        this.tokenContractOperationFactory = tokenContractOperationFactory;
    }

    public void performReadOnlyRequests() throws IOException, TransactionException, CipherException {

        Credentials senderCredentials = credentialsHelper.getOwnerCredentials();

        GetTokenSymbolOperation getTokenSymbolOperation = tokenContractOperationFactory.getTokenSymbolOperation(senderCredentials);
        System.out.println(getTokenSymbolOperation.execute());

        GetTokenNameOperation getTokenNameOperation = tokenContractOperationFactory.getTokenNameOperation(senderCredentials);
        System.out.println(getTokenNameOperation.execute());

        GetTokenDecimalsOperation getTokenDecimalsOperation = tokenContractOperationFactory.getTokenDecimalsOperation(senderCredentials);
        System.out.println(getTokenDecimalsOperation.execute());

        GetTokenMaxSupplyOperation getTokenMaxSupplyOperation = tokenContractOperationFactory.getTokenMaxSupplyOperation(senderCredentials);
        System.out.println(getTokenMaxSupplyOperation.execute());

        GetTokenTotalSupplyOperation getTokenTotalSupplyOperation = tokenContractOperationFactory.getTokenTotalSupplyOperation(senderCredentials);
        System.out.println(getTokenTotalSupplyOperation.execute());

        GetTokenIsLatestVersionOperation getTokenIsLatestVersionOperation = tokenContractOperationFactory.getTokenIsLatestVersionOperation(senderCredentials);
        System.out.println(getTokenIsLatestVersionOperation.execute());

        GetTokenCheckContractTypeOperation getTokenCheckContractTypeOperation = tokenContractOperationFactory.getTokenCheckContractTypeOperation(senderCredentials);
        System.out.println(getTokenCheckContractTypeOperation.execute());

        String userAddress = "0x78c1815ca2ee590362f7fde4d529ff63fafa411c";
        GetTokenBalanceOfOperation getTokenBalanceOfOperation = tokenContractOperationFactory.getTokenBalanceOfOperation(senderCredentials, userAddress);
        System.out.println(getTokenBalanceOfOperation.execute());
    }

}
