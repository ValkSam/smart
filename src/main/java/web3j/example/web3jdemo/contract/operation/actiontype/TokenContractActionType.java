package web3j.example.web3jdemo.contract.operation.actiontype;

import lombok.Getter;

@Getter
public enum TokenContractActionType implements ContractActionType {
    GET_SYMBOL,
    GET_NAME,
    GET_DECIMALS,
    GET_MAX_SUPPLY,
    GET_TOTAL_SUPPLY,
    GET_IS_LATEST_VERSION,
    GET_CHECK_CONTRACT_TYPE,
    GET_BALANCE_OF,
    REGISTER_ENROLL_DOCUMENT,
    CANCEL_ENROLL_DOCUMENT;

    @Override
    public String getActionTypeName() {
        return this.name();
    }

}
