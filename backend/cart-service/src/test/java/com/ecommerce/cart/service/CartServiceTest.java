package com.ecommerce.cart.service;

import com.ecommerce.cart.model.Cart;
import com.ecommerce.cart.model.CartItem;
import com.ecommerce.cart.repository.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartService cartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCartByUserId_ExistingCart() {
        Cart cart = new Cart();
        cart.setUserId(1L);

        when(cartRepository.findByUserId(1L)).thenReturn(Optional.of(cart));

        Cart result = cartService.getCartByUserId(1L);

        assertNotNull(result);
        assertEquals(1L, result.getUserId());
    }

    @Test
    void testGetCartByUserId_NewCart() {
        when(cartRepository.findByUserId(1L)).thenReturn(Optional.empty());
        when(cartRepository.save(any(Cart.class))).thenAnswer(i -> i.getArguments()[0]);

        Cart result = cartService.getCartByUserId(1L);

        assertNotNull(result);
        assertEquals(1L, result.getUserId());
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    void testAddItemToCart() {
        Cart cart = new Cart();
        cart.setUserId(1L);

        when(cartRepository.findByUserId(1L)).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart result = cartService.addItemToCart(1L, 100L, 2);

        assertNotNull(result);
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    void testClearCart() {
        Cart cart = new Cart();
        cart.setUserId(1L);

        when(cartRepository.findByUserId(1L)).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        cartService.clearCart(1L);

        verify(cartRepository, times(1)).save(any(Cart.class));
    }
}
