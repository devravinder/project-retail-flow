package com.paravar.retailflow.config;

import com.paravar.retailflow.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class KeycloakAdminConfig {

    private final ApplicationProperties properties;


    // https://www.keycloak.org/docs-api/latest/javadocs/org/keycloak/admin/client/KeycloakBuilder.html
    @Bean
    public Keycloak keycloakAdmin() {
        return KeycloakBuilder.builder()
                .serverUrl(properties.keycloak().url())
                .realm(properties.keycloak().masterRealm())                    // admin-cli lives in master
                .clientId(properties.keycloak().adminClientId())
                .username(properties.keycloak().username())
                .password(properties.keycloak().password())
                .grantType(OAuth2Constants.PASSWORD)
                .build();
    }
}