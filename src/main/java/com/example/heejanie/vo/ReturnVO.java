package com.example.heejanie.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;


@Data
public class ReturnVO {

	//회원 ID
	@Schema(description="응답 코드", example = "SUCCESS")
	String code;
	
	@Builder
	public ReturnVO(String code) {
		super();
		this.code = code;
	}
	
}
