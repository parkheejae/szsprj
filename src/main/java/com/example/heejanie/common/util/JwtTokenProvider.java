package com.example.heejanie.common.util;

import java.time.Duration;
import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
	
	@Autowired
    private SecretKey secretKey;
    
    private String jwtSecretKey;
    
    // ��ü �ʱ�ȭ, secretKey�� Base64�� ���ڵ��Ѵ�.
    @PostConstruct
    protected void init() {
    	jwtSecretKey = Base64.getEncoder().encodeToString(secretKey.getJwtKey().getBytes());
    }

    // JWT ��ū ����
    public String createToken(String userId, String userName) {
    	Date now = new Date();
    	
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // ���Ÿ��
                .setIssuer("fresh") // ��ū �߱���
                .setIssuedAt(now) // ��ū �߱� �ð�
                .setExpiration(new Date(now.getTime() + Duration.ofMinutes(30).toMillis())) // ��ū ���� �ð�
                .setSubject(userId)
                .claim("userId", userId) //ȸ�� ID
                .claim("userName", userName) //ȸ�� �̸�
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey) // keyvalue
                .compact();
    }


    // ��ū���� ȸ�� ���� ����
    public String getUserId(String jwtToken) {
        return (String) Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(jwtToken).getBody().getSubject();
    }

    // Request�� Header���� token ���� �����ɴϴ�. "X-AUTH-TOKEN" : "TOKEN��'
    public String resolveToken(HttpServletRequest request) {
        return (String) request.getSession().getAttribute("X-AUTH-TOKEN");
    }

    // ��ū�� ��ȿ�� + �������� Ȯ��
    public boolean expireToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(jwtToken);
            return claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
