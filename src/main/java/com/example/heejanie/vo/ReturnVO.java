package com.example.heejanie.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;


@Data
public class ReturnVO {

	//ȸ�� ID
	@Schema(description="���� �ڵ�", example = "SUCCESS")
	String code;
	
	@Builder
	public ReturnVO(String code) {
		super();
		this.code = code;
	}
	
}
