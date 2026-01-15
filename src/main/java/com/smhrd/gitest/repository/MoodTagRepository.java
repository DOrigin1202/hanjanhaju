package com.smhrd.gitest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smhrd.gitest.entity.MoodTagEntity;

@Repository
public interface MoodTagRepository extends JpaRepository<MoodTagEntity, Long>{
	@Query("SELECT m.tag_id FROM MoodTagEntity m WHERE m.tag_name IN :tagNames")
    List<Long> findTagIdsByTagNames(@Param("tagNames") List<String> tagNames);

}
