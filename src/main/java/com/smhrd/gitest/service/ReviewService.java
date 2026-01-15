package com.smhrd.gitest.service;

import java.util.List;
import java.util.Optional;

import com.smhrd.gitest.entity.MemberEntity;
import com.smhrd.gitest.entity.ReviewEntity;

public interface ReviewService {
    
    /**
     * 새로운 리뷰를 저장합니다.
     * @param user 작성자 정보
     * @param storeId 가게 ID
     * @param content 리뷰 내용
     */
    void saveReview(MemberEntity user, Long storeId, String content);
    
    /**
     * 특정 회원이 작성한 모든 리뷰를 조회합니다.
     * @param email 회원 이메일
     * @return 해당 회원의 리뷰 목록
     */
    List<ReviewEntity> findMyReviews(String email);

    /**
     * ★★★ 특정 가게에 달린 모든 리뷰를 찾는 메소드 명세를 여기에 추가해야 합니다. ★★★
     * @param storeId 가게 ID
     * @return 해당 가게의 리뷰 목록
     */
    List<ReviewEntity> findReviewsByStoreId(Long storeId);
    
    Optional<ReviewEntity> findReviewById(Long reviewId);
    ReviewEntity updateReview(Long reviewId, String content, String userEmail);
    void deleteReview(Long reviewId, String userEmail);
    
}
