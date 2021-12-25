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

@Tag(name = "ȯ�� ����", description = "ȸ���� ���� �ݾ�, �ѵ��� ȯ�ޱ� ���� ó��")
@Service
public class MemberService {
	
	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	SecretKey secretKey;
    /**
     * ȸ�� ���
     * @param paramMap
     * @return
     * @throws Exception 
     */
    public void signUp(SignUpVO member) throws Exception {
    	
    	if(StringUtils.isBlank(member.getUserId())
    			|| StringUtils.isBlank(member.getPassword())
    			|| StringUtils.isBlank(member.getRegNo())
    			|| StringUtils.isBlank(member.getUserName())) {
    		throw new ApiException("�ʼ��Ķ���Ͱ� �����Ǿ����ϴ�.");
    	}
    	
		if(memberRepository.existsById(member.getUserId())) {
    		throw new ApiException("���̵� �ߺ��Դϴ�. �ٸ� ���̵�� �õ��� �ּ���");
		}
		String regNo = Aes256.encrypt(member.getRegNo(), secretKey.getRegNoKey());
		
		if(memberRepository.existsByRegNo(regNo)) {
    		throw new ApiException("���Ե� �ֹ� ��� ��ȣ�Դϴ�. ���Ե� ȸ�� ������ Ȯ�� ���ּ���");
		}
		
    	Member newMember = memberRepository.save(Member.builder()
													.userId(member.getUserId())
													.password(Aes256.encrypt(member.getPassword(), secretKey.getPasswordKey()))
													.userName(member.getUserName())
													.regNo(regNo)
													.build());
    	
    	if(newMember == null) {
    		throw new ApiException("ȸ�� ��Ͽ� �����Ͽ����ϴ�.");
    	}
    	
    }
    
    /**
     * ȸ�� ���� ��ȸ
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
     * ȸ�� ���� ��ȸ
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
     * JWT ��ū ����
     * @param memberNo
     * @param email
     * @return
     */
    public String makeJwtToken(String userId, String userName) {
        Date now = new Date();

        return Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // ���Ÿ��
            .setIssuer("fresh") // ��ū �߱���
            .setIssuedAt(now) // ��ū �߱� �ð�
            .setExpiration(new Date(now.getTime() + Duration.ofMinutes(30).toMillis())) // ��ū ���� �ð�
            .claim("userId", userId) // ȸ�� ��ȣ
            .claim("userName", userName) //email
            .signWith(SignatureAlgorithm.HS256, "secretKey") // keyvalue
            .compact();
    }
    
    
    
    
}
