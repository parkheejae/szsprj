package com.example.heejanie.member;

import java.time.Duration;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.heejanie.common.exception.ApiException;
import com.example.heejanie.common.util.Aes256;
import com.example.heejanie.common.util.SecretKey;
import com.example.heejanie.domain.Member;
import com.example.heejanie.repository.MemberRepository;
import com.example.heejanie.vo.SignUpVO;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "환급 정보", description = "회원의 공제 금액, 한도등 환급금 관련 처리")
@Service
public class MemberService {
	
	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	SecretKey secretKey;
    /**
     * 회원 등록
     * @param paramMap
     * @return
     * @throws Exception 
     */
    public void signUp(SignUpVO member) throws Exception {
    	
    	if(StringUtils.isBlank(member.getUserId())
    			|| StringUtils.isBlank(member.getPassword())
    			|| StringUtils.isBlank(member.getRegNo())
    			|| StringUtils.isBlank(member.getUserName())) {
    		throw new ApiException("필수파라미터가 누락되었습니다.");
    	}
    	
		if(memberRepository.existsById(member.getUserId())) {
    		throw new ApiException("아이디 중복입니다. 다른 아이디로 시도해 주세요");
		}
		String regNo = Aes256.encrypt(member.getRegNo(), secretKey.getRegNoKey());
		
		if(memberRepository.existsByRegNo(regNo)) {
    		throw new ApiException("가입된 주민 등록 번호입니다. 가입된 회원 정보를 확인 해주세요");
		}
		
    	Member newMember = memberRepository.save(Member.builder()
													.userId(member.getUserId())
													.password(Aes256.encrypt(member.getPassword(), secretKey.getPasswordKey()))
													.userName(member.getUserName())
													.regNo(regNo)
													.build());
    	
    	if(newMember == null) {
    		throw new ApiException("회원 등록에 실패하였습니다.");
    	}
    	
    }
    
    /**
     * 회원 정보 조회
     * @param memberNo
     * @return
     */
    public Member memberInfo(String userId) {
    	Member member = memberRepository.findById(userId).get();
    	if(member == null) {
    		return new Member();
    	}
        return member;
    }
    
    /**
     * 회원 정보 조회
     * @param memberNo
     * @return
     */
    public boolean checkPasword(String dbPassword, String password) {
        try {
        	System.out.println(dbPassword);
        	System.out.println(Aes256.encrypt(password, secretKey.getPasswordKey()));
			return StringUtils.equals(dbPassword, Aes256.encrypt(password, secretKey.getPasswordKey()));
		} catch (Exception e) {
			return false;
		}
    }
    
    /**
     * JWT 토큰 생성
     * @param memberNo
     * @param email
     * @return
     */
    public String makeJwtToken(String userId, String userName) {
        Date now = new Date();

        return Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // 헤더타입
            .setIssuer("fresh") // 토큰 발급자
            .setIssuedAt(now) // 토큰 발급 시간
            .setExpiration(new Date(now.getTime() + Duration.ofMinutes(30).toMillis())) // 토큰 만료 시간
            .claim("userId", userId) // 회원 번호
            .claim("userName", userName) //email
            .signWith(SignatureAlgorithm.HS256, "secretKey") // keyvalue
            .compact();
    }
    
    
    
    
}
