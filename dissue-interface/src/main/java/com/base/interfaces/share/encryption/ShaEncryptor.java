package com.base.interfaces.share.encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ShaEncryptor {

	public static String encryption(String inputKey, Algorithm Algorithm) {
		MessageDigest messageDigest;
	    String  encryptedKey = "";

	    try {
	        messageDigest = MessageDigest.getInstance(Algorithm.getValue());
	        messageDigest.update(inputKey.getBytes());

	        byte[] mb = messageDigest.digest();

			for (int i = 0; i < mb.length; i++) {
	            byte temp = mb[i];

	            String hexString = Integer.toHexString(new Byte(temp));

				while (hexString.length() < 2) {
	                hexString = "0" + hexString;
	            }

	            hexString = hexString.substring(hexString.length() - 2);

				encryptedKey += hexString;
	        }

	        return encryptedKey;
	    } catch (NoSuchAlgorithmException e) {
	        return "";
	    }	    
	}

}
