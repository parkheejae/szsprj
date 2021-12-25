package com.example.heejanie.vo;

import java.util.List;
import java.util.Map;

import lombok.Data;


@Data
public class ScrapListVO {
	
	List<Map<String,String>> scrap001;
	
	List<Map<String,String>> scrap002;
	
	String errMSg;
	
	String company;
	
	String svcCd;
	
	String userId;
}
