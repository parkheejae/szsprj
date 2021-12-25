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
@Table(name= "scrap_infos_tbl")
@Entity
public class ScrapInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "scrap_info_no")
	long scrapInfoNo;
	//회원 ID
	@Column(name = "user_id")
	String userId;
	
	//App Version
	@Column(name = "app_ver")
	String appVer;
	
	//호스트 명
	@Column(name = "host_name")
	String hostName;
	
	//응답일자
	@Column(name = "worker_Res_Dt")
	String workerResDt;

	//요청일자
	@Column(name = "worker_Req_Dt")
	String workerReqDt;
	
	@Builder
	public ScrapInfo(long scrapInfoNo, String userId, String appVer, String hostName, String workerResDt,
			String workerReqDt) {
		this.scrapInfoNo = scrapInfoNo;
		this.userId = userId;
		this.appVer = appVer;
		this.hostName = hostName;
		this.workerResDt = workerResDt;
		this.workerReqDt = workerReqDt;
	}
    
	
	
}