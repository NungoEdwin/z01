package com.ecommerce.cart.controller;

import com.ecommerce.cart.dto.AddToCartRequest;
import com.ecommerce.cart.model.Cart;
import com.ecommerce.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCart(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getCartByUserId(userId));
    }

    @PostMapping("/{userId}/items")
    public ResponseEntity<Cart> addToCart(@PathVariable Long userId, @RequestBody AddToCartRequest request) {
        return ResponseEntity.ok(cartService.addItemToCart(userId, request.getProductId(), request.getQuantity()));
    }

    @PutMapping("/{userId}/items/{productId}")
    public ResponseEntity<Cart> updateQuantity(@PathVariable Long userId, @PathVariable Long productId, @RequestParam Integer quantity) {
        return ResponseEntity.ok(cartService.updateItemQuantity(userId, productId, quantity));
    }

    @DeleteMapping("/{userId}/items/{productId}")
    public ResponseEntity<Cart> removeItem(@PathVariable Long userId, @PathVariable Long productId) {
        return ResponseEntity.ok(cartService.removeItemFromCart(userId, productId));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}
