package com.ecommerce.order.repository;

import com.ecommerce.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
    
    List<Order> findByStatus(Order.OrderStatus status);
    
    @Query("SELECT DISTINCT o FROM Order o JOIN o.items oi WHERE oi.sellerId = :sellerId")
    List<Order> findOrdersBySellerId(@Param("sellerId") Long sellerId);
    
    @Query("SELECT o FROM Order o WHERE " +
           "(:userId IS NULL OR o.userId = :userId) AND " +
           "(:status IS NULL OR o.status = :status)")
    List<Order> searchOrders(@Param("userId") Long userId, @Param("status") Order.OrderStatus status);
}
