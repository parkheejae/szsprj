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
		
		//ȸ�� ���� ��ȸ
		Member member = null;
		String reponseBody = "";
		String regNo ="";
		try {
			member = memberRepository.findById(userId).get();
			regNo = Aes256.decrypt(member.getRegNo(), secretKey.getRegNoKey());
			
		}catch (Exception e) {
			throw new ApiException("ȸ�� ���� ��ȸ ����");
		}
		
		// ������ SCRAP ����
		try {
			reponseBody = httpClient.sendPost(member.getUserName(), regNo);
		} catch (ClientProtocolException e) {
			throw new ApiException("��ũ�� ������ ��ȸ ���� ����");
		} catch (IOException e) {
			throw new ApiException("��ũ�� ������ ��ȸ ���� ����");
		} 
		JSONObject jsonObject = null;
		try {
			JSONParser jsonParser = new JSONParser();
			jsonObject = (JSONObject) jsonParser.parse(reponseBody);
			
		} catch (ParseException e) {
			throw new ApiException("���� ������ �б� ����");
		}
		
		//���� SCRAP ����
		deleteOldScrap(userId);
		
		//SCRAP ����
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
	 * ȯ�޾� ���
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
		returnMap.put("�̸�", userName);

		returnMap.put("�ѵ�", transFormAmt(limitAmt));
		returnMap.put("������", transFormAmt(deductibleAmt));
		returnMap.put("ȯ�޾�", transFormAmt(limitAmt < deductibleAmt? limitAmt : deductibleAmt));
		return returnMap;
	}
	
	/**
	 * �ݾ� format ����
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
				returnStr += checkZero(check, "��");
				str = str.substring(str.length() - 8);
			}
			if(str.length() > 4) {
				String check = str.substring(0, str.length() - 4);
				
				returnStr += checkZero(check, "��");
				str = str.substring(str.length() - 4);
			}
			if(!str.substring(str.length() - 4, str.length() - 3).equals("0")){
				returnStr += str.substring(0, str.length() - 3) + "õ";
			}
			
			if(returnStr.length() > 0) {
				returnStr += "��";
			}
		return returnStr;
	}
	
	//0üũ
	private String checkZero(String amt, String unit) {
		String str = "";
		
		for(int i = 0; i < amt.length(); i++) {
			if(!amt.substring(i).startsWith("0")) { //0���� ���� �ϸ� �о� / 0���� �������� ������ ���� ���� ���� ����
				str += amt.substring(i);  
				break;
			}
		}
		
		if(str.length()>0) { //���ڰ� �ִ� ��� �ݾ� ���� ���� / ���ڰ��ϳ��� ���� ��� ������������
			str+=unit;
		}
		
		return str;
	}
	
	/**
	 * �ѵ� �ݾ� ���
	 * @param paymentAmt
	 * @return
	 */
	private int calcLimitAmt(int paymentAmt) {
		
		int limitAmt = 0;
		
		if(paymentAmt <= 33000000) { 		//�����޾� 3300���� ����
			limitAmt = 740000;
		} else if(paymentAmt > 70000000) { 	//�����޾� 7000���� �ʰ�
			int calcAmt = 660000 - (int)((paymentAmt - 70000000) * 0.5);
			limitAmt = calcAmt < 500000? 50000 : calcAmt;
		} else {							//�����޾� 3300���� �ʰ� && 7000���� ����
			int calcAmt = 740000 - (int)((paymentAmt - 33000000)* 0.0008);
			limitAmt = calcAmt < 660000? 660000 : calcAmt;
		}
		
		return limitAmt;
	}
	
	/**
	 * ���� �ݾ� ���
	 * @param paymentAmt
	 * @return
	 */
	private int calcDeductibleAmt(int usedAmt) {

		int deductibleAmt = 0;
		
		if(usedAmt <= 1300000) { 	//���⼼�� 130���� ����
			deductibleAmt = (int)(usedAmt * 0.55);
		} else {					//���⼼�� 130���� �ʰ�
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
                    .incomeTitle((String)scrapObject.get("�ҵ泻��"))
                    .totalPaymentAmt((String)scrapObject.get("�����޾�"))
                    .businessStartDate((String)scrapObject.get("����������"))
                    .companyName((String)scrapObject.get("�����"))
                    .paymentDate((String)scrapObject.get("������"))
                    .businessEndDate((String)scrapObject.get("����������"))
                    .incomeType((String)scrapObject.get("�ҵ汸��"))
                    .companyRegNo((String)scrapObject.get("����ڵ�Ϲ�ȣ"))
                    .build());
            
        	map.put("�ҵ泻��", scrap.getIncomeTitle());
            map.put("�����޾�", scrap.getTotalPaymentAmt());
            map.put("����������", scrap.getBusinessStartDate());
            map.put("�����", scrap.getCompanyName());
            map.put("������", scrap.getPaymentDate());
            map.put("����������", scrap.getBusinessEndDate());
            map.put("�ҵ汸��", scrap.getIncomeType());
            map.put("����ڵ�Ϲ�ȣ", scrap.getCompanyRegNo());
            map.put("�̸�", member.getUserName());
            map.put("�ֹε�Ϲ�ȣ", regNo);
            
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
	                .incomeType((String)scrapObject.get("�ҵ汸��"))
	                .totalUsedAmt((String)scrapObject.get("�ѻ��ݾ�"))
	                .build());

            map.put("�ҵ泻��", scrap.getIncomeType());
            map.put("�ѻ��ݾ�", scrap.getTotalUsedAmt());
            
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
