package ltbank.model.utils;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

public class Argon2Instance {
	private static final Argon2PasswordEncoder ARGONENCODER = new Argon2PasswordEncoder(16, 64, 2, 128000, 10);
	public static boolean matchPassword(CharSequence rawPassword, String enconderPassword) {
		boolean match = ARGONENCODER.matches(rawPassword, enconderPassword);
		return match;
	}
}