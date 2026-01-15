package com.smhrd.gitest.controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

@Controller
public class FastController {
	/*@GetMapping("/fast-recommend")
	//비동기방식으로 추천하는 시스템 방식. 
	public String recommendList(@RequestParam(value="dong",required=false) String dong,
			@RequestParam(value="emotion", required=false) String emotion,
			@RequestParam(value="situation", required=false) String situation,
			Model model
			) {
	    String url = "http://127.0.0.1:8001/api/recommend"
	    		+"?dong="+dong
	    		+"&emotion="+emotion
	    		+"&situation="+situation;
	    
	    WebClient webClient = WebClient.create();
	    List<Map<String,Object>> shopList = webClient.get()
	    		.uri(url)
	    		.retrieve()
	    		.bodyToMono(List.class)
	    		.block();

	    // 임시 샘플 데이터 20개 (이미지 url, 별점, 주소는 임의로)
	    model.addAttribute("shops", shopList);
	    return "fast-recommend"; // fast-recommend.html
	}*/
}