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
@Table(name= "scraps_tbl")
@Entity
public class Scrap {
	

	
	//회원 ID
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "scrap_no")
	long scraNo;
	
	//회원 ID
	@Column(name = "scrap_List_no")
	long scrapListNo;
	
	//회원 ID
	@Column(name = "user_id")
	String userId;
	
	
	//스크랩 구분(001,002)
	@Column(name = "type")
	String type;
	
	//소득 구분
	@Column(name = "income_type")
	String incomeType;
	
	//소득 내역
	@Column(name = "imcome_title")
	String incomeTitle;
    
	//총지급액
	@Column(name = "total_payment_amt")
	String totalPaymentAmt;
    
	//업무 시작일
	@Column(name = "user_name")
	String businessStartDate;
    
	//기업명
	@Column(name = "company_name")
	String companyName;

	//지급일
	@Column(name = "payment_date")
	String paymentDate;
	
	//업무 종료일
	@Column(name = "business_end_date")
	String businessEndDate;

	//사업자 등록번호
	@Column(name = "company_reg_no")
	String companyRegNo;

	//총사용금액
	@Column(name = "total_used_amt")
	String totalUsedAmt;
	
	@Builder
	public Scrap(long scrapListNo, String type,String userId, String incomeType, String incomeTitle, String totalPaymentAmt,
			String businessStartDate, String companyName, String paymentDate, String businessEndDate,
			String companyRegNo, String totalUsedAmt) {
		super();
		this.scrapListNo = scrapListNo;
		this.type = type;
		this.userId = userId;
		this.incomeType = incomeType;
		this.incomeTitle = incomeTitle;
		this.totalPaymentAmt = totalPaymentAmt;
		this.businessStartDate = businessStartDate;
		this.companyName = companyName;
		this.paymentDate = paymentDate;
		this.businessEndDate = businessEndDate;
		this.companyRegNo = companyRegNo;
		this.totalUsedAmt = totalUsedAmt;
	}
    
	
	
	
}