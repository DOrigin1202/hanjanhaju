package com.smhrd.gitest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.smhrd.gitest.entity.MemberEntity;
import com.smhrd.gitest.service.MemberWishlistService;
import com.smhrd.gitest.service.ReviewService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MyPageController {
	@Autowired
	private MemberWishlistService wishlistService;
	@Autowired
	private ReviewService reviewService;
	@GetMapping("/mypage")
	public String mypage(HttpSession session, Model model) {
		//1. 세션에서 로그인 사용자 정보 가져오기
		MemberEntity loginUser = (MemberEntity) session.getAttribute("loginUser");
		if(loginUser == null) {
			// 로그인 하지 않은 경우 로그인 페이지로 리다이렉트
			return "redirect:/login";
		}
		// 2. 로그인한 회원의 정보 마이페이지로 전달
		model.addAttribute("user",loginUser);
		// 내가 작성한 리뷰 찜목록 마이페이지에서 보여주기!
		
		model.addAttribute("myWishlist", wishlistService.getMyWishlist(loginUser.getEmail()));
		 // 내가 쓴 리뷰 리스트 추가
		model.addAttribute("user", loginUser);
	    model.addAttribute("myReviews", reviewService.findMyReviews(loginUser.getEmail()));
		return "mypage"; //templates/mypage.html
	}
	 @GetMapping("/myreviews")
	    public String myReviewsPage(HttpSession session, Model model) {
	        // 1. 세션에서 로그인 사용자 정보 가져오기
	        MemberEntity loginUser = (MemberEntity) session.getAttribute("loginUser");
	        if (loginUser == null) {
	            // 로그인하지 않은 경우 로그인 페이지로 리다이렉트
	            return "redirect:/login";
	        }

	        // 2. ReviewService를 사용하여 내가 쓴 리뷰 목록 조회
	        //    (이전 대화에서 ReviewService를 이미 주입했다고 가정합니다.)
	        model.addAttribute("myReviews", reviewService.findMyReviews(loginUser.getEmail()));

	        // 3. myreviews.html 템플릿을 렌더링
	        return "myreviews";
}
}
