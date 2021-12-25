package com.example.heejanie.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpVO {
	
	//ȸ�� ID
	@Schema(description="ȸ�� ID", example = "DAECADID", required = true)
	String userId;
    
	//��й�ȣ
	@Schema(description="��� ��ȣ", example = "test1234", required = true)
	String password;
    
	//�̸�
	@Schema(description="ȸ�� �̸�", example = "ȫ�浿", required = true)
	String userName;
    
	//�ֹ� ��� ��ȣ
	@Schema(description="�ֹε�Ϲ�ȣ", example = "860824-1655068", required = true)
	String regNo;
    
}
