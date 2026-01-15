package com.smhrd.gitest.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smhrd.gitest.repository.MemberRepository;

@Controller
@RestController
@RequestMapping("/api")
public class RegisterControlloer {
	@Autowired
    private MemberRepository memberRepository;

    @GetMapping("/check-email")
    public Map<String, Boolean> checkEmail(@RequestParam String value) {
        boolean exists = memberRepository.existsByEmail(value);
        return Collections.singletonMap("duplicate", exists);
    }

    @GetMapping("/check-nickname")
    public Map<String, Boolean> checkNickname(@RequestParam String value) {
        boolean exists = memberRepository.existsByNickname(value);
        return Collections.singletonMap("duplicate", exists);
    }
}
