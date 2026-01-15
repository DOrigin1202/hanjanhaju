package com.smhrd.gitest.entity;

import jakarta.persistence.*;// * jakarta.persistence. 에 해당하는 모든 import
import lombok.Data;

@Entity
@Data
@Table(name = "review")
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long review_id;

    // FK: member_entity.email
    @ManyToOne //리뷰는 회원 술집 둘다와 다대일 관계이므로 사용함
    @JoinColumn(name = "email", referencedColumnName = "email", nullable = false)
    // 왜래키 이름 명시 및 not null 설정
    private MemberEntity member;

    // FK: store.store_id
    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private StoreEntity store;

    @Column(nullable = false, columnDefinition = "TEXT")// mysql의 text 타입을 명시적지정
    private String content;

    private String image;
}