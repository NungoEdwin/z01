package com.ecommerce.product.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.ecommerce.product.service.ProductService;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class MediaEventConsumer {

    private final ProductService productService;

    @KafkaListener(topics = "media-uploaded", groupId = "product-service-group")
    public void handleMediaUploadedEvent(Map<String, Object> event) {
        try {
            String mediaId = (String) event.get("mediaId");
            String productId = (String) event.get("productId");
            String url = (String) event.get("url");
            String uploadedBy = (String) event.get("uploadedBy");
            
            log.info("Received media uploaded event for mediaId: {}, productId: {}", mediaId, productId);
            
            if (productId != null && url != null) {
                productService.addProductImage(productId, uploadedBy, url);
            }
            
        } catch (Exception e) {
            log.error("Error processing media uploaded event: {}", e.getMessage(), e);
        }
    }

    @KafkaListener(topics = "media-deleted", groupId = "product-service-group")
    public void handleMediaDeletedEvent(Map<String, Object> event) {
        try {
            String mediaId = (String) event.get("mediaId");
            String productId = (String) event.get("productId");
            
            log.info("Received media deleted event for mediaId: {}, productId: {}", mediaId, productId);
            
            // Note: We would need to find the URL to remove it
            // For now, we'll implement this as a future enhancement
            
        } catch (Exception e) {
            log.error("Error processing media deleted event: {}", e.getMessage(), e);
        }
    }
}