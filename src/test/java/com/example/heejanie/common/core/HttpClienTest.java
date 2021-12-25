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
			String json = httpClient.sendPost("손오공", "820326-2715702");
			
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
			jsonObject =(JSONObject) jsonObject.get("jsonList");
			jsonObject.get("userId");
			String compare = "{\"jsonList\":{\"scrap002\":[{\"총사용금액\":1300000,\"소득구분\":\"산출세액\"}],\"scrap001\":[{\"소득내역\":\"급여\",\"총지급액\":94000000,\"업무시작일\":\"2020.10.03\",\"기업명\":\"지구행성0-1\",\"이름\":\"손오공\",\"지급일\":\"2020.11.02\",\"업무종료일\":\"2020.11.02\",\"주민등록번호\":\"820326-2715702\",\"소득구분\":\"근로소득(연간)\",\"사업자등록번호\":\"012-23-12345\"}],\"errMsg\":\"\",\"company\":\"삼쩜삼\",\"svcCd\":\"test01\",\"userId\":5},\"appVer\":\"2021112501\",\"hostNm\":\"jobis-codetest\",\"workerResDt\":\"2021-12-25T20:49:04.474692\",\"workerReqDt\":\"2021-12-25T20:49:04.474753\"}";
			JSONObject jsonObject2 = (JSONObject) jsonParser.parse(compare);
			jsonObject2 =(JSONObject) jsonObject.get("jsonList");
			jsonObject2.get("userId");
			
	        assertEquals((String)jsonObject.get("userId"), (String)jsonObject2.get("userId"));
	        
	        
		} catch (Exception e) {
			fail("error", e);
		}
	}

}
