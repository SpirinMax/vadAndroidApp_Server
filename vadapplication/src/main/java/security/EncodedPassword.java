package security;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;


public class EncodedPassword {
	private static final String ALGORITHM = "SHA-256";
	public static final int LEN = 64;
	private static String createHash(String password) {
		byte[] encodedPassword=null;
		byte[] plainPassword = password.getBytes();
		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance(ALGORITHM);
			messageDigest.update(plainPassword);
			encodedPassword = messageDigest.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return DatatypeConverter.printHexBinary(encodedPassword).toLowerCase();
	}
	
	public static String sendHash(String password) {
		return createHash(password);
	}

}
