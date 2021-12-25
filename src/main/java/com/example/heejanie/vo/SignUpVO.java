package com.example.heejanie.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpVO {
	
	//회원 ID
	@Schema(description="회원 ID", example = "DAECADID", required = true)
	String userId;
    
	//비밀번호
	@Schema(description="비밀 번호", example = "test1234", required = true)
	String password;
    
	//이름
	@Schema(description="회원 이름", example = "홍길동", required = true)
	String userName;
    
	//주민 등록 번호
	@Schema(description="주민등록번호", example = "860824-1655068", required = true)
	String regNo;
    
}
