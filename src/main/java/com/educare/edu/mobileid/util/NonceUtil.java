package com.educare.edu.mobileid.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.spongycastle.util.encoders.Hex;

import com.raonsecure.omnione.core.eoscommander.crypto.digest.Sha256;

public class NonceUtil {

	public static String createNonce() {
		byte[] b = new byte[20];
		try {
			SecureRandom.getInstance("SHA1PRNG").nextBytes(b);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		String nonce = Hex.toHexString(Sha256.from(b).getBytes());
		return nonce;
	}

}
