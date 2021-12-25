package com.example.heejanie.vo;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
public class LoginVO {

	//ȸ�� ID
	@Schema(description="ȸ�� ID", example = "DAECADID")
	String userId;
    
	//��й�ȣ
	@Schema(description="��� ��ȣ", example = "test1234")
	String password;
}
