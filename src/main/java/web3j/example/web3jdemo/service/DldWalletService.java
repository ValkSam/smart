package web3j.example.web3jdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web3j.example.web3jdemo.domain.entity.DldWallet;
import web3j.example.web3jdemo.repository.DldWalletRepository;


@Service
public class DldWalletService {

    private final DldWalletRepository dldWalletRepository;

    @Autowired
    public DldWalletService(DldWalletRepository dldWalletRepository) {
        this.dldWalletRepository = dldWalletRepository;
    }

    public DldWallet getWalletOne() {
        return dldWalletRepository.findWalletOne();
    }

}
