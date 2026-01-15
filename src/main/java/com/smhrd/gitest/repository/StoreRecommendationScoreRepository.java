package com.smhrd.gitest.repository;

import com.smhrd.gitest.entity.StoreRecommendationScoreEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface StoreRecommendationScoreRepository extends JpaRepository<StoreRecommendationScoreEntity, Long> {

    // 점수가 높은 순서대로 가게의 점수 정보를 가져오는 쿼리 (페이징 처리)
    @Query("SELECT srs FROM StoreRecommendationScoreEntity srs ORDER BY srs.totalScore DESC")
    List<StoreRecommendationScoreEntity> findTopStoresByScore(Pageable pageable);
}