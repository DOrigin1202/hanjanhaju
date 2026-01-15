package com.smhrd.gitest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smhrd.gitest.entity.MemberEntity;

@Repository
public interface RegisterRepository extends JpaRepository<MemberEntity, Long>{
	boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
}
