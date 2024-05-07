package com.group4.fashionstarshop.repository;

import com.group4.fashionstarshop.model.OptionTable;
import com.group4.fashionstarshop.model.OptionValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface OptionValueRepository extends JpaRepository<OptionValue,Long> {
	List<OptionValue> findOptionValueById(Long id);
	List<OptionValue> findByOptionTable(OptionTable optionTable);
	
}
