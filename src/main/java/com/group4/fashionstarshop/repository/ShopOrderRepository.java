package com.group4.fashionstarshop.repository;

import com.group4.fashionstarshop.model.ShopOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopOrderRepository extends JpaRepository<ShopOrder, Long> {
    ShopOrder findByUserId(Long userId);
}
