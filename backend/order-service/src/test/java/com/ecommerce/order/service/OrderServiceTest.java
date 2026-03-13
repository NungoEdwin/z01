package com.ecommerce.order.service;

import com.ecommerce.order.model.Order;
import com.ecommerce.order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserOrders() {
        List<Order> orders = Arrays.asList(new Order(), new Order());
        when(orderRepository.findByUserId(1L)).thenReturn(orders);

        List<Order> result = orderService.getUserOrders(1L);

        assertEquals(2, result.size());
        verify(orderRepository, times(1)).findByUserId(1L);
    }

    @Test
    void testUpdateOrderStatus() {
        Order order = new Order();
        order.setId(1L);
        order.setStatus(Order.OrderStatus.PENDING);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order result = orderService.updateOrderStatus(1L, "CONFIRMED");

        assertEquals(Order.OrderStatus.CONFIRMED, result.getStatus());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testCancelOrder() {
        Order order = new Order();
        order.setId(1L);
        order.setStatus(Order.OrderStatus.PENDING);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        orderService.cancelOrder(1L);

        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testDeleteOrder() {
        doNothing().when(orderRepository).deleteById(1L);

        orderService.deleteOrder(1L);

        verify(orderRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetSellerOrders() {
        List<Order> orders = Arrays.asList(new Order());
        when(orderRepository.findOrdersBySellerId(1L)).thenReturn(orders);

        List<Order> result = orderService.getSellerOrders(1L);

        assertEquals(1, result.size());
        verify(orderRepository, times(1)).findOrdersBySellerId(1L);
    }
}
