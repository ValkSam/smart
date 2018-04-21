package web3j.example.web3jdemo.blockchain.utils;


import lombok.ToString;
import web3j.example.web3jdemo.contract.wrapper.DldContract;

import javax.xml.bind.DatatypeConverter;

public class ConvertHelper {

    public static String bytesToHex(byte[] bytes) {
        return DatatypeConverter.printHexBinary(bytes);
    }

    public static String TransactionEventResponseToString(DldContract.TransactionEventResponse response) {
        return "TransactionEventResponse{" +
                "log=" + response.log +
                ", txType=" + response.txType +
                ", sender='" + response.sender + '\'' +
                ", receiver='" + response.receiver + '\'' +
                ", amount=" + response.amount +
                ", documentUID='" + response.documentUID + '\'' +
                ", user=0x" + bytesToHex(response.user) +
                ", quantity=" + response.quantity +
                ", executor=0x" + bytesToHex(response.executor) +
                '}';
    }

}
