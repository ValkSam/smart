package web3j.example.web3jdemo.contract.operation.actiontype;

import lombok.Getter;
import web3j.example.web3jdemo.domain.UserAddressType;

import static web3j.example.web3jdemo.domain.UserAddressType.ACTIVE;
import static web3j.example.web3jdemo.domain.UserAddressType.INDEX;

@Getter
public enum ContractUserActionType implements ContractActionType {
    REGISTER_USER(INDEX),
    TRANSFER(ACTIVE),
    ENROLL(INDEX),
    WITHDRAW(null),
    DEPOSIT(null),
    TAKE(null),
    ENROLL_REQUEST(INDEX),
    CANCEL_ENROLL_REQUEST(INDEX),
    WITHDRAW_REQUEST(ACTIVE);

    private final UserAddressType userAddressType;

    ContractUserActionType(UserAddressType userAddressType) {
        this.userAddressType = userAddressType;
    }
}
