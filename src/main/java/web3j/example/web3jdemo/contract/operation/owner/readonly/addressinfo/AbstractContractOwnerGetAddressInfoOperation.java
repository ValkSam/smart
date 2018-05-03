package web3j.example.web3jdemo.contract.operation.owner.readonly.addressinfo;

import web3j.example.web3jdemo.contract.operation.actiontype.ContractOwnerActionType;
import web3j.example.web3jdemo.contract.operation.owner.readonly.AbstractContractOwnerReadonlyOperation;

public abstract class AbstractContractOwnerGetAddressInfoOperation extends AbstractContractOwnerReadonlyOperation {

    protected final String address;

    public AbstractContractOwnerGetAddressInfoOperation(ContractOwnerActionType contractOwnerActionType,
                                                        String address) {
        super(contractOwnerActionType);
        this.address = address;
    }

}
