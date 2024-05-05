package com.group4.fashionstarshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.group4.fashionstarshop.model.ResetSellerToken;
import com.group4.fashionstarshop.model.Seller;
import com.group4.fashionstarshop.model.User;

import jakarta.transaction.Transactional;
@Repository
public interface ResetSellerTokenRepository extends JpaRepository<ResetSellerToken, Long> {
	ResetSellerToken findByToken(String resetSellerToken);

	Optional<ResetSellerToken> findBySeller(Seller seller);

	@Transactional
	void deleteBySeller(Seller seller);

	@Query("SELECT t FROM ResetSellerToken t WHERE t.seller = :seller")
	ResetSellerToken findBySellerIm(@Param("seller") Seller seller);
}
