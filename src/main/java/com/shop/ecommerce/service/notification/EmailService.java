package com.shop.ecommerce.service.notification;

import org.springframework.stereotype.Service;

@Service
public class EmailService implements NotificationService{
    @Override
    public void send(String to, String subject, String body) {
        System.out.println("Sending welcome email to " + to + " " + subject);
    }
}
