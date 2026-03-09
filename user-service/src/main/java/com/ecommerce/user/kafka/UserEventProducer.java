package com.ecommerce.user.kafka;

import com.ecommerce.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    
    private static final String USER_REGISTERED_TOPIC = "user-registered";
    private static final String USER_UPDATED_TOPIC = "user-updated";

    public void publishUserRegisteredEvent(User user) {
        Map<String, Object> event = new HashMap<>();
        event.put("userId", user.getId());
        event.put("email", user.getEmail());
        event.put("firstName", user.getFirstName());
        event.put("lastName", user.getLastName());
        event.put("role", user.getRole().name());
        event.put("createdAt", user.getCreatedAt().toString());
        
        kafkaTemplate.send(USER_REGISTERED_TOPIC, user.getId(), event);
        log.info("Published user registered event for userId: {}", user.getId());
    }

    public void publishUserUpdatedEvent(User user) {
        Map<String, Object> event = new HashMap<>();
        event.put("userId", user.getId());
        event.put("email", user.getEmail());
        event.put("firstName", user.getFirstName());
        event.put("lastName", user.getLastName());
        event.put("role", user.getRole().name());
        event.put("avatarUrl", user.getAvatarUrl());
        event.put("updatedAt", user.getUpdatedAt().toString());
        
        kafkaTemplate.send(USER_UPDATED_TOPIC, user.getId(), event);
        log.info("Published user updated event for userId: {}", user.getId());
    }
}
