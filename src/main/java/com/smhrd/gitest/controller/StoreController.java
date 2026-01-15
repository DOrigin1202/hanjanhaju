package com.smhrd.gitest.controller;

import com.smhrd.gitest.dto.StoreDto;
import com.smhrd.gitest.entity.MemberEntity;
import com.smhrd.gitest.entity.ReviewEntity; // ReviewEntity import
import com.smhrd.gitest.service.MemberWishlistService;
import com.smhrd.gitest.service.ReviewService; // ReviewService import
import com.smhrd.gitest.service.StoreService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@Controller
public class StoreController {

    @Autowired
    private StoreService storeService;

    @Autowired
    private MemberWishlistService wishlistService;
    
    @Autowired
    private ReviewService reviewService; // 리뷰 서비스를 주입

    @GetMapping("/store/{id}")
    public String storeDetail(@PathVariable("id") Long storeId, HttpSession session, Model model) {
        
        // 1. 가게 정보 조회
        StoreDto storeDto = storeService.findStoreById(storeId)
                .map(entity -> StoreDto.fromEntity(entity))
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 가게입니다. id=" + storeId));
        
        // 2. 로그인 상태 확인 및 찜 여부 설정
        MemberEntity user = (MemberEntity) session.getAttribute("loginUser");
        if (user != null) {
            boolean isWishlisted = wishlistService.existsByEmailAndStoreId(user.getEmail(), storeId);
            storeDto.setWishlisted(isWishlisted);
        } else {
            storeDto.setWishlisted(false);
        }

        // 3. 해당 가게의 모든 리뷰 조회
        List<ReviewEntity> reviews = reviewService.findReviewsByStoreId(storeId); // 이 메소드는 ReviewService에 새로 추가해야 함

        // 4. 모델에 데이터 담기
        model.addAttribute("store", storeDto);
        model.addAttribute("reviews", reviews);
        
        return "detail"; // templates/detail.html
    }
}