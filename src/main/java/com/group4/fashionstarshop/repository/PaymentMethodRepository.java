package com.group4.fashionstarshop.repository;

import com.group4.fashionstarshop.model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
    List<PaymentMethod> findByUserId(Long userId);
    
    @Query("SELECT p FROM PaymentMethod p WHERE p.nameOnCard = :name")
    PaymentMethod findByName(@Param("name") String name);
}
