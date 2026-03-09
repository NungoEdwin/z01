package com.ecommerce.product.kafka;

import com.ecommerce.product.model.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    
    private static final String PRODUCT_CREATED_TOPIC = "product-created";
    private static final String PRODUCT_UPDATED_TOPIC = "product-updated";
    private static final String PRODUCT_DELETED_TOPIC = "product-deleted";

    public void publishProductCreatedEvent(Product product) {
        Map<String, Object> event = new HashMap<>();
        event.put("productId", product.getId());
        event.put("name", product.getName());
        event.put("sellerId", product.getSellerId());
        event.put("price", product.getPrice());
        event.put("category", product.getCategory());
        event.put("createdAt", product.getCreatedAt().toString());
        
        kafkaTemplate.send(PRODUCT_CREATED_TOPIC, product.getId(), event);
        log.info("Published product created event for productId: {}", product.getId());
    }

    public void publishProductUpdatedEvent(Product product) {
        Map<String, Object> event = new HashMap<>();
        event.put("productId", product.getId());
        event.put("name", product.getName());
        event.put("sellerId", product.getSellerId());
        event.put("price", product.getPrice());
        event.put("quantity", product.getQuantity());
        event.put("category", product.getCategory());
        event.put("updatedAt", product.getUpdatedAt().toString());
        
        kafkaTemplate.send(PRODUCT_UPDATED_TOPIC, product.getId(), event);
        log.info("Published product updated event for productId: {}", product.getId());
    }

    public void publishProductDeletedEvent(String productId, String sellerId) {
        Map<String, Object> event = new HashMap<>();
        event.put("productId", productId);
        event.put("sellerId", sellerId);
        
        kafkaTemplate.send(PRODUCT_DELETED_TOPIC, productId, event);
        log.info("Published product deleted event for productId: {}", productId);
    }
}
