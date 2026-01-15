package com.smhrd.gitest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StartController {
	@GetMapping("/start")
	public String StartPage() {
		return "start"; // templates/start.html
	}
}
