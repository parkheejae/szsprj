package com.example.heejanie.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.heejanie.common.util.Aes256;

@RunWith(SpringRunner.class)
@SpringBootTest
class Aes256Test {
	
	@Value("${secretkey.password-key}")
	String passwordKey;
	
	@Test
	void test() {
		try {

	        assertEquals(Aes256.encrypt("TEST1234", passwordKey), "oMAW9KO2oWjii74Q7a1bpA==");
		} catch (Exception e) {
			fail("error", e);
		}
	}

}
