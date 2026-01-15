package com.smhrd.gitest.controller;

import com.smhrd.gitest.dto.StoreDto;
import com.smhrd.gitest.entity.MemberEntity;
import com.smhrd.gitest.service.MemberWishlistService;
import com.smhrd.gitest.service.StoreService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainPageController {

    @Autowired
    private StoreService storeService;

    @Autowired
    private MemberWishlistService wishlistService;

    // ★★★ start 페이지에서 "/main"으로 요청이 오도록 수정했으므로 경로를 "/main"으로 변경 ★★★
    @GetMapping("/main")
    public String mainPage(HttpSession session, Model model) {
        
        // 1. 로그인 사용자 정보 가져오기 (없으면 null)
        MemberEntity user = (MemberEntity) session.getAttribute("loginUser");

        // 2. 점수 기반으로 추천된 가게 목록(DTO 리스트)을 바로 가져옴
        List<StoreDto> topPicks = storeService.getTopRatedStores(10);

        // 3. (중요) 불필요한 엔티티 -> DTO 변환 과정을 완전히 삭제했습니다.

        // 4. 로그인 상태에 따라 찜 여부(wishlisted)를 각 DTO에 설정
        if (user != null) {
            // 로그인 상태: 각 가게마다 DB에서 찜 여부 체크
            for (StoreDto store : topPicks) {
                boolean isWishlisted = wishlistService.existsByEmailAndStoreId(user.getEmail(), store.getStoreId());
                store.setWishlisted(isWishlisted);
            }
        } else {
            // 비로그인 상태: 모든 가게의 찜 여부를 false로 설정
            for (StoreDto store : topPicks) {
                store.setWishlisted(false);
            }
        }

        // 5. 최종적으로 완성된 DTO 리스트를 모델에 담아 뷰로 전달
        model.addAttribute("topPicks", topPicks);

        return "main"; // templates/main.html 렌더링
    }
}