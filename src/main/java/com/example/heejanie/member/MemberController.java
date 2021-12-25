package com.example.heejanie.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.heejanie.common.exception.ApiException;
import com.example.heejanie.common.util.JwtTokenProvider;
import com.example.heejanie.domain.Member;
import com.example.heejanie.vo.LoginVO;
import com.example.heejanie.vo.MemberVO;
import com.example.heejanie.vo.ReturnVO;
import com.example.heejanie.vo.SignUpVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "ȸ�� ���� ��Ʈ�ѷ�", description = "ȸ�� ���, ���� ��ȸ, �α��ε� ȸ���� ���õ� ���� ó��")
@RestController
public class MemberController {

	@Autowired
	MemberService memberService;
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;

	/**
	 * ȸ�� ���
	 * @param paramMap
	 * @return
	 */
	@ResponseBody
	@PostMapping("/szs/signup")
    @Operation(summary = "ȸ�� ����", description = "ȸ�� ������ ���� �մϴ�.")
    public ResponseEntity<ReturnVO> signUp(HttpServletRequest request
    		          						, HttpServletResponse response
    		          						, @Parameter @RequestBody SignUpVO signUpVO) {
		
		try {
			memberService.signUp(signUpVO);
		    
		} catch (ApiException ae) {
			throw ae;
		} catch (Exception e) {
			throw new ApiException("ȸ����Ͽ� �����Ͽ����ϴ�.");
		}
		
	    return new ResponseEntity<>(ReturnVO.builder().code("SUCCESS").build(), HttpStatus.OK);
	    
    }

	/**
	 * �α���(jwt)
	 * @param request
	 * @param paramMap
	 * @return
	 */
	@PostMapping("/szs/login")
	@Operation(summary = "ȸ�� �α���")
    public ResponseEntity<ReturnVO> login(HttpServletRequest request
										, HttpServletResponse response
										, @Parameter @RequestBody LoginVO loginVO) {
		String jwtToken = "";
		try {
			Member member = memberService.memberInfo(loginVO.getUserId());
			
			if(memberService.checkPasword(member.getPassword(), loginVO.getPassword())) {
				jwtToken = jwtTokenProvider.createToken(member.getUserId(), member.getUserName());
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
			}
			
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
		}
		
		request.getSession().setAttribute("X-AUTH-TOKEN", jwtToken);
		
	    return new ResponseEntity<>(ReturnVO.builder().code("SUCCESS").build(), HttpStatus.OK);
    }
	
	/**
	 * ȸ�� ���� ��ȸ
	 * @param request
	 * @param paramMap
	 * @return
	 */
	@PostMapping("/szs/me")
	@Operation(summary = "ȸ�� ���� ��ȸ")
    public ResponseEntity<MemberVO> memberInfo(HttpServletRequest request, HttpServletResponse response) {
		String userId = (String)request.getAttribute("sessionUserId");

		Member member = memberService.memberInfo(userId);
		MemberVO memberVO = MemberVO.builder()
								.code("SUCCESS")
								.userId(member.getUserId())
								.userName(member.getUserName())
								.createdAt(member.getCreatedAt())
								.build();
	    return new ResponseEntity<MemberVO>(memberVO, HttpStatus.OK);
    }
}
