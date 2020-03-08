package com.zzikza.springboot.web.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtil {

	private final static int ITERATION_NUMBER = 1000;

	/**
	 * 비밀번호 생성
	 * 
	 * @param password 평문 비밀번호
	 * @param salt
	 * @return
	 * @throws Exception
	 */
	public static String getEncriptPassword(String password, String salt) {
		String encriptPassword = "";
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.reset();
			digest.update(salt.getBytes());
			byte[] input = digest.digest(password.getBytes("UTF-8"));
			for (int i = 0; i < ITERATION_NUMBER; i++) {
				digest.reset();
				input = digest.digest(input);
			}

			encriptPassword = new String(Base64.getEncoder().encode(input));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encriptPassword;
	}

	/**
	 * SALT 생성
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String getSalt() {
		String value = "";
		try {
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			// Salt generation 128 bits long
			byte[] salt = new byte[16];
			secureRandom.nextBytes(salt);
			byte[] encoded = Base64.getEncoder().encode(salt);
			return new String(encoded);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * 평문 비밀번호를 SHA256(평문비밀번호 + salt) 로 암호화 한 후 DB에서 가져온 암호화된 값이랑 일치 여부를 비교
	 * 
	 * @param password
	 * @param dbEncriptPassword
	 * @param dbSalt
	 * @return
	 */
	public static boolean isEquaslPassword(String password, String dbEncriptPassword, String dbSalt) {
		String encriptPassword = getEncriptPassword(password, dbSalt);
		if (encriptPassword.equals(dbEncriptPassword)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 입력 받은 알고리즘 방식에 따른 해시값을 생성
	 * 
	 * @param seedKey
	 * @param hmac
	 * @return
	 */
	public static byte[] hash(byte[] seedKey, String hmac) {
		String security = null;
		if ("HmacSHA1".equals(hmac)) {
			security = "SHA1";
		} else if ("HmacSHA256".equals(hmac)) {
			security = "SHA-256";
		} else if ("HmacSHA512".equals(hmac)) {
			security = "SHA-512";
		}

		try {
			MessageDigest messageDigest = MessageDigest.getInstance(security);
			return messageDigest.digest(seedKey);
		} catch (Exception e) {
			e.printStackTrace();
			return seedKey;
		}
	}
}
