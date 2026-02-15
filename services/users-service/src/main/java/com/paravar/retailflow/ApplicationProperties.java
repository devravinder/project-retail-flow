package com.paravar.retailflow;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "users-service")
public record ApplicationProperties(
        @NotNull String encryptionKey,
        @NotNull String hashSecret
) {}
