package web3j.example.web3jdemo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import web3j.example.web3jdemo.domain.entity.DldWallet;

@Repository
public class DldWalletRepository {

    @Autowired
    DldWalletStorage dldWalletStorage;

    public DldWallet findWalletOne() {
        return DldWallet.builder()
                .address("0xe73d6c1d416A136967c3B2Fc81B70924Ce399d8f")
                .addressKey("80008441183320096200621157175427654080185878799075637582084341523145842529220")
                .build();
    }

    public DldWallet findWalletById(int id) {
        return dldWalletStorage.wallets.get(id - 1);
    }


}
