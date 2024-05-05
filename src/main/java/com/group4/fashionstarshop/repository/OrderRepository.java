package com.group4.fashionstarshop.repository;

import com.group4.fashionstarshop.model.Order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStoreId(Long storeId);
    List<Order> findByUserId(Long userId);
    
    @Query("SELECT o FROM Order o WHERE o.user.id = :userId AND (o.status = 'PENDING' OR o.status = 'ACCEPTED' OR o.status = 'COMPLETED' OR o.status = 'CANCELLED')")
	public List<Order> getUserOder(@Param("userId") Long userId);
   
}



