package com.ecommerce.order.service;

import com.ecommerce.order.dto.CreateOrderRequest;
import com.ecommerce.order.dto.OrderStatsDTO;
import com.ecommerce.order.model.Order;
import com.ecommerce.order.model.OrderItem;
import com.ecommerce.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private RestTemplate restTemplate;

    @Transactional
    public Order createOrder(CreateOrderRequest request) {
        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setShippingAddress(request.getShippingAddress());
        order.setPaymentMethod(request.getPaymentMethod());
        order.setStatus(Order.OrderStatus.PENDING);
        
        BigDecimal total = BigDecimal.ZERO;
        for (CreateOrderRequest.OrderItemRequest itemReq : request.getItems()) {
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProductId(itemReq.getProductId());
            item.setSellerId(itemReq.getSellerId());
            item.setQuantity(itemReq.getQuantity());
            item.setPrice(itemReq.getPrice());
            order.getItems().add(item);
            
            total = total.add(itemReq.getPrice().multiply(BigDecimal.valueOf(itemReq.getQuantity())));
        }
        
        order.setTotalAmount(total);
        return orderRepository.save(order);
    }

    public List<Order> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public List<Order> getSellerOrders(Long sellerId) {
        return orderRepository.findOrdersBySellerId(sellerId);
    }

    public List<Order> searchOrders(Long userId, String status) {
        Order.OrderStatus orderStatus = status != null ? Order.OrderStatus.valueOf(status) : null;
        return orderRepository.searchOrders(userId, orderStatus);
    }

    @Transactional
    public Order updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(Order.OrderStatus.valueOf(status));
        return orderRepository.save(order);
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(Order.OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    @Transactional
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    public Map<String, Object> getBuyerStats(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        
        double totalSpent = orders.stream()
            .filter(o -> o.getStatus() != Order.OrderStatus.CANCELLED)
            .mapToDouble(o -> o.getTotalAmount().doubleValue())
            .sum();
        
        Map<Long, Long> productCounts = new HashMap<>();
        for (Order order : orders) {
            if (order.getStatus() != Order.OrderStatus.CANCELLED) {
                for (OrderItem item : order.getItems()) {
                    productCounts.merge(item.getProductId(), (long) item.getQuantity(), Long::sum);
                }
            }
        }
        
        List<Map<String, Object>> mostBuyingProducts = productCounts.entrySet().stream()
            .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
            .limit(5)
            .map(e -> {
                Map<String, Object> map = new HashMap<>();
                map.put("productId", e.getKey());
                map.put("count", e.getValue());
                return map;
            })
            .collect(Collectors.toList());
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalSpent", totalSpent);
        stats.put("mostBuyingProducts", mostBuyingProducts);
        stats.put("bestProducts", mostBuyingProducts);
        
        return stats;
    }

    public Map<String, Object> getSellerStats(Long sellerId) {
        List<Order> orders = orderRepository.findOrdersBySellerId(sellerId);
        
        double totalEarned = 0;
        Map<Long, Long> productSales = new HashMap<>();
        
        for (Order order : orders) {
            if (order.getStatus() != Order.OrderStatus.CANCELLED) {
                for (OrderItem item : order.getItems()) {
                    if (item.getSellerId().equals(sellerId)) {
                        totalEarned += item.getPrice().doubleValue() * item.getQuantity();
                        productSales.merge(item.getProductId(), (long) item.getQuantity(), Long::sum);
                    }
                }
            }
        }
        
        List<Map<String, Object>> bestSellingProducts = productSales.entrySet().stream()
            .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
            .limit(5)
            .map(e -> {
                Map<String, Object> map = new HashMap<>();
                map.put("productId", e.getKey());
                map.put("count", e.getValue());
                return map;
            })
            .collect(Collectors.toList());
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalEarned", totalEarned);
        stats.put("bestSellingProducts", bestSellingProducts);
        
        return stats;
    }
}
