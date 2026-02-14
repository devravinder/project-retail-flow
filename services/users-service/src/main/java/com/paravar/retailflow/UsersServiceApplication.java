package com.paravar.retailflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class UsersServiceApplication {
    static void main(String[] args) {
        SpringApplication.run(UsersServiceApplication.class, args);
    }
}
