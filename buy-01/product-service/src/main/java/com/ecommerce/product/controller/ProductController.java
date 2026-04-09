package com.ecommerce.product.controller;

import com.ecommerce.product.dto.CreateProductRequest;
import com.ecommerce.product.dto.ProductResponse;
import com.ecommerce.product.dto.UpdateProductRequest;
import com.ecommerce.product.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ProductResponse> createProduct(
            HttpServletRequest request,
            @Valid @RequestBody CreateProductRequest createRequest
    ) {
        String sellerId = (String) request.getAttribute("userId");
        ProductResponse product = productService.createProduct(sellerId, createRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable String id) {
        ProductResponse product = productService.getProduct(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/seller/my-products")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<List<ProductResponse>> getMyProducts(HttpServletRequest request) {
        String sellerId = (String) request.getAttribute("userId");
        List<ProductResponse> products = productService.getSellerProducts(sellerId);
        return ResponseEntity.ok(products);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable String id,
            HttpServletRequest request,
            @Valid @RequestBody UpdateProductRequest updateRequest
    ) {
        String sellerId = (String) request.getAttribute("userId");
        ProductResponse product = productService.updateProduct(id, sellerId, updateRequest);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable String id,
            HttpServletRequest request
    ) {
        String sellerId = (String) request.getAttribute("userId");
        productService.deleteProduct(id, sellerId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/images")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ProductResponse> addProductImage(
            @PathVariable String id,
            @RequestBody Map<String, String> request,
            HttpServletRequest httpRequest
    ) {
        String sellerId = (String) httpRequest.getAttribute("userId");
        String imageUrl = request.get("imageUrl");
        
        ProductResponse product = productService.addProductImageToProduct(id, sellerId, imageUrl);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}/images")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ProductResponse> removeProductImage(
            @PathVariable String id,
            @RequestBody Map<String, String> request,
            HttpServletRequest httpRequest
    ) {
        String sellerId = (String) httpRequest.getAttribute("userId");
        String imageUrl = request.get("imageUrl");
        
        ProductResponse product = productService.removeProductImageFromProduct(id, sellerId, imageUrl);
        return ResponseEntity.ok(product);
    }
}
