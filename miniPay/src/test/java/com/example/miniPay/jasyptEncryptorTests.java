package com.example.miniPay;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.event.annotation.BeforeTestClass;

public class jasyptEncryptorTests {
    private static String stringKey = "random";
    private static String username = "random";
    private static String password = "random";
    private static String url = "random";

    private static StandardPBEStringEncryptor pbeEnc;

    public StandardPBEStringEncryptor setEncryptor(){
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setAlgorithm("PBEWITHMD5ANDDES");
        pbeEnc.setPassword(stringKey);
        return pbeEnc;
    }

    @Test
    void stringEncrytor(){
        pbeEnc = setEncryptor();
        System.out.println("username= " + jasyptEncoding(username));
        System.out.println("password= " + jasyptEncoding(password));
        System.out.println("url= " + jasyptEncoding(url));
    }

    public String jasyptEncoding(String value){
        return pbeEnc.encrypt(value);
    }

    public String jasyptDecoding(String value){
        return pbeEnc.decrypt(value);
    }
}
