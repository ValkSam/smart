package web3j.example.web3jdemo.contract.builder.defaultgas;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;
import web3j.example.web3jdemo.contract.builder.ContractFactory;
import web3j.example.web3jdemo.domain.UserAddressType;
import web3j.example.web3jdemo.domain.entity.Casino;
import web3j.example.web3jdemo.domain.entity.DldWallet;

@Component
public abstract class DefaultContractFactory extends ContractFactory {

    @Lookup
    public abstract DefaultOwnerContractBuilder defaultOwnerContractBuilder();

    @Lookup
    public abstract DefaultSingletonOwnerContractBuilder defaultSingletonOwnerContractBuilder();

    @Lookup
    public abstract DefaultUserContractBuilder defaultUserContractBuilder(DldWallet dldWallet, UserAddressType senderUserAddressType);

    @Lookup
    public abstract DefaultCasinoContractBuilder defaultCasinoContractBuilder(Casino casino);

}
