package com.smhrd.gitest.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smhrd.gitest.entity.MemberEntity;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long>{

	
	
	//로그인 기능
	Optional<MemberEntity> findByEmail(String email);
		
	//닉네임,이메일 중복 확인
		boolean existsByNickname(String nickname);
		
		boolean existsByEmail(String email);
		
		 // 닉네임과 생년월일로 회원 찾기
	    Optional<MemberEntity> findByNicknameAndBirthdate(String nickname, LocalDate birthdate);
		
	    /**
	     * 이메일과 생년월일로 회원이 존재하는지 확인하는 쿼리 메소드
	     */
	    boolean existsByEmailAndBirthdate(String email, LocalDate birthdate);
}	
