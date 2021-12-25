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

@Tag(name = "회원 정보 컨트롤러", description = "회원 등록, 정보 조회, 로그인등 회원과 관련된 내용 처리")
@RestController
public class MemberController {

	@Autowired
	MemberService memberService;
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;

	/**
	 * 회원 등록
	 * @param paramMap
	 * @return
	 */
	@ResponseBody
	@PostMapping("/szs/signup")
    @Operation(summary = "회원 가입", description = "회원 정보를 저장 합니다.")
    public ResponseEntity<ReturnVO> signUp(HttpServletRequest request
    		          						, HttpServletResponse response
    		          						, @Parameter @RequestBody SignUpVO signUpVO) {
		
		try {
			memberService.signUp(signUpVO);
		    
		} catch (ApiException ae) {
			throw ae;
		} catch (Exception e) {
			throw new ApiException("회원등록에 실패하였습니다.");
		}
		
	    return new ResponseEntity<>(ReturnVO.builder().code("SUCCESS").build(), HttpStatus.OK);
	    
    }

	/**
	 * 로그인(jwt)
	 * @param request
	 * @param paramMap
	 * @return
	 */
	@PostMapping("/szs/login")
	@Operation(summary = "회원 로그인")
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
	 * 회원 정보 조회
	 * @param request
	 * @param paramMap
	 * @return
	 */
	@PostMapping("/szs/me")
	@Operation(summary = "회원 정보 조회")
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
