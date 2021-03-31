package com.jessica.jwt;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

public class KeyGenerator {
    public static int KEY_SIZE = 2048;
    public static String ALGORITHM = "RSA";

    public static void main(String[] args) {
        //创建公钥私钥
        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        } catch (
                NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        keyPairGenerator.initialize(KEY_SIZE);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        ByteBuffer byteBuffer = ByteBuffer.allocate(10000);
        byteBuffer.put(rsaPrivateKey.getEncoded());
        Charset charset = Charset.forName("utf-8");

        System.out.println(encryptBase64(rsaPrivateKey.getEncoded()));
        System.out.println("----------------------------");
        System.out.println(encryptBase64(rsaPublicKey.getEncoded()));
    }

    public static String encryptBase64(byte[] key) {
        return Base64.getEncoder().encodeToString(key);
    }
}
