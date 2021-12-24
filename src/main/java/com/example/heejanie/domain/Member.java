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
	
	//회원 ID
	@Id
	@Column(name = "user_id")
	@Schema(description="회원 ID", example = "DAECADID")
	String userId;
    
	//비밀번호
	@Column(name = "password")
	@Schema(description="비밀 번호", example = "test1234")
	String password;
    
	//이름
	@Column(name = "user_name")
	@Schema(description="회원 이름", example = "홍길동")
	String userName;
    
	//주민 등록 번호
	@Column(name = "reg_no")
	@Schema(description="주민등록번호", example = "860824-1655068")
	String regNo;
    
	//등록 일자
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
