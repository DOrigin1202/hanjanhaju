package com.smhrd.gitest.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "store_recommendation_score")
public class StoreRecommendationScoreEntity {

    @Id
    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "total_score", nullable = false)
    private Double totalScore;

    @Column(name = "top_tags")
    private String topTags;

    @Column(name = "analysis_date", nullable = false)
    private LocalDateTime analysisDate;

    // store 테이블과 1:1 관계 설정
    @OneToOne
    @MapsId // storeId 필드를 StoreEntity의 ID와 매핑
    @JoinColumn(name = "store_id")
    private StoreEntity store;
}