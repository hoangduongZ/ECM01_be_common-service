package com.shop.ecommerce.service.notification;

import com.shop.ecommerce.exception.EmailSendException;

import java.util.Map;

public interface NotificationService {
    void send(String to, String subject, String body) throws EmailSendException;

    void sendHtmlEmail(String to, String subject, String templateName, Map<String, Object> variables);
}
