package web3j.example.web3jdemo.contract.builder.defaultgas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.web3j.crypto.CipherException;
import web3j.example.web3jdemo.blockchain.utils.CredentialsHelper;
import web3j.example.web3jdemo.contract.wrapper.DldContract;
import web3j.example.web3jdemo.domain.AddressType;
import web3j.example.web3jdemo.domain.entity.DldWallet;

import java.io.IOException;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DefaultUserContractBuilder extends DefaultContractBuilder {

    private final String privateKey;
    @Autowired
    private CredentialsHelper credentialsHelper;

    public DefaultUserContractBuilder(DldWallet dldWallet, AddressType senderAddressType) {
        switch (senderAddressType) {
            case INDEX: {
                this.privateKey = dldWallet.getAddressIndexKey();
                break;
            }
            case ACTIVE: {
                this.privateKey = dldWallet.getAddressActiveKey();
                break;
            }
            case PASSIVE: {
                this.privateKey = dldWallet.getAddressPassiveKey();
                break;
            }
            default: {
                throw new AssertionError("AddressType not supported");
            }
        }
    }

    public DldContract build() throws IOException, CipherException {
        setCredentials(credentialsHelper.getCredentials(this.privateKey));
        return super.build();
    }

}
