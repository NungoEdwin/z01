package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Product;
import com.example.demo.services.ProductService;
@RestController
public class Controllers {
	 @Autowired
    ProductService productService;
    @GetMapping("/products")
	public List<Product> GetProducts(){
		return productService.getAll();
	}
	
}
