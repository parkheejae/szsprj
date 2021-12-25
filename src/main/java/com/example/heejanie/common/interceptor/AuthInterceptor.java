package com.example.heejanie.common.interceptor;

import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.heejanie.common.exception.TokenCheckException;
import com.example.heejanie.common.util.JwtTokenProvider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor  {
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		System.out.println("start interceptor");
		
		String token = jwtTokenProvider.resolveToken(request);

		System.out.println("token : " + token);
		
		if(StringUtils.isBlank(token)) {
			throw new TokenCheckException("��ūŰ�� �������� �ʽ��ϴ�.");
		}
		
		if(!jwtTokenProvider.expireToken(token)) {
			String userId = jwtTokenProvider.getUserId(token);
			
			request.setAttribute("sessionUserId", userId);
			
		} else {
			throw new TokenCheckException("��ūŰ�� ����Ǿ����ϴ�.");
		}
		
		return true;

	}
	
	 public Claims parseJwtToken(String authorizationHeader) {
        validationAuthorizationHeader(authorizationHeader); // (1)
        String token = extractToken(authorizationHeader); // (2) 

        return Jwts.parser()
            .setSigningKey("secret") // (3)
            .parseClaimsJws(token) // (4)
            .getBody();
    }


    private void validationAuthorizationHeader(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
          throw new IllegalArgumentException();
        }
    }

    private String extractToken(String authorizationHeader) {
        return authorizationHeader.substring("Bearer ".length());
    }
}
