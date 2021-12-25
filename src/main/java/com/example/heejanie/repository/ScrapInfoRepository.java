package com.example.heejanie.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.heejanie.domain.ScrapInfo;


public interface ScrapInfoRepository extends JpaRepository<ScrapInfo, String> {
	
	public void deleteByUserId(String userId);
}
