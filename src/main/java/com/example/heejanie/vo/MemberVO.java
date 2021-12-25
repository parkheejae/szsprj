package com.example.heejanie.vo;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;


@Data
public class MemberVO {

	//���� �ڵ�
	@Schema(description="���� �ڵ�", example = "SUCCESS")
	String code;
	
	//ȸ�� ID
	@Schema(description="ȸ�� ID", example = "DAECADID")
	String userId;
    
	//�̸�
	@Schema(description="ȸ�� �̸�", example = "ȫ�浿")
	String userName;
    
	//��� ����
	@Schema(description="��� ����", example = "2021-12-24T15:00:00.000+00:00")
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
