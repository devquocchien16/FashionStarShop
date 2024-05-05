package com.group4.fashionstarshop.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group4.fashionstarshop.model.OrderItem;
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
	
	OrderItem findOrderById(Long id);

}
