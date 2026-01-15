package com.smhrd.gitest.service;

import com.smhrd.gitest.entity.MemberWishlistEntity;
import com.smhrd.gitest.repository.MemberWishlistRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MemberWishlistService {

    @Autowired
    private MemberWishlistRepository wishlistRepository;

    // 찜 추가
    public String addWishlist(String email, Long store_id) {
        if (wishlistRepository.existsByEmailAndStoreId(email, store_id)) {
            return "already"; // 이미 찜함
        }
        MemberWishlistEntity entity = new MemberWishlistEntity();
        entity.setEmail(email);
        entity.setStoreId(store_id);
        wishlistRepository.save(entity);
        return "success";
    }

    // 찜 취소
    @Transactional
    public void removeWishlist(String email, Long store_id) {
        wishlistRepository.deleteByEmailAndStoreId(email, store_id);
    }

    // 내 찜한 술집 리스트
    public List<MemberWishlistEntity> getMyWishlist(String email) {
        return wishlistRepository.findAllByEmail(email);
    }
 // ★ 찜 여부 확인 메소드 추가
    public boolean existsByEmailAndStoreId(String email, Long storeId) {
        return wishlistRepository.existsByEmailAndStoreId(email, storeId);
    }
}