package com.group4.fashionstarshop.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group4.fashionstarshop.model.Image;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {
    List<Image> findImagesByVariant_Id(Long id) ;

	List<Image> findByStatus(boolean b);
}
