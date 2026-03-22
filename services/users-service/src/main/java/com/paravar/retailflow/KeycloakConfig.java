package com.paravar.retailflow;

import jakarta.validation.constraints.NotNull;

public record KeycloakConfig (@NotNull String url,
                              @NotNull String masterRealm,
                              @NotNull String adminClientId,
                              @NotNull String username,
                              @NotNull String password,
                              @NotNull String realm){}