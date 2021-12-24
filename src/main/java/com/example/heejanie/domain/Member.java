package com.example.heejanie.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name= "members_tbl")
@Entity
public class Member {
	
	//ȸ�� ID
	@Id
	@Column(name = "user_id")
	@Schema(description="ȸ�� ID", example = "DAECADID")
	String userId;
    
	//��й�ȣ
	@Column(name = "password")
	@Schema(description="��� ��ȣ", example = "test1234")
	String password;
    
	//�̸�
	@Column(name = "user_name")
	@Schema(description="ȸ�� �̸�", example = "ȫ�浿")
	String userName;
    
	//�ֹ� ��� ��ȣ
	@Column(name = "reg_no")
	@Schema(description="�ֹε�Ϲ�ȣ", example = "860824-1655068")
	String regNo;
    
	//��� ����
	@CreatedDate
	@Column(name = "created_At")
	@Schema(hidden = true)
	Date createdAt;
	  
    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }
	
	@Builder
	public Member(String userId, String password, String userName, String regNo) {
		super();
		this.userId = userId;
		this.password = password;
		this.userName = userName;
		this.regNo = regNo;
	}
	
}
