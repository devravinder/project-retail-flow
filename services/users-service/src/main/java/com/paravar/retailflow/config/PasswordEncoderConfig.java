package com.paravar.retailflow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class PasswordEncoderConfig {

    @Bean
    @Primary
    public PasswordEncoder bCryptpasswordEncoder() {
        return new BCryptPasswordEncoder();// prefixed with {bcrypt}$...
    }
    @Bean // this is just for reference
    public PasswordEncoder argon2passwordEncoder() {
        // Default encoder for new passwords (use Argon2id in 2026) // prefixed with {argon2}$...
        PasswordEncoder defaultEncoder = new Argon2PasswordEncoder(
                16,      // salt length
                32,      // hash length
                1,       // parallelism (threads)
                32 * 1024, // memory (32 MiB – adjust based on your server)
                3        // iterations (tune to ~0.5–1 sec on your hardware)
        );

        // Support legacy formats during migration
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("argon2", defaultEncoder);
        encoders.put("bcrypt", new BCryptPasswordEncoder(12)); // strength 12 = ~0.3–0.5s
        encoders.put("scrypt", SCryptPasswordEncoder.defaultsForSpringSecurity_v5_8());

        return new DelegatingPasswordEncoder("argon2", encoders);
    }
}