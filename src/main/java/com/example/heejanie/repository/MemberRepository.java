package com.example.heejanie.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.heejanie.domain.Member;


public interface MemberRepository extends JpaRepository<Member, String> {
	
	public Member findByUserIdAndPassword(String userId, String password);
	
//	
//	public Member save(Member member);
}
