package com.paravar.retailflow.util;

import com.paravar.retailflow.ApplicationProperties;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class HashingUtil {

    private final ApplicationProperties properties; // 32-byte key

    @PostConstruct
    public void validateKeys() {
        if (properties.hashSecret().length() < 32) {
            throw new IllegalStateException("Blind index HMAC key too short");
        }
    }


    /**
     * Deterministic blind index — same input + key → same output
     * Use HMAC-SHA256 (or Argon2id if want memory-hard)
     */
    public String blindIndex(String plaintext) {
        if (plaintext == null) return null;

        String normalized = plaintext.trim().toLowerCase(); // important: normalize!

        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec keySpec = new SecretKeySpec(properties.hashSecret().getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(keySpec);
            byte[] hash = mac.doFinal(normalized.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash); // or Base64 — hex is common & index-friendly
        } catch (Exception e) {
            throw new RuntimeException("Blind index failed", e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}