package web3j.example.web3jdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web3j.example.web3jdemo.domain.entity.Casino;
import web3j.example.web3jdemo.repository.CasinoRepository;


@Service
public class CasinoService {

    private final CasinoRepository casinoRepository;

    @Autowired
    public CasinoService(CasinoRepository casinoRepository) {
        this.casinoRepository = casinoRepository;
    }

    public Casino getCasinoOne() {
        return casinoRepository.findCasinoOne();
    }

}
