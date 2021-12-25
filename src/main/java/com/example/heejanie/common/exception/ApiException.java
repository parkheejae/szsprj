package com.example.heejanie.common.exception;

import lombok.Data;

@Data
public class ApiException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	String resultMessage;

	public ApiException(String resultMessage) {
		super();
		this.resultMessage = resultMessage;
	}
	
	
}
