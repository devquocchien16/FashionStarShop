package com.group4.fashionstarshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group4.fashionstarshop.model.Category;
import com.group4.fashionstarshop.model.ParentCategory;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findByNameContaining(String text);

	List<Category> findByParentCategory(ParentCategory parentCategory);
}
