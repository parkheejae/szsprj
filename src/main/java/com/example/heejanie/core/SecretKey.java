package com.example.heejanie.core;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties("secretkey")
public class SecretKey {
	
	String passwordKey;
	
	String regNoKey;
}
