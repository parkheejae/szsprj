package com.example.heejanie.common.Advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.heejanie.common.exception.ApiException;
import com.example.heejanie.common.exception.TokenCheckException;

import io.jsonwebtoken.ExpiredJwtException;

@RestControllerAdvice
public class DefaultControllerAdvice {
	
    @ExceptionHandler(TokenCheckException.class)
    public ResponseEntity<Map<String, String>> handlerException(TokenCheckException e){
        Map<String, String> result = new HashMap<String, String>();
        result.put("code", "ERROR");
        result.put("message", e.getResultMessage());
 
        return new ResponseEntity<Map<String, String>>(result, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Map<String, String>> handlerException(ApiException e){
        Map<String, String> result = new HashMap<String, String>();
        result.put("code", "ERROR");
        result.put("message", e.getResultMessage());
 
        return new ResponseEntity<Map<String, String>>(result, HttpStatus.OK);
    }
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Map<String, String>> handlerException(ExpiredJwtException e){
    	Map<String, String> result = new HashMap<String, String>();
    	result.put("code", "ERROR");
    	result.put("message", "토큰키가 만료되었습니다.");
    	
    	return new ResponseEntity<Map<String, String>>(result, HttpStatus.OK);
    }
    
}
