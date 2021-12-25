package com.example.heejanie.refund;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.heejanie.member.MemberService;
import com.example.heejanie.repository.MemberRepository;
import com.example.heejanie.vo.ScrapVO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RefundServiceTest {

	@Autowired
	public RefundService refundService;
	
	@Test
	public void scrap() {
		ScrapVO scrap	 = refundService.scrap("DAECADID");
		

        assertEquals("홍길동", scrap.getJsonList().getScrap001().get(0).get("이름"));
        assertEquals("24000000", scrap.getJsonList().getScrap001().get(0).get("총지급액"));
        assertEquals("2000000", scrap.getJsonList().getScrap002().get(0).get("총사용금액"));
	}
	
	@Test
	public void refund() {
		Map<String,String> map = refundService.refund("DAECADID", "홍길동");
		

        assertEquals("74만원", map.get("한도"));
        assertEquals("50만5천원", map.get("공제액"));
        assertEquals("50만5천원", map.get("환급액"));
	}
}
