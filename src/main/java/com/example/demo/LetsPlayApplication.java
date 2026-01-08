package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class LetsPlayApplication {

	public static void main(String[] args) {
		SpringApplication.run(LetsPlayApplication.class, args);
	}
	@GetMapping("/hello")
	public Greeting gReeeting(@RequestParam(defaultValue = "world",value="name") String param){
		return new Greeting(String.format("Hello, %s",param));
	}
	public record Greeting(String para){};



}
