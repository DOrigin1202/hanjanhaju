package com.smhrd.gitest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smhrd.gitest.entity.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist, Long>{
	Optional<Wishlist> findByUserIdAndShopId(String userId, Integer shopId);
}
