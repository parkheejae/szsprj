package com.example.heejanie.refund;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.heejanie.vo.ScrapVO;

import io.swagger.v3.oas.annotations.Operation;

@Controller
public class RefundController {
	
	@Autowired
	RefundService refundService;
	/**
	 * ȸ�� ���
	 * @param paramMap
	 * @return
	 */
	@ResponseBody
	@PostMapping("/szs/scrap")
    @Operation(summary = "scrap", description = "ȸ�� scrap ����")
    public ResponseEntity<ScrapVO> scrap(HttpServletRequest request
    		          						, HttpServletResponse response) {
		String userId = (String)request.getAttribute("sessionUserId");
		ScrapVO scrapVO = refundService.scrap(userId);
		    
		scrapVO.setCode("SUCCESS");
		
	    return new ResponseEntity<>(scrapVO, HttpStatus.OK);
	    
    }
	/**
	 * ȸ�� ���
	 * @param paramMap
	 * @return
	 */
	@ResponseBody
	@PostMapping("/szs/refund")
	@Operation(summary = "���� �ݾ� ���", description = "scrap �����͸� ������ ���� �ݾ��� ��� �Ѵ�.")
	public ResponseEntity<Map<String, String>> srefundcrap(HttpServletRequest request
			, HttpServletResponse response) {
		String userId = (String)request.getAttribute("sessionUserId");
		String userName = (String)request.getAttribute("sessionUserName");
		Map<String, String> map = refundService.refund(userId, userName);
		
		map.put("code", "SUCCESS");
		
		return new ResponseEntity<>(map, HttpStatus.OK);
		
	}
}
