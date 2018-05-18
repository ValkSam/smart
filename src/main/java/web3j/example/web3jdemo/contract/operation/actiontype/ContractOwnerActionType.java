package web3j.example.web3jdemo.contract.operation.actiontype;

import lombok.Getter;

@Getter
public enum ContractOwnerActionType implements ContractActionType {
    GET_USER_DOCUMENTS,
    GET_USER_DOCUMENT_BLOCK,
    GET_SYMBOL,
    GET_NAME,
    GET_DECIMALS,
    GET_MAX_SUPPLY,
    GET_TOTAL_SUPPLY,
    GET_IS_LATEST_VERSION,
    GET_CHECK_CONTRACT_TYPE,
    GET_BALANCE_OF;

    @Override
    public String getActionTypeName() {
        return this.name();
    }

}
