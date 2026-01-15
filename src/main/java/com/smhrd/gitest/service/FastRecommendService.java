package com.smhrd.gitest.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
@Service
public class FastRecommendService {
	private final WebClient webClient;
	
	public FastRecommendService(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.baseUrl("http.//127.0.0.1:8001").build();
	}
	
	//추천 리스트 받기
	public List<Map<String, Object>> getRecommend(String dong, String emotion, String situation){
		return webClient.get()
				.uri(uriBuilder -> uriBuilder
				.path("/api/recommend")
				.queryParam("dong",dong)
				.queryParam("emotion",emotion)
				.queryParam("situation",situation)
				.build())
			.retrieve()
			.bodyToFlux(new ParameterizedTypeReference<Map<String, Object>>(){})
			.collectList()
			.block();

	}
	//추천 리스트 저장(POST)
	public void saveRecommend(List<Map<String, Object>> recommendList) {
		webClient.post()
		.uri("/api/save_recommend")
		.contentType(MediaType.APPLICATION_JSON)
		.bodyValue(recommendList)
		.retrieve()
		.bodyToMono(Void.class)
		.block();
	}
}
