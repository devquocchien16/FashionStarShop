package com.group4.fashionstarshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group4.fashionstarshop.model.OptionTable;
import com.group4.fashionstarshop.model.Product;


@Repository
public interface OptionTableRepository  extends JpaRepository<OptionTable, Long> {
	List<OptionTable> findByProduct(Product product);
}
