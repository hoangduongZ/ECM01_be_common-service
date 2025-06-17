package com.shop.ecommerce.service.email;

import org.springframework.stereotype.Service;

@Service
public class EmailService implements IEmailService {
    public void sendEmail(String to, String name) {
        System.out.println("Sending welcome email to " + to + " " + name);
    }
}
