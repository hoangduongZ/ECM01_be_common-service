package com.shop.ecommerce.kafka.listener;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.ecommerce.kafka.event.UserSummaryEvent;
import com.shop.ecommerce.service.MailLinkBuilder;
import com.shop.ecommerce.service.notification.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserEventListener {
    private final NotificationService notificationService;
    private final ObjectMapper objectMapper;
    private final MailLinkBuilder mailLinkBuilder;

    public UserEventListener(NotificationService notificationService, ObjectMapper objectMapper, MailLinkBuilder mailLinkBuilder) {
        this.notificationService = notificationService;
        this.objectMapper = objectMapper;
        this.mailLinkBuilder = mailLinkBuilder;
    }

    @KafkaListener(
            topics = "${kafka.topics.user-created}",
            groupId = "common-service"
    )
    public void handleUserCreated(String message) {
        try {
            UserSummaryEvent event = objectMapper.readValue(message, UserSummaryEvent.class);
            notificationService.send(event.getEmail(), event.getFirstName() + " " + event.getLastName(),"");
        } catch (Exception e) {
            System.err.println("❌ Failed to parse message: " + e.getMessage());
        }
    }

    @KafkaListener(topics = "${kafka.topics.user-created}", groupId = "common-service")
    public void listenUserEvents(String message) {
        try {
            JsonNode root = objectMapper.readTree(message);
            String eventType = root.get("eventType").asText();

            if ("UserCreated".equals(eventType)) {
                JsonNode dataNode = root.get("data");
                UserSummaryEvent userData = objectMapper.treeToValue(dataNode, UserSummaryEvent.class);

                System.out.printf("✅ Received UserCreated: uuid=%s, email=%s, role=%s%n",
                        userData.getUserId(), userData.getEmail(), userData.getRole());

                Map<String, Object> vars = new HashMap<>();
                vars.put("fullName", userData.getUserName());
                vars.put("verifyLink", mailLinkBuilder.buildVerifyLink(userData.getUuid()));
                notificationService.sendHtmlEmail( userData.getEmail(),
                        "Welcome to MyApp",
                        "welcome",
                        vars);
            }
        } catch (Exception e) {
            System.err.println("❌ Failed to handle user event: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
