package web3j.example.web3jdemo.contract.operation.actiontype;

import lombok.Getter;

@Getter
public enum ContractOwnerActionType implements ContractActionType {
    GET_USER_DOCUMENTS,
    GET_USER_DOCUMENT_BLOCK;

    @Override
    public String getActionTypeName() {
        return this.name();
    }

}
