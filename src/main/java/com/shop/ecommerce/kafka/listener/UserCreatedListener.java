package com.shop.ecommerce.kafka.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.ecommerce.kafka.event.UserCreatedEvent;
import com.shop.ecommerce.service.email.EmailService;
import com.shop.ecommerce.service.notification.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserCreatedListener {
    private final NotificationService notificationService;
    private final ObjectMapper objectMapper;

    public UserCreatedListener(NotificationService notificationService, ObjectMapper objectMapper) {
        this.notificationService = notificationService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(
            topics = "${kafka.topics.user-created}",
            groupId = "common-service"
    )
    public void handleUserCreated(String message) {
        try {
            UserCreatedEvent event = objectMapper.readValue(message, UserCreatedEvent.class);
            notificationService.send(event.getEmail(), event.getFirstName() + " " + event.getLastName(),"");
        } catch (Exception e) {
            System.err.println("❌ Failed to parse message: " + e.getMessage());
        }
    }
}
