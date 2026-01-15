package com.smhrd.gitest.controller;

import com.smhrd.gitest.entity.MemberEntity;
import com.smhrd.gitest.entity.ReviewEntity;
import com.smhrd.gitest.service.ReviewService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/reviews/add")
    public String addReview(@RequestParam("storeId") Long storeId, // ★★★ 여기서 'storeId'가 선언되었습니다.
                            @RequestParam("content") String content,
                            HttpSession session) {
        
        // 1. 세션에서 로그인한 사용자 정보 가져오기
        MemberEntity loginUser = (MemberEntity) session.getAttribute("loginUser");

        // 2. 비로그인 사용자는 로그인 페이지로 리다이렉트
        if (loginUser == null) {
            return "redirect:/login";
        }

        // 3. 서비스 호출하여 리뷰 저장
        reviewService.saveReview(loginUser, storeId, content);

        // 4. 리뷰를 작성한 가게의 상세 페이지로 다시 돌아가기
        // ★★★ 'storeId'는 이 메소드 안에서만 유효합니다. ★★★
        return "redirect:/store/" + storeId; 
    }
    	
    // === 리뷰 수정 기능 ===

    // 1. 리뷰 수정 폼을 보여주는 페이지
    @GetMapping("/reviews/edit/{reviewId}")
    public String editReviewForm(@PathVariable("reviewId") Long reviewId, HttpSession session, Model model) {
        MemberEntity loginUser = (MemberEntity) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }

        // 수정하려는 리뷰 정보를 가져옴
        ReviewEntity review = reviewService.findReviewById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리뷰입니다. id=" + reviewId));
        
        // ★★★ (매우 중요) 본인이 쓴 리뷰가 맞는지 확인 ★★★
        if (!review.getMember().getEmail().equals(loginUser.getEmail())) {
            // 본인 리뷰가 아니면 에러 페이지 또는 이전 페이지로 리다이렉트 (여기서는 메인으로)
            return "redirect:/";
        }

        model.addAttribute("review", review);
        return "edit_review_form"; // templates/edit_review_form.html
    }

    // 2. 수정한 리뷰 내용을 DB에 업데이트
    @PostMapping("/reviews/update")
    public String updateReview(@RequestParam("reviewId") Long reviewId,
                               @RequestParam("content") String content,
                               HttpSession session) {
        MemberEntity loginUser = (MemberEntity) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }

        // 서비스에 리뷰 수정 요청 (내부에서 권한 확인 로직 추가 권장)
        ReviewEntity updatedReview = reviewService.updateReview(reviewId, content, loginUser.getEmail());

        // 수정 완료 후, 해당 리뷰가 있었던 가게 상세 페이지로 이동
        return "redirect:/store/" + updatedReview.getStore().getStoreId();
    }

    // === 리뷰 삭제 기능 ===

    @PostMapping("/reviews/delete")
    public String deleteReview(@RequestParam("reviewId") Long reviewId,
                               @RequestParam("storeId") Long storeId,
                               HttpSession session) {
        MemberEntity loginUser = (MemberEntity) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }

        // 서비스에 리뷰 삭제 요청 (내부에서 권한 확인 로직 추가 권장)
        reviewService.deleteReview(reviewId, loginUser.getEmail());

        // 삭제 완료 후, 해당 리뷰가 있었던 가게 상세 페이지로 이동
        return "redirect:/store/" + storeId;
    }
   
}