package com.example.heejanie.vo;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;


@Data
public class MemberVO {

	//응답 코드
	@Schema(description="응답 코드", example = "SUCCESS")
	String code;
	
	//회원 ID
	@Schema(description="회원 ID", example = "DAECADID")
	String userId;
    
	//이름
	@Schema(description="회원 이름", example = "홍길동")
	String userName;
    
	//등록 일자
	@Schema(description="등록 일자", example = "2021-12-24T15:00:00.000+00:00")
	Date createdAt;

	@Builder
	public MemberVO(String code, String userId, String userName, Date createdAt) {
		super();
		this.code = code;
		this.userId = userId;
		this.userName = userName;
		this.createdAt = createdAt;
	}
	
	
}
