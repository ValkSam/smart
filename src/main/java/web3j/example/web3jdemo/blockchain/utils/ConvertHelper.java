package web3j.example.web3jdemo.blockchain.utils;


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
                ", user='" + response.user + '\'' +
                ", sender='" + response.sender + '\'' +
                ", receiver='" + response.receiver + '\'' +
                ", amount=" + response.amount +
                ", documentUID='" + response.documentUID + '\'' +
                ", details='" + response.details + '\'' +
                '}';
    }

}
