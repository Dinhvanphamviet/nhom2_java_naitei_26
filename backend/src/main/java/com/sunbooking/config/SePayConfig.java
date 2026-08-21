package com.sunbooking.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "sepay")
@Data
public class SePayConfig {
    private String bankAccount;
    private String bankName;
    private String apiKey;
    private String webhookSecret;
    private String qrPrefix;
}