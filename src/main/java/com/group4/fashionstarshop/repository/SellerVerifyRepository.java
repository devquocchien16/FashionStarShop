package com.group4.fashionstarshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group4.fashionstarshop.model.Seller;
import com.group4.fashionstarshop.model.VerifiyTokenSeller;
@Repository
public interface SellerVerifyRepository extends JpaRepository<VerifiyTokenSeller, Long>{
	VerifiyTokenSeller findBySeller (Seller seller);
	VerifiyTokenSeller findByToken(String token);
	
}
