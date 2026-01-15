package com.smhrd.gitest.service;

import java.util.List;
import java.util.Optional;

import com.smhrd.gitest.dto.StoreDto;
import com.smhrd.gitest.entity.StoreEntity;

public interface StoreService {
	  /**
	   * 서비스 인터페이스 -> 기능명세
     * 메인 페이지용 추천 술집 목록 조회
     * @param username 현재 로그인된 사용자 아이디(또는 닉네임)
     * @return StoreDto 리스트
     */
	// 전체 술집 목록 조회
    List<StoreEntity> findAllStores();
    
    // id로 특정 술집 조회
    Optional<StoreEntity> findStoreById(Long storeId);
    
    // 상위 5개 술집만 가져오기
    List<StoreEntity> getTopPicks();
    
    // 태그 기반 추천 메소드
    List<StoreDto> recommendStoresByTags(List<String>tagNames);
    
    // 점수 기반으로 추천 가게 목록을 가져오는 메소드
    List<StoreDto> getTopRatedStores(int limit);
    
 // 필요에 따라 추가 기능 선언
    // List<StoreDto> findByEmotion(String emotion);
}
