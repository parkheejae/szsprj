package com.example.heejanie.member;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.heejanie.core.ResultCode;
import com.example.heejanie.domain.Member;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "ȸ�� ���� ��Ʈ�ѷ�", description = "ȸ�� ���, ���� ��ȸ, �α��ε� ȸ���� ���õ� ���� ó��")
@RestController
public class MemberController {

	@Autowired
	MemberService memberService;

	/**
	 * ȸ�� ���
	 * @param paramMap
	 * @return
	 */
	@ResponseBody
	@PostMapping("/szs/signup")
    @Operation(summary = "ȸ�� ����", description = "ȸ�� ������ ���� �մϴ�.")
    public ResponseEntity<Void>  signUp(HttpServletRequest request
    		          , HttpServletResponse response, @Parameter @RequestBody Member member) {
		
		//ȸ�����
		boolean successFlag = memberService.signUp(member);
		
		if(!successFlag) {
			return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
		}
	    
	    return new ResponseEntity<>(HttpStatus.OK);
	    
    }

}
