package com.smhrd.gitest.repository;

import com.smhrd.gitest.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<StoreEntity, Long> {

    // 1. 가게 이름으로 검색 (부분 일치)
    // ★★★ findByStoreNameContaining -> findByShopNameContaining 으로 수정 ★★★
	// keyword로 담아주는 이유 : 사용자가 입력한 값을 담는 변수이기 떄문!
    List<StoreEntity> findByShopNameContaining(String keyword);

    // 2. 주소로 검색 (부분 일치)
    // ★★★ findByStoreLocationContaining -> findByAddressContaining 으로 수정 ★★★
    List<StoreEntity> findByAddressContaining(String keyword);
    
    // 3. 메인 페이지 Top 5 추천 (ID 순)
    // ★★★ findTop5ByOrderByStoreIdAsc -> findTop5ByOrderByIdAsc 로 수정 (storeId -> id) ★★★
    List<StoreEntity> findTop5ByOrderByStoreIdAsc();
    
    // 4. 태그 기반 추천 시스템용 쿼리 (이 코드는 수정할 필요 없음)
    // 네이티브 쿼리는 엔티티 필드명이 아닌, 실제 DB 컬럼명('store_id')을 사용하므로 그대로 둡니다.
    @Query(value = "SELECT store_id FROM store_tag WHERE tag_id IN :tagIds GROUP BY store_id ORDER BY COUNT(store_id) DESC LIMIT 20",
           nativeQuery = true)
    List<Long> findStoreIdsByTags(@Param("tagIds") List<Long> tagIds);
}