package com.example.heejanie.vo;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
public class LoginVO {

	//회원 ID
	@Schema(description="회원 ID", example = "DAECADID")
	String userId;
    
	//비밀번호
	@Schema(description="비밀 번호", example = "test1234")
	String password;
}
