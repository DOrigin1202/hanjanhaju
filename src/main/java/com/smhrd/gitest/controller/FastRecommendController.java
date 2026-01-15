package com.smhrd.gitest.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import com.smhrd.gitest.service.FastRecommendService;

import reactor.core.publisher.Mono;


@Controller
public class FastRecommendController{
	private final FastRecommendService fastRecommendService;
	private final WebClient webClient;
	
	public FastRecommendController(FastRecommendService fastRecommendService) {
		this.fastRecommendService = fastRecommendService;
		this.webClient = WebClient.builder()
				.baseUrl("http://127.0.0.1:8001")//fastapi주소
				.build();
		
	}
	@GetMapping("/fast-recommend")
	public String recommendList(@RequestParam String dong,
			@RequestParam String emotion,
			@RequestParam String situation,
			Model model
			) {
		Mono<List> response = webClient.get()
				.uri(uriBuilder -> uriBuilder
				.path("/api/recommend")
				.queryParam("dong",dong)
				.queryParam("emotion",emotion)
				.queryParam("situation",situation)
				.build())
				.retrieve()// 이것에 의해서 파이썬 서버로 네트워크 요청이 날아감.
				.bodyToMono(List.class);
		List<Map<String, Object>> shopList = response.block();
		
		//fastapi로 post하여 db에 요청
		webClient.post()
			.uri("/api/save_recommend")
			.bodyValue(shopList)
			.retrieve()
			.bodyToMono(String.class)
			.block();
		
		
		model.addAttribute("shopList", shopList);
		
		return"fast-recommend";
	}
}
