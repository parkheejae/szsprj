package com.example.heejanie.vo;

import java.util.List;

import lombok.Data;


@Data
public class ScrapVO {
	
	String code;
	
	ScrapListVO jsonList;
	
	String appVer;
	
	String hostNm;
	
	String workerResDt;
	
	String workerReqDt;
	
}
