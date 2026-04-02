package com.educare.util;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESCryptoUtil {

	private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
	private static String KEY = "";
	private static String IV = "";

	public static String decrypt(String data) {
		try {
			
			SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8),"AES");
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			
			cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(IV.getBytes()));
			byte[] encryptedBytes = Base64.decodeBase64(data.getBytes());
			byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
			return new String(decryptedBytes, StandardCharsets.UTF_8);
		} catch (NullPointerException |NoSuchAlgorithmException| NoSuchPaddingException| InvalidKeyException| InvalidAlgorithmParameterException| IllegalBlockSizeException| BadPaddingException e) {
			return null;
		}

	}

	public static void setIV(String iv) {
		IV = iv;
	}

	public static void setKEY(String key) {
		KEY = key;
	}

}
