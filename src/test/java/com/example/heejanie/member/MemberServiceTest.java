package com.example.heejanie.member;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.heejanie.common.util.Aes256;
import com.example.heejanie.domain.Member;
import com.example.heejanie.repository.MemberRepository;
import com.example.heejanie.vo.SignUpVO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberServiceTest {

	@Value("${secretkey.password-key}")
	String passwordKey;

	@Value("${secretkey.reg-no-key}")
	String regNoKey;
	
	@Autowired
	public MemberService memberService;

	@Autowired
	MemberRepository memberRepository;
	
	public void signUp() {
		
		try {
			
			memberService.signUp(SignUpVO.builder()
					.userId("MEMBERTEST")
					.password("TEST1234")
					.userName("ȫ�浿")
					.regNo("850808-1234567")
					.build());
			
			Member member = memberRepository.findById("MEMBERTEST").get();
			
	        assertEquals(member.getPassword(), Aes256.encrypt("TEST1234", passwordKey));
	        
	        assertEquals(member.getRegNo(), Aes256.encrypt("850808-1234567", regNoKey));
	        
		} catch (Exception e) {
			fail("error", e);
		}
	}
	
	@Test
	public void checkPasword() {
		
		try {
			boolean check = memberService.checkPasword("bxURmX+9hrKfDbp2P7kG9g==", "test1234");
			
			
	        assertEquals(true, check);
	        
		} catch (Exception e) {
			fail("error", e);
		}
	}
	
	@Test
	public void memberInfo() {
		
		try {
			Member member = memberService.memberInfo("DAECADID");
			
			
			assertEquals("DAECADID", member.getUserId());
			
		} catch (Exception e) {
			fail("error", e);
		}
	}
}
