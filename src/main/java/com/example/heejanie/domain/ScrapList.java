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
	
	//��ũ�� ���� ��ȣ
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "scrap_list_no")
	long scrapListNo;
	
	//��ũ�� ���� ��ȣ
	@Column(name = "scrap_info_no")
	long scrapInfoNo;

	//ȸ�� ID
	@Column(name = "user_id")
	String userId;
	
	//ȸ���
	@Column(name = "err_msg")
	String errMsg;
	
	//ȸ���
	@Column(name = "company")
	String company;
	
	//���� �ڵ�
	@Column(name = "svc_cd")
	String svcCd;
	
	//�ҵ� ����
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