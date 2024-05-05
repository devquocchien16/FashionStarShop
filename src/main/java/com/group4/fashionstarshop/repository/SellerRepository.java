package com.group4.fashionstarshop.repository;

import com.group4.fashionstarshop.dto.UserResetDTO;
import com.group4.fashionstarshop.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
	
    Seller findByEmail(String email);
}
