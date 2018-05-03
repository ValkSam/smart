package web3j.example.web3jdemo.contract.operation.owner.readonly.addressinfo.document;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.web3j.protocol.exceptions.TransactionException;
import web3j.example.web3jdemo.contract.operation.actiontype.ContractOwnerActionType;
import web3j.example.web3jdemo.contract.operation.owner.readonly.addressinfo.AbstractContractOwnerGetAddressInfoOperation;
import web3j.example.web3jdemo.domain.entity.DldWallet;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;
import static web3j.example.web3jdemo.contract.operation.actiontype.ContractOwnerActionType.GET_USER_DOCUMENTS;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GetUserDocumentListOperation extends AbstractContractOwnerGetAddressInfoOperation {

    private static final ContractOwnerActionType ACTION_TYPE = GET_USER_DOCUMENTS;

    public GetUserDocumentListOperation(DldWallet dldWallet) {
        this(dldWallet.getAddressIndex());
    }

    public GetUserDocumentListOperation(String address) {
        super(ACTION_TYPE, address);
    }

    @Override
    public List<String> execute() throws IOException, TransactionException {
        return ((List<byte[]>) execute(contract.getDocumentsUIDs(
                address)))
                .stream()
                .map(e -> new String(e, UTF_8).replaceAll("\0", ""))
                .collect(Collectors.toList());
    }

}
