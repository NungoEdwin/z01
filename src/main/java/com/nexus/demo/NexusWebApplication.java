package com.nexus.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class NexusWebApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(NexusWebApplication.class, args);
    }
}
