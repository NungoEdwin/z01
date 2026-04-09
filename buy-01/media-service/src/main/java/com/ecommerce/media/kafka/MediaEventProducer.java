package com.ecommerce.media.kafka;

import com.ecommerce.media.model.Media;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class MediaEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    
    private static final String MEDIA_UPLOADED_TOPIC = "media-uploaded";
    private static final String MEDIA_DELETED_TOPIC = "media-deleted";

    public void publishMediaUploadedEvent(Media media) {
        Map<String, Object> event = new HashMap<>();
        event.put("mediaId", media.getId());
        event.put("filename", media.getFilename());
        event.put("productId", media.getProductId());
        event.put("uploadedBy", media.getUploadedBy());
        event.put("url", media.getUrl());
        event.put("uploadedAt", media.getUploadedAt().toString());
        
        kafkaTemplate.send(MEDIA_UPLOADED_TOPIC, media.getId(), event);
        log.info("Published media uploaded event for mediaId: {}", media.getId());
    }

    public void publishMediaDeletedEvent(String mediaId, String userId, String productId) {
        Map<String, Object> event = new HashMap<>();
        event.put("mediaId", mediaId);
        event.put("userId", userId);
        event.put("productId", productId);
        
        kafkaTemplate.send(MEDIA_DELETED_TOPIC, mediaId, event);
        log.info("Published media deleted event for mediaId: {}", mediaId);
    }
}
