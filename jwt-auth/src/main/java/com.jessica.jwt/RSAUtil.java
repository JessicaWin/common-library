package com.jessica.jwt;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAUtil {

     /**
       *
       * @param key
       * @return
       * @throws Exception
       */
     public static PublicKey getPublicKey(String key) throws Exception {
      X509EncodedKeySpec spec = new X509EncodedKeySpec(decryptBase64(key));
         KeyFactory factory = KeyFactory.getInstance("RSA");
         return factory.generatePublic(spec);
     }

     /**
       *
       * @param privateKey
       * @return
       * @throws Exception
       */
     public static PrivateKey getPrivateKey(String privateKey) throws Exception {
         PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decryptBase64(privateKey));
         KeyFactory factory = KeyFactory.getInstance("RSA");
         return factory.generatePrivate(spec);
     }

    public static String encryptBase64(byte[] key) {
        return Base64.getEncoder().encodeToString(key);
    }

    public static byte[] decryptBase64(String key) throws Exception {
        return  Base64.getDecoder().decode(key);
    }

}
