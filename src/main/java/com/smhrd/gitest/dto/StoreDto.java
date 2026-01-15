package com.smhrd.gitest.dto;

import com.smhrd.gitest.entity.StoreEntity;
import lombok.Data;

@Data
public class StoreDto {

	private Long storeId;
    private String shopName;
    private String address;
    private Double rating;
    private String photoUrl;
    private String midTag;
    private Double lat;
    private Double lng;
    private Boolean wishlisted; // 찜 여부 필드는 그대로 유지

    // 필요한 경우 추가 필드 (예: 거리, 감정 태그 등)

 // Entity -> DTO 변환 메소드 (getter 이름도 camelCase로 변경)
    public static StoreDto fromEntity(StoreEntity entity) {
        StoreDto dto = new StoreDto();
        dto.setStoreId(entity.getStoreId());
        dto.setShopName(entity.getShopName());
        dto.setAddress(entity.getAddress());
        dto.setRating(entity.getRating());
        dto.setPhotoUrl(entity.getPhotoUrl());
        dto.setMidTag(entity.getMidTag());
        dto.setLat(entity.getLat());
        dto.setLng(entity.getLng());
        // wishlisted는 서비스 계층에서 별도로 설정하므로 여기서는 비워둡니다.
        return dto;
    }
}