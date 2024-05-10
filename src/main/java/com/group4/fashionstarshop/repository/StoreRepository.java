package com.group4.fashionstarshop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.group4.fashionstarshop.model.Seller;
import com.group4.fashionstarshop.model.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {
	Store findBySeller(Seller seller);

	List<Store> findByStatusIsFalse();
	
    List<Store> findByStatus(boolean status);

    @Query("SELECT s FROM Store s WHERE s.id = :store_id")
    Optional<Store> findByStoreId(Long store_id);
 }
