package main.java.com.av.regapp.utils;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class SHAHasing{
    // salting
    public static String getSalt(){
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    
    public static String hash(String pass, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes());
            byte[] hashedBytes = md.digest(pass.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes){
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (Exception e) {
            
            e.printStackTrace();
            return null;
        }
    }

}