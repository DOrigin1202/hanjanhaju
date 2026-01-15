package com.smhrd.gitest.controller;

import com.smhrd.gitest.dto.StoreDto;
import com.smhrd.gitest.entity.MemberEntity;
import com.smhrd.gitest.entity.MemberWishlistEntity;
import com.smhrd.gitest.service.MemberWishlistService;
import com.smhrd.gitest.service.StoreService;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MemberWishlistController {

    @Autowired
    private MemberWishlistService wishlistService;
    
    @Autowired
    private StoreService storeService;

    // 찜 추가
    @PostMapping("/wishlist/add")
    public String addWishlist(@RequestParam Long storeId, HttpSession session) {
        MemberEntity user = (MemberEntity) session.getAttribute("loginUser");
        if (user == null) {
            return "redirect:/login";
        }
        wishlistService.addWishlist(user.getEmail(), storeId);
        return "redirect:/store/" + storeId; // 원하는 페이지로 리다이렉트
    }

    // 찜 취소
    @PostMapping("/wishlist/remove")
    public String removeWishlist(@RequestParam Long storeId, HttpSession session) {
        MemberEntity user = (MemberEntity) session.getAttribute("loginUser");
        if (user == null) {
            return "redirect:/login";
        }
        wishlistService.removeWishlist(user.getEmail(), storeId);
        return "redirect:/store/" + storeId;
    }

    // 내 찜 목록 보기 (마이페이지 등)
    @GetMapping("/wishlist/mine")
    public String myWishlist(HttpSession session, Model model) {
        MemberEntity user = (MemberEntity) session.getAttribute("loginUser");
        if (user == null) {
            return "redirect:/login";
        }

        // 1. 내 찜 목록(MemberWishlistEntity) 조회
        List<MemberWishlistEntity> myWishes = wishlistService.getMyWishlist(user.getEmail());

        // 2. 찜 목록의 store_id를 사용하여 실제 Store 정보(DTO) 리스트 생성
        List<StoreDto> wishlistedStores = myWishes.stream()
            .map(wish -> storeService.findStoreById(wish.getStoreId())) // Optional<StoreEntity>
            .filter(Optional::isPresent) // 비어있지 않은 것만
            .map(Optional::get)          // StoreEntity 추출
            .map(entity -> StoreDto.fromEntity(entity))   // ★★★ 이 부분을 람다로 수정 ★★★
            .collect(Collectors.toList());

        // 3. 모델에 StoreDto 리스트를 담아 전달
        model.addAttribute("wishlistedStores", wishlistedStores);
        
        return "mywishlist";
    }
   
}