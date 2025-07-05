package com.shop.ecommerce.service;

import com.shop.ecommerce.helper.AppUrlResolver;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MailLinkBuilder {
    private final AppUrlResolver appUrlResolver;

    public MailLinkBuilder(AppUrlResolver appUrlResolver) {
        this.appUrlResolver = appUrlResolver;
    }

    public String buildVerifyLink(UUID uuid) {
        return appUrlResolver.getFrontendUrl() + "/verify/" + uuid;
    }

    public String buildResetLink(UUID uuid) {
        return appUrlResolver.getFrontendUrl() + "/reset-password/" + uuid;
    }
}
