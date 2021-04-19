package py.org.atom.heart.mother.test;

import java.util.HashMap;
import java.util.Map;

import javax.security.enterprise.identitystore.PasswordHash;

public class PasswordTest {
//PBKDF2WithHmacSHA512:3072:FfjTB8NHcxpSHd+3/N8XCt+MLOld52j4a+fG9OJkFqBIiGDy6ULQECN1a39IddXmLEwtPsxmWcIwVMv+o56flg==:Y/py3Uk9SUxkhM/knNgv7mbQzh89Q1kdM+ruNuWr5Yo=
	
	public static void main(String[] args) {
		String password = "admin";
		PasswordHash ph = new org.glassfish.soteria.identitystores.hash.Pbkdf2PasswordHashImpl();
        Map<String, String> parameters = new HashMap<String,String>();
        parameters.put("Pbkdf2PasswordHash.Iterations", "3072");
        parameters.put("Pbkdf2PasswordHash.Algorithm", "PBKDF2WithHmacSHA512");
        parameters.put("Pbkdf2PasswordHash.SaltSizeBytes", "64");
        ph.initialize(parameters);		
		String out = ph.generate(password.toCharArray());
		System.out.println(out);
	}
	
}
