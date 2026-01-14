package com.example.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class Controllers {
    @GetMapping("/products")
	public  Product GetProducts(){
		return new Product();
	}
	public record Product(){};
}
