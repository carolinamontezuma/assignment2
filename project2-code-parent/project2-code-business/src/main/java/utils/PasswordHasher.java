package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class PasswordHasher {
    public static String plainTextToHash(String plainText){
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(plainText.getBytes());
            byte[] bytes = md5.digest();
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            
            return sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            return plainText;
        }
    }
}