package com.nexus.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, Object> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Welcome to Nexus Artifact Management Demo Application!");
        response.put("status", "running");
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("endpoints", new String[]{
            "/", "/version", "/health", "/info", "/status"
        });
        return response;
    }

    @GetMapping("/version")
    public Map<String, Object> version() {
        Map<String, Object> response = new HashMap<>();
        response.put("application", "Nexus Web Application");
        response.put("version", "1.0.0-SNAPSHOT");
        response.put("buildTime", LocalDateTime.now().toString());
        response.put("javaVersion", System.getProperty("java.version"));
        return response;
    }

    @GetMapping("/health")
    public Map<String, Object> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("application", "Nexus Web Application");
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("uptime", "Application is running");
        return response;
    }

    @GetMapping("/info")
    public Map<String, Object> info() {
        Map<String, Object> response = new HashMap<>();
        response.put("project", "Nexus Artifact Management System");
        response.put("description", "Spring Boot application for Nexus demo");
        response.put("framework", "Spring Boot 2.7.14");
        response.put("packaging", "WAR");
        response.put("features", new String[]{
            "Maven artifact publishing",
            "Docker integration",
            "CI/CD pipeline",
            "Security & RBAC"
        });
        return response;
    }

    @GetMapping("/status")
    public Map<String, Object> status() {
        Map<String, Object> response = new HashMap<>();
        response.put("server", "Tomcat");
        response.put("port", 8080);
        response.put("status", "ACTIVE");
        response.put("memory", Runtime.getRuntime().totalMemory() / (1024 * 1024) + " MB");
        response.put("processors", Runtime.getRuntime().availableProcessors());
        return response;
    }
}
