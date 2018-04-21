package web3j.example.web3jdemo.domain.entity;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DldWallet {

    private Long id;

    private String addressIndex;
    private String addressIndexKey;

    private String addressActive;
    private String addressActiveKey;

    private String addressPassive;
    private String addressPassiveKey;

}
