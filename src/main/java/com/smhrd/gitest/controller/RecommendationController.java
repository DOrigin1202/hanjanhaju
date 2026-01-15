package com.smhrd.gitest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smhrd.gitest.dto.StoreDto;
import com.smhrd.gitest.service.StoreService;

import jakarta.servlet.http.HttpSession;

@Controller
public class RecommendationController {
	@Autowired
	private StoreService storeService;
	
	 @PostMapping("/recommend")
	    public String getRecommendations(@RequestParam("tags") List<String> tags, Model model,HttpSession session) {
	        // 서비스 호출하여 추천 술집 리스트 받기
	        List<StoreDto> recommendedStores = storeService.recommendStoresByTags(tags);
	        
	        // 사용자가 선택한 태그를 세션에 저장
	        session.setAttribute("lastRecommendedTags", tags);

	        // 모델에 추천 결과 담기
	        model.addAttribute("recommendedStores", recommendedStores);
	        model.addAttribute("selectedTags", tags);

	        // ★★★ 추천 결과를 보여줄 페이지로 이동 ★★★
	        return "recommend"; // templates/result.html
	    }
	 @GetMapping("/recommend")
	    public String showLastRecommendations(HttpSession session, Model model) {
	        // 1. 세션에서 이전에 선택했던 태그 목록 가져오기
	        List<String> lastTags = (List<String>) session.getAttribute("lastRecommendedTags");

	        // 2. 세션에 태그 정보가 없으면 메인 페이지로 이동 (예외 처리)
	        if (lastTags == null || lastTags.isEmpty()) {
	            return "redirect:/main"; // 태그 선택 페이지로
	        }

	        // 3. 세션의 태그로 추천 로직 다시 실행
	        List<StoreDto> recommendedStores = storeService.recommendStoresByTags(lastTags);

	        // 4. 모델에 데이터 담기
	        model.addAttribute("recommendedStores", recommendedStores);
	        model.addAttribute("selectedTags", lastTags);

	        // 5. 추천 결과 페이지 보여주기
	        return "recommend";
	    }
	 
	 
}
