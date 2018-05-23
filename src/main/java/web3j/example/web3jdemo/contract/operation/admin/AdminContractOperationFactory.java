package web3j.example.web3jdemo.contract.operation.admin;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import web3j.example.web3jdemo.contract.operation.admin.readonly.GetIsAdminOperation;
import web3j.example.web3jdemo.contract.operation.admin.transactional.register.AdminRegisterOperation;
import web3j.example.web3jdemo.contract.operation.wrapper.event.AdminRegisterEvent;
import web3j.example.web3jdemo.contract.operation.wrapper.receipt.AdminRegisterReceipt;
import web3j.example.web3jdemo.domain.entity.DldWallet;

import java.util.function.Consumer;

@Component
public abstract class AdminContractOperationFactory {

    @Lookup
    public abstract GetIsAdminOperation getIsAdminOperation(Credentials senderCredentials, DldWallet walletToCheck);

    @Lookup
    public abstract GetIsAdminOperation getIsAdminOperation(Credentials senderCredentials, String addressToCheck);

    @Lookup
    public abstract AdminRegisterOperation adminRegisterOperation(
            Credentials senderCredentials,
            String addressToBeRegistered,
            String data);

    @Lookup
    public abstract AdminRegisterOperation adminRegisterOperation(
            Credentials senderCredentials,
            String addressToBeRegistered,
            String data,
            Consumer<AdminRegisterEvent> onExecute,
            Consumer<AdminRegisterReceipt> onReject);

}
