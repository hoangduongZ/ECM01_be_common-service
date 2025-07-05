package com.shop.ecommerce.helper;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppUrlResolver {
    private final AppUrlMap urlMap;
    @Value("${env:dev}")
    private String currentEnv;

    public AppUrlResolver(@Qualifier("appUrlMap") AppUrlMap urlMap) {
        this.urlMap = urlMap;
    }

    public String getFrontendUrl() {
        return switch (currentEnv) {
            case "prod" -> urlMap.getProd().getFrontendUrl();
            case "staging" -> urlMap.getStaging().getFrontendUrl();
            case "dev" -> urlMap.getDev().getFrontendUrl();
            default -> throw new IllegalStateException("Invalid ENV value: " + currentEnv);
        };
    }

    public String getBackendUrl() {
        return switch (currentEnv) {
            case "prod" -> urlMap.getProd().getBackendUrl();
            case "staging" -> urlMap.getStaging().getBackendUrl();
            case "dev" -> urlMap.getDev().getBackendUrl();
            default -> throw new IllegalStateException("Invalid ENV value: " + currentEnv);
        };
    }
}
