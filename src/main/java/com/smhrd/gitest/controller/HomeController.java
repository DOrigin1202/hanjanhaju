package com.smhrd.gitest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	 /**
     * 루트 URL ("/")로 접속 시, 시작 페이지 ("/start")로 리다이렉트합니다.
     */
    @GetMapping("/")
    public String redirectToStart() {
        return "redirect:/start";
    }
}
