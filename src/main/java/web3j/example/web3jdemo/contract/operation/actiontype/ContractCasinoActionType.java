package web3j.example.web3jdemo.contract.operation.actiontype;

public enum ContractCasinoActionType implements ContractActionType {
    REGISTER_CASINO;

    @Override
    public String getActionTypeName() {
        return this.name();
    }
}
