package com.example.heejanie.common.exception;

import lombok.Data;

@Data
public class TokenCheckException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	String resultMessage;

	public TokenCheckException(String resultMessage) {
		super();
		this.resultMessage = resultMessage;
	}
	
	
}
