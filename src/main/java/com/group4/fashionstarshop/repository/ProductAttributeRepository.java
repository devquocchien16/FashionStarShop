package com.group4.fashionstarshop.repository;

import com.group4.fashionstarshop.model.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductAttributeRepository extends JpaRepository<Attribute, Long> {
    List<Attribute> findByProductId(Long id);
}
