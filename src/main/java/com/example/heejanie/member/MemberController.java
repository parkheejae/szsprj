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

@Tag(name = "회원 정보 컨트롤러", description = "회원 등록, 정보 조회, 로그인등 회원과 관련된 내용 처리")
@RestController
public class MemberController {

	@Autowired
	MemberService memberService;

	/**
	 * 회원 등록
	 * @param paramMap
	 * @return
	 */
	@ResponseBody
	@PostMapping("/szs/signup")
    @Operation(summary = "회원 가입", description = "회원 정보를 저장 합니다.")
    public ResponseEntity<Void>  signUp(HttpServletRequest request
    		          , HttpServletResponse response, @Parameter @RequestBody Member member) {
		
		//회원등록
		boolean successFlag = memberService.signUp(member);
		
		if(!successFlag) {
			return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
		}
	    
	    return new ResponseEntity<>(HttpStatus.OK);
	    
    }

}
