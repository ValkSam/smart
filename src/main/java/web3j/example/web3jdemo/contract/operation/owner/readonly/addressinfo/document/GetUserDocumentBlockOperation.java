package web3j.example.web3jdemo.contract.operation.owner.readonly.addressinfo.document;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.web3j.protocol.exceptions.TransactionException;
import web3j.example.web3jdemo.contract.operation.actiontype.ContractOwnerActionType;
import web3j.example.web3jdemo.contract.operation.owner.readonly.addressinfo.AbstractContractOwnerGetAddressInfoOperation;
import web3j.example.web3jdemo.domain.entity.DldWallet;

import java.io.IOException;
import java.math.BigInteger;

import static web3j.example.web3jdemo.contract.operation.actiontype.ContractOwnerActionType.GET_USER_DOCUMENT_BLOCK;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GetUserDocumentBlockOperation extends AbstractContractOwnerGetAddressInfoOperation {

    private static final ContractOwnerActionType ACTION_TYPE = GET_USER_DOCUMENT_BLOCK;

    private final String documentUID;

    public GetUserDocumentBlockOperation(DldWallet dldWallet, String documentUID) {
        this(dldWallet.getAddressIndex(), documentUID);
    }

    public GetUserDocumentBlockOperation(String address, String documentUID) {
        super(ACTION_TYPE, address);
        this.documentUID = documentUID;
    }

    @Override
    public BigInteger execute() throws IOException, TransactionException {
        return (BigInteger) execute(contract.getDocumentGenerationBlock(
                address, documentUID));
    }

}
