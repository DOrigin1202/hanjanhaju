package com.smhrd.gitest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;






@Controller

public class MainController {
	
	@GetMapping("/mainPage")
	public String main(){
		return "fast-main";
	}
	
	@GetMapping("/fast-main")
	public String goMain() {
	    return "fast-main";  // main.html로 이동
	}	
	

}



