package com.smhrd.gitest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smhrd.gitest.entity.ReviewEntity;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long>{
// 이메일로 내가 쓴 리뷰 전체 조회
	List<ReviewEntity> findAllByMember_Email(String email);
	 // ★★★ 특정 가게(store_id)에 달린 모든 리뷰를 찾는 메소드 새로 추가
	List<ReviewEntity> findAllByStore_StoreId(Long storeId);
}
