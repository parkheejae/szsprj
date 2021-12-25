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
    
    // 객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
    	jwtSecretKey = Base64.getEncoder().encodeToString(secretKey.getJwtKey().getBytes());
    }

    // JWT 토큰 생성
    public String createToken(String userId, String userName) {
    	Date now = new Date();
    	
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // 헤더타입
                .setIssuer("fresh") // 토큰 발급자
                .setIssuedAt(now) // 토큰 발급 시간
                .setExpiration(new Date(now.getTime() + Duration.ofMinutes(30).toMillis())) // 토큰 만료 시간
                .setSubject(userId)
                .claim("userId", userId) //회원 ID
                .claim("userName", userName) //회원 이름
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey) // keyvalue
                .compact();
    }


    // 토큰에서 회원 정보 추출
    public String getUserId(String jwtToken) {
        return (String) Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(jwtToken).getBody().getSubject();
    }

    // Request의 Header에서 token 값을 가져옵니다. "X-AUTH-TOKEN" : "TOKEN값'
    public String resolveToken(HttpServletRequest request) {
        return (String) request.getSession().getAttribute("X-AUTH-TOKEN");
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean expireToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(jwtToken);
            return claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
