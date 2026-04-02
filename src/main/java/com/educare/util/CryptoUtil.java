package com.educare.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.compress.utils.ByteUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Class Name : CryptoUtil.java
 * @author SI개발팀 박용주
 * @since 2019. 11. 12.
 * @version 1.0
 * @see
 * @Description 암호화 관련 Utility Class
 * 
 *              <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2019. 11. 12.	SI개발팀 박용주     		최초생성
 * </pre>
 */
public class CryptoUtil {

	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(CryptoUtil.class.getName());

	/**
	 * SHA-256 암호화
	 * 
	 * @param str
	 *            암호화할 스트링
	 * @return 암호화된 스트링
	 */
	public static String encodeSHA256CryptoNotDecode(String str) {

		String sha = "";
		if (str == null || "".equals(str)) {
			return null;
		}

		try {
			MessageDigest sh = MessageDigest.getInstance("SHA-256");
			sh.update(str.getBytes());
			byte byteData[] = sh.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
			sha = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			LOG.info("sha256ERROR:", e);
			sha = null;
		}

		return sha;
	}

	/**
	 * 비밀번호를 암호화하는 기능(복호화가 되면 안되므로 SHA-256 인코딩 방식 적용)
	 * 
	 * @param password
	 *            암호화될 패스워드
	 * @param id
	 *            salt로 사용될 사용자 ID 지정
	 * @return
	 * @throws Exception
	 */
	public static String encryptPassword(String password, String id) {

		if (password == null) {
			return "";
		}

		byte[] hashValue = null; // 해쉬값

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			LOG.error("ERROR:", e);
			return null;
		}

		md.reset();
		md.update(id.getBytes());

		hashValue = md.digest(password.getBytes());

		return new String(Base64.encodeBase64(hashValue));
	}

	public static String decryptAes(String strp, String key,int length) throws NoSuchAlgorithmException, GeneralSecurityException, UnsupportedEncodingException {
		String iv = key.substring(0, length);
		byte[] keyBytes = new byte[length];
		byte[] b = key.getBytes("UTF-8");
		int len = b.length;
		if (len > keyBytes.length) {
			len = keyBytes.length;
		}
		System.arraycopy(b, 0, keyBytes, 0, len);
		SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

		String str = strp.replaceAll("\\+", "%2B");
		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
		byte[] byteStr = Base64.decodeBase64(URLDecoder.decode(str, "utf-8").getBytes());
		return new String(c.doFinal(byteStr), "UTF-8");
	}
	
	
}
