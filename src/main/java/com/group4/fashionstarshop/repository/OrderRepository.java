package com.group4.fashionstarshop.repository;

import com.group4.fashionstarshop.model.Order;

import java.util.Date;
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
    
    @Query("SELECT SUM(o.orderTotal) FROM Order o " +
            "WHERE o.createdAt >= :startDate " +
            "AND o.createdAt <= :endDate " +
            "AND o.store.id = :storeId")
     Double calculateStoreRevenue(@Param("startDate") Date startDate,
                                  @Param("endDate") Date endDate,
                                  @Param("storeId") Long storeId);
    @Query("SELECT " +
            "  CASE " +
            "    WHEN SUM(o.orderTotal) <= 500 THEN SUM(o.orderTotal) * 0.15 " +
            "    ELSE SUM(o.orderTotal) * 0.20 " +
            "  END " +
            "FROM " +
            "  Order o " +
            "WHERE " +
            "  o.createdAt >= :startDate " +
            "  AND o.createdAt <= :endDate " +
            "  AND o.store.id= :storeId")
     Double calculateCommission(@Param("startDate") Date startDate, 
                                @Param("endDate") Date endDate, 
                                @Param("storeId") Long storeId);
    @Query("SELECT o FROM Order o WHERE o.createdAt >= :startDate AND o.createdAt <= :endDate")
    List<Order> findOrdersByCreatedAtBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
	List<Order> findOrdersByCreatedAtBetweenAndStoreId(Date startDate, Date endDate, Long storeId);
}



