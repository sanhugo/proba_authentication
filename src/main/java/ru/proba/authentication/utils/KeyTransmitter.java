package ru.proba.authentication.utils;

import lombok.experimental.UtilityClass;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@UtilityClass
public class KeyTransmitter {
    public static PrivateKey getPrivateKeyFromString(String privateKey) throws Exception {
        byte[] encoded = Base64.getDecoder().decode(privateKey);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(new PKCS8EncodedKeySpec(encoded));
    }

    public static PublicKey getPublicKeyFromString(String privateKey) throws Exception {
        byte[] encoded = Base64.getDecoder().decode(privateKey);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(new X509EncodedKeySpec(encoded));
    }
}
