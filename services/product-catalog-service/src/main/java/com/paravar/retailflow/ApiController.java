package com.paravar.retailflow;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @Value("${app.name}")
    String name;

    @GetMapping()
    public String get() {
        return "Hello"+name;
    }
}
