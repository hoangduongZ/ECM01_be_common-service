package com.shop.ecommerce.service.notification;

import com.shop.ecommerce.service.MailLinkBuilder;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Service
public class EmailService implements NotificationService{
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final MailLinkBuilder mailLinkBuilder;

    public EmailService(JavaMailSender mailSender, TemplateEngine templateEngine, MailLinkBuilder mailLinkBuilder) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.mailLinkBuilder = mailLinkBuilder;
    }

    @Override
    public void send(String to, String subject, String body) {
        System.out.println("Sending welcome email to " + to + " " + subject);
    }

    @Override
    @Async
    public void sendHtmlEmail(String to, String subject, String templateName, Map<String, Object> variables) {
        try {
            Context context = new Context();
            context.setVariables(variables);

            String body = templateEngine.process(templateName, context);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);

            mailSender.send(message);
            System.out.println("Sent HTML email to "+ to);
        } catch (Exception e) {
            System.err.println("❌ Failed to send email to "+ to + " " + e.getMessage());
        }
    }
}
