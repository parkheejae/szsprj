package com.example.heejanie.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.heejanie.domain.Member;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MemberRepositoryTest {

	@Autowired
	TestEntityManager testEntityManager;
	
	@Autowired
	MemberRepository memberRepositry;
	
	@Test
	public void saveMemberAndGetMemberTest() {
		 //given
        Member member = Member.builder()
                .userId("TESTUSER")
                .password("12345678901234567890")
                .userName("테스트유저")
                .regNo("111111-2222222")
                .build();

        //when
        testEntityManager.persist(member);

        //then
        assertEquals(member, memberRepositry.findById(member.getUserId()).get());
	}

	
	
	
	
	
	
	
	
	
}	
