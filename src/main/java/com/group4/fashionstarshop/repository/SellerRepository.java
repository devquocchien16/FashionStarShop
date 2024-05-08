package com.group4.fashionstarshop.repository;

import com.group4.fashionstarshop.dto.UserResetDTO;
import com.group4.fashionstarshop.model.Seller;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
	@Query("SELECT s FROM Seller s WHERE s.email = :email")
    Seller findBySellerEmail(@Param("email") String email);
	 @Query("SELECT s FROM Seller s WHERE s.email = :email")
    Seller findByEmail(String email);
    @Query("SELECT u FROM Seller u WHERE u.id IN :ids")
	List<Seller> findAllByIdsIn(List<Long> ids);
}
