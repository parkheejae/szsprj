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
	

	
	//ȸ�� ID
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "scrap_no")
	long scraNo;
	
	//ȸ�� ID
	@Column(name = "scrap_List_no")
	long scrapListNo;
	
	//ȸ�� ID
	@Column(name = "user_id")
	String userId;
	
	
	//��ũ�� ����(001,002)
	@Column(name = "type")
	String type;
	
	//�ҵ� ����
	@Column(name = "income_type")
	String incomeType;
	
	//�ҵ� ����
	@Column(name = "imcome_title")
	String incomeTitle;
    
	//�����޾�
	@Column(name = "total_payment_amt")
	String totalPaymentAmt;
    
	//���� ������
	@Column(name = "user_name")
	String businessStartDate;
    
	//�����
	@Column(name = "company_name")
	String companyName;

	//������
	@Column(name = "payment_date")
	String paymentDate;
	
	//���� ������
	@Column(name = "business_end_date")
	String businessEndDate;

	//����� ��Ϲ�ȣ
	@Column(name = "company_reg_no")
	String companyRegNo;

	//�ѻ��ݾ�
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