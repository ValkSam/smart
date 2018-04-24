package web3j.example.web3jdemo.repository;

import org.springframework.stereotype.Repository;
import web3j.example.web3jdemo.domain.entity.Casino;

@Repository
public class CasinoRepository {

    public Casino findCasinoOne() {
        return Casino.builder()
                .address("0x2b26a13aBd217649350E1503eCC1Fc29293A7Ef2")
                .addressKey("50393019249931977701734554239765909779853417355157472646709080216803494309236")
                .build();
    }

}
