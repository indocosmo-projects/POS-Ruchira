/*
 * This class uses AES specification.
 * Further string is encrypted to 64bit format and
 * URL encoded before sending as web request.
 */
package com.indocosmo.pos.common.utilities;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class PosCryptUtil {

	private static PosCryptUtil instance = null;
	private final String iv = "rpoemvcf72509351";
	private final String SECRET_KEY = "hvpejdnb92758364";

	private IvParameterSpec ivspec;
	private SecretKeySpec keyspec;
	private Cipher cipher;

//	public static void main(String[] args) throws Exception {
//
//		CryptUtil obj = new CryptUtil();
//
//		StringBuffer myString = new StringBuffer("Sample statement for testing...");
//		System.out.println("Value to be encrypted:" + myString.toString());
//
//		StringBuffer str = new StringBuffer(obj.getEncryptedURL(myString));
//		System.out.println("Encrypted:" + str.toString());
//
//		System.out.println("Decrypted:" + obj.getDecryptedURL(str));
//
//	}

	private PosCryptUtil() {
		ivspec = new IvParameterSpec(iv.getBytes());
		keyspec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
		try {
			cipher = Cipher.getInstance("AES/CFB8/NoPadding");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
	}

	// Implementing Singleton Class
	public static PosCryptUtil getInstance() {
		if (instance == null) {
			instance = new PosCryptUtil();
		}
		return instance;
	}

	//Returns encrypted string fit for using in URL
	public String getEncryptedURL(StringBuffer text) throws Exception {
		return URLEncoder.encode(getEncryptedText(text), "UTF-8");
	}
	
	
	// Encrypting data
	public String getEncryptedText(StringBuffer text) throws Exception {
		if (text == null || text.toString().length() == 0)
			throw new Exception("Empty string");
		 
		byte[] encrypted = null;

		try {
			cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
			encrypted = cipher.doFinal(padString(text.toString()).getBytes());

		} catch (Exception e) {
			throw new Exception("[encrypt] " + e.getMessage());
		}
		
		return Base64.encodeBase64String(encrypted);
	}
	
	//Returns encrypted string fit for using in URL
	public String getDecryptedURL(StringBuffer text) throws Exception {
		
		return getDecryptedText(new StringBuffer(URLDecoder.decode(text.toString(), "UTF-8")));
	}
	

	// Decrypting data
	public String getDecryptedText(StringBuffer code) throws Exception {
		if (code == null || code.toString().length() == 0)
			throw new Exception("Empty string");

		byte[] decrypted = null;

		try {
			cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
			decrypted = cipher.doFinal(Base64.decodeBase64(URLDecoder.decode(
					code.toString(), "UTF-8")));

		} catch (Exception e) {
			throw new Exception("[decrypt] " + e.getMessage());
		}
		return (new String(decrypted));
	}

	
	private String padString(String source) {
		char paddingChar = ' ';
		int size = 16;
		int x = source.length() % size;
		int padLength = size - x;

		for (int i = 0; i < padLength; i++) {
			source += paddingChar;
		}

		return source;
	}
}