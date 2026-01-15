package com.smhrd.gitest.controller;

import com.smhrd.gitest.entity.MemberEntity;
import com.smhrd.gitest.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate; // LocalDate 사용을 위해 추가
import org.springframework.format.annotation.DateTimeFormat; // 날짜 형식 변환을 위해 추가

@Controller
public class MemberController {

    @Autowired
    MemberService memberService;

    // 회원가입 폼을 보여주는 페이지
    @GetMapping("/register")
    public String registerPage() {
        return "register"; // templates/register.html 렌더링
    }

    // 회원가입 처리
    @PostMapping("/register")
    public String register(@RequestParam String email, @RequestParam String pw,
                           @RequestParam String nickname, @RequestParam int age,
                           @RequestParam String gender,
                           @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate birthdate) { // ★ String -> LocalDate로 변경
        MemberEntity entity = new MemberEntity();
        entity.setEmail(email);
        entity.setPw(pw);
        entity.setNickname(nickname);
        entity.setAge(age);
        entity.setGender(gender);
        entity.setBirthdate(birthdate); // ★ LocalDate 타입으로 저장

        String result = memberService.register(entity);

        if ("success".equals(result)) {
            return "redirect:/login";
        } else if ("duplicate".equals(result)) {
            return "redirect:/register?error=duplicate";
        }
        return "redirect:/register?error=fail";
    }

    // 로그인 폼을 보여주는 페이지
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String pw, HttpSession session) {
        MemberEntity user = memberService.login(email, pw);
        if (user != null) {
            session.setAttribute("loginUser", user);
            return "redirect:/main";
        } else {
            return "redirect:/login?error";
        }
    }

    // 로그아웃 처리
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/start";
    }
}