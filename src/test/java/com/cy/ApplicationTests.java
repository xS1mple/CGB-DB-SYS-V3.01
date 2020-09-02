package com.cy;

import java.security.Key;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.UUID;

import org.apache.shiro.crypto.AesCipherService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApplicationTests {

	@Test
	void testApp() {
		String pwd=UUID.randomUUID().toString();
		Encoder encoder=Base64.getEncoder();
		String newStr=encoder.encodeToString(pwd.getBytes());
		System.out.println(newStr);
		
		 AesCipherService cipherService = new AesCipherService();
		 Key key=cipherService.generateNewKey();
		 System.out.println(key.getAlgorithm());
		
	}
}
