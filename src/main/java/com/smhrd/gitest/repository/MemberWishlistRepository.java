package com.smhrd.gitest.repository;

import com.smhrd.gitest.entity.MemberWishlistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface MemberWishlistRepository extends JpaRepository<MemberWishlistEntity, Long> {
	 boolean existsByEmailAndStoreId(String email, Long storeId);
	    Optional<MemberWishlistEntity> findByEmailAndStoreId(String email, Long storeId);
	    List<MemberWishlistEntity> findAllByEmail(String email);
	    void deleteByEmailAndStoreId(String email, Long storeId);
}