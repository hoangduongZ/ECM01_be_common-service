package com.shop.ecommerce.helper;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.url-map")
public class AppUrlMap {
    private EnvUrl dev;
    private EnvUrl prod;
    private EnvUrl staging;

    public EnvUrl getDev() {
        return dev;
    }

    public void setDev(EnvUrl dev) {
        this.dev = dev;
    }

    public EnvUrl getProd() {
        return prod;
    }

    public void setProd(EnvUrl prod) {
        this.prod = prod;
    }

    public EnvUrl getStaging() {
        return staging;
    }

    public void setStaging(EnvUrl staging) {
        this.staging = staging;
    }

    public static class EnvUrl {
        private String frontendUrl;
        private String backendUrl;

        public String getBackendUrl() {
            return backendUrl;
        }

        public void setBackendUrl(String backendUrl) {
            this.backendUrl = backendUrl;
        }

        public String getFrontendUrl() {
            return frontendUrl;
        }

        public void setFrontendUrl(String frontendUrl) {
            this.frontendUrl = frontendUrl;
        }
    }
}
