package com.shop.ecommerce.service.notification;

import com.shop.ecommerce.exception.EmailSendException;

public interface NotificationService {
    void send(String to, String subject, String body) throws EmailSendException;
}
