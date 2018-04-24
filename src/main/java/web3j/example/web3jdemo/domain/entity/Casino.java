package web3j.example.web3jdemo.domain.entity;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Casino {

    private Long id;

    private String address;
    private String addressKey;

}
