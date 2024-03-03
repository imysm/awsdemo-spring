package com.imysm.awsdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    private static final String template = "Hello, %s!";

    @GetMapping("/health")
    public HealthCheck health(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new HealthCheck(String.format(template, name));
    }
}
