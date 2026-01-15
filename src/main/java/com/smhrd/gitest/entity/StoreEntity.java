package com.smhrd.gitest.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "store") // 테이블이 이미 생성됬기 때문에 name = 테이블명 지정
public class StoreEntity {
	

	    @Id //pk 값
	    @GeneratedValue(strategy = GenerationType.IDENTITY) 
	    //숫자 자동 증가 MySQL AUTO_INCREMENT
	    @Column(name="store_id")// DB 컬럼명은 store_id
	    private Long storeId;	// 필드명은 camelCase로 변경

	    @Column(name="shop_name", nullable = false) // not null 설정
	    private String shopName;

	    @Column(name="address" ,nullable = false)
	    private String address;

	    @Column(name = "rating")
	    private Double rating; // 평점은 소수점을 포함할 수 있으므로 Double

	    @Column(name = "photo_url", length = 2083) // URL은 길 수 있으므로 길이 지정
	    private String photoUrl;

	    @Column(name = "all_keywords", columnDefinition = "TEXT")
	    private String allKeywords; // 키워드는 길 수 있으므로 TEXT 타입 지정

	    @Column(name = "mid_tag", columnDefinition = "TEXT")
	    private String midTag;

	    @Column(name = "clean_address")
	    private String cleanAddress;

	    @Column(name = "lat")
	    private Double lat; // 위도

	    @Column(name = "lng")
	    private Double lng; // 경도
	    
	    // store_name, store_location, store_owner 필드는 이 클래스에서 완전히 삭제합니다.
	
	}

