package com.smhrd.gitest.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "member_wishlist")
@Data
public class MemberWishlistEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idx;
	
	  @Column(name = "email", nullable = false)
	    private String email; // FK : member_entity.email

	    @Column(name = "store_id", nullable = false)
	    private Long storeId; // FK : store.store_id
}
