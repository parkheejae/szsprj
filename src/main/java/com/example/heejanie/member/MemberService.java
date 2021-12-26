package com.example.heejanie.member;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.heejanie.common.exception.ApiException;
import com.example.heejanie.common.util.Aes256;
import com.example.heejanie.common.util.SecretKey;
import com.example.heejanie.domain.Member;
import com.example.heejanie.repository.MemberRepository;
import com.example.heejanie.vo.SignUpVO;

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
    		throw new ApiException("아이디 중복입니다. 다른 아이디로 시도해 주세요.");
		}
		String regNo = Aes256.encrypt(member.getRegNo(), secretKey.getRegNoKey());
		
		if(memberRepository.existsByRegNo(regNo)) {
    		throw new ApiException("가입된 주민 등록 번호입니다. 가입된 회원 정보를 확인 해주세요.");
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
    	Optional<Member> member = memberRepository.findById(userId);
    	if(member.isEmpty()) {
    		throw new ApiException("등록되지 않은 ID입니다.");
    	}
        return member.get();
    }
    
    /**
     * 회원 정보 조회
     * @param memberNo
     * @return
     */
    public boolean checkPasword(String dbPassword, String password) {
        try {
			return StringUtils.equals(dbPassword, Aes256.encrypt(password, secretKey.getPasswordKey()));
		} catch (Exception e) {
			throw new ApiException("비밀번호 오류입니다.");
		}
    }
}
