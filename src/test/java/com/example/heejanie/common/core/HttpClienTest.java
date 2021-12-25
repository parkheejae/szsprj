package com.example.heejanie.common.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.heejanie.common.util.HttpClient;

@RunWith(SpringRunner.class)
@SpringBootTest
class HttpClienTest {
	
	@Autowired
	HttpClient httpClient;
	@Test
	void test() {
		try {
			String json = httpClient.sendPost("�տ���", "820326-2715702");
			
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
			jsonObject =(JSONObject) jsonObject.get("jsonList");
			jsonObject.get("userId");
			String compare = "{\"jsonList\":{\"scrap002\":[{\"�ѻ��ݾ�\":1300000,\"�ҵ汸��\":\"���⼼��\"}],\"scrap001\":[{\"�ҵ泻��\":\"�޿�\",\"�����޾�\":94000000,\"����������\":\"2020.10.03\",\"�����\":\"�����༺0-1\",\"�̸�\":\"�տ���\",\"������\":\"2020.11.02\",\"����������\":\"2020.11.02\",\"�ֹε�Ϲ�ȣ\":\"820326-2715702\",\"�ҵ汸��\":\"�ٷμҵ�(����)\",\"����ڵ�Ϲ�ȣ\":\"012-23-12345\"}],\"errMsg\":\"\",\"company\":\"������\",\"svcCd\":\"test01\",\"userId\":5},\"appVer\":\"2021112501\",\"hostNm\":\"jobis-codetest\",\"workerResDt\":\"2021-12-25T20:49:04.474692\",\"workerReqDt\":\"2021-12-25T20:49:04.474753\"}";
			JSONObject jsonObject2 = (JSONObject) jsonParser.parse(compare);
			jsonObject2 =(JSONObject) jsonObject.get("jsonList");
			jsonObject2.get("userId");
			
	        assertEquals((String)jsonObject.get("userId"), (String)jsonObject2.get("userId"));
	        
	        
		} catch (Exception e) {
			fail("error", e);
		}
	}

}
