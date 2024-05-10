package com.group4.fashionstarshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.group4.fashionstarshop.model.Seller;
import com.group4.fashionstarshop.model.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {
	Store findBySeller(Seller seller);
	@Query("SELECT s FROM Store s WHERE s.editingName IS NOT NULL")
	List<Store> findByEditingNameIsNotNull();
	
	List<Store> findByStatus(boolean b);
	
	@Query("SELECT s FROM Store s WHERE s.status = false")
	List<Store> findByStatusFalse();
	
	@Query("SELECT s FROM Store s WHERE s.status = true")
	List<Store> findByStatusTrue();
 }
