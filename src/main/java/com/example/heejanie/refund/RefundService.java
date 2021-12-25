package com.example.heejanie.refund;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.heejanie.common.exception.ApiException;
import com.example.heejanie.common.util.Aes256;
import com.example.heejanie.common.util.HttpClient;
import com.example.heejanie.common.util.SecretKey;
import com.example.heejanie.domain.Member;
import com.example.heejanie.domain.Scrap;
import com.example.heejanie.domain.ScrapInfo;
import com.example.heejanie.domain.ScrapList;
import com.example.heejanie.repository.MemberRepository;
import com.example.heejanie.repository.ScrapInfoRepository;
import com.example.heejanie.repository.ScrapListRepository;
import com.example.heejanie.repository.ScrapRepository;
import com.example.heejanie.vo.ScrapListVO;
import com.example.heejanie.vo.ScrapVO;

@Service
public class RefundService {
	
	@Autowired
	HttpClient httpClient;

	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	ScrapInfoRepository scrapInfoRepository;
	
	@Autowired
	ScrapListRepository scrapListRepository;
	
	@Autowired
	ScrapRepository scrapRepository;
	
	@Autowired
	SecretKey secretKey;
	
	@Transactional
	public ScrapVO scrap(String userId) {
		
		//회원 정보 조회
		Member member = null;
		String reponseBody = "";
		String regNo ="";
		try {
			member = memberRepository.findById(userId).get();
			regNo = Aes256.decrypt(member.getRegNo(), secretKey.getRegNoKey());
			
		}catch (Exception e) {
			throw new ApiException("회원 정보 조회 실패");
		}
		
		// 데이터 SCRAP 연동
		try {
			reponseBody = httpClient.sendPost(member.getUserName(), regNo);
		} catch (ClientProtocolException e) {
			throw new ApiException("스크랩 데이터 조회 연동 실패");
		} catch (IOException e) {
			throw new ApiException("스크랩 데이터 조회 연동 실패");
		} 
		JSONObject jsonObject = null;
		try {
			JSONParser jsonParser = new JSONParser();
			jsonObject = (JSONObject) jsonParser.parse(reponseBody);
			
		} catch (ParseException e) {
			throw new ApiException("응답 데이터 읽기 실패");
		}
		
		//기등록 SCRAP 삭제
		deleteOldScrap(userId);
		
		//SCRAP 정보
		ScrapInfo scrapInfo = scrapInfoRepository.save(ScrapInfo.builder()
				.appVer((String)jsonObject.get("appVer"))
				.hostName((String)jsonObject.get("hostNm"))
				.userId(member.getUserId())
				.workerResDt((String)jsonObject.get("workerResDt"))
				.workerReqDt((String)jsonObject.get("workerReqDt")).build());
		
		ScrapVO scrapVO = new ScrapVO();
		
		scrapVO.setAppVer(scrapInfo.getAppVer());
		scrapVO.setHostNm(scrapInfo.getHostName());
		scrapVO.setWorkerResDt(scrapInfo.getWorkerReqDt());
		scrapVO.setWorkerReqDt(scrapInfo.getWorkerReqDt());
		
		//jsonList
		JSONObject jsonListObj = (JSONObject)jsonObject.get("jsonList");
    	ScrapListVO scrapListVO = new ScrapListVO();
    	
        
        ScrapList scrapList = scrapListRepository.save(ScrapList.builder()
        		.scrapInfoNo(scrapInfo.getScrapInfoNo())
				.userId(member.getUserId())
                .errMsg((String)jsonListObj.get("errMsg"))
                .company((String)jsonListObj.get("company"))
    			.svcCd((String)jsonListObj.get("svcCd"))
    			.scrapUserID((String)jsonListObj.get("userId")).build());
        		
        
        scrapListVO.setErrMSg(scrapList.getErrMsg());
        scrapListVO.setCompany(scrapList.getCompany());
        scrapListVO.setSvcCd(scrapList.getSvcCd());
        scrapListVO.setUserId(scrapList.getScrapUserID());
        
        //scrap001
        List<Map<String, String>> scrap001 = setScrap001(member, regNo, jsonListObj, scrapList);

        //scrap002
        List<Map<String, String>> scrap002 = setScrap002(jsonListObj, scrapList, member.getUserId());
        
        scrapListVO.setScrap001(scrap001);
        scrapListVO.setScrap002(scrap002);
        
        scrapVO.setJsonList(scrapListVO);
        
		return scrapVO;
	}
	
	/**
	 * 환급액 계산
	 * @param userId
	 * @param userName
	 * @return
	 */
	public Map<String,String> refund(String userId, String userName) { 
		
		List<Scrap> list = scrapRepository.findByUserId(userId);
		
		int paymentAmt = 0;
		int usedAmt = 0;
		
		for(Scrap scrap : list) {
			paymentAmt += Integer.parseInt(StringUtils.defaultIfBlank(scrap.getTotalPaymentAmt(), "0"));
			usedAmt += Integer.parseInt(StringUtils.defaultIfBlank(scrap.getTotalUsedAmt(), "0"));
		}
		
		int limitAmt =  this.calcLimitAmt(paymentAmt);
		int deductibleAmt = this.calcDeductibleAmt(usedAmt);
		
		Map<String,String> returnMap =new HashMap<String,String>();
		returnMap.put("이름", userName);

		returnMap.put("한도", transFormAmt(limitAmt));
		returnMap.put("공제액", transFormAmt(deductibleAmt));
		returnMap.put("환급액", transFormAmt(limitAmt < deductibleAmt? limitAmt : deductibleAmt));
		return returnMap;
	}
	
	/**
	 * 금액 format 변경
	 * @param amt
	 * @return
	 */
	private String transFormAmt(int amt) {
		String returnStr = "";
		String str = String.valueOf(amt);
		if(str.length()<3) {
			return "0";
		}

			if(str.length() > 8) {
				String check = str.substring(0, str.length() - 8);
				returnStr += checkZero(check, "억");
				str = str.substring(str.length() - 8);
			}
			if(str.length() > 4) {
				String check = str.substring(0, str.length() - 4);
				
				returnStr += checkZero(check, "만");
				str = str.substring(str.length() - 4);
			}
			if(!str.substring(str.length() - 4, str.length() - 3).equals("0")){
				returnStr += str.substring(0, str.length() - 3) + "천";
			}
			
			if(returnStr.length() > 0) {
				returnStr += "원";
			}
		return returnStr;
	}
	
	//0체크
	private String checkZero(String amt, String unit) {
		String str = "";
		
		for(int i = 0; i < amt.length(); i++) {
			if(!amt.substring(i).startsWith("0")) { //0으로 시작 하면 패쓰 / 0으로 시작하지 않으면 남은 숫자 전부 리턴
				str += amt.substring(i);  
				break;
			}
		}
		
		if(str.length()>0) { //숫자가 있는 경우 금액 단위 설정 / 글자가하나도 없는 경우 단위설정안함
			str+=unit;
		}
		
		return str;
	}
	
	/**
	 * 한도 금액 계산
	 * @param paymentAmt
	 * @return
	 */
	private int calcLimitAmt(int paymentAmt) {
		
		int limitAmt = 0;
		
		if(paymentAmt <= 33000000) { 		//총지급액 3300만원 이하
			limitAmt = 740000;
		} else if(paymentAmt > 70000000) { 	//총지급액 7000만원 초과
			int calcAmt = 660000 - (int)((paymentAmt - 70000000) * 0.5);
			limitAmt = calcAmt < 500000? 50000 : calcAmt;
		} else {							//총지급액 3300만원 초과 && 7000만원 이하
			int calcAmt = 740000 - (int)((paymentAmt - 33000000)* 0.0008);
			limitAmt = calcAmt < 660000? 660000 : calcAmt;
		}
		
		return limitAmt;
	}
	
	/**
	 * 공제 금액 계싼
	 * @param paymentAmt
	 * @return
	 */
	private int calcDeductibleAmt(int usedAmt) {

		int deductibleAmt = 0;
		
		if(usedAmt <= 1300000) { 	//산출세엑 130만원 이하
			deductibleAmt = (int)(usedAmt * 0.55);
		} else {					//산출세엑 130만원 초과
			deductibleAmt = 715000 - (int)((usedAmt - 1300000)* 0.3);
		}
		
		return deductibleAmt;
	}

	private List<Map<String, String>> setScrap001(Member member, String regNo, JSONObject jsonListObj,
			ScrapList scrapList) {
		JSONArray scrapArr = (JSONArray) jsonListObj.get("scrap001");
        
        List<Map<String,String>> scrap001 = new ArrayList<Map<String,String>>();
        for(int n=0; n<scrapArr.size(); n++){
        	Map<String,String> map = new HashMap<String,String>();
            JSONObject scrapObject = (JSONObject) scrapArr.get(n);
            
            
            Scrap scrap = scrapRepository.save(Scrap.builder()
                    .scrapListNo(scrapList.getScrapListNo())
                    .type("001")
    				.userId(member.getUserId())
                    .incomeTitle((String)scrapObject.get("소득내역"))
                    .totalPaymentAmt((String)scrapObject.get("총지급액"))
                    .businessStartDate((String)scrapObject.get("업무시작일"))
                    .companyName((String)scrapObject.get("기업명"))
                    .paymentDate((String)scrapObject.get("지급일"))
                    .businessEndDate((String)scrapObject.get("업무종료일"))
                    .incomeType((String)scrapObject.get("소득구분"))
                    .companyRegNo((String)scrapObject.get("사업자등록번호"))
                    .build());
            
        	map.put("소득내역", scrap.getIncomeTitle());
            map.put("총지급액", scrap.getTotalPaymentAmt());
            map.put("업무시작일", scrap.getBusinessStartDate());
            map.put("기업명", scrap.getCompanyName());
            map.put("지급일", scrap.getPaymentDate());
            map.put("업무종료일", scrap.getBusinessEndDate());
            map.put("소득구분", scrap.getIncomeType());
            map.put("사업자등록번호", scrap.getCompanyRegNo());
            map.put("이름", member.getUserName());
            map.put("주민등록번호", regNo);
            
            scrap001.add(map);
        }
		return scrap001;
	}


	private List<Map<String, String>> setScrap002(JSONObject jsonListObj, ScrapList scrapList, String userId) {
		JSONArray scrapArr;
		scrapArr = (JSONArray) jsonListObj.get("scrap002");

        List<Map<String,String>> scrap002 = new ArrayList<Map<String,String>>();
        
        for(int n=0; n<scrapArr.size(); n++){

        	Map<String,String> map = new HashMap<String,String>();
        	
            JSONObject scrapObject = (JSONObject) scrapArr.get(n);
            
            Scrap scrap = scrapRepository.save(Scrap.builder()
	                .scrapListNo(scrapList.getScrapListNo())
	                .type("002")
					.userId(userId)
	                .incomeType((String)scrapObject.get("소득구분"))
	                .totalUsedAmt((String)scrapObject.get("총사용금액"))
	                .build());

            map.put("소득내역", scrap.getIncomeType());
            map.put("총사용금액", scrap.getTotalUsedAmt());
            
            scrap002.add(map);
        }
		return scrap002;
	}


	private void deleteOldScrap(String userId) {
		scrapInfoRepository.deleteByUserId(userId);

		scrapListRepository.deleteByUserId(userId);

		scrapRepository.deleteByUserId(userId);
	}
}
