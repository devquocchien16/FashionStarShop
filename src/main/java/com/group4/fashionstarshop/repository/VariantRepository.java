package com.group4.fashionstarshop.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.group4.fashionstarshop.model.OptionValue;
import com.group4.fashionstarshop.model.Product;
import com.group4.fashionstarshop.model.Variant;

@Repository
public interface VariantRepository extends JpaRepository<Variant,Long > {
    List<Variant> findByProduct_Id(Long id);   
    List<Variant> findVariantsByProductId(Long product_id);
    Variant findTopByProductIdOrderByPriceAsc(Long product_id);
    Page<Variant> findByNameContaining(String text, Pageable pageable);
    Page<Variant>findVariantsByNameContainingAndPriceBetween(String text,double minPrice, double maxPrice, Pageable pageable );
    @Query("SELECT AVG(r.star) FROM Review r WHERE r.variant = :variant")
    Double findAverageStarByReview(@Param("variant") Variant variant);
    @Query("SELECT v.product.createAt FROM Variant v WHERE v.id = :variantId")
    Date findCreatedAtByVariantId(@Param("variantId") Long variantId);
	List<Variant> findByProduct(Product product);
	List<Variant> findByProductAndIsDeletedFalse(Product product);
    Variant findVariantById(Long id);

	List<Variant> findByProductAndIsDeletedNullOrIsDeletedFalse(Product product);

	
	

}
