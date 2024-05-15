package com.group4.fashionstarshop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group4.fashionstarshop.model.Product;
import com.group4.fashionstarshop.model.Store;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Optional<Product> findById(Long id);
    List<Product> findAll();
    List<Product> findByTitleContaining(String text);

    List<Product> findAllByStore(Store store);
    List<Product> findAllByStoreCategory(StoreCategory storeCategory);
	List<Product> findByStatus(boolean b);
	
	
	 @Query("SELECT p FROM Product p WHERE p.id = :product_id AND p.status = true AND p.editDesc IS NOT NULL")
	    Product findDescEditingProductAndStatus(@Param("product_id") Long product_id);

}

