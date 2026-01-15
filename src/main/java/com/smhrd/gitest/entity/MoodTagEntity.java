package com.smhrd.gitest.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="mood_tag")
public class MoodTagEntity {
	@Id
	private Long tag_id;
	private String tag_name;
}
