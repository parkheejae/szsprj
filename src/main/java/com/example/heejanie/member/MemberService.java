package com.example.heejanie.member;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.heejanie.core.Aes256;
import com.example.heejanie.core.SecretKey;
import com.example.heejanie.domain.Member;
import com.example.heejanie.repository.MemberRepository;

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
    public boolean signUp(Member member) throws Exception {
    	
    	if(StringUtils.isBlank(member.getUserId())
    			|| StringUtils.isBlank(member.getPassword())
    			|| StringUtils.isBlank(member.getRegNo())
    			|| StringUtils.isBlank(member.getUserName())) {
    		return false;
    	}
    	
    	Member newMember = memberRepository.save(Member.builder()
													.userId(member.getUserId())
													.password(Aes256.encrypt(member.getPassword(), secretKey.getPasswordKey()))
													.userName(member.getUserName())
													.regNo(Aes256.encrypt(member.getRegNo(), secretKey.getRegNoKey()))
													.build());
    	
    	if(newMember == null) {
    		return false;
    	}
    	
    	return true;
    }
 
}
