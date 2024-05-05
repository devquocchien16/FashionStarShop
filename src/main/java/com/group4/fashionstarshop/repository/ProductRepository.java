package com.group4.fashionstarshop.repository;

import com.group4.fashionstarshop.model.Product;

import com.group4.fashionstarshop.model.Store;
import com.group4.fashionstarshop.model.StoreCategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Optional<Product> findById(Long id);
    List<Product> findAll();
    List<Product> findByTitleContaining(String text);

    List<Product> findAllByStore(Store store);
    List<Product> findAllByStoreCategory(StoreCategory storeCategory);

}

