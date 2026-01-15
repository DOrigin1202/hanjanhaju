package com.smhrd.gitest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.smhrd.gitest.dto.StoreDto;
import com.smhrd.gitest.entity.StoreEntity;
import com.smhrd.gitest.entity.StoreRecommendationScoreEntity;
import com.smhrd.gitest.repository.MoodTagRepository;
import com.smhrd.gitest.repository.StoreRecommendationScoreRepository;
import com.smhrd.gitest.repository.StoreRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service //실제 구현될 서비스 내용
public class StoreServiceImpl implements StoreService {
	
	@Autowired
	private StoreRepository storeRepository;
	
	@Autowired
	private MoodTagRepository moodTagRepository;
	
	@Autowired
	private StoreRecommendationScoreRepository scoreRepository;

	    public StoreServiceImpl(StoreRepository storeRepository, StoreRecommendationScoreRepository scoreRepository) {
	        this.storeRepository = storeRepository;
	        this.scoreRepository = scoreRepository;
	        
	    }

	    // 전체 술집 목록 조회
	    @Override
	    public List<StoreEntity> findAllStores() {
	        return storeRepository.findAll();
	    }

	    // 술집 ID로 조회
	    @Override
	    public Optional<StoreEntity> findStoreById(Long storeId) {
	        return storeRepository.findById(storeId);
	    }

	    // (예시) 상위 5개 술집만 가져오는 로직
	    @Override
	    public List<StoreEntity> getTopPicks() {
	        return storeRepository.findTop5ByOrderByStoreIdAsc();
	    }
	    @Override
	    public List<StoreDto> recommendStoresByTags(List<String> tagNames) {
	        // 1. 태그 이름으로 태그 ID 목록 조회
	        List<Long> tagIds = moodTagRepository.findTagIdsByTagNames(tagNames);
	        if (tagIds.isEmpty()) {
	            return List.of(); // 일치하는 태그가 없으면 빈 리스트 반환
	        }

	        // 2. 태그 ID와 일치하는 store_id를 '일치 개수' 순으로 정렬하여 조회
	        List<Long> recommendedStoreIds = storeRepository.findStoreIdsByTags(tagIds);
	        if (recommendedStoreIds.isEmpty()) {
	            return List.of();
	        }

	        // 3. 조회된 store_id 목록으로 실제 StoreEntity 정보 조회
	        List<StoreEntity> recommendedStores = storeRepository.findAllById(recommendedStoreIds);

	        // 4. 순서 보정 및 DTO 변환 (findAllById는 순서를 보장하지 않으므로)
	        Map<Long, StoreEntity> storeMap = recommendedStores.stream()
	        		.collect(Collectors.toMap(StoreEntity::getStoreId, store -> store));

	        return recommendedStoreIds.stream()
	                .map(storeMap::get)
	                .map(StoreDto::fromEntity)
	                .collect(Collectors.toList());
	    }
	    @Override
	    public List<StoreDto> getTopRatedStores(int limit) {
	        // Pageable 객체를 사용하여 상위 limit 개수만큼만 가져오도록 설정
	        Pageable pageable = PageRequest.of(0, limit);

	        // 점수 높은 순으로 가게 점수 정보 조회
	        List<StoreRecommendationScoreEntity> topScores = scoreRepository.findTopStoresByScore(pageable);

	        // StoreRecommendationScoreEntity 리스트에서 StoreDto 리스트로 변환
	        return topScores.stream()
	                .map(scoreEntity -> StoreDto.fromEntity(scoreEntity.getStore()))
	                .collect(Collectors.toList());
	    }
	    
	}