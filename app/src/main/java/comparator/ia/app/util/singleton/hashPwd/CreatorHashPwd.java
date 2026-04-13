package comparator.ia.app.util.singleton.hashPwd;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

@Component
public class CreatorHashPwd implements Hashable{
	
	private static final String ALGORITHM = "SHA-512";

	@Override
	public String createHash(String pwd) {

		byte[] passwordBytes = pwd.getBytes();
		byte[] encriptedPassword;
		MessageDigest encripter;
		try {
			encripter = MessageDigest.getInstance(ALGORITHM);
			encriptedPassword = encripter.digest(passwordBytes);
			
			StringBuilder stb = new StringBuilder();

			for (byte b : encriptedPassword) {
				stb.append(String.format("%02x", b & 0xff));
			}
			return stb.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Critical error: Algorithm " + ALGORITHM + " not found", e);
		}
	}

}
