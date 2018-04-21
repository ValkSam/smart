package web3j.example.web3jdemo.repository;

import org.springframework.stereotype.Repository;
import web3j.example.web3jdemo.domain.entity.DldWallet;

@Repository
public class DldWalletRepository {

    public DldWallet findWalletOne() {
        return DldWallet.builder()
                .addressIndex("0xe73d6c1d416A136967c3B2Fc81B70924Ce399d8f")
                .addressIndexKey("80008441183320096200621157175427654080185878799075637582084341523145842529220")
                .addressActive("0x76a53c513782e142b6F0449f033599Ec480b4c05")
                .addressActiveKey("79242247143518674568930308080918976251020825086843874638910589441762354066249")
                .addressPassive("0x6Ed5967ea0D5A4998DFeb5e8610dD00060Df45D2")
                .addressPassiveKey("75229837313104562591578203806681600510108485549959610618236813012721680190914")
                .build();
    }

}
