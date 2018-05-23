package web3j.example.web3jdemo.contract.operation.actiontype;

import lombok.Getter;

@Getter
public enum AdminContractActionType implements ContractActionType {
    GET_IS_LATEST_VERSION,
    GET_CHECK_CONTRACT_TYPE,
    GET_IS_ADMIN,
    REGISTER_ADMIN;

    @Override
    public String getActionTypeName() {
        return this.name();
    }

}
