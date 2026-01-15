package com.smhrd.gitest.controller;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smhrd.gitest.service.MemberService;

@Controller
public class FindController {
	@Autowired
	private MemberService memberService;
	
	// 아이디/비밀번호 찾기 "선택 페이지"를 보여주는 역할
    @GetMapping("/find")
    public String findChoicePage() {
        return "find_choice"; // templates/find_choice.html 템플릿을 렌더링
    }
	
	  // 아이디 찾기 폼을 보여주는 페이지
    @GetMapping("/find/id")
    public String findIdForm() {
        return "find_id_form"; // templates/find_id_form.html
    }

    // 아이디 찾기 처리
    @PostMapping("/find/id")
    public String handleFindId(@RequestParam String nickname,
                               @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate birthdate,
                               Model model) {
        Optional<String> foundEmail = memberService.findEmailByNicknameAndBirthdate(nickname, birthdate);

        if (foundEmail.isPresent()) {
            model.addAttribute("foundEmail", foundEmail.get());
        } else {
            model.addAttribute("errorMessage", "일치하는 회원 정보가 없습니다.");
        }
        return "find_id_result"; // templates/find_id_result.html
    }
    // === 비밀번호 재설정 기능 (새로 추가) ===

    // 1. 비밀번호 찾기(이메일 입력) 폼을 보여주는 페이지
    @GetMapping("/find/pw")
    public String findPwForm() {
        return "find_password_form"; // templates/find_pw_form.html
    }

    // 2. 이메일 및 생년월일을 확인 후, 새 비밀번호 입력 폼으로 이동
    @PostMapping("/find/pw")
    public String handleFindPw(@RequestParam String email,
                               @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate birthdate, // ★ 생년월일 파라미터 추가
                               Model model) {
        // ★ 서비스 호출 시 생년월일도 함께 전달
        boolean accountExists = memberService.checkAccountExists(email, birthdate); 

        if (accountExists) {
            model.addAttribute("email", email);
            return "reset_password_form";
        } else {
            model.addAttribute("errorMessage", "입력한 정보와 일치하는 회원이 없습니다.");
            return "find_password_form";
        }
    }

    // 3. 새로운 비밀번호로 업데이트 처리
    @PostMapping("/reset/password")
    public String handleResetPassword(@RequestParam String email, @RequestParam String newPassword) {
        memberService.updatePassword(email, newPassword);
        return "redirect:/reset/password/success"; // 성공 페이지로 리다이렉트
    }

    // 4. 비밀번호 재설정 완료 페이지 보여주기
    @GetMapping("/reset/password/success")
    public String resetPasswordSuccess() {
        return "reset_password_success"; // templates/reset_password_success.html
    }

}
