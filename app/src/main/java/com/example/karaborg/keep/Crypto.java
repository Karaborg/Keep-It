package com.example.karaborg.keep;

import android.support.v7.app.AppCompatActivity;
import android.util.Base64;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class Crypto extends AppCompatActivity {

    String AES = "AES";
    String encrypted;
    final private String key = "karab0rg";

    public String doEncrypt(String encrypt){

        try {

            encrypted = ecrypt(encrypt, key);

        } catch (Exception e) {

            e.printStackTrace();

        }
        return encrypted;

    }

    private String ecrypt(String Data, String password) throws Exception {

        SecretKeySpec key = generateKey(password);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(Data.getBytes());
        String encryptedValue = Base64.encodeToString(encVal, Base64.DEFAULT);
        return encryptedValue;

    }

    private SecretKeySpec generateKey(String password) throws Exception {

        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = password.getBytes("UTF-8");
        digest.update(bytes, 0, bytes.length);
        byte[] key = digest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        return secretKeySpec;

    }

}

