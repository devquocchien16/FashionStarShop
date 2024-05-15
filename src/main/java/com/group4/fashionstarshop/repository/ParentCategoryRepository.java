package com.group4.fashionstarshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group4.fashionstarshop.model.MainCategory;
import com.group4.fashionstarshop.model.ParentCategory;

@Repository
public interface ParentCategoryRepository extends JpaRepository<ParentCategory,Long>{

	List<ParentCategory> findByMainCategory(MainCategory mainCategory);

}
