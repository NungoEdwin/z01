package com.ecommerce.product.service;

import com.ecommerce.product.dto.CreateProductRequest;
import com.ecommerce.product.dto.ProductResponse;
import com.ecommerce.product.dto.UpdateProductRequest;
import com.ecommerce.product.exception.ResourceNotFoundException;
import com.ecommerce.product.exception.UnauthorizedException;
import com.ecommerce.product.kafka.ProductEventProducer;
import com.ecommerce.product.model.Product;
import com.ecommerce.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductEventProducer productEventProducer;

    public ProductResponse createProduct(String sellerId, CreateProductRequest request) {
        log.info("Creating product for seller: {}", sellerId);

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .category(request.getCategory())
                .sellerId(sellerId)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        product = productRepository.save(product);
        log.info("Product created with id: {}", product.getId());

        productEventProducer.publishProductCreatedEvent(product);

        return mapToResponse(product);
    }

    public ProductResponse getProduct(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
        
        return mapToResponse(product);
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> getSellerProducts(String sellerId) {
        return productRepository.findBySellerId(sellerId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse updateProduct(String productId, String sellerId, UpdateProductRequest request) {
        log.info("Updating product {} for seller: {}", productId, sellerId);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        if (!product.getSellerId().equals(sellerId)) {
            throw new UnauthorizedException("You are not authorized to update this product");
        }

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setCategory(request.getCategory());
        product.setUpdatedAt(LocalDateTime.now());

        product = productRepository.save(product);
        log.info("Product updated with id: {}", product.getId());

        productEventProducer.publishProductUpdatedEvent(product);

        return mapToResponse(product);
    }

    public void deleteProduct(String productId, String sellerId) {
        log.info("Deleting product {} for seller: {}", productId, sellerId);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        if (!product.getSellerId().equals(sellerId)) {
            throw new UnauthorizedException("You are not authorized to delete this product");
        }

        productRepository.delete(product);
        log.info("Product deleted with id: {}", productId);

        productEventProducer.publishProductDeletedEvent(productId, sellerId);
    }

    public void addProductImage(String productId, String sellerId, String imageUrl) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        // Only validate seller if sellerId is provided (not from Kafka event)
        if (sellerId != null && !product.getSellerId().equals(sellerId)) {
            throw new UnauthorizedException("You are not authorized to modify this product");
        }

        product.getImageUrls().add(imageUrl);
        product.setUpdatedAt(LocalDateTime.now());
        productRepository.save(product);
        
        log.info("Image added to product {}: {}", productId, imageUrl);
    }

    public void removeProductImage(String productId, String sellerId, String imageUrl) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        if (!product.getSellerId().equals(sellerId)) {
            throw new UnauthorizedException("You are not authorized to modify this product");
        }

        product.getImageUrls().remove(imageUrl);
        product.setUpdatedAt(LocalDateTime.now());
        productRepository.save(product);
        
        log.info("Image removed from product {}: {}", productId, imageUrl);
    }

    public ProductResponse addProductImageToProduct(String productId, String sellerId, String imageUrl) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        if (!product.getSellerId().equals(sellerId)) {
            throw new UnauthorizedException("You are not authorized to modify this product");
        }

        product.getImageUrls().add(imageUrl);
        product.setUpdatedAt(LocalDateTime.now());
        product = productRepository.save(product);

        return mapToResponse(product);
    }

    public ProductResponse removeProductImageFromProduct(String productId, String sellerId, String imageUrl) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        if (!product.getSellerId().equals(sellerId)) {
            throw new UnauthorizedException("You are not authorized to modify this product");
        }

        product.getImageUrls().remove(imageUrl);
        product.setUpdatedAt(LocalDateTime.now());
        product = productRepository.save(product);

        return mapToResponse(product);
    }

    private ProductResponse mapToResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .category(product.getCategory())
                .sellerId(product.getSellerId())
                .imageUrls(product.getImageUrls())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}
