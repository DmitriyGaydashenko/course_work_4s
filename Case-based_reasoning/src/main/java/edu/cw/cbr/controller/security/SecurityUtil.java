package edu.cw.cbr.controller.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * This class centralizes all security properties and common security methods.
 * @author Dmitriy Gaydashenko
 *
 */
public class SecurityUtil {
	
	/** 
	 * Returns a hash of the input.
	 * @param input -  the string to be hashed
	 * @return hash of the {@code input}
	 */
	public static String hashInput(String input) {
        MessageDigest mDigest = null;
		try {
			mDigest = MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16)
            		.substring(1));
        }
        return sb.toString();
	}
}
