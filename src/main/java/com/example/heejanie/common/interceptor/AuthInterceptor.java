package com.example.heejanie.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.heejanie.common.exception.TokenCheckException;
import com.example.heejanie.common.util.JwtTokenProvider;
import com.example.heejanie.domain.Member;

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

		String token = jwtTokenProvider.resolveToken(request);

		if(StringUtils.isBlank(token)) {
			throw new TokenCheckException("토큰키가 존재하지 않습니다.");
		}
		
		if(!jwtTokenProvider.expireToken(token)) {
			Member member = jwtTokenProvider.getUserInfo(token);
			
			request.setAttribute("sessionUserId", member.getUserId());
			request.setAttribute("sessionUserName", member.getUserName());
			
		} else {
			throw new TokenCheckException("토큰키가 만료되었습니다.");
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
