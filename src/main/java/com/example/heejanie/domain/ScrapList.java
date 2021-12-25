package com.example.heejanie.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name= "scrap_lists_tbl")
@Entity
public class ScrapList {
	
	//스크랩 정보 번호
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "scrap_list_no")
	long scrapListNo;
	
	//스크랩 정보 번호
	@Column(name = "scrap_info_no")
	long scrapInfoNo;

	//회원 ID
	@Column(name = "user_id")
	String userId;
	
	//회사명
	@Column(name = "err_msg")
	String errMsg;
	
	//회사명
	@Column(name = "company")
	String company;
	
	//서비스 코드
	@Column(name = "svc_cd")
	String svcCd;
	
	//소득 내역
	@Column(name = "scrap_user_id")
	String scrapUserID;
	
	@Builder
	public ScrapList(long scrapInfoNo, String userId, String errMsg, String company, String svcCd, String scrapUserID) {
		super();
		this.errMsg = errMsg;
		this.scrapInfoNo = scrapInfoNo;
		this.userId = userId;
		this.company = company;
		this.svcCd = svcCd;
		this.scrapUserID = scrapUserID;
	}
    
	
}