package com.example.heejanie.common.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.heejanie.common.util.JwtTokenProvider;
import com.example.heejanie.domain.Member;

@RunWith(SpringRunner.class)
@SpringBootTest
class JwtTokenProviderTest {
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	@Test
	void test() {
		try {
			String token = jwtTokenProvider.createToken("DAECADID", "¹ÚÈñÀç");
			
			Member member = jwtTokenProvider.getUserInfo(token);
	        assertEquals(member.getUserId(), "DAECADID");
	        assertEquals(member.getUserName(), "¹ÚÈñÀç");
	        
	        
		} catch (Exception e) {
			fail("error", e);
		}
	}

}
