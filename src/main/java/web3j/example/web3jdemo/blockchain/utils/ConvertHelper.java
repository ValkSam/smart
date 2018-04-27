package web3j.example.web3jdemo.blockchain.utils;


import javax.xml.bind.DatatypeConverter;

public class ConvertHelper {

    public static String bytesToHex(byte[] bytes) {
        return DatatypeConverter.printHexBinary(bytes);
    }

}
