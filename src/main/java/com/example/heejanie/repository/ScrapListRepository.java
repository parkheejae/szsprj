package com.example.heejanie.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.heejanie.domain.ScrapList;


public interface ScrapListRepository extends JpaRepository<ScrapList, String> {

	public void deleteByUserId(String userId);
}
