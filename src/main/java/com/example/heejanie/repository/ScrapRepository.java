package com.example.heejanie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.heejanie.domain.Scrap;


public interface ScrapRepository extends JpaRepository<Scrap, String> {

	public void deleteByUserId(String userId);

	public List<Scrap> findByUserId(String userId);
}
