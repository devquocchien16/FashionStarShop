package com.group4.fashionstarshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.group4.fashionstarshop.model.OptionValue;
import com.group4.fashionstarshop.model.Variant;
import com.group4.fashionstarshop.model.VariantOptionValue;
import java.util.List;


public interface VariantOptionValueRepository extends JpaRepository<VariantOptionValue, Long> {
		List<VariantOptionValue> findByVariant(Variant variant);

	
}
