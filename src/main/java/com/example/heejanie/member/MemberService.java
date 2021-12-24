package com.example.heejanie.member;

import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.heejanie.core.Aes256;
import com.example.heejanie.domain.Member;
import com.example.heejanie.repository.MemberRepository;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class MemberService {
	
	@Autowired
	MemberRepository memberRepository;
    /**
     * 회원 등록
     * @param paramMap
     * @return
     */
    public boolean signUp(Member member) {

    	Member newMember = memberRepository.save(member);
    	
    	if(newMember == null) {
    		return false;
    	}
    	
    	return true;
    }
 
}
