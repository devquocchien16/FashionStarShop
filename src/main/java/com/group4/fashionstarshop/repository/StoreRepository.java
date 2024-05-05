package com.group4.fashionstarshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group4.fashionstarshop.model.Seller;
import com.group4.fashionstarshop.model.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {
	Store findBySeller(Seller seller);
 }
