package web3j.example.web3jdemo.contract.train;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.exceptions.TransactionException;
import web3j.example.web3jdemo.blockchain.utils.CredentialsHelper;
import web3j.example.web3jdemo.contract.operation.ContractOperationFactory;
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

    private final ContractOperationFactory contractOperationFactory;

    public TokenContractRequestsByOwner(
            CredentialsHelper credentialsHelper,
            ContractOperationFactory contractOperationFactory) {
        this.credentialsHelper = credentialsHelper;
        this.contractOperationFactory = contractOperationFactory;
    }

    public void performReadOnlyRequests() throws IOException, TransactionException, CipherException {

        Credentials senderCredentials = credentialsHelper.getOwnerCredentials();

        GetTokenSymbolOperation getTokenSymbolOperation = contractOperationFactory.getTokenSymbolOperation(senderCredentials);
        System.out.println(getTokenSymbolOperation.execute());

        GetTokenNameOperation getTokenNameOperation = contractOperationFactory.getTokenNameOperation(senderCredentials);
        System.out.println(getTokenNameOperation.execute());

        GetTokenDecimalsOperation getTokenDecimalsOperation = contractOperationFactory.getTokenDecimalsOperation(senderCredentials);
        System.out.println(getTokenDecimalsOperation.execute());

        GetTokenMaxSupplyOperation getTokenMaxSupplyOperation = contractOperationFactory.getTokenMaxSupplyOperation(senderCredentials);
        System.out.println(getTokenMaxSupplyOperation.execute());

        GetTokenTotalSupplyOperation getTokenTotalSupplyOperation = contractOperationFactory.getTokenTotalSupplyOperation(senderCredentials);
        System.out.println(getTokenTotalSupplyOperation.execute());

        GetTokenIsLatestVersionOperation getTokenIsLatestVersionOperation = contractOperationFactory.getTokenIsLatestVersionOperation(senderCredentials);
        System.out.println(getTokenIsLatestVersionOperation.execute());

        GetTokenCheckContractTypeOperation getTokenCheckContractTypeOperation = contractOperationFactory.getTokenCheckContractTypeOperation(senderCredentials);
        System.out.println(getTokenCheckContractTypeOperation.execute());

        String userAddress = "0x78c1815ca2ee590362f7fde4d529ff63fafa411c";
        GetTokenBalanceOfOperation getTokenBalanceOfOperation = contractOperationFactory.getTokenBalanceOfOperation(senderCredentials, userAddress);
        System.out.println(getTokenBalanceOfOperation.execute());
    }

}
