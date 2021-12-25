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
		

        assertEquals("ȫ�浿", scrap.getJsonList().getScrap001().get(0).get("�̸�"));
        assertEquals("24000000", scrap.getJsonList().getScrap001().get(0).get("�����޾�"));
        assertEquals("2000000", scrap.getJsonList().getScrap002().get(0).get("�ѻ��ݾ�"));
	}
	
	@Test
	public void refund() {
		Map<String,String> map = refundService.refund("DAECADID", "ȫ�浿");
		

        assertEquals("74����", map.get("�ѵ�"));
        assertEquals("50��5õ��", map.get("������"));
        assertEquals("50��5õ��", map.get("ȯ�޾�"));
	}
}
